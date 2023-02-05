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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

// "modmenu": [ "io.jaquobia.nawt.integration.NawtModMenuImpl" ],

public class NawtMinecraft extends Minecraft {

    private static NawtMinecraft INSTANCE;

    private WindowManager wm;
    private Supplier<WindowManager> wmSupplier = IntegratedGlfwWM::new;

    List<MouseEvent> mouseEvents;
    List<KeyboardEvent> keyboardEvents;
    List<ScrollEvent> scrollEvents;

    record MouseEvent(int button, boolean state, int x, int y, int dx, int dy) {}
    record KeyboardEvent(int button, boolean state, int modifiers, char character) {}
    record ScrollEvent(int delta) {}

    /// General Usage Variables
    Thread mcThread;
    public int lastFpsLimit;
    private boolean isCloseRequested = false;

    /// Current Mouse Event
    private int currentMouseButton = 0;
    private boolean currentMouseButtonState = false;
    private int mouseX = 0, mouseY = 0;
    private int mouseDX = 0, mouseDY = 0;

    /// Current Keyboard Event
//    private KeyboardEvent currentKeyboardEvent;
    private int currentKeyboardButton = 0;
    private boolean currentKeyboardButtonState = false;
    private int currentKeyboardButtonModifiers = 0;
    private char currentKeyboardButtonCharacter = '\0';

    /// Current Scroll Event
    private int currentScrollDelta = 0;

    public NawtMinecraft(int width, int height, boolean fullscreen, String username, String host, String port) {
        super(null, null, null, width, height, fullscreen); // This also fixes the quit button

        mcThread = new Thread(this, "Minecraft main thread");
        this.width = this.actualWidth;
        this.session = new Session(username, "");
        this.mcApplet = null;
        this.isApplet = false;

        keyboardEvents = new ArrayList<>();
        mouseEvents = new ArrayList<>();
        scrollEvents = new ArrayList<>();
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

    public static boolean NextKeyboard() {
        if (INSTANCE.keyboardEvents.size() > 0) {
            KeyboardEvent event = INSTANCE.keyboardEvents.remove(0);
            INSTANCE.currentKeyboardButton = event.button;
            INSTANCE.currentKeyboardButtonState = event.state;
            INSTANCE.currentKeyboardButtonModifiers = event.modifiers;
            INSTANCE.currentKeyboardButtonCharacter = event.character;
            return true;
        }
        INSTANCE.currentKeyboardButton = 0;
        INSTANCE.currentKeyboardButtonState = false;
        INSTANCE.currentKeyboardButtonModifiers = 0;
        INSTANCE.currentKeyboardButtonCharacter = '\0';
        return false;
    }

    public static int NumberEventsKeyboard() {
        return INSTANCE.keyboardEvents.size();
    }

    public static boolean NextMouse() {
        if (INSTANCE.mouseEvents.size() > 0) {
            MouseEvent event = INSTANCE.mouseEvents.remove(0);
            INSTANCE.currentMouseButton = event.button;
            INSTANCE.currentMouseButtonState = event.state;
            return true;
        }
        INSTANCE.currentMouseButton = -1;
        INSTANCE.currentMouseButtonState = true;
        return false;
    }

    public static int NumberEventsMouse() {
        return INSTANCE.mouseEvents.size();
    }

    public static boolean NextEventScroll() {
        if (INSTANCE.scrollEvents.size() > 0) {
            ScrollEvent event = INSTANCE.scrollEvents.remove(0);
            INSTANCE.currentScrollDelta = event.delta;
            return true;
        }
        INSTANCE.currentScrollDelta = 0;
        return false;
    }

    public static int NumberEventsScroll() {
        return INSTANCE.scrollEvents.size();
    }

    public static void PushMouseDeltaEvent(int mouseDX, int mouseDY) {
        INSTANCE.mouseEvents.add(new MouseEvent(-1, false, 0, 0, mouseDX, mouseDY));
    }
    public static void PushMousePositionEvent(int mouseX, int mouseY) {
        INSTANCE.mouseEvents.add(new MouseEvent(-1, false, mouseX, mouseY, 0, 0));
    }
    public static void PushMouseButtonEvent(int button, boolean buttonState) {
        INSTANCE.mouseEvents.add(new MouseEvent(button, buttonState, 0, 0, 0, 0));
    }
    public static void PushKeyboardEvent(int key, boolean state, int modifiers, char character) {
        INSTANCE.keyboardEvents.add(new KeyboardEvent(key, state, modifiers, character));
    }
    public static void PushScrollEvent(int delta) {
        INSTANCE.scrollEvents.add(new ScrollEvent(delta));
    }

    public static int GetEventMouseX() {
        return INSTANCE.mouseX;
    }
    public static int GetEventMouseY() {
        return INSTANCE.mouseY;
    }
    public static int GetEventMouseDX() {
        return INSTANCE.mouseDX;
    }
    public static int GetEventMouseDY() {
        return INSTANCE.mouseDY;
    }

    public static int GetMouseX() {
        return INSTANCE.wm.getMouseX();
    }
    public static int GetMouseY() {
        return INSTANCE.wm.getMouseY();
    }
    public static int GetMouseDX() {
        return INSTANCE.wm.getMouseDX();
    }
    public static int GetMouseDY() {
        return INSTANCE.wm.getMouseDY();
    }

    public static boolean GetMouseButtonDown(int button) {
        return INSTANCE.wm.isMouseButtonDown(button);
    }

    public static int GetEventMouseButton() {
        return INSTANCE.currentMouseButton;
    }
    public static boolean GetEventMouseButtonState() {
        return INSTANCE.currentMouseButtonState;
    }


    public static void SetMouseGrabbed(boolean grab) {
        INSTANCE.wm.setMouseGrab(grab);
    }

    public static boolean GetMouseGrabbed() {
        return INSTANCE.wm.isMouseGrabbed();
    }

    public static int GetMouseScroll() {
        return INSTANCE.wm.getMouseScroll();
    }

    public static int GetCurrentKeyboardButton() {
        return INSTANCE.currentKeyboardButton;
    }
    public static boolean GetCurrentKeyboardButtonState() {
        return INSTANCE.currentKeyboardButtonState;
    }
    public static int GetCurrentKeyboardButtonModifiers() {
        return INSTANCE.currentKeyboardButtonModifiers;
    }

    public static char GetCurrentKeyboardButtonChar() {
        return INSTANCE.currentKeyboardButtonCharacter;
    }

    public static boolean GetKeyDown(int key) {
        return INSTANCE.wm.isKeyDown(key);
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
