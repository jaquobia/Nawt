package io.jaquobia.nawt.impl;

import io.jaquobia.nawt.Nawt;
import io.jaquobia.nawt.api.WindowManager;
import io.jaquobia.nawt.impl.glfw.IntegratedGlfwWM;
import net.minecraft.client.GameStartupError;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.Session;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import java.util.function.Supplier;

// "modmenu": [ "io.jaquobia.nawt.integration.NawtModMenuImpl" ],

public class NawtMinecraft extends Minecraft {

    private static NawtMinecraft INSTANCE;


    private WindowManager wm;
    private Supplier<WindowManager> wmSupplier = IntegratedGlfwWM::new;
    Thread mcThread;
    public int lastFpsLimit;

    public NawtMinecraft(int width, int height, boolean fullscreen, String username, String host, String port) {
        super(null, null, null, width, height, fullscreen); // This also fixes the quit button

        mcThread = new Thread(this, "Minecraft main thread");
        this.width = this.actualWidth;
        this.session = new Session(username, "");
        this.mcApplet = null;
        this.isApplet = false;
    }

    public static void runWindow(int width, int height, boolean fullscreen, String username, String host, String port) {
        INSTANCE = new NawtMinecraft(width, height, fullscreen, username, host, port);
        try {
//            INSTANCE.internalCreateWM();
            INSTANCE.mcThread.start();
            INSTANCE.mcThread.join(); // Pause this thread until the window is done
//            INSTANCE.internalDestroyWM();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(1); // Our window closed, don't continue and open normal mc's window
    }

    /**
     * Sets the wm supplier
     * @param supplier a non-null supplier of a WindowManager
     */
    public static void SetWMSupplier(Supplier<WindowManager> supplier) {
        INSTANCE.wmSupplier = supplier;
    }

    /**
     * Swap the image buffer of the internal wm context
     */
    public static void SwapBuffers() {
        INSTANCE.wm.swapBuffers();
    }

    /**
     * Used to signify when the window should close
     */
    public static void Terminate() {
        INSTANCE.scheduleStop();
    }

    /**
     *  Create the WM at the start of the run cycle
     */
    public static void CreateWM() {
        INSTANCE.internalCreateWM();
    }

    public void internalCreateWM() {
        wm = wmSupplier.get();
        wm.create(width, height);
    }

    /**
     *  Destroy the WM at the end of the run cycle
     */
    public static void DestroyWM() {
        INSTANCE.internalDestroyWM();
    }

    public void internalDestroyWM() {
        wm.destroy();
    }

    /**
     *  Poll the window events whenever display.update() is called
     */
    public static void PollEvents() {
        INSTANCE.internalPollEvents();
    }

    public void internalPollEvents() {
        wm.pollEvents();
    }

    @Override
    public void showGameStartupError(GameStartupError arg) {}

    @Override
    public void toggleFullscreen() {
        this.isFullscreen = !this.isFullscreen;
        if (this.isFullscreen) {
            wm.toggleFullscreen();
            this.actualWidth = Display.getDisplayMode().getWidth();
            this.actualHeight = Display.getDisplayMode().getHeight();

        } else {
            this.actualWidth = this.width;
            this.actualHeight = this.height;
        }
        if (this.actualWidth <= 0) {
            this.actualWidth = 1;
        }
        if (this.actualHeight <= 0) {
            this.actualHeight = 1;
        }

        if (this.currentScreen != null) {
            this.updateScreenResolution(this.actualWidth, this.actualHeight);
        }

        try {
            Display.setFullscreen(this.isFullscreen);
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
        Display.update();
    }

}
