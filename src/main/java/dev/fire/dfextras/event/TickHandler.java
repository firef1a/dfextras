package dev.fire.dfextras.event;

import dev.fire.dfextras.Mod;
import dev.fire.dfextras.config.overlayScreen.OverlayConfig;
import dev.fire.dfextras.server.ServerManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;

public class TickHandler {
    public int tickInt = 0;
    public TickHandler() {
        tickInt = 0;
    }
    public void onTick() {
        tickInt++;

        MinecraftClient instance = Mod.MC;
        ClientPlayerEntity client = instance.player;
        World world = instance.world;

        if (client == null) return;
        if (world == null) return;


        if (!ServerManager.isPlayingDiamondfire()) return;

        // do stuff?
        if (instance.getNetworkHandler() != null) ;
        //Mod.PLOT_MANAGER.requestPlotInfo();
        Mod.PLOT_MANAGER.onTick();
    }
}
