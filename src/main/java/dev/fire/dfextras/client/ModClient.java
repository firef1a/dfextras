package dev.fire.dfextras.client;

import dev.fire.dfextras.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


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
        NbtCompound nbt = itemStack.getNbt();

        int flagCmdColor = 0x858dd6;

        if (nbt != null){
            var bukkitvalues = nbt.getCompound("PublicBukkitValues");
            if (bukkitvalues != null) {
                Set<String> keys = bukkitvalues.getKeys();
                if (!keys.isEmpty()) {
                    list.add(Text.empty());
                    for (String key : keys) {
                        int keyColor = 0xb785d6;
                        int valueColor = 0x6fd6f2;
                        String value = bukkitvalues.get(key).toString();
                        if ((!(value.startsWith("\"") && value.endsWith("\""))) && !(value.startsWith("'") && value.endsWith("'"))) {
                            valueColor = 0xeb4b4b;
                        }
                        Text addText = Text.literal(key.substring(10) + ": ").withColor(keyColor).append(Text.literal(value).withColor(valueColor));
                        list.add(addText);
                    }
                }
            }
            var cmd = nbt.get("CustomModelData");
            var flags = nbt.get("HideFlags");
            if (cmd != null || flags != null) {
                list.add(Text.empty());
                if (cmd != null) { list.add(Text.literal("CustomModelData: ").withColor(flagCmdColor).append(Text.literal(cmd.toString()).withColor(0xeb4b4b))); }
                if (flags != null) { list.add(Text.literal("HideFlags: ").withColor(flagCmdColor).append(Text.literal(flags.toString()).withColor(0xeb4b4b))); }
            }

        }
    }


}

/// mc.getToastManager().add(new SystemToast(Type.PERIODIC_NOTIFICATION, Text.translatable("nbttooltip.copy_failed"), Text.literal(e.getMessage())));
//