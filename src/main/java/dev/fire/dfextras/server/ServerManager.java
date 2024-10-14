package dev.fire.dfextras.server;

import dev.fire.dfextras.Mod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.*;
import net.minecraft.util.math.Vec3d;

public class ServerManager {
    private static final MinecraftClient instance = Mod.MC;

    public static boolean isPlayingDiamondfire() {
        ServerInfo server = instance.getCurrentServerEntry();

        if (server == null) return false;
        String address = server.address;
        return address.contains("mcdiamondfire.com") || address.contains("54.39.29.75") || address.contains("51.222.245.178");
    }

    public static Vec3d getPlayerPosition() {
        ClientPlayerEntity clientPlayerEntity = instance.player;
        if (clientPlayerEntity == null) return null;

        return clientPlayerEntity.getPos();
    }


}
