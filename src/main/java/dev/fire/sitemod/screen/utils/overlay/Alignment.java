package dev.fire.sitemod.screen.utils.overlay;

import com.google.gson.JsonObject;

public enum Alignment {
    LEFT,
    RIGHT,
    TOP,
    BOTTOM;


    public void toJson(JsonObject jsonObject, String namespace, String fieldName) { this.toJson(jsonObject,namespace + "." + fieldName); }
    public static Alignment getFromJson(JsonObject jsonObject, String namespace, String fieldName) { return getFromJson(jsonObject,namespace + "." + fieldName); }

    public void toJson(JsonObject jsonObject, String namespace) {
        jsonObject.addProperty(namespace, this.name());
    }

    public static Alignment getFromJson(JsonObject jsonObject, String namespace) {
        String alignment = jsonObject.get(namespace).getAsString();
        return Alignment.valueOf(alignment);
    }
}
