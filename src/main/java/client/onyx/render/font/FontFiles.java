package client.onyx.render.font;

import java.util.Locale;

public final class FontFiles {
    private static final GlyphAtlasFont[][] glyphAtlasFontArray = new GlyphAtlasFont[FontFiles.Size.values().length][FontFiles.Weight.values().length];
    public static final float FLOAT_16_01 = 16.0F;
    private static GlyphAtlasFont glyphAtlasFont;
    private static final String STRING_CONST = "/assets/onyx/font/ttf/";

    private FontFiles() {
    }

    public static GlyphAtlasFont method_624() {
        if (glyphAtlasFont == null) {
            glyphAtlasFont = GlyphAtlasFont.method_630("/assets/onyx/font/ttf/materialicons-regular.ttf", 16.0F);
        }

        return glyphAtlasFont;
    }

    public static GlyphAtlasFont method_625(FontFiles.Size var0, FontFiles.Weight var1) {
        GlyphAtlasFont var3;
        if ((var3 = glyphAtlasFontArray[var0.ordinal()][var1.ordinal()]) != null) {
            return var3;
        } else {
            GlyphAtlasFont var4 = GlyphAtlasFont.method_630(
                new StringBuilder()
                    .insert(0, "/assets/onyx/font/ttf/googlesansflex_")
                    .append(var0.getString())
                    .append("-")
                    .append(var1.name().toLowerCase(Locale.ROOT).replace("_", ""))
                    .append(".ttf")
                    .toString(),
                var0.getFloatValue()
            );
            glyphAtlasFontArray[var0.ordinal()][var1.ordinal()] = var4;
            return var4;
        }
    }

    public static enum Size {
        PT9("9pt", 18.0F),
        PT24("24pt", 24.0F),
        PT36("36pt", 36.0F),
        PT72("72pt", 54.0F),
        PT120("120pt", 144.0F);

        private final String string;
        private final float floatValue;

        public String getString() {
            return this.string;
        }

        private Size(String var3, float var4) {
            this.string = var3;
            this.floatValue = var4;
        }

        public float getFloatValue() {
            return this.floatValue;
        }
    }

    public static enum Weight {
        THIN,
        EXTRA_LIGHT,
        LIGHT,
        REGULAR,
        MEDIUM,
        SEMI_BOLD,
        BOLD,
        EXTRA_BOLD,
        BLACK;
    }
}
