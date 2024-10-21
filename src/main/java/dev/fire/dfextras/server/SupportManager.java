package dev.fire.dfextras.server;

import dev.fire.dfextras.devutils.OSValidator;
import dev.fire.dfextras.external.SystemIOManager;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.util.List;

import static dev.fire.dfextras.devutils.TextUtils.getContent;

public class SupportManager {
    public static int SUPPORT_COLOR = 0x557FD4;
    public static int VIP_COLOR = 0xFFD47F;
    public static int VIP_BRACKET_COLOR = 0xFFAA00;

    public static Text supportAddons(Text message) throws IOException, InterruptedException {
        Text return_message = message;

        List<Text> siblings = message.getSiblings();

        Text supportPrefix = Text.literal("[SUPPORT] ").withColor(SUPPORT_COLOR);

        if (isListIndexEqual(siblings, 0, supportPrefix)) {
            if (isListIndexEqual(siblings, 2, Text.literal(" joined the support queue. ").formatted(Formatting.GRAY))) {
                String player = getContent(siblings.get(1));
                String reason = getContent(siblings.get(4));

                if (OSValidator.isUnix()) {
                    SystemIOManager.sendDesktopNotification(player + " requested a support session", reason, 1000*5, "critical");
                }

                return_message = Text.empty()
                        .append(message)
                        .append(Text.literal(" [ACCEPT]").withColor(VIP_COLOR))
                        .styled(style -> style
                                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/support accept " + player))
                                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to accept support session with ").withColor(VIP_COLOR).append(Text.literal(player.toString()).withColor(VIP_BRACKET_COLOR))))
                        );
            }
        }
        return return_message;
    }


    public static boolean isListIndexEqual(List list, int index, Object value) {
        if (list.size() >= index+1) {
            return list.get(index).equals(value);
        }
        return false;
    }






}