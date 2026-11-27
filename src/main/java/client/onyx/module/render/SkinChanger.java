package client.onyx.module.render;

import client.onyx.Onyx;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.hud.notification.NotificationType;
import client.onyx.setting.impl.ButtonSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.TextSetting;
import client.onyx.skin.MojangSkinLookup;
import client.onyx.skin.SkinOverride;
import client.onyx.util.ChatUtil;
import client.onyx.util.FileDialog;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public final class SkinChanger extends Module {
    private static final ResourceLocation resourceLocation = new ResourceLocation("onyx", "skins/local");
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor(var0 -> {
        Thread var10000 = new Thread(var0, "onyx-skin-loader");
        var10000.setDaemon(true);
        return var10000;
    });
    public EnumSetting<SkinChanger.SkinSource> source = new EnumSetting<>("Source", SkinChanger.SkinSource.LOCAL_FILE);
    public TextSetting skinFile = new TextSetting("Skin file", "")
        .describe("Full path to a 64x64 or 64x32 skin PNG")
        .visibleWhen(this.source, var0 -> var0 == SkinChanger.SkinSource.LOCAL_FILE);
    public ButtonSetting browse = new ButtonSetting(
            "Browse", () -> FileDialog.method_2181("Select a skin PNG", this.skinFile.getValue(), this.skinFile::setValue)
        )
        .method_82("Browse…")
        .describe("Or paste the path into the field above")
        .visibleWhen(() -> this.source.method_168(SkinChanger.SkinSource.LOCAL_FILE));
    public TextSetting playerName = new TextSetting("Player name", "").visibleWhen(this.source, var0 -> var0 == SkinChanger.SkinSource.PLAYER_NAME);
    public EnumSetting<SkinChanger.ModelType> armWidth = new EnumSetting<>("Arm width", SkinChanger.ModelType.DEFAULT)
        .describe("Slim (Alex) vs default (Steve) - can't be detected from a bare local PNG")
        .visibleWhen(this.source, var0 -> var0 == SkinChanger.SkinSource.LOCAL_FILE);
    public ButtonSetting apply = new ButtonSetting("Apply", this::method_1098).method_82("Apply skin");

    @Override
    public void onEnable() {
        this.method_1098();
    }

    private static void method_1094(String var0, NotificationType var1) {
        ChatUtil.method_2112("SkinChanger", var0, var1);
    }

    private void method_1095(BufferedImage var1, String var2, String var3) {
        BufferedImage var5;
        if ((var5 = new ImageBufferDownload().parseUserSkin(var1)) == null) {
            method_1094("Not a valid skin image", NotificationType.ERROR);
        } else {
            mc.addScheduledTask(() -> {
                mc.getTextureManager().deleteTexture(resourceLocation);
                mc.getTextureManager().loadTexture(resourceLocation, new DynamicTexture(var5));
                if (this.isToggled()) {
                    SkinOverride.method_267(resourceLocation, var2);
                    method_1094(var3, NotificationType.SUCCESS);
                }
            });
        }
    }

    private void method_1096(String var1) {
        executorService.submit(
            () -> {
                BufferedImage var11;
                try {
                    var11 = ImageIO.read(new File(var1));
                } catch (IOException var10) {
                    Onyx.LOGGER.warn("SkinChanger: failed to read '{}'", var1, var10);
                    method_1094("Can't read that file", NotificationType.ERROR);
                    return;
                }

                if (var11 == null) {
                    method_1094("Not a readable PNG", NotificationType.ERROR);
                } else if (var11.getWidth() == 64 && (var11.getHeight() == 64 || var11.getHeight() == 32)) {
                    SkinChanger var7;
                    BufferedImage var8;
                    String var9;
                    if (!this.armWidth.method_168(SkinChanger.ModelType.SLIM)) {
                        var9 = "default";
                        var8 = var11;
                        var7 = this;
                    } else {
                        var9 = "slim";
                        var8 = var11;
                        var7 = this;
                    }

                    var7.method_1095(var8, var9, "Skin applied");
                } else {
                    method_1094(
                        new StringBuilder()
                            .insert(0, "Skin must be 64x64 or 64x32, not ")
                            .append(var11.getWidth())
                            .append("x")
                            .append(var11.getHeight())
                            .toString(),
                        NotificationType.ERROR
                    );
                }
            }
        );
    }

    private void method_1097(String var1) {
        MojangSkinLookup.method_262(
            var1,
            var2 -> this.method_1095(var2.bufferedImage(), var2.string(), new StringBuilder().insert(0, "Wearing ").append(var1).append("'s skin").toString()),
            var0 -> {
                Onyx.LOGGER.warn("SkinChanger: {}", var0);
                method_1094(var0, NotificationType.ERROR);
            }
        );
    }

    private void method_1098() {
        if (this.source.method_168(SkinChanger.SkinSource.LOCAL_FILE)) {
            if (this.skinFile.getValue().isEmpty()) {
                method_1094("Pick a skin PNG first", NotificationType.WARNING);
            } else {
                this.method_1096(this.skinFile.getValue());
            }
        } else if (this.playerName.getValue().isEmpty()) {
            method_1094("Type a player name first", NotificationType.WARNING);
        } else {
            this.method_1097(this.playerName.getValue());
        }
    }

    @Override
    public void onDisable() {
        SkinOverride.method_268();
    }

    public SkinChanger() {
        super("SkinChanger", "Overrides your own skin texture locally", Category.RENDER);
        this.armWidth.onChange(var1 -> {
            if (this.isToggled() && this.source.method_168(SkinChanger.SkinSource.LOCAL_FILE) && SkinOverride.method_266()) {
                ResourceLocation var7 = SkinOverride.getResourceLocation();
                ResourceLocation var3;
                String var4;
                if (var1 != SkinChanger.ModelType.SLIM) {
                    ResourceLocation var2 = var7;
                    var4 = "default";
                    var3 = var2;
                } else {
                    var4 = "slim";
                    var3 = var7;
                }

                SkinOverride.method_267(var3, var4);
            }
        });
    }

    public static enum ModelType {
        DEFAULT,
        SLIM;
    }

    public static enum SkinSource {
        LOCAL_FILE,
        PLAYER_NAME;
    }
}
