package dev.fire.dfextras.client;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import dev.fire.dfextras.Mod;
import dev.fire.dfextras.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;


@Environment(EnvType.CLIENT)
public class ModClient implements ClientModInitializer {
    private static KeyBinding keyBinding;

    // The KeyBinding declaration and registration are commonly executed here statically

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();

        //ClientTickEvents.END_CLIENT_TICK.register(ModClient::clientTick);
        ItemTooltipCallback.EVENT.register(ModClient::onInjectTooltip);
        /*
        KeyBindingHelper.registerKeyBinding(COPY_TO_CLIPBOARD);
        KeyBindingHelper.registerKeyBinding(TOGGLE_NBT);
        KeyBindingHelper.registerKeyBinding(SCROLL_DOWN);
        KeyBindingHelper.registerKeyBinding(SCROLL_UP);
       
         */
    }

    private static void onInjectTooltip(ItemStack itemStack, TooltipContext tooltipContext, List<net.minecraft.text.Text> list) {
        list.add(Text.literal( "No NBT data"));
    }


}

/// mc.getToastManager().add(new SystemToast(Type.PERIODIC_NOTIFICATION, Text.translatable("nbttooltip.copy_failed"), Text.literal(e.getMessage())));
//