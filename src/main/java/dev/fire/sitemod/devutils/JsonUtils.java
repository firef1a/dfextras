package dev.fire.sitemod.devutils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonUtils {
    public static void addProperty(JsonObject jsonObject, String namespace, String fieldName, String object) { jsonObject.addProperty(namespace + "." + fieldName, object); }
    public static void addProperty(JsonObject jsonObject, String namespace, String fieldName, int object) { jsonObject.addProperty(namespace + "." + fieldName, object); }
    public static void addProperty(JsonObject jsonObject, String namespace, String fieldName, double object) { jsonObject.addProperty(namespace + "." + fieldName, object); }
    public static void addProperty(JsonObject jsonObject, String namespace, String fieldName, boolean object) { jsonObject.addProperty(namespace + "." + fieldName, object); }
    public static void addProperty(JsonObject jsonObject, String namespace, String fieldName, float object) { jsonObject.addProperty(namespace + "." + fieldName, object); }
    public static void addProperty(JsonObject jsonObject, String namespace, String fieldName, long object) { jsonObject.addProperty(namespace + "." + fieldName, object); }
    public static void addProperty(JsonObject jsonObject, String namespace, String fieldName, char object) { jsonObject.addProperty(namespace + "." + fieldName, object); }

    public static JsonElement getElement(JsonObject jsonObject, String namespace, String fieldName) {
        return jsonObject.get(namespace + "." + fieldName);
    }
}
