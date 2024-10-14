package dev.fire.sitemod.event;

import dev.fire.sitemod.SiteMod;
import dev.fire.sitemod.screen.screens.OverlayScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY = SiteMod.MOD_NAME;

    public static KeyBinding openMenuKeybinding;
    //public static KeyBinding acceptLatestSupport;

    //public static boolean acceptLatestSupportPressed = false;
    //public static boolean latestAcceptLatestSupportPressed = false;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openMenuKeybinding.wasPressed()) {
                if (!(SiteMod.MC.currentScreen instanceof OverlayScreen)) {
                    OverlayScreen screen = new OverlayScreen(Text.literal("hi"), SiteMod.MC.currentScreen);
                    SiteMod.MC.setScreen(screen);
                }
            }
            /*
            if (acceptLatestSupport.isPressed()) { acceptLatestSupportPressed = true;}
            else { acceptLatestSupportPressed = false; }
            if (acceptLatestSupportPressed && !(latestAcceptLatestSupportPressed)) {
                ChatManager.sendMessageAsPlayer("/support accept");
            }
            latestAcceptLatestSupportPressed = acceptLatestSupportPressed;

             */
        });
    }


    public static void register() {
        openMenuKeybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Open Overlay Config", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_Y, // The keycode of the key
                KEY_CATEGORY // The translation key of the keybinding's category.
        ));
        /*
        acceptLatestSupport = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Accept Latest Support", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_GRAVE_ACCENT, // The keycode of the key
                KEY_CATEGORY // The translation key of the keybinding's category.
        ));

         */
        registerKeyInputs();

    }
}
