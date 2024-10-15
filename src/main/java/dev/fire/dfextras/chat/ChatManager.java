package dev.fire.dfextras.chat;

import dev.fire.dfextras.Mod;
import net.minecraft.text.Text;

import java.util.Objects;

import static dev.fire.dfextras.Mod.MOD_NAME;

public class ChatManager {
    public static void sendMessageAsPlayer(String content) {
        if (content.charAt(0) == '/') {
            Objects.requireNonNull(Mod.MC.getNetworkHandler()).sendChatCommand(content.substring(1));
        } else {
            Objects.requireNonNull(Mod.MC.getNetworkHandler()).sendChatMessage(content);
        }
    }

    public static void sendCommandAsPlayer(String content) {
        Objects.requireNonNull(Mod.MC.getNetworkHandler()).sendChatCommand(content);
    }

    private static void sendMessageToPlayerDisplay(Text content) {
        assert Mod.MC.player != null;
        Mod.MC.player.sendMessage(content);
    }

    public static void displayChatMessageToPlayer(Text content) {
        if (Mod.MC.player != null) {
            Mod.MC.player.sendMessage(Text.literal("[" + MOD_NAME.toUpperCase() +"]").withColor(0xed743b).append(Text.literal(" ").append(content)));
        }
    }

}
