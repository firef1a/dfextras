package dev.fire.sitemod.mixin.player;

import dev.fire.sitemod.SiteMod;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import java.io.IOException;

@Mixin(ClientPlayerEntity.class)
public class MClientPlayerEntity {
    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) throws IOException, InterruptedException {
        SiteMod.onTick();
    }
}
