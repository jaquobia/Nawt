package io.jaquobia.nawt.mixin;

import org.lwjgl.input.Cursor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.IntBuffer;

/**
 * prevent crashing
 */
@Mixin(Cursor.class)
public class MixinCursor {
    @Inject(method = "createCursors", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectCreateCursors(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, IntBuffer delays, CallbackInfoReturnable<Object []> cir) {
        cir.cancel();
    }
}
