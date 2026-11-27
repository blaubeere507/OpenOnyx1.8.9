package client.onyx.render.shader;

import client.onyx.Onyx;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.BufferUtils;

public final class ShaderProgram {
    private static final String STRING_CONST = "/assets/onyx/shaders/core/";
    private static final int INT_10241 = 1024;
    private final Map<String, Integer> map = new HashMap<>();
    private final FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(4);
    private final String string2;
    private final String string3;
    private final String string;
    private boolean flag;
    private int intValue;

    private int method_809(String var1) {
        if (!this.flag) {
            return -1;
        } else {
            Integer var3;
            if ((var3 = this.map.get(var1)) == null) {
                var3 = OpenGlHelper.glGetUniformLocation(this.intValue, var1);
                this.map.put(var1, var3);
            }

            return var3;
        }
    }

    public void method_810(String var1, float var2, float var3, float var4) {
        int var6;
        if ((var6 = this.method_809(var1)) >= 0) {
            this.floatBuffer.clear();
            this.floatBuffer.put(var2).put(var3).put(var4).flip();
            OpenGlHelper.glUniform3(var6, this.floatBuffer);
        }
    }

    private void method_811() throws IOException {
        if (!OpenGlHelper.shadersSupported) {
            Onyx.LOGGER.warn("Shaders unsupported, '{}' disabled", this.string2);
        } else {
            int var1 = this.method_823(OpenGlHelper.GL_VERTEX_SHADER, new StringBuilder().insert(0, this.string3).append(".vsh").toString());
            int var3 = this.method_823(OpenGlHelper.GL_FRAGMENT_SHADER, new StringBuilder().insert(0, this.string).append(".fsh").toString());
            if (var1 != 0 && var3 != 0) {
                this.intValue = OpenGlHelper.glCreateProgram();
                OpenGlHelper.glAttachShader(this.intValue, var1);
                OpenGlHelper.glAttachShader(this.intValue, var3);
                OpenGlHelper.glLinkProgram(this.intValue);
                OpenGlHelper.glDeleteShader(var1);
                OpenGlHelper.glDeleteShader(var3);
                if (OpenGlHelper.glGetProgrami(this.intValue, OpenGlHelper.GL_LINK_STATUS) == 0) {
                    Onyx.LOGGER.error("Failed to link shader '{}': {}", this.string2, OpenGlHelper.glGetProgramInfoLog(this.intValue, 1024));
                    OpenGlHelper.glDeleteProgram(this.intValue);
                    this.intValue = 0;
                } else {
                    this.flag = true;
                }
            } else {
                if (var1 != 0) {
                    OpenGlHelper.glDeleteShader(var1);
                }

                if (var3 != 0) {
                    OpenGlHelper.glDeleteShader(var3);
                }
            }
        }
    }

    public void method_812(String var1, float var2, float var3) {
        int var5;
        if ((var5 = this.method_809(var1)) >= 0) {
            this.floatBuffer.clear();
            this.floatBuffer.put(var2).put(var3).flip();
            OpenGlHelper.glUniform2(var5, this.floatBuffer);
        }
    }

    public void method_813(String var1, int var2) {
        this.method_810(var1, (var2 >> 16 & 0xFF) / 255.0F, (var2 >> 8 & 0xFF) / 255.0F, (var2 & 0xFF) / 255.0F);
    }

    public static ShaderProgram method_814(String var0, String var1, String var2) {
        ShaderProgram var5 = new ShaderProgram(var0, var1, var2);

        try {
            var5.method_811();
            return var5;
        } catch (Throwable var3) {
            Onyx.LOGGER.error("Failed to build shader '{}'", var0, var3);
            var5.flag = false;
            return var5;
        }
    }

    public void method_815(String var1, int var2) {
        int var4;
        if ((var4 = this.method_809(var1)) >= 0) {
            OpenGlHelper.glUniform1i(var4, var2);
        }
    }

    private static ByteBuffer method_816(String var0) throws IOException {
        String var1 = var0;
        InputStream var8 = ShaderProgram.class.getResourceAsStream(new StringBuilder().insert(0, "/assets/onyx/shaders/core/").append(var0).toString());

        ByteBuffer var4;
        try {
            if (var8 == null) {
                throw new IOException(new StringBuilder().insert(0, "Missing shader resource /assets/onyx/shaders/core/").append(var1).toString());
            }

            byte[] var2;
            ByteBuffer var3;
            (var3 = BufferUtils.createByteBuffer((var2 = var8.readAllBytes()).length)).put(var2);
            var3.flip();
            var4 = var3;
        } catch (Throwable var7) {
            if (var8 != null) {
                try {
                    var8.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                    throw var7;
                }

                throw var7;
            }

            throw var7;
        }

        if (var8 != null) {
            var8.close();
        }

        return var4;
    }

    public void method_817() {
        if (this.flag) {
            OpenGlHelper.glUseProgram(this.intValue);
        }
    }

    public void method_818(String var1, FloatBuffer var2) {
        int var4;
        if ((var4 = this.method_809(var1)) >= 0) {
            OpenGlHelper.glUniformMatrix4(var4, false, var2);
        }
    }

    public static void method_819() {
        if (OpenGlHelper.shadersSupported) {
            OpenGlHelper.glUseProgram(0);
        }
    }

    public static ShaderProgram method_820(String var0) {
        return method_814(var0, var0, var0);
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void method_821(String var1, float var2) {
        int var4;
        if ((var4 = this.method_809(var1)) >= 0) {
            this.floatBuffer.clear();
            this.floatBuffer.put(var2).flip();
            OpenGlHelper.glUniform1(var4, this.floatBuffer);
        }
    }

    public void method_822(String var1, float var2, float var3, float var4, float var5) {
        int var7;
        if ((var7 = this.method_809(var1)) >= 0) {
            this.floatBuffer.clear();
            this.floatBuffer.put(var2).put(var3).put(var4).put(var5).flip();
            OpenGlHelper.glUniform4(var7, this.floatBuffer);
        }
    }

    private int method_823(int var1, String var2) throws IOException {
        int var3;
        OpenGlHelper.glShaderSource(var3 = OpenGlHelper.glCreateShader(var1), method_816(var2));
        OpenGlHelper.glCompileShader(var3);
        if (OpenGlHelper.glGetShaderi(var3, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
            Onyx.LOGGER.error("Failed to compile '{}': {}", var2, OpenGlHelper.glGetShaderInfoLog(var3, 1024));
            OpenGlHelper.glDeleteShader(var3);
            return 0;
        } else {
            return var3;
        }
    }

    private ShaderProgram(String var1, String var2, String var3) {
        this.string2 = var1;
        this.string3 = var2;
        this.string = var3;
    }
}
