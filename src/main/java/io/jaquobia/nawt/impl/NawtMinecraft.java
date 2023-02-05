package io.jaquobia.nawt.impl;

import io.jaquobia.nawt.Nawt;
import io.jaquobia.nawt.api.WindowManager;
import io.jaquobia.nawt.impl.glfw.IntegratedGlfwWM;
import net.minecraft.client.GameStartupError;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.Session;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.util.function.Supplier;

// "modmenu": [ "io.jaquobia.nawt.integration.NawtModMenuImpl" ],

public class NawtMinecraft extends Minecraft {

    private static NawtMinecraft INSTANCE;

    public static boolean NextMouse = false;

    private WindowManager wm;
    private Supplier<WindowManager> wmSupplier = IntegratedGlfwWM::new;
    Thread mcThread;
    public int lastFpsLimit;
    private boolean isCloseRequested = false;

    public int mouseX = 0, mouseY = 0;
    public int mouseDX = 0, mouseDY = 0;
    public boolean currentMouseButtonState = false;
    public int currentMouseButton = 0;

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
            INSTANCE.mcThread.start();
            INSTANCE.mcThread.join(); // Pause this thread until the window is done
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(1); // Our window closed, don't continue and open normal mc's window
    }

    /**
     * Sets the wm supplier, to be used as mod integration until I learn how entrypoints work
     * Use this to set a custom WindowManager, use the IntegratedGlfwWM class to learn how things should work
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
     *  Resizes the viewport and updates the gui resolutions
     * @param width the new width of the window
     * @param height the new height of the window
     */
    public static void Resize(int width, int height) {
        GL11.glViewport(0, 0, width, height);
        INSTANCE.updateScreenResolution(width, height);
    }

    /**
     * Tells the WindowMangaer to set the title of the window
     * @param title the new title of the window
     */
    public static void SetTitle(String title) {
        INSTANCE.wm.setTitle(title);
    }

    /**
     * The proper call to close the loop and schedule the stopping of the client thread
     */
    public static void RequestClose() {
        INSTANCE.isCloseRequested = true;
        Terminate();
    }

    /**
     * Obtains if the thread has been requested to shutdown
     * @return if the thread should be shutdown
     */
    public static boolean IsCloseRequested() {
        return INSTANCE.isCloseRequested;
    }

    public static void SetMousePosition(int mouseX, int mouseY) {
        INSTANCE.mouseX = mouseX;
        INSTANCE.mouseY = mouseY;
    }

    public static int GetMouseX() {
        return INSTANCE.mouseX;
    }
    public static int GetMouseY() {
        return INSTANCE.mouseY;
    }
    public static boolean GetMouseButtonDown(int button) {
        return INSTANCE.wm.isMouseButtonDown(button);
    }

    public static void SetCurrentMouseButton(int button, boolean buttonState) {
        INSTANCE.currentMouseButton = button;
        INSTANCE.currentMouseButtonState = buttonState;
        NextMouse = true;
    }

    public static int GetCurrentMouseButton() {
        return INSTANCE.currentMouseButton;
    }
    public static boolean GetCurrentMouseButtonState() {
        return INSTANCE.currentMouseButtonState;
    }

    public static void SetMouseDXY(int dx, int dy) {
        INSTANCE.mouseDX = dx;
        INSTANCE.mouseDY = dy;
    }

    public static int GetMouseDX() {
        return INSTANCE.mouseDX;
    }

    public static int GetMouseDY() {
        return INSTANCE.mouseDY;
    }

    public static void SetMouseGrabbed(boolean grab) {
        INSTANCE.wm.setMouseGrab(grab);
    }

    public static boolean GetMouseGrabbed() {
        return INSTANCE.wm.isMouseGrabbed();
    }
    /**
     *  Create the WM at the start of the run cycle
     *  Useful for static environments with no access to INSTANCE
     */
    public static void CreateWM() {
        INSTANCE.internalCreateWM();
    }

    /**
     * An internal function to create the WM
     */
    public void internalCreateWM() {
        wm = wmSupplier.get();
        wm.create(width, height);

        // Pass in a fake context, so we can just use opengl.
        try {
            GLContext.useContext(new Object());
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }

//        GL11.glViewport(0, 0, width, height);
        Nawt.LOGGER.info("Created OpenGL 3.3 context!");
    }

    /**
     *  Destroy the WM at the end of the run cycle
     *  Useful for static environments with no access to INSTANCE
     */
    public static void DestroyWM() {
        INSTANCE.internalDestroyWM();
    }

    /**
     * An internal function to destroy the WM
     */
    public void internalDestroyWM() {
        wm.destroy();
    }

    /**
     *  Poll the WM for window events whenever display.update() is called
     *  Useful for static environments with no access to INSTANCE
     */
    public static void PollEvents() {
        INSTANCE.internalPollEvents();
    }

    /**
     * Internal poll events
     */
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
