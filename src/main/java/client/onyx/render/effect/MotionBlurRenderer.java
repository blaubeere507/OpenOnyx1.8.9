package client.onyx.render.effect;

import client.onyx.Onyx;
import client.onyx.render.shader.ShaderProgram;
import client.onyx.render.util.RenderTarget;
import client.onyx.util.IMinecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public final class MotionBlurRenderer implements IMinecraft {
    private static final RenderTarget renderTarget = new RenderTarget(false);
    private static ShaderProgram shaderProgram;
    private static boolean flag;
    private static boolean flag2;
    private static boolean flag3;

    public static void method_709() {
        flag = false;
    }

    public static void method_710() {
        if (!flag2 && Onyx.moduleManager != null) {
            float var0;
            if ((var0 = Onyx.moduleManager.camera.method_1102()) <= 0.0F) {
                flag = false;
            } else if (PostProcessing.method_538()) {
                if (!flag3) {
                    flag3 = true;
                    shaderProgram = ShaderProgram.method_814("onyx_motion_blur", "onyx_post", "onyx_motion_blur");
                }

                if (shaderProgram != null && shaderProgram.isFlag()) {
                    int var1 = PostProcessing.method_534();
                    int var2 = PostProcessing.method_536();
                    if (var1 > 0 && var2 > 0) {
                        try {
                            if (renderTarget.method_531(var1, var2)) {
                                flag = false;
                            }

                            if (flag) {
                                PostProcessing.method_535(shaderProgram, var1x -> {
                                    HandMaskRenderer.method_359(1, renderTarget.getIntValue4());
                                    var1x.method_815("Sampler1", 1);
                                    var1x.method_821("uAmount", var0);
                                    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
                                });
                                HandMaskRenderer.method_359(1, 0);
                                GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
                            }

                            GlStateManager.bindTexture(renderTarget.getIntValue4());
                            GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, var1, var2);
                            GlStateManager.bindTexture(0);
                            flag = true;
                        } catch (Throwable var4) {
                            Onyx.LOGGER.error("Motion blur disabled", var4);
                            flag2 = true;
                            flag = false;
                            renderTarget.method_532();
                        }
                    }
                }
            }
        }
    }

    private MotionBlurRenderer() {
    }
}
