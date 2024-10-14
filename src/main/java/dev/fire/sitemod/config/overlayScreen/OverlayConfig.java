package dev.fire.sitemod.config.overlayScreen;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.fire.sitemod.FileManager;
import dev.fire.sitemod.SiteMod;
import dev.fire.sitemod.screen.utils.overlay.TextList;
import dev.fire.sitemod.screen.utils.overlay.containers.SitePlayerList;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class OverlayConfig {
    private static OverlayConfig instance;
    public CharSetOption SaveCharSet = DefaultOverlayConfig.SaveCharSet;
    public CharSetOption FileCharSet = DefaultOverlayConfig.FileCharSet;

    public SitePlayerList PlayerListTextListObject = DefaultOverlayConfig.PlayerListTextListObject;

    public static OverlayConfig getConfig() {
        if (instance == null) {
            try {
                instance = new OverlayConfig();
                //Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonObject object = new JsonParser().parse(FileManager.readConfig(FileManager.getOverlayConfigFile())).getAsJsonObject();

                instance.PlayerListTextListObject = SitePlayerList.getFromJson(object, "PlayerListTextListObject");


            } catch (Exception exception) {
                SiteMod.LOGGER.info("Config didn't load: " + exception);
                SiteMod.LOGGER.info("Making a new one.");;
                instance = new OverlayConfig();
                instance.save();
            }
        }
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    public void save() {
        try {
            JsonObject object = new JsonObject();

            SiteMod.OVERLAY_MANAGER.playerListDisplay.toJson(object, "PlayerListTextListObject");


            FileManager.writeConfig(FileManager.getOverlayConfigFile(), object.toString());
        } catch (Exception e) {
            SiteMod.LOGGER.info("Couldn't save config: " + e);
        }
    }



    public static enum CharSetOption {
        ISO_8859_1(StandardCharsets.ISO_8859_1),
        UTF_8(StandardCharsets.UTF_8);

        public final Charset charSet;

        CharSetOption(Charset charSet) {
            this.charSet = charSet;
        }
    }
}
