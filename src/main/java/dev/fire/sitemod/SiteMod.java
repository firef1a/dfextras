package dev.fire.sitemod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.fire.sitemod.chat.ChatLogger;
import dev.fire.sitemod.config.overlayScreen.OverlayConfig;
import dev.fire.sitemod.event.TickHandler;
import dev.fire.sitemod.screen.OverlayManager;
import dev.fire.sitemod.server.ServerManager;
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


public class SiteMod implements ModInitializer {
	public static final String MOD_NAME = "SiteMod";
	public static final String MOD_ID = "sitemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
	public static final MinecraftClient MC = MinecraftClient.getInstance();
	public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static ChatLogger CHAT_LOGGER = new ChatLogger();

	public static String MOD_VERSION;

	public static String PLAYER_UUID = null;
	public static String PLAYER_NAME = null;

	public static TickHandler TICK_HANDLER;
	public static OverlayManager OVERLAY_MANAGER;
	public static ServerManager SERVER_MANAGER;
	public static OverlayConfig OVERLAY_CONFIG;

	public static int tickNumber = 0;

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
		SERVER_MANAGER = new ServerManager();
		OVERLAY_CONFIG = new OverlayConfig();

		LOGGER.info(getRandomDebugMessage());
	}

	private static String getRandomDebugMessage() {
		ArrayList<String> messageList = new ArrayList<>(List.of(
				"smoke me a kipper, i'll be back for breakfast",
				"blazingly fast since 2024",
				"not a tokenlogger tm"
		));


		Random rand = new Random();

		int n = rand.nextInt(messageList.size()-1);
		return messageList.get(n);
	}

	public static TextRenderer getTextRenderer() {
		return MC.textRenderer;
	}

	public static int getWindowWidth() {
        return SiteMod.MC.getWindow().getScaledWidth();
	}

	public static int getWindowHeight() {
		return SiteMod.MC.getWindow().getScaledHeight();
	}

	public void onClose() {
		LOGGER.info("Closed");
	}

	public static void onTick() throws IOException, InterruptedException {
		if (tickNumber % 10 == 0) {
			TICK_HANDLER.onTick();
		}
		tickNumber++;
	}
}