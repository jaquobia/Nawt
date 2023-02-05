package io.jaquobia.nawt.mixin;

import io.jaquobia.nawt.impl.NawtMinecraft;
import net.minecraft.client.MinecraftApplet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftApplet.class)
public class MixinMinecraftApplet {
	@Inject(at = @At("HEAD"), method = "init", remap = false)
	private void init(CallbackInfo ci) {
		MinecraftApplet applet = (MinecraftApplet)(Object)this;
		String s_width = applet.getParameter("width");
		String s_height = applet.getParameter("height");
		String s_username = applet.getParameter("username");
		String s_fullscreen = applet.getParameter("fullscreen");

		int width = s_width == null ? applet.getWidth() : Integer.parseInt(s_width);
		int height = s_height == null ? applet.getHeight() : Integer.parseInt(s_height);
		String username = s_username == null ? "Player" : s_username;
		boolean fullscreen = Boolean.parseBoolean(s_fullscreen);

		String host = applet.getDocumentBase().getHost();
		String port = String.valueOf(applet.getDocumentBase().getPort());

		NawtMinecraft.runWindow(width, height, fullscreen, username, host, port);
	}
}
