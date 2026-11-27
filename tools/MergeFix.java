import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Replays a hand fix onto changed decompiler output (a small 3-way merge).
 * The edit from <orig> to <fixed> is split into hunks (line diff); each hunk is located in <new> by its
 * removed lines plus surrounding context, which must match exactly once. If every hunk lands, the merged
 * file is written to <out> and the exit code is 0; otherwise nothing is written and the exit code is 1.
 *
 * Usage: java tools/MergeFix.java <orig> <fixed> <new> <out>
 */
public class MergeFix {
    record Hunk(int origStart, List<String> removed, List<String> added) {}

    public static void main(String[] a) throws Exception {
        List<String> orig = lines(a[0]), fixed = lines(a[1]), cur = lines(a[2]);
        List<Hunk> hunks = new ArrayList<>();
        // a hunk replacing N lines with N lines is split into single-line edits: neighbouring field
        // declarations changed together may no longer be neighbours in the new output
        for (Hunk hk : diff(orig, fixed)) {
            if (hk.removed.size() > 1 && hk.removed.size() == hk.added.size())
                for (int k = 0; k < hk.removed.size(); k++)
                    hunks.add(new Hunk(hk.origStart + k, List.of(hk.removed.get(k)), List.of(hk.added.get(k))));
            else hunks.add(hk);
        }
        List<String> out = new ArrayList<>(cur);
        // a line the fix changed everywhere it occurs (e.g. "final" dropped from same-named fields in two
        // nested classes) is changed everywhere in the new output too, whatever its position
        Map<String, List<Hunk>> byLine = new LinkedHashMap<>();
        for (Hunk hk : hunks)
            if (hk.removed.size() == 1 && hk.added.size() == 1) byLine.computeIfAbsent(hk.removed.get(0) + "\u0000" + hk.added.get(0), k -> new ArrayList<>()).add(hk);
        for (Map.Entry<String, List<Hunk>> e : byLine.entrySet()) {
            String from = e.getValue().get(0).removed.get(0), to = e.getValue().get(0).added.get(0);
            long inOrig = orig.stream().filter(from::equals).count();
            if (inOrig < 2 || inOrig != e.getValue().size()) continue;
            if (out.stream().filter(from::equals).count() != inOrig) continue;
            out.replaceAll(s -> s.equals(from) ? to : s);
            hunks.removeAll(e.getValue());
        }
        // apply bottom-up so earlier positions stay valid
        List<int[]> placed = new ArrayList<>();
        for (int h = hunks.size() - 1; h >= 0; h--) {
            Hunk hk = hunks.get(h);
            int pos = -1;
            for (int ctx = 3; ctx >= 0 && pos < 0; ctx--) {
                List<String> before = orig.subList(Math.max(0, hk.origStart - ctx), hk.origStart);
                int endOrig = hk.origStart + hk.removed.size();
                List<String> after = orig.subList(endOrig, Math.min(orig.size(), endOrig + ctx));
                if (before.isEmpty() && after.isEmpty() && hk.removed.isEmpty()) break;
                List<String> needle = new ArrayList<>(before);
                needle.addAll(hk.removed);
                needle.addAll(after);
                int found = find(out, needle);
                if (found >= 0) pos = found + before.size();
            }
            if (pos < 0) { System.out.println("MergeFix: hunk at line " + (hk.origStart + 1) + " of " + a[0] + " did not land"); System.exit(1); }
            for (int k = 0; k < hk.removed.size(); k++) out.remove(pos);
            out.addAll(pos, hk.added);
        }
        Files.writeString(Paths.get(a[3]), String.join("\n", out), StandardCharsets.UTF_8);
    }

    static List<String> lines(String p) throws Exception {
        return new ArrayList<>(Arrays.asList(Files.readString(Paths.get(p), StandardCharsets.UTF_8).split("\n", -1)));
    }

    /** index of the only occurrence of needle in hay, or -1 if absent or ambiguous */
    static int find(List<String> hay, List<String> needle) {
        if (needle.isEmpty()) return -1;
        int hit = -1;
        for (int i = 0; i + needle.size() <= hay.size(); i++) {
            if (!hay.subList(i, i + needle.size()).equals(needle)) continue;
            if (hit >= 0) return -1;
            hit = i;
        }
        return hit;
    }

    /** LCS line diff, grouped into hunks of consecutive changes */
    static List<Hunk> diff(List<String> x, List<String> y) {
        int n = x.size(), m = y.size();
        int[][] l = new int[n + 1][m + 1];
        for (int i = n - 1; i >= 0; i--)
            for (int j = m - 1; j >= 0; j--)
                l[i][j] = x.get(i).equals(y.get(j)) ? l[i + 1][j + 1] + 1 : Math.max(l[i + 1][j], l[i][j + 1]);
        List<Hunk> hunks = new ArrayList<>();
        int i = 0, j = 0;
        while (i < n || j < m) {
            if (i < n && j < m && x.get(i).equals(y.get(j))) { i++; j++; continue; }
            int start = i;
            List<String> rem = new ArrayList<>(), add = new ArrayList<>();
            while ((i < n || j < m) && !(i < n && j < m && x.get(i).equals(y.get(j)))) {
                if (j < m && (i == n || l[i][j + 1] >= l[i + 1][j])) add.add(y.get(j++));
                else rem.add(x.get(i++));
            }
            hunks.add(new Hunk(start, rem, add));
        }
        return hunks;
    }
}
