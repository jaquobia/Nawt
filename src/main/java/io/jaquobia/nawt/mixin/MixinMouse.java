package io.jaquobia.nawt.mixin;

import io.jaquobia.nawt.Nawt;
import io.jaquobia.nawt.impl.NawtMinecraft;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Redirect to use glfw inputs
 */
@Mixin(Mouse.class)
public class MixinMouse {
    @Inject(method = "getX",at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectMouseX(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(NawtMinecraft.GetMouseX());
    }
    @Inject(method = "getY",at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectMouseY(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(NawtMinecraft.GetMouseY());
    }
    @Inject(method = "getEventX",at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectMouseEventX(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(NawtMinecraft.GetMouseX());
    }
    @Inject(method = "getEventY",at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectMouseEventY(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(NawtMinecraft.GetMouseY());
    }
    @Inject(method = "isButtonDown",at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectIsButtonDown(int button, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(NawtMinecraft.GetMouseButtonDown(button));
    }

    @Inject(method = "next",at = @At("HEAD"), cancellable = true, remap = false)
    private static void injectNext(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(NawtMinecraft.NextMouse);
        NawtMinecraft.NextMouse = false;
    }

    @Inject(method = "getButtonIndex", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetButtonIndex(String buttonName, CallbackInfoReturnable<Integer> cir) {
        Nawt.LOGGER.info("Getting button" + buttonName);
//        cir.setReturnValue(GlfwMinecraft.INSTANCE.currentMouseButton);
    }

    @Inject(method = "getEventButtonState", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetEventButtonState(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(NawtMinecraft.GetCurrentMouseButtonState());
    }

    @Inject(method = "getEventButton", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetEventButton(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(NawtMinecraft.GetCurrentMouseButton());
    }
    @Inject(method = "isCreated", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectIsCreated(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "getEventDX", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetEventDX(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(NawtMinecraft.GetMouseDX());
    }
    @Inject(method = "getEventDY", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetEventDY(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(NawtMinecraft.GetMouseDY());
    }
    @Inject(method = "getDX", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetDX(CallbackInfoReturnable<Integer> cir) {
//        cir.setReturnValue(GlfwMinecraft.INSTANCE.mouseDX);
    }
    @Inject(method = "getDY", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetDY(CallbackInfoReturnable<Integer> cir) {
//        cir.setReturnValue(GlfwMinecraft.INSTANCE.mouseDY);
    }

    @Inject(method = "getEventDWheel", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetEventDWheel(CallbackInfoReturnable<Integer> cir) {
//        cir.setReturnValue(GlfwMinecraft.INSTANCE.mouseScroll);
    }
    @Inject(method = "getDWheel", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetDWheel(CallbackInfoReturnable<Integer> cir) {
//        cir.setReturnValue(GlfwMinecraft.INSTANCE.mouseScroll);
    }

    @Inject(method = "create()V", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectCreate(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "create(Lorg/lwjgl/opengl/InputImplementation;)V", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectCreate2(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "setGrabbed", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectSetGrabbed(boolean grabbed, CallbackInfo ci) {
        NawtMinecraft.SetMouseGrabbed(grabbed);
        ci.cancel();
    }

    @Inject(method = "isGrabbed", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectSetGrabbed(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(NawtMinecraft.GetMouseGrabbed());
    }
}
