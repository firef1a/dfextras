package dev.fire.dfextras.mixin.hud;

import dev.fire.dfextras.Mod;
import dev.fire.dfextras.server.PlotManager;
import dev.fire.dfextras.server.SupportManager;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;


@Mixin(ChatHud.class)
public class MChatHud {
    @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public Text inject(Text message) throws IOException, InterruptedException {
        Mod.LOGGER.info(String.valueOf(message));
        message = SupportManager.supportAddons(message);
        return message;
    }

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;)V")
    public void inject(Text message, CallbackInfo ci) {
        boolean should_cancel;

        should_cancel = Mod.PLOT_MANAGER.onChatMessage(message);

        //if (should_cancel) ci.cancel();


    }

}