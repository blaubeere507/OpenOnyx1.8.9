import com.sun.source.tree.*;
import com.sun.source.util.*;

import javax.lang.model.type.*;
import javax.lang.model.util.Types;
import javax.tools.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * Declares local variables that Vineflower assigns but never declares ("var8 = switch (...) {...}").
 * Uses javac itself: every source is attributed, "cannot find symbol" diagnostics for varN identifiers are
 * collected per method, the static type of each assignment's right-hand side is asked from the compiler, and
 * "<Type> varN;" is inserted at the top of the enclosing method body.
 *
 * Usage: java tools/DeclareMissing.java <source root> <classpath>
 */
public class DeclareMissing {
    public static void main(String[] a) throws Exception {
        Path root = Paths.get(a[0]);
        List<Path> files;
        try (Stream<Path> s = Files.walk(root)) { files = s.filter(p -> p.toString().endsWith(".java")).toList(); }
        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diags = new DiagnosticCollector<>();
        StandardJavaFileManager fm = jc.getStandardFileManager(diags, Locale.ENGLISH, StandardCharsets.UTF_8);
        JavacTask task = (JavacTask) jc.getTask(null, fm, diags,
                List.of("-proc:none", "-cp", a[1], "-Xmaxerrs", "100000", "-nowarn", "-encoding", "UTF-8"), null,
                fm.getJavaFileObjectsFromPaths(files));
        Iterable<? extends CompilationUnitTree> units = task.parse();
        task.analyze();
        Trees trees = Trees.instance(task);
        Types types = task.getTypes();

        // undeclared identifiers by file -> positions
        Map<String, Set<Long>> missing = new HashMap<>();
        for (Diagnostic<? extends JavaFileObject> d : diags.getDiagnostics()) {
            if (d.getKind() != Diagnostic.Kind.ERROR || d.getSource() == null) continue;
            String msg = d.getMessage(Locale.ENGLISH);
            if (!msg.startsWith("cannot find symbol") || !msg.matches("(?s).*symbol:\\s+variable var\\d+x?.*")) continue;
            missing.computeIfAbsent(d.getSource().toUri().getPath(), k -> new HashSet<>()).add(d.getStartPosition());
        }
        int declared = 0, filesChanged = 0;
        for (CompilationUnitTree cu : units) {
            String path = cu.getSourceFile().toUri().getPath();
            Set<Long> positions = missing.get(path);
            if (positions == null) continue;
            SourcePositions sp = trees.getSourcePositions();
            // method body start offset -> (var name -> type)
            Map<Long, Map<String, TypeMirror>> perMethod = new TreeMap<>(Comparator.reverseOrder());
            new TreePathScanner<Void, Void>() {
                // innermost block that can hold the declaration: a method body or a block-bodied lambda
                // (a variable assigned inside a lambda must be declared inside that lambda)
                Tree scope;
                @Override public Void visitMethod(MethodTree m, Void v) {
                    Tree prev = scope;
                    scope = m.getBody();
                    super.visitMethod(m, v);
                    scope = prev;
                    return null;
                }
                @Override public Void visitLambdaExpression(LambdaExpressionTree l, Void v) {
                    Tree prev = scope;
                    if (l.getBodyKind() == LambdaExpressionTree.BodyKind.STATEMENT) scope = l.getBody();
                    super.visitLambdaExpression(l, v);
                    scope = prev;
                    return null;
                }
                @Override public Void visitIdentifier(IdentifierTree id, Void v) {
                    if (scope != null && positions.contains(sp.getStartPosition(cu, id)))
                        perMethod.computeIfAbsent(sp.getStartPosition(cu, scope), k -> new LinkedHashMap<>())
                                 .putIfAbsent(id.getName().toString(), null);
                    return super.visitIdentifier(id, v);
                }
                @Override public Void visitAssignment(AssignmentTree as, Void v) {
                    if (scope != null && as.getVariable() instanceof IdentifierTree id
                            && positions.contains(sp.getStartPosition(cu, id))) {
                        TypeMirror t = trees.getTypeMirror(new TreePath(getCurrentPath(), as.getExpression()));
                        Map<String, TypeMirror> m = perMethod.computeIfAbsent(sp.getStartPosition(cu, scope), k -> new LinkedHashMap<>());
                        String name = id.getName().toString();
                        m.put(name, merge(types, m.get(name), t));
                    }
                    return super.visitAssignment(as, v);
                }
            }.scan(cu, null);
            String src = Files.readString(Paths.get(cu.getSourceFile().toUri()), StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder(src);
            boolean changed = false;
            for (Map.Entry<Long, Map<String, TypeMirror>> e : perMethod.entrySet()) { // reverse order keeps offsets valid
                StringBuilder decl = new StringBuilder();
                for (Map.Entry<String, TypeMirror> v : e.getValue().entrySet()) {
                    String type = typeName(types, v.getValue());
                    if (type == null) continue;
                    decl.append("\n        ").append(type).append(' ').append(v.getKey()).append("; // declared by DeclareMissing");
                    declared++;
                }
                if (decl.length() == 0) continue;
                int brace = (int) (long) e.getKey(); // offset of the '{' of the method body
                sb.insert(brace + 1, decl);
                changed = true;
            }
            if (changed) { Files.writeString(Paths.get(cu.getSourceFile().toUri()), sb.toString(), StandardCharsets.UTF_8); filesChanged++; }
        }
        System.out.println("DeclareMissing: " + declared + " declarations added in " + filesChanged + " files");
    }

    static TypeMirror merge(Types types, TypeMirror a, TypeMirror b) {
        if (b == null || b.getKind() == TypeKind.ERROR || b.getKind() == TypeKind.NULL) return a;
        if (a == null) return b;
        if (types.isAssignable(b, a)) return a;
        if (types.isAssignable(a, b)) return b;
        // least common supertype along a's superclass chain (e.g. two Account subclasses -> Account)
        for (TypeMirror s = a; s != null; ) {
            if (types.isAssignable(b, s)) return s;
            List<? extends TypeMirror> sup = types.directSupertypes(s);
            s = sup.isEmpty() ? null : sup.get(0);
        }
        return a;
    }

    static String typeName(Types types, TypeMirror t) {
        if (t == null || t.getKind() == TypeKind.ERROR || t.getKind() == TypeKind.NULL || t.getKind() == TypeKind.VOID) return null;
        if (t.getKind() == TypeKind.INTERSECTION) t = ((IntersectionType) t).getBounds().get(0);
        String s = t.toString();
        if (s.contains("capture#") || s.contains("?") || s.contains("<any>")) s = types.erasure(t).toString();
        return s;
    }
}
