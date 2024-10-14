package dev.fire.sitemod.screen;

import dev.fire.sitemod.SiteMod;
import dev.fire.sitemod.config.configScreen.Config;
import dev.fire.sitemod.config.overlayScreen.OverlayConfig;
import dev.fire.sitemod.screen.screens.OverlayScreen;
import dev.fire.sitemod.screen.utils.RenderObject;
import dev.fire.sitemod.screen.utils.overlay.TextList;
import dev.fire.sitemod.screen.utils.overlay.containers.SitePlayerList;
import dev.fire.sitemod.server.ServerManager;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static dev.fire.sitemod.devutils.MathUtils.inRange;

public class OverlayManager {
    public static int WHITE = Color.white.getRGB();
    public static int DARK_GRAY = Color.darkGray.getRGB();

    public SitePlayerList playerListDisplay;

    private static final TextRenderer textRenderer = SiteMod.MC.textRenderer;

    private ArrayList<RenderObject> renderObjectList;

    public OverlayManager() {
        renderObjectList = new ArrayList<>();

        playerListDisplay = OverlayConfig.getConfig().PlayerListTextListObject;
        renderObjectList.add(playerListDisplay);
    }

    public ArrayList<RenderObject> getRenderList() {
        return renderObjectList;
    }

    public void onRender(DrawContext context, float tickDelta, TextRenderer textRenderer, CallbackInfo ci) {
        boolean isOnSite03 = ServerManager.isOnSite03(ServerManager.getPlayerPosition());

        if (!Config.getConfig().PlotHeaderFooterToggle) {
            SiteMod.MC.inGameHud.getPlayerListHud().setHeader(Text.empty());
            SiteMod.MC.inGameHud.getPlayerListHud().setFooter(Text.empty());
        }

        playerListDisplay.setEnabled(true);

        if (ServerManager.isPlayingDiamondfire()) {
            for (RenderObject object : renderObjectList) {
                object.update();
                if (!OverlayScreen.isOverlayScreenOpen()) { object.render(context, textRenderer); }

            }



        }
    }

    private void updateRecipeList(DrawContext context, TextRenderer textRenderer) {
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();

    }

}
