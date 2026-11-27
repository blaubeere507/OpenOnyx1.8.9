package client.onyx.module.render;

import client.onyx.event.impl.PostTickEvent;
import client.onyx.module.Category;
import client.onyx.module.Module;
import client.onyx.module.hud.notification.NotificationType;
import client.onyx.render.texture.CapeTexture;
import client.onyx.render.texture.ImageFit;
import client.onyx.setting.impl.ButtonSetting;
import client.onyx.setting.impl.ColorSetting;
import client.onyx.setting.impl.EnumSetting;
import client.onyx.setting.impl.NumberSetting;
import client.onyx.setting.impl.TextSetting;
import client.onyx.ui.screen.CapeEditorScreen;
import client.onyx.util.ChatUtil;
import client.onyx.util.FileDialog;
import java.awt.image.BufferedImage;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.ResourceLocation;

public final class CapeChanger extends Module {
    public EnumSetting<CapeChanger.CapeSource> source = new EnumSetting<>("Source", CapeChanger.CapeSource.ONYX_LOGO)
        .describe("The built-in Onyx logo cape, or a PNG off your disk");
    public TextSetting capeFile = new TextSetting("Cape file", "")
        .describe("Full path to a cape PNG - any resolution")
        .visibleWhen(this.source, var0 -> var0 == CapeChanger.CapeSource.LOCAL_FILE);
    public ButtonSetting browse = new ButtonSetting(
            "Browse", () -> FileDialog.method_2181("Select a cape PNG", this.capeFile.getValue(), this.capeFile::setValue)
        )
        .method_82("Browse…")
        .describe("Or paste the path into the field above")
        .visibleWhen(() -> this.source.method_168(CapeChanger.CapeSource.LOCAL_FILE));
    public ButtonSetting apply = new ButtonSetting("Apply", this::method_1109)
        .method_82("Apply cape")
        .visibleWhen(() -> this.source.method_168(CapeChanger.CapeSource.LOCAL_FILE));
    public EnumSetting<ImageFit.FitMode> fit = new EnumSetting<>("Fit", ImageFit.FitMode.AUTO)
        .describe("Fit shows the whole picture on a background, Fill crops it to the cape, Texture takes the PNG as a finished cape sheet")
        .visibleWhen(() -> this.source.method_168(CapeChanger.CapeSource.LOCAL_FILE))
        .onChange(var1 -> this.method_1112());
    public ColorSetting background = new ColorSetting("Background", -15329507)
        .method_117()
        .describe("What fills the cape where the picture does not reach")
        .visibleWhen(() -> this.source.method_168(CapeChanger.CapeSource.LOCAL_FILE) && !this.fit.method_168(ImageFit.FitMode.TEXTURE))
        .onChange(var1 -> this.method_1112());
    public NumberSetting zoom = new NumberSetting("Zoom", 100.0, 10.0, 400.0, 5.0)
        .method_147("%")
        .visibleWhen(() -> this.source.method_168(CapeChanger.CapeSource.LOCAL_FILE) && !this.fit.method_168(ImageFit.FitMode.TEXTURE))
        .onChange(var1 -> this.method_1112());
    public NumberSetting offsetX = new NumberSetting("Offset X", 0.0, -100.0, 100.0, 1.0)
        .method_147("%")
        .visibleWhen(() -> this.source.method_168(CapeChanger.CapeSource.LOCAL_FILE) && !this.fit.method_168(ImageFit.FitMode.TEXTURE))
        .onChange(var1 -> this.method_1112());
    public NumberSetting offsetY = new NumberSetting("Offset Y", 0.0, -100.0, 100.0, 1.0)
        .method_147("%")
        .visibleWhen(() -> this.source.method_168(CapeChanger.CapeSource.LOCAL_FILE) && !this.fit.method_168(ImageFit.FitMode.TEXTURE))
        .onChange(var1 -> this.method_1112());
    public ButtonSetting edit = new ButtonSetting("Edit", () -> mc.displayGuiScreen(new CapeEditorScreen(mc.currentScreen)))
        .method_82("Edit cape…")
        .describe("Drag the picture around the cape and scroll to zoom")
        .visibleWhen(() -> this.source.method_168(CapeChanger.CapeSource.LOCAL_FILE) && !this.fit.method_168(ImageFit.FitMode.TEXTURE));
    private String string = "";
    private BufferedImage bufferedImage;
    private boolean flag;
    private ResourceLocation resourceLocation;
    private int intValue;

    public void method_1108(boolean var1) {
        if (this.flag != var1) {
            this.flag = var1;
            if (!var1) {
                this.method_1112();
            }
        }
    }

    private void method_1109() {
        if (this.source.method_168(CapeChanger.CapeSource.ONYX_LOGO)) {
            CapeTexture.method_662(var1 -> {
                this.method_1111(var1);
                ChatUtil.method_2112("CapeChanger", "Onyx cape applied", NotificationType.SUCCESS);
            }, var1 -> ChatUtil.method_2112("CapeChanger", var1, NotificationType.ERROR));
        } else if (this.capeFile.getValue().isEmpty()) {
            ChatUtil.method_2112("CapeChanger", "Pick a cape PNG first", NotificationType.WARNING);
        } else if (this.bufferedImage != null && this.string.equals(this.capeFile.getValue())) {
            this.method_1112();
        } else {
            CapeTexture.method_663(this.capeFile.getValue(), var1 -> {
                this.bufferedImage = var1;
                this.string = this.capeFile.getValue();
                CapeTexture.method_661(var1);
                this.method_1112();
                ChatUtil.method_2112("CapeChanger", "Cape applied", NotificationType.SUCCESS);
            }, var1 -> ChatUtil.method_2112("CapeChanger", var1, NotificationType.ERROR));
        }
    }

    @Override
    public void onEnable() {
        this.method_1109();
    }

    private void method_1110() {
        if (this.resourceLocation != null && mc.thePlayer != null) {
            mc.thePlayer.setLocationOfCape(this.resourceLocation);
        }
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    private void method_1111(ResourceLocation var1) {
        this.resourceLocation = var1;
        if (this.isToggled()) {
            this.method_1110();
        }
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer != null) {
            mc.thePlayer.setLocationOfCape(null);
        }

        this.resourceLocation = null;
    }

    @EventHandler
    public void onPostTick(PostTickEvent var1) {
        this.method_1110();
    }

    public CapeChanger() {
        super("CapeChanger", "Overrides your own cape texture locally", Category.RENDER);
        this.source.onChange(var1 -> {
            if (this.isToggled()) {
                this.method_1109();
            }
        });
    }

    public void method_1112() {
        if (!this.flag) {
            if (this.isToggled() && this.source.method_168(CapeChanger.CapeSource.LOCAL_FILE) && this.bufferedImage != null) {
                int var2 = ++this.intValue;
                CapeTexture.method_664(
                    this.bufferedImage,
                    this.fit.getValue(),
                    this.background.getValue(),
                    this.zoom.getValue() / 100.0,
                    this.offsetX.getValue() / 100.0,
                    this.offsetY.getValue() / 100.0,
                    var2x -> {
                        if (var2 == this.intValue) {
                            this.method_1111(var2x);
                        }
                    }
                );
            }
        }
    }

    public static enum CapeSource {
        ONYX_LOGO,
        LOCAL_FILE;
    }
}
