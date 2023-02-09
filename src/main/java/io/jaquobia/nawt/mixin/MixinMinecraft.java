package io.jaquobia.nawt.mixin;

import io.jaquobia.nawt.impl.NawtMinecraft;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

/**
 * Inject creation and termination of the WM into the head and tail Minecraft.run() function - compats with stapi
 */
@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "run", at = @At("HEAD"), remap = false)
    private void injectHeadRun(CallbackInfo ci) {
        NawtMinecraft.CreateWM();
    }

    @Inject(method = "run", at = @At("TAIL"), remap = false)
    private void injectTailRun(CallbackInfo ci) {
        NawtMinecraft.DestroyWM();
    }

}
