package dev.fire.sitemod.client;

import dev.fire.sitemod.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.KeyBinding;


@Environment(EnvType.CLIENT)
public class SiteModClient implements ClientModInitializer {
    private static KeyBinding keyBinding;

    // The KeyBinding declaration and registration are commonly executed here statically

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
    }
}