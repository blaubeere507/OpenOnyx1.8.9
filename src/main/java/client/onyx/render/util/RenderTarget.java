package client.onyx.render.util;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

public final class RenderTarget {
    private int intValue4 = -1;
    private int intValue = -1;
    private int intValue3 = -1;
    private final boolean flag;
    private int intValue2;
    private int intValue5;

    public int getIntValue2() {
        return this.intValue2;
    }

    public void method_529() {
        this.method_530();
        GlStateManager.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
        GlStateManager.clearDepth(1.0);
        short var1;
        if (this.flag) {
            var1 = 16640;
        } else {
            var1 = 16384;
        }

        GlStateManager.clear(var1);
    }

    public int getIntValue4() {
        return this.intValue4;
    }

    public void method_530() {
        OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.intValue);
        GlStateManager.viewport(0, 0, this.intValue5, this.intValue2);
    }

    public RenderTarget(boolean var1) {
        this.flag = var1;
    }

    public int getIntValue5() {
        return this.intValue5;
    }

    public boolean method_531(int var1, int var2) {
        var1 = Math.max(1, var1);
        int var5 = Math.max(1, var2);
        if (this.intValue4 != -1 && this.intValue5 == var1 && this.intValue2 == var5) {
            return false;
        } else {
            this.method_532();
            this.intValue4 = TextureUtil.glGenTextures();
            GlStateManager.bindTexture(this.intValue4);
            GL11.glTexImage2D(3553, 0, 32856, var1, var5, 0, 6408, 5121, (ByteBuffer)null);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
            this.intValue = OpenGlHelper.glGenFramebuffers();
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.intValue);
            OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, this.intValue4, 0);
            if (this.flag) {
                this.intValue3 = OpenGlHelper.glGenRenderbuffers();
                OpenGlHelper.glBindRenderbuffer(OpenGlHelper.GL_RENDERBUFFER, this.intValue3);
                OpenGlHelper.glRenderbufferStorage(OpenGlHelper.GL_RENDERBUFFER, 33190, var1, var5);
                OpenGlHelper.glFramebufferRenderbuffer(
                    OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT, OpenGlHelper.GL_RENDERBUFFER, this.intValue3
                );
            }

            int var3 = OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER);
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
            if (var3 != OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
                this.method_532();
                throw new IllegalStateException(new StringBuilder().insert(0, "Could not create a hand effect target, status ").append(var3).toString());
            } else {
                this.intValue5 = var1;
                this.intValue2 = var5;
                return true;
            }
        }
    }

    public void method_532() {
        if (this.intValue != -1) {
            OpenGlHelper.glDeleteFramebuffers(this.intValue);
            this.intValue = -1;
        }

        if (this.intValue3 != -1) {
            OpenGlHelper.glDeleteRenderbuffers(this.intValue3);
            this.intValue3 = -1;
        }

        if (this.intValue4 != -1) {
            TextureUtil.deleteTexture(this.intValue4);
            this.intValue4 = -1;
        }

        this.intValue2 = this.intValue5 = 0;
    }
}
