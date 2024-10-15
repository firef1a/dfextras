package dev.fire.dfextras.screen.utils.overlay.containers;

import com.google.gson.JsonObject;
import dev.fire.dfextras.Mod;
import dev.fire.dfextras.config.configScreen.Config;
import dev.fire.dfextras.devutils.ColorUtils;
import dev.fire.dfextras.devutils.JsonUtils;
import dev.fire.dfextras.screen.utils.overlay.Alignment;
import dev.fire.dfextras.screen.utils.overlay.Container;
import dev.fire.dfextras.screen.utils.overlay.TextList;
import dev.fire.dfextras.screen.utils.scaler.Scaler;
import dev.fire.dfextras.server.LocationType;
import dev.fire.dfextras.server.ServerManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;
import java.util.List;

import static dev.fire.dfextras.devutils.MathUtils.inRange;
import static dev.fire.dfextras.devutils.TextUtils.monoText;

public class PlotInfoOverlay extends TextList {
    public PlotInfoOverlay(Scaler scaler, Alignment alignment, boolean isVisible) {
        super(scaler, alignment, isVisible);
    }

    @Override
    public void update() {
        LocationType locType = Mod.PLOT_MANAGER.plotInfo.locationType;
        ArrayList<Text> textList = new ArrayList<Text>();
        if (locType == LocationType.SPAWN) {
            textList.add(monoText("At Spawn", 0xdbdbdb).styled(style -> style.withUnderline(false)));
            textList.add(monoText("[", ColorUtils.DARK_GRAY).append(Text.literal(Mod.PLOT_MANAGER.plotInfo.node).withColor(ColorUtils.GRAY).append(monoText("]", ColorUtils.DARK_GRAY))));
        }
        if (locType == LocationType.PLOT) {
            textList.add(Mod.PLOT_MANAGER.plotInfo.plotName);
            textList.add(monoText("by ", ColorUtils.GRAY).append(Mod.PLOT_MANAGER.plotInfo.plotOwner));
            //textList.add(Text.literal(Mod.PLOT_MANAGER.plotInfo.node));
        }
        if (locType != LocationType.NONE) {
            ArrayList<Text> playerList = Mod.PLOT_MANAGER.playerList;
            int numPlayers = playerList.size();

            int numColor = 0xc541d9;
            if (inRange(numPlayers, 0, 2)) numColor = 0xe8703c;
            if (inRange(numPlayers, 3, 4)) numColor = 0xe8bd3c;
            if (inRange(numPlayers, 5, 7)) numColor = 0xf0e856;
            if (inRange(numPlayers, 8, 16)) numColor = 0x71d941;
            if (numPlayers > 16) numColor = 0x41bfd9;

            textList.add(Text.literal(""));
            textList.add(monoText("Players: ", ColorUtils.GRAY).append(monoText("" + numPlayers, numColor)));
            int i = 0;
            for (Text playerName : playerList) {
                if (i > 10) {
                    textList.add(Text.literal("...").withColor(0x9ec1db));
                    break;
                } else {
                    textList.add(playerName);
                }

                i++;
            }

        }


        this.setTextList(textList);
    }




    public static PlotInfoOverlay getFromJson(JsonObject jsonObject, String namespace, String fieldName) { return getFromJson(jsonObject,namespace + "." + fieldName); }

    public void toJson(JsonObject jsonObject, String namespace) {
        this.scaler.toJson(jsonObject, namespace, "scaler");
        this.alignment.toJson(jsonObject, namespace, "alignment");
        JsonUtils.addProperty(jsonObject, namespace, "isVisible", this.isVisible);
    }

    public static PlotInfoOverlay getFromJson(JsonObject jsonObject, String namespace) {
        Scaler scaler = Scaler.getFromJson(jsonObject, namespace, "scaler");
        Alignment alignment = Alignment.getFromJson(jsonObject, namespace, "alignment");
        boolean isVisible = jsonObject.get(namespace + "." + "isVisible").getAsBoolean();

        return new PlotInfoOverlay(scaler, alignment, isVisible);
    }

}
