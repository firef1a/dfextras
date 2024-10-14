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

    public boolean PlotHeaderFooterToggle = DefaultConfig.PlotHeaderFooterToggle;

    public boolean PlayerListToggleOnSite03 = DefaultConfig.PlayerListToggleOnSite03;
    public boolean PlayerListReportValues = DefaultConfig.PlayerListReportValues;
    public String PlayerListBaseURL = DefaultConfig.PlayerListBaseURL;
    public String PlayerListUpdatePath = DefaultConfig.PlayerListUpdatePath;
    public String PlayerListFetchPath = DefaultConfig.PlayerListFetchPath;

    public static Config getConfig() {
        if (instance == null) {
            try {
                instance = new Config();

                //Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonObject object = new JsonParser().parse(FileManager.readConfig(FileManager.getConfigFile())).getAsJsonObject();

                instance.PlotHeaderFooterToggle = object.get("PlotHeaderFooterToggle").getAsBoolean();

                instance.PlayerListToggleOnSite03 = object.get("PlayerListToggleOnSite03").getAsBoolean();
                instance.PlayerListReportValues = object.get("PlayerListReportValues").getAsBoolean();

                instance.PlayerListBaseURL = object.get("PlayerListBaseURL").getAsString();
                instance.PlayerListUpdatePath = object.get("PlayerListUpdatePath").getAsString();
                instance.PlayerListFetchPath = object.get("PlayerListFetchPath").getAsString();

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

            object.addProperty("PlotHeaderFooterToggle", Config.getConfig().PlotHeaderFooterToggle);

            object.addProperty("PlayerListToggleOnSite03", Config.getConfig().PlayerListToggleOnSite03);
            object.addProperty("PlayerListReportValues", Config.getConfig().PlayerListReportValues);

            object.addProperty("PlayerListBaseURL", Config.getConfig().PlayerListBaseURL);
            object.addProperty("PlayerListUpdatePath", Config.getConfig().PlayerListUpdatePath);
            object.addProperty("PlayerListFetchPath", Config.getConfig().PlayerListFetchPath);

            FileManager.writeConfig(FileManager.getConfigFile(), object.toString());
        } catch (Exception e) {
            Mod.LOGGER.info("Couldn't save config: " + e);
        }
    }

    public YetAnotherConfigLib getLibConfig() {
        YetAnotherConfigLib.Builder yacl =
                YetAnotherConfigLib.createBuilder()
                        .title(Text.literal("Used for narration. Could be used to render a title in the future."))
                            .category(displayCategory().build())
                            .category(miscCategory().build());

        return yacl.save(this::save).build();
    }


    private ConfigCategory.Builder displayCategory() {
        ConfigCategory.Builder configBuilder = ConfigCategory.createBuilder()
                .name(Text.literal("HUD Displays"))
                .tooltip(Text.literal("Customize your Site-03 HUD!"));

        OptionGroup playerList = OptionGroup.createBuilder()
                .name(Text.literal("Player List"))
                .description(OptionDescription.of(Text.literal("Shows a list on your HUD of current player on Site-03, requires at least one player with the mod to be playing Site-03.")))
                .option(Option.createBuilder(boolean.class)
                        .name(Text.literal("Panel Enabled while on Site-03"))
                        .description(OptionDescription.createBuilder()
                                .text(Text.literal("Toggles the player list panel is shown while playing Site-03. Why you would want this? I don't really know other than so you don't piss off a Site-03 dev by sharing a screenshot with this mod."))
                                .build())
                        .binding(
                                DefaultConfig.PlayerListToggleOnSite03,
                                () -> PlayerListToggleOnSite03,
                                opt -> PlayerListToggleOnSite03 = opt
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                .build();

        configBuilder.group(playerList);

        OptionGroup site03API = OptionGroup.createBuilder()
                .name(Text.literal("Site-03 API"))
                .description(OptionDescription.of(Text.literal("Configs for the player list API. Replace these URLs with your own at your discretion.")))
                .option(Option.createBuilder(boolean.class)
                        .name(Text.literal("Enabled Value Reporting"))
                        .description(OptionDescription.createBuilder()
                                .text(Text.literal("Toggles whether you will report Site-03 player counts back to the below list server IP. The whole point of this mod is to report player count values so idk why you would want this other than using replay mod."))
                                .build())
                        .binding(
                                DefaultConfig.PlayerListReportValues,
                                () -> PlayerListReportValues,
                                opt -> PlayerListReportValues = opt
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                .option(Option.createBuilder(String.class)
                        .name(Text.literal("API Base URL"))
                        .description(OptionDescription.createBuilder()
                                .text(Text.literal("The namespace URL that the mod request and sends data from and to."))
                                .build())
                        .binding(
                                DefaultConfig.PlayerListBaseURL,
                                () -> PlayerListBaseURL,
                                opt -> PlayerListBaseURL = opt
                        )
                        .controller(StringControllerBuilder::create)
                        .build())
                .option(Option.createBuilder(String.class)
                        .name(Text.literal("API Update Path"))
                        .description(OptionDescription.createBuilder()
                                .text(Text.literal("The path that the mod sends player data to while you are on Site-03."))
                                .build())
                        .binding(
                                DefaultConfig.PlayerListUpdatePath,
                                () -> PlayerListUpdatePath,
                                opt -> PlayerListUpdatePath = opt
                        )
                        .controller(StringControllerBuilder::create)
                        .build())
                .option(Option.createBuilder(String.class)
                        .name(Text.literal("API Fetch Path"))
                        .description(OptionDescription.createBuilder()
                                .text(Text.literal("The path that the mod requests data from while you are not on Site-03."))
                                .build())
                        .binding(
                                DefaultConfig.PlayerListFetchPath,
                                () -> PlayerListFetchPath,
                                opt -> PlayerListFetchPath = opt
                        )
                        .controller(StringControllerBuilder::create)
                        .build())
                .build();

        configBuilder.group(site03API);

        return configBuilder;
    }

    private ConfigCategory.Builder miscCategory() {
        ConfigCategory.Builder configBuilder = ConfigCategory.createBuilder()
                .name(Text.literal("Misc Options"))
                .tooltip(Text.literal("Silly things"));

        OptionGroup playerList = OptionGroup.createBuilder()
                .name(Text.literal("Tab List"))
                .description(OptionDescription.of(Text.literal("Toggles for the Tab List.")))

                .option(Option.createBuilder(boolean.class)
                        .name(Text.literal("Enable Footers and Headers"))
                        .description(OptionDescription.createBuilder()
                                .text(Text.literal("Toggles whether Footer and Header for the tab list are rendered."))
                                .build())
                        .binding(
                                DefaultConfig.PlotHeaderFooterToggle,
                                () -> PlotHeaderFooterToggle,
                                opt -> PlotHeaderFooterToggle = opt
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build())
                .build();

        configBuilder.group(playerList);

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
