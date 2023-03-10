package io.jaquobia.nawt.mixin;

import io.jaquobia.nawt.impl.NawtMinecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "isKeyDown", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectIsKeyDown(int key, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(NawtMinecraft.GetKeyDown(key));
    }
    @Inject(method = "next", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectNext(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(NawtMinecraft.NextKeyboard());
    }
    @Inject(method = "getNumKeyboardEvents", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetNumKeyboardEvents(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(NawtMinecraft.NumberEventsKeyboard());
    }
    @Inject(method = "getEventKey", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetEventKey(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(NawtMinecraft.GetEventKeyboardButton());
    }
    @Inject(method = "getEventCharacter", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetCharacter(CallbackInfoReturnable<Character> cir) {
        cir.setReturnValue(NawtMinecraft.GetEventKeyboardButtonChar());
    }
    @Inject(method = "getEventKeyState", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetEventKeyState(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(NawtMinecraft.GetEventKeyboardButtonState());
    }

    @Inject(method = "create()V", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectCreate(CallbackInfo ci) {
        ci.cancel();
    }
}
