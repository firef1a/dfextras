package dev.fire.dfextras.server;

import dev.fire.dfextras.chat.ChatManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class PlotManager {
    private int outstandingRequests;

    public PlotInfo plotInfo;

    public PlotManager() {
        plotInfo = new PlotInfo(LocationType.NONE);
        outstandingRequests = 0;
    }

    public void requestPlotInfo() {
        outstandingRequests++;
        ChatManager.sendCommandAsPlayer("locate");
    }

    public boolean onChatMessage(Text message) {
        boolean cancelMessage = false;

        Text join_message = Text.empty()
                .append(Text.literal("◆ ").formatted(Formatting.DARK_RED))
                .append(Text.literal("Welcome back to DiamondFire!").formatted(Formatting.AQUA))
                .append(Text.literal(" ◆").formatted(Formatting.DARK_RED));

        if (message == join_message) {
            requestPlotInfo();
            cancelMessage = true;
        }

        return cancelMessage;
    }
}
