package dev.fire.dfextras.server;

import com.mojang.authlib.GameProfile;
import dev.fire.dfextras.Mod;
import dev.fire.dfextras.chat.ChatManager;
import dev.fire.dfextras.devutils.PositionUtils;
import dev.fire.dfextras.devutils.VectorUtils;
import net.minecraft.block.Block;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.*;

public class PlotManager {
    private int outstandingRequests;
    public PlotInfo plotInfo;

    public BlockPos lastSpawn;
    public Vec3d lastPosition;

    public ArrayList<Text> playerList;

    public static HashMap<String, BlockPos> worldSpawnLocations = new HashMap<>(
            Map.ofEntries(
                    Map.entry("node1", new BlockPos(0, 60, 0)),
                    Map.entry("node2", new BlockPos(0, 60, 0)),
                    Map.entry("node3", new BlockPos(0, 60, 0)),
                    Map.entry("node4", new BlockPos(0, 60, 0)),
                    Map.entry("node5", new BlockPos(0, 60, 0)),
                    Map.entry("node6", new BlockPos(0, 60, 0)),
                    Map.entry("node7", new BlockPos(0, 60, 0)),
                    Map.entry("dev", new BlockPos(0, 60, 0)),
                    Map.entry("build", new BlockPos(0, 60, 0)),
                    Map.entry("dev-events", new BlockPos(0, 60, 0)),
                    Map.entry("edu", new BlockPos(0, 60, 0)),
                    Map.entry("dev2", new BlockPos(0, 60, 0)),
                    Map.entry("dev3", new BlockPos(0, 60, 0))
                    )
    );

    public PlotManager() {
        plotInfo = new PlotInfo(LocationType.NONE);
        outstandingRequests = 0;
        playerList = new ArrayList<>();
    }

    public void requestPlotInfo() {
        outstandingRequests++;
        ChatManager.sendCommandAsPlayer("locate");
    }

    public boolean onChatMessage(Text message) {
        boolean cancelMessage = false;

        List<Text> siblings = message.getSiblings();
        if (!siblings.isEmpty()){
            if (message.getStyle().getClickEvent() != null &&
                    message.getStyle().getClickEvent().getAction() != null &&
                    message.getStyle().getClickEvent().getAction().equals(ClickEvent.Action.RUN_COMMAND) &&
                    Objects.equals(siblings.get(0).getString(), "                                       ") &&
                    siblings.get(0).getStyle().isStrikethrough() &&
                    siblings.get(0).getStyle().getColor().getRgb() == 0xAAD4AA) {

                List<Text> content = siblings.get(1).getSiblings();
                Text first = content.get(1);

                if (first.getString().startsWith("You are currently")) {
                    if (first.getString().endsWith("spawn")) {
                        String node = content.get(content.size()-1).getString().substring(11);
                        plotInfo = new PlotInfo(node, LocationType.SPAWN);
                    } else {
                        String node = content.get(content.size()-1).getString().substring(11);
                        String owner = content.get(content.size()-2).getString().substring(10);
                        if (owner.endsWith(" ")) {
                            owner = owner.substring(0, owner.length()-1);
                        }

                        Text name = content.get(3).getSiblings().get(0).getSiblings().get(1);
                        List<Text> plotNameSiblings = name.getSiblings();
                        MutableText plotName = Text.empty();

                        int i = 0;
                        for (Text sibling : plotNameSiblings) {
                            if (i > 1) plotName.append(sibling);
                            i++;
                        }

                        int plotId = Integer.parseInt(message.getStyle().getClickEvent().getValue().substring(6));
                        plotInfo = new PlotInfo(plotName, owner, plotId, node, LocationType.PLOT);
                    }
                }
                if (outstandingRequests > 0) {
                    cancelMessage = true;
                    outstandingRequests--;
                }
            }


        }



        return cancelMessage;
    }

    public void onTick() {
        BlockPos currentWorldSpawn = Mod.MC.world.getSpawnPos();
        Vec3d playerPosition = Mod.MC.player.getPos();

        // player list info

        ClientPlayNetworkHandler networkHandler = Mod.MC.player.networkHandler;
        Collection<PlayerListEntry> playerListEntries =  networkHandler.getPlayerList();

        playerList = new ArrayList<>();
        int staffInsertPos = 0;
        for (PlayerListEntry playerListEntry : playerListEntries) {
            GameProfile profile = playerListEntry.getProfile();
            Team team = playerListEntry.getScoreboardTeam();
            if (profile != null && team != null) {
                Integer teamColorInt = team.getColor().getColorValue();
                if (teamColorInt != null || plotInfo.locationType == LocationType.SPAWN) {
                    String name = profile.getName();
                    if (!name.contains("§")) {
                        if (name.equals("Jeremaster")) {
                            playerList.add(0, Text.literal(profile.getName()).withColor(0xdb2323));
                            staffInsertPos++;
                        } else if (teamColorInt != null && teamColorInt == 0x55FFFF && plotInfo.locationType == LocationType.SPAWN) {
                            playerList.add(staffInsertPos, Text.literal(profile.getName()).withColor(0x5bc4f0));
                        } else {
                            playerList.add(Text.literal(profile.getName()).withColor(0x9ec1db));
                        }
                    }
                }
            }
        }

        // other crap
        //ChatManager.displayChatMessageToPlayer(Text.literal(currentWorldSpawn.toString()));
        if (!PositionUtils.isequal(currentWorldSpawn, lastSpawn) && !PositionUtils.isequal(currentWorldSpawn, new BlockPos(0,0,0))) {
            lastPosition = null;
            outstandingRequests = 0;
            requestPlotInfo();
        } else if (!VectorUtils.withinHorizontalRangeInclusive(playerPosition, lastPosition, 1414.21356237) && lastPosition != null && playerPosition != null) {
            requestPlotInfo();
        }

        lastPosition = playerPosition;
        lastSpawn = PositionUtils.copyBlockPos(currentWorldSpawn);
    }

}


