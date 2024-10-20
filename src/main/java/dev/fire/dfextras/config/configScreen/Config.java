package dev.fire.dfextras.config.configScreen;

import com.google.gson.*;
import dev.fire.dfextras.FileManager;
import dev.fire.dfextras.Mod;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.text.Text;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Config {
    private static Config instance;
    public CharSetOption SaveCharSet = DefaultConfig.SaveCharSet;
    public CharSetOption FileCharSet = DefaultConfig.FileCharSet;

    public boolean ClickSupportSessionToAccept = DefaultConfig.ClickSupportSessionToAccept;
    public boolean SendDesktopNotificationsOnSupportRequest = DefaultConfig.SendDesktopNotificationsOnSupportRequest;
    public boolean CanSeeHeaderAndFootersTabList = DefaultConfig.CanSeeHeaderAndFootersTabList;


    public static Config getConfig() {
        if (instance == null) {
            try {
                instance = new Config();

                //Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonObject object = new JsonParser().parse(FileManager.readConfig(FileManager.getConfigFile())).getAsJsonObject();

                instance.ClickSupportSessionToAccept = object.get("ClickSupportSessionToAccept").getAsBoolean();
                instance.SendDesktopNotificationsOnSupportRequest = object.get("SendDesktopNotificationsOnSupportRequest").getAsBoolean();

                instance.CanSeeHeaderAndFootersTabList = object.get("CanSeeHeaderAndFootersTabList").getAsBoolean();


            } catch (Exception exception) {
                Mod.LOGGER.info("Config didn't load: " + exception);
                Mod.LOGGER.info("Making a new one.");
                instance = new Config();
                instance.save();
            }
        }
        return instance;
    }

    public static void clear() {
        instance = null;
    }

    private void save() {
        try {
            JsonObject object = new JsonObject();

            object.addProperty("ClickSupportSessionToAccept", Config.getConfig().ClickSupportSessionToAccept);
            object.addProperty("SendDesktopNotificationsOnSupportRequest", Config.getConfig().SendDesktopNotificationsOnSupportRequest);

            object.addProperty("CanSeeHeaderAndFootersTabList", Config.getConfig().CanSeeHeaderAndFootersTabList);


            FileManager.writeConfig(FileManager.getConfigFile(), object.toString());
        } catch (Exception e) {
            Mod.LOGGER.info("Couldn't save config: " + e);
        }
    }

    public YetAnotherConfigLib getLibConfig() {
        YetAnotherConfigLib.Builder yacl =
                YetAnotherConfigLib.createBuilder()
                        .title(Text.literal("Used for narration. Could be used to render a title in the future."))
                        .category(supportCategory().build())
                        .category(miscCategory().build());

        return yacl.save(this::save).build();
    }


    private ConfigCategory.Builder supportCategory() {
        ConfigCategory.Builder configBuilder = ConfigCategory.createBuilder()
                .name(Text.literal("Support"))
                .tooltip(Text.literal("Custom features for support members."));

        OptionGroup playerList = OptionGroup.createBuilder()
                .name(Text.literal("Utility Features"))
                .description(OptionDescription.of(Text.literal("")))
                .option(Option.createBuilder(boolean.class)
                        .name(Text.literal("Click support session to accept"))
                        .description(OptionDescription.createBuilder()
                                .text(Text.literal("Toggles whether you can click on support session to accept them. Indicated by [ SUPPORT ] at the end of the message."))
                                .build())
                        .binding(
                                DefaultConfig.ClickSupportSessionToAccept,
                                () -> ClickSupportSessionToAccept,
                                opt -> ClickSupportSessionToAccept = opt
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                .option(Option.createBuilder(boolean.class)
                        .name(Text.literal("Send Desktop Notifications"))
                        .description(OptionDescription.createBuilder()
                                .text(Text.literal("Sends a desktop notification when someone requests a support session. Currently only works on Linux."))
                                .build())
                        .binding(
                                DefaultConfig.SendDesktopNotificationsOnSupportRequest,
                                () -> SendDesktopNotificationsOnSupportRequest,
                                opt -> SendDesktopNotificationsOnSupportRequest = opt
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                .build();


        configBuilder.group(playerList);


        return configBuilder;
    }

    private ConfigCategory.Builder miscCategory() {
        ConfigCategory.Builder configBuilder = ConfigCategory.createBuilder()
                .name(Text.literal("Misc. Features"))
                .tooltip(Text.literal("Mostly just for debugging this mod."));

        OptionGroup hud = OptionGroup.createBuilder()
                .name(Text.literal("Hud Toggles"))
                .description(OptionDescription.of(Text.literal("")))
                .option(Option.createBuilder(boolean.class)
                        .name(Text.literal("Enable Tablist Header/Footers"))
                        .description(OptionDescription.createBuilder()
                                .text(Text.literal("Toggles whether you can... header and footers in the tablist."))
                                .build())
                        .binding(
                                DefaultConfig.CanSeeHeaderAndFootersTabList,
                                () -> CanSeeHeaderAndFootersTabList,
                                opt -> CanSeeHeaderAndFootersTabList = opt
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                .build();

        configBuilder.group(hud);


        return configBuilder;
    }



    public enum CharSetOption {
        ISO_8859_1(StandardCharsets.ISO_8859_1),
        UTF_8(StandardCharsets.UTF_8);

        public final Charset charSet;

        CharSetOption(Charset charSet) {
            this.charSet = charSet;
        }
    }
}
