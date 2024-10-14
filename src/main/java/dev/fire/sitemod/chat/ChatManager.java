package dev.fire.sitemod.chat;

import dev.fire.sitemod.SiteMod;
import net.minecraft.text.Text;

import java.util.Objects;

public class ChatManager {
    public static void sendMessageAsPlayer(String content) {
        if (content.charAt(0) == '/') {
            Objects.requireNonNull(SiteMod.MC.getNetworkHandler()).sendChatCommand(content.substring(1));
        } else {
            Objects.requireNonNull(SiteMod.MC.getNetworkHandler()).sendChatMessage(content);
        }
    }
    private static void sendMessageToPlayerDisplay(Text content) {
        assert SiteMod.MC.player != null;
        SiteMod.MC.player.sendMessage(content);
    }

    public static void displayChatMessageToPlayer(Text content) {
        if (SiteMod.MC.player != null) {
            SiteMod.MC.player.sendMessage(Text.literal("[SITEMOD]").withColor(0xed743b).append(Text.literal(" ").append(content)));
        }
    }

}
