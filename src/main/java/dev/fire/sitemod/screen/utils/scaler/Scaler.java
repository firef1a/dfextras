package dev.fire.sitemod.screen.utils.scaler;

import com.google.gson.JsonObject;
import dev.fire.sitemod.SiteMod;
import dev.fire.sitemod.devutils.JsonUtils;
import dev.fire.sitemod.screen.utils.Point2i;

public class Scaler {
    public double sx;
    public double sy;

    public Scaler(double sx, double sy) {
        this.sx = sx;
        this.sy = sy;
    }

    public void setScaler(Scaler scaler) {
        this.sx = scaler.sx;
        this.sy = scaler.sy;
    }

    public Point2i getScreenPosition() {
        return new Point2i((int) (sx* SiteMod.getWindowWidth()), (int) (sy* SiteMod.getWindowHeight()));
    }

    public static Scaler fromPosition(int x, int y) {
        return new Scaler((double) x /SiteMod.getWindowWidth(), (double) y /SiteMod.getWindowHeight());
    }


    public void toJson(JsonObject jsonObject, String namespace, String fieldName) { this.toJson(jsonObject,namespace + "." + fieldName); }
    public static Scaler getFromJson(JsonObject jsonObject, String namespace, String fieldName) { return getFromJson(jsonObject,namespace + "." + fieldName); }

    public void toJson(JsonObject jsonObject, String namespace) {
        JsonUtils.addProperty(jsonObject, namespace, "sx", this.sx);
        JsonUtils.addProperty(jsonObject, namespace, "sy", this.sy);
    }

    public static Scaler getFromJson(JsonObject jsonObject, String namespace) {
        double sx = JsonUtils.getElement(jsonObject, namespace, "sx").getAsDouble();
        double sy = JsonUtils.getElement(jsonObject, namespace, "sy").getAsDouble();
        return new Scaler(sx, sy);
    }

    public Scaler add(Scaler scaler2) {
        return new Scaler(sx+scaler2.sx, sy + scaler2.sy);
    }

}
