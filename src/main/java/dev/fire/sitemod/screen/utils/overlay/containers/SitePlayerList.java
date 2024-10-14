package dev.fire.sitemod.screen.utils.overlay.containers;

import com.google.gson.JsonObject;
import dev.fire.sitemod.SiteMod;
import dev.fire.sitemod.config.configScreen.Config;
import dev.fire.sitemod.devutils.JsonUtils;
import dev.fire.sitemod.screen.utils.overlay.Alignment;
import dev.fire.sitemod.screen.utils.overlay.TextList;
import dev.fire.sitemod.screen.utils.scaler.Scaler;
import dev.fire.sitemod.server.ServerManager;
import net.minecraft.text.Text;

import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;
import java.util.List;

import static dev.fire.sitemod.devutils.MathUtils.inRange;
import static dev.fire.sitemod.devutils.TextUtils.monoText;

public class SitePlayerList extends TextList {
    public SitePlayerList(Scaler scaler, Alignment alignment, boolean isVisible) {
        super(scaler, alignment, isVisible);
    }

    @Override
    public void update() {
        if (!Config.getConfig().PlayerListToggleOnSite03 && SiteMod.SERVER_MANAGER.isOnPlot) { this.setEnabled(false); }

        int numPlayers = SiteMod.SERVER_MANAGER.getNumplayers();
        Text playerText;

        boolean isNotOnPlotAndHasNoConnection = !SiteMod.SERVER_MANAGER.isOnPlot && SiteMod.SERVER_MANAGER.lastestResponseCode != HttpsURLConnection.HTTP_OK;

        if (numPlayers == -1 || isNotOnPlotAndHasNoConnection) {
            playerText = monoText("NO DATA", 0xed452b);
        } else {
            int numColor = 0xc541d9;

            if (inRange(numPlayers, 0, 2)) numColor = 0xe8703c;
            if (inRange(numPlayers, 3, 4)) numColor = 0xe8bd3c;
            if (inRange(numPlayers, 5, 7)) numColor = 0xf0e856;
            if (inRange(numPlayers, 8, 16)) numColor = 0x71d941;
            if (numPlayers > 16) numColor = 0x41bfd9;



            playerText = monoText(String.valueOf(numPlayers), numColor);
        }


        ArrayList<Text> textList = new ArrayList<Text>(List.of(
                Text.empty()
                        .append(monoText("᛫", 0x5A7ACC))
                        .append(monoText("᛬", 0x5A7ACC))
                        .append(monoText("\uD800\uDF38", 0x7E9BE6))
                        .append(monoText("᛬", 0x5A7ACC))
                        .append(monoText("᛫", 0x5A7ACC))
                        .append(Text.literal(" "))
                        .append(monoText("Site-03", 0xB8C8F2)),
                monoText("Players: ", 0xadadad).append(playerText)

        ));

        if (isNotOnPlotAndHasNoConnection) {
            textList.add(Text.literal("Unable to connect to server: "+ ServerManager.getBaseURL()).withColor(0xed452b));
            textList.add(Text.literal("Connection refused with status code " + SiteMod.SERVER_MANAGER.lastestResponseCode).withColor(0xed452b));
        } else {
            for (String playerName : SiteMod.SERVER_MANAGER.getPlayerList()) {
                textList.add(Text.literal(playerName).withColor(0x9ec1db));
            }
        }

        this.setTextList(textList);
    }
    @Override
    public void toJson(JsonObject jsonObject, String namespace, String fieldName) { this.toJson(jsonObject,namespace + "." + fieldName); }
    public static SitePlayerList getFromJson(JsonObject jsonObject, String namespace, String fieldName) { return getFromJson(jsonObject,namespace + "." + fieldName); }

    @Override
    public void toJson(JsonObject jsonObject, String namespace) {
        this.scaler.toJson(jsonObject, namespace, "scaler");
        this.alignment.toJson(jsonObject, namespace, "alignment");
        JsonUtils.addProperty(jsonObject, namespace, "isVisible", this.isVisible);
    }

    public static SitePlayerList getFromJson(JsonObject jsonObject, String namespace) {
        Scaler scaler = Scaler.getFromJson(jsonObject, namespace, "scaler");
        Alignment alignment = Alignment.getFromJson(jsonObject, namespace, "alignment");
        boolean isVisible = jsonObject.get(namespace + "." + "isVisible").getAsBoolean();

        return new SitePlayerList(scaler, alignment, isVisible);
    }
}
