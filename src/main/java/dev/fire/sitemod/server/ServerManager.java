package dev.fire.sitemod.server;

import com.mojang.authlib.GameProfile;
import dev.fire.sitemod.SiteMod;
import dev.fire.sitemod.config.configScreen.Config;
import dev.fire.sitemod.network.NetworkManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.*;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.Vec3d;

import java.net.http.HttpResponse;
import java.util.*;

import static dev.fire.sitemod.devutils.StringUtils.convertStringArrayListToJson;
import static dev.fire.sitemod.devutils.VectorUtils.isVectorWithinVectorRangeInclusive;

public class ServerManager {
    private static final Vec3d plotBase = new Vec3d(102000, 0, 8000);
    private static final Vec3d plotUpperBound = plotBase.add(1000,255,1000);
    private static final MinecraftClient instance = SiteMod.MC;

    private int numPlayers;
    private ArrayList<String> playerList = new ArrayList<>();

    public int lastestResponseCode;
    public boolean isOnPlot;

    public ServerManager() {
        numPlayers = -1;
        playerList = new ArrayList<>();
        lastestResponseCode = -1;
        isOnPlot = false;
    }

    public static String getBaseURL() { return Config.getConfig().PlayerListBaseURL; }
    public static String getUpdateURL() { return Config.getConfig().PlayerListBaseURL + Config.getConfig().PlayerListUpdatePath; }
    public static String getFetchURL() { return Config.getConfig().PlayerListBaseURL + Config.getConfig().PlayerListFetchPath; }

    public static boolean isOnSite03(Vec3d position) {
        return isVectorWithinVectorRangeInclusive(position, plotBase, plotUpperBound);
    }

    public void updateLocalValues() {

        ClientPlayerEntity clientPlayerEntity = instance.player;
        if (clientPlayerEntity == null) return;

        ClientPlayNetworkHandler networkHandler = clientPlayerEntity.networkHandler;
        Collection<PlayerListEntry> playerListEntries =  networkHandler.getPlayerList();

        if (ServerManager.isOnSite03(clientPlayerEntity.getPos())) {
            ArrayList<String> tempPlayerList = new ArrayList<>();
            int tempPlayerCount = 0;
            for (PlayerListEntry playerListEntry : playerListEntries) {
                GameProfile profile = playerListEntry.getProfile();
                Team team = playerListEntry.getScoreboardTeam();
                if (profile != null && team != null) {
                    Integer teamColorInt = team.getColor().getColorValue();
                    if (teamColorInt != null) {
                        boolean hasSite03Color = teamColorInt == 11184810 || teamColorInt == 5635925 || teamColorInt == 5636095 || teamColorInt == 16733695;
                        if (hasSite03Color) {
                            String name = profile.getName();
                            if (!name.contains("§")) {
                                tempPlayerList.add(profile.getName());
                                tempPlayerCount++;
                            }
                        }
                    }
                }
            }


            final int finalTempPlayerCount = tempPlayerCount;

            updateServerValues(finalTempPlayerCount, tempPlayerList);
            this.numPlayers = finalTempPlayerCount;
            this.playerList = tempPlayerList;
            isOnPlot = true;
        }
    }


    private static void updateServerValues(int playerCount, ArrayList<String> listPlayers) {
        if (Config.getConfig().PlayerListReportValues) {
            ClientPlayerEntity player = instance.player;
            if (player == null) {
                SiteMod.LOGGER.info("Player Object is NULL while sending POST? Unable to send update request.");
                return;
            }
            String uuid = player.getUuidAsString();
            String requestBody = "{\"numPlayers\": " + playerCount +  ", \"playerList\": " + convertStringArrayListToJson(listPlayers) + ", \"uuid\": \"" + uuid +"\"}";
            NetworkManager.sendPOSTAsyncHTTP(ServerManager::updateServerValuesCallback, getUpdateURL(), requestBody);
        }
    }

    private static void updateServerValuesCallback(HttpResponse<String> httpResponse) {
        SiteMod.SERVER_MANAGER.lastestResponseCode = httpResponse.statusCode();
    }


    public void requestServerValues() {

        ClientPlayerEntity player = instance.player;
        if (player == null) {
            SiteMod.LOGGER.info("Player Object is NULL while sending POST? Unable to send fetch request.");
            return;
        }
        String uuid = player.getUuidAsString();
        String requestBody =  "{\"uuid\": \"" + uuid +"\"}";
        NetworkManager.sendPOSTAsyncHTTP(ServerManager::receivePostRequest, getFetchURL(), requestBody);



    }

    private static void receivePostRequest(HttpResponse<String> httpResponse) {
        int statusCode = httpResponse.statusCode();
        String responseString = httpResponse.body();

        SiteMod.SERVER_MANAGER.lastestResponseCode = statusCode;

        if (statusCode == 200) {
            ResponseDataObject responseObject = SiteMod.gson.fromJson(responseString, ResponseDataObject.class);
            //SiteMod.LOGGER.info("RESPONSE OBJECT {} {}", responseObject.numPlayers, responseObject.playerList);
            SiteMod.SERVER_MANAGER.numPlayers = responseObject.numPlayers;
            SiteMod.SERVER_MANAGER.playerList = responseObject.playerList;
        }

    }

    private static void receivePostRequest(String responseString) {

        // -1 = no
    }

    class ResponseDataObject {
        int numPlayers;
        ArrayList<String> playerList;
    }


    public int getNumplayers() {
        return numPlayers;
    }

    public ArrayList<String> getPlayerList() {
        return playerList;
    }

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
