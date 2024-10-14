package dev.fire.dfextras.screen.utils.overlay.containers;

import com.google.gson.JsonObject;
import dev.fire.dfextras.Mod;
import dev.fire.dfextras.config.configScreen.Config;
import dev.fire.dfextras.devutils.JsonUtils;
import dev.fire.dfextras.screen.utils.overlay.Alignment;
import dev.fire.dfextras.screen.utils.overlay.Container;
import dev.fire.dfextras.screen.utils.overlay.TextList;
import dev.fire.dfextras.screen.utils.scaler.Scaler;
import dev.fire.dfextras.server.ServerManager;
import net.minecraft.text.Text;

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
        ArrayList<Text> textList = new ArrayList<Text>(List.of(
                Text.empty()
                        .append(monoText("᛫", 0x5A7ACC))
                        .append(monoText("᛬", 0x5A7ACC))
                        .append(monoText("\uD800\uDF38", 0x7E9BE6))
                        .append(monoText("᛬", 0x5A7ACC))
                        .append(monoText("᛫", 0x5A7ACC))
                        .append(Text.literal(" "))
                        .append(monoText("Site-03", 0xB8C8F2)),
                monoText("Players: ", 0xadadad)

        ));
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
