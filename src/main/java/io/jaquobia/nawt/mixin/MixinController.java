package io.jaquobia.nawt.mixin;

import org.lwjgl.input.Controllers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Controllers.class)
public class MixinController {
    @Inject(method = "create", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectCreate(CallbackInfo ci) {
        ci.cancel();
    }

}
