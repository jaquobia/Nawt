package io.jaquobia.nawt.mixin;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "isKeyDown", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectIsKeyDown(int key, CallbackInfoReturnable<Boolean> cir) {
//        int glfwKey = translateKeyToGlfw(key);
//        cir.setReturnValue(Glfw.glfwGetKey(GlfwMinecraft.INSTANCE.window, glfwKey) >= Glfw.GLFW_PRESS);
    }
    @Inject(method = "next", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectNext(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
    @Inject(method = "getNumKeyboardEvents", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetNumKeyboardEvents(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }
    @Inject(method = "getEventKey", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetEventKey(CallbackInfoReturnable<Integer> cir) {
//        int key = translateKeyToLWJGL(GlfwMinecraft.INSTANCE.currentKeyboardButton);
//        cir.setReturnValue(key);
    }
    @Inject(method = "getEventCharacter", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetCharacter(CallbackInfoReturnable<Character> cir) {
//        cir.setReturnValue(GlfwMinecraft.INSTANCE.currentKeyboardButtonCharacter);
    }
    @Inject(method = "getEventKeyState", at = @At("HEAD"), remap = false, cancellable = true)
    private static void injectGetEventKeyState(CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(GlfwMinecraft.INSTANCE.currentKeyboardButtonState);
    }
}
