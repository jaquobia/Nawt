package io.jaquobia.nawt.mixin;

import io.jaquobia.nawt.impl.NawtMinecraft;
import net.minecraft.class_596;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_596.class)
public class MixinClass_596 {
    @Inject(method = "method_1971", at = @At("HEAD"), cancellable = true)
    private void injectMethod_1971(CallbackInfo ci) {
        Mouse.setCursorPosition(NawtMinecraft.Width() / 2, NawtMinecraft.Height() / 2);
        Mouse.setGrabbed(false);
        ci.cancel();
    }
}
