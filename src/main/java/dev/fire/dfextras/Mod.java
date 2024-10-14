package dev.fire.dfextras;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.fire.dfextras.chat.ChatLogger;
import dev.fire.dfextras.config.overlayScreen.OverlayConfig;
import dev.fire.dfextras.event.TickHandler;
import dev.fire.dfextras.screen.OverlayManager;
import dev.fire.dfextras.server.PlotManager;
import dev.fire.dfextras.server.ServerManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

// word()
// literal("foo")
// argument("bar", word())
// Import everything in the CommandManager


public class Mod implements ModInitializer {
	public static final String MOD_NAME = "dfextras";
	public static final String MOD_ID = "dfextras";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
	public static final MinecraftClient MC = MinecraftClient.getInstance();
	public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static ChatLogger CHAT_LOGGER = new ChatLogger();

	public static String MOD_VERSION;

	public static String PLAYER_UUID = null;
	public static String PLAYER_NAME = null;

	public static TickHandler TICK_HANDLER;
	public static OverlayManager OVERLAY_MANAGER;
	public static OverlayConfig OVERLAY_CONFIG;
	public static PlotManager PLOT_MANAGER;

	@Override
	public void onInitialize() {
		ClientTickEvents.START_CLIENT_TICK.register(client -> {});
		LOGGER.info("Initializing..");

		Runtime.getRuntime().addShutdownHook(new Thread(this::onClose));

		// allows FileDialog class to open without a HeadlessException
		System.setProperty("java.awt.headless", "false");

		PLAYER_UUID = MC.getSession().getUuidOrNull().toString();
		PLAYER_NAME = MC.getSession().getUsername();

		MOD_VERSION = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();

		TICK_HANDLER = new TickHandler();
		OVERLAY_MANAGER = new OverlayManager();
		OVERLAY_CONFIG = new OverlayConfig();
		PLOT_MANAGER = new PlotManager();

		LOGGER.info(getRandomDebugMessage());
	}

	private static String getRandomDebugMessage() {
		ArrayList<String> messageList = new ArrayList<>(List.of(
				"erm actually!",
				"hello guys"
		));


		Random rand = new Random();

		int n = rand.nextInt(messageList.size()-1);
		return messageList.get(n);
	}

	public static TextRenderer getTextRenderer() {
		return MC.textRenderer;
	}

	public static int getWindowWidth() {
        return Mod.MC.getWindow().getScaledWidth();
	}

	public static int getWindowHeight() {
		return Mod.MC.getWindow().getScaledHeight();
	}

	public void onClose() {
		LOGGER.info("Closed");
	}

	public static void onTick() {
		TICK_HANDLER.onTick();
	}
}