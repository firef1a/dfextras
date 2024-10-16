package dev.fire.dfextras.screen.utils.overlay.containers;

import com.google.gson.JsonObject;
import dev.fire.dfextras.Mod;
import dev.fire.dfextras.devutils.ColorUtils;
import dev.fire.dfextras.devutils.JsonUtils;
import dev.fire.dfextras.screen.utils.overlay.Alignment;
import dev.fire.dfextras.screen.utils.overlay.TextList;
import dev.fire.dfextras.screen.utils.scaler.Scaler;
import dev.fire.dfextras.server.LocationType;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.ArrayList;

import static dev.fire.dfextras.devutils.MathUtils.inRange;
import static dev.fire.dfextras.devutils.TextUtils.monoText;

public class SupportInfoOverlay extends TextList {
    public SupportInfoOverlay(Scaler scaler, Alignment alignment, boolean isVisible) {
        super(scaler, alignment, isVisible);
    }

    @Override
    public void update() {
        ArrayList<Text> textList = new ArrayList<Text>();

        textList.add(Text.literal("Support Queue: ").withColor(ColorUtils.SUPPORT_COLOR));
        textList.add(Text.literal("Clear!").withColor(ColorUtils.GRAY));

        this.setTextList(textList);
    }




    public static SupportInfoOverlay getFromJson(JsonObject jsonObject, String namespace, String fieldName) { return getFromJson(jsonObject,namespace + "." + fieldName); }

    public void toJson(JsonObject jsonObject, String namespace) {
        this.scaler.toJson(jsonObject, namespace, "scaler");
        this.alignment.toJson(jsonObject, namespace, "alignment");
        JsonUtils.addProperty(jsonObject, namespace, "isVisible", this.isVisible);
    }

    public static SupportInfoOverlay getFromJson(JsonObject jsonObject, String namespace) {
        Scaler scaler = Scaler.getFromJson(jsonObject, namespace, "scaler");
        Alignment alignment = Alignment.getFromJson(jsonObject, namespace, "alignment");
        boolean isVisible = jsonObject.get(namespace + "." + "isVisible").getAsBoolean();

        return new SupportInfoOverlay(scaler, alignment, isVisible);
    }

}
