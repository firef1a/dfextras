package dev.fire.sitemod.event;

import dev.fire.sitemod.SiteMod;
import dev.fire.sitemod.config.configScreen.Config;
import dev.fire.sitemod.config.overlayScreen.OverlayConfig;
import dev.fire.sitemod.server.ServerManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TickHandler {
    public void onTick() {
        MinecraftClient instance = SiteMod.MC;
        ClientPlayerEntity client = instance.player;
        World world = instance.world;

        if (client == null) return;
        if (world == null) return;

        if (!ServerManager.isPlayingDiamondfire()) return;

        Vec3d position = client.getPos();

        if (ServerManager.isOnSite03(position)) {
            //SiteMod.SERVER_MANAGER.isOnPlot = true;
            SiteMod.SERVER_MANAGER.updateLocalValues();
        } else {
            SiteMod.SERVER_MANAGER.isOnPlot = false;
            if (OverlayConfig.getConfig().PlayerListTextListObject.isElementRendered()) { SiteMod.SERVER_MANAGER.requestServerValues(); }
        }
    }
}
