package io.jaquobia.nawt.mixin;

import io.jaquobia.nawt.impl.NawtMinecraft;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "run", at = @At("HEAD"), remap = false)
    private void injectHeadInit(CallbackInfo ci) {
        NawtMinecraft.CreateWM();
    }

    @Inject(method = "run", at = @At("TAIL"), remap = false)
    private void injectTailInit(CallbackInfo ci) {
        NawtMinecraft.DestroyWM();
    }
}
