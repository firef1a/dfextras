package dev.fire.dfextras.screen;

import dev.fire.dfextras.Mod;
import dev.fire.dfextras.config.configScreen.Config;
import dev.fire.dfextras.config.overlayScreen.OverlayConfig;
import dev.fire.dfextras.devutils.ColorUtils;
import dev.fire.dfextras.screen.screens.OverlayScreen;
import dev.fire.dfextras.screen.utils.RenderObject;
import dev.fire.dfextras.screen.utils.overlay.containers.PlotInfoOverlay;
import dev.fire.dfextras.screen.utils.overlay.containers.SupportInfoOverlay;
import dev.fire.dfextras.server.ServerManager;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.ArrayList;

public class OverlayManager {
    public static int WHITE = Color.white.getRGB();
    public static int DARK_GRAY = Color.darkGray.getRGB();

    private static final TextRenderer textRenderer = Mod.MC.textRenderer;
    private ArrayList<RenderObject> renderObjectList;

    public PlotInfoOverlay plotInfoOverlay;
    public SupportInfoOverlay supportInfoOverlay;

    public OverlayManager() {
        renderObjectList = new ArrayList<>();

        plotInfoOverlay = OverlayConfig.getConfig().plotInfoOverlay;
        renderObjectList.add(plotInfoOverlay);

        supportInfoOverlay = OverlayConfig.getConfig().supportInfoOverlay;
        renderObjectList.add(supportInfoOverlay);
    }

    public void onRender(DrawContext context, float tickDelta, TextRenderer textRenderer, CallbackInfo ci) {
        if (!Config.getConfig().CanSeeHeaderAndFootersTabList) {
            Mod.MC.inGameHud.getPlayerListHud().setFooter(Text.literal("Headers/Footers are currently disabled").withColor(ColorUtils.DARK_GRAY).styled(style -> style.withItalic(true)));
            Mod.MC.inGameHud.getPlayerListHud().setHeader(Text.empty());
        }
        if (ServerManager.isPlayingDiamondfire()) {
            for (RenderObject object : renderObjectList) {
                object.update();
                if (!OverlayScreen.isOverlayScreenOpen()) { object.render(context, textRenderer); }

            }
        }
    }

    public ArrayList<RenderObject> getRenderList() {
        return renderObjectList;
    }

}
