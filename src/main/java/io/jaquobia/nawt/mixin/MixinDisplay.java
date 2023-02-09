package io.jaquobia.nawt.mixin;

import io.jaquobia.nawt.impl.NawtMinecraft;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

/**
 * We take over the lwjgl display class to be glfw in the background
 */
@Mixin(Display.class)
public class MixinDisplay {
    @Inject(method = "update()V", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectUpdate(CallbackInfo ci) {
        NawtMinecraft.SwapBuffers();
        NawtMinecraft.PollEvents();
        ci.cancel();
    }
    @Inject(method = "isActive", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectIsActive(CallbackInfoReturnable<Boolean> cir) {

        cir.setReturnValue(true);
    }

    @Inject(method = "swapBuffers", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectSwapBuffers(CallbackInfo ci) {
        NawtMinecraft.SwapBuffers();
        ci.cancel();
    }

    @Inject(method = "setParent", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectSetParent(Canvas parent, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "setFullscreen", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectSetFullscreen(boolean fullscreen, CallbackInfo ci) {
        NawtMinecraft.ToggleFullscreen();
        ci.cancel();
    }

    @Inject(method = "setDisplayMode", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectSetDisplayMode(DisplayMode mode, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "create()V", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectCreate(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "create(Lorg/lwjgl/opengl/PixelFormat;)V", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectCreate2(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "setTitle", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectSetTitle(String newTitle, CallbackInfo ci) {
        NawtMinecraft.SetTitle(newTitle);
        ci.cancel();
    }

    @Inject(method = "isCloseRequested", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectIsCloseRequested(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(NawtMinecraft.IsCloseRequested());
    }

    @Inject(method = "destroy", at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectDestroy(CallbackInfo ci) {
        NawtMinecraft.DestroyWM();
        ci.cancel();
    }
}
