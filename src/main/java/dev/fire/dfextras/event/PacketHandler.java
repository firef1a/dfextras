package dev.fire.dfextras.event;

import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;

import java.io.IOException;

import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;

public class PacketHandler {
    public static <T extends PacketListener> void handlePacket(Packet<T> packet) throws IOException {
        if ( packet instanceof PlayerListHeaderS2CPacket playerListUpdate ) {
            playerListUpdate.getFooter();
        }

    }


}
