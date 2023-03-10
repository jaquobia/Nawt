package io.jaquobia.nawt.impl;

import io.jaquobia.nawt.Nawt;
import io.jaquobia.nawt.api.NawtProvider;
import io.jaquobia.nawt.api.WindowManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.GameStartupError;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.Session;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/// This is extracted code from the fabric.mod.json entrypoints because it was causing crashes
// "modmenu": [ "io.jaquobia.nawt.integration.NawtModMenuImpl" ],

@Environment(EnvType.CLIENT)
public class NawtMinecraft extends Minecraft {

    private static NawtMinecraft INSTANCE;

    private WindowManager wm;

    List<MouseEvent> mouseEvents;
    List<KeyboardEvent> keyboardEvents;

    record MouseEvent(int button, boolean state, int x, int y, int dx, int dy, int delta) {}
    record KeyboardEvent(int button, boolean state, int modifiers, char character) {}

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
    private int currentKeyboardButton = 0;
    private boolean currentKeyboardButtonState = false;
    private int currentKeyboardButtonModifiers = 0;
    private char currentKeyboardButtonCharacter = '\0';

    /// Current Scroll Event
    private int currentScrollDelta = 0;

    //TODO: Figure out what to do with sessionID, host, and port
    public NawtMinecraft(int width, int height, boolean fullscreen, String username, String host, String port) {
        super(null, null, null, width, height, fullscreen); // This also fixes the quit button
        mcThread = new Thread(this, "Minecraft main thread");
        this.width = this.actualWidth;
        this.session = new Session(username, "");
        this.mcApplet = null;
        this.isApplet = false;
        keyboardEvents = new ArrayList<>();
        mouseEvents = new ArrayList<>();
    }

    public static void runWindow(int width, int height, boolean fullscreen, String username, String host, String port) {
        INSTANCE = new NawtMinecraft(width, height, fullscreen, username, host, port);
        try {
            INSTANCE.mcThread.start();
            INSTANCE.mcThread.join(); // Pause this thread until the window is done
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // This line honestly shouldn't ever run, but in the case it does, make sure to close early
        System.exit(1);
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
    public static void PushResizeEvent(int width, int height) {
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
            INSTANCE.mouseX = event.x;
            INSTANCE.mouseY = event.y;
            INSTANCE.mouseDX = event.dx;
            INSTANCE.mouseDY = event.dy;
            INSTANCE.currentScrollDelta = event.delta;
            return true;
        }
        INSTANCE.currentMouseButton = -1;
        INSTANCE.currentMouseButtonState = false;
        INSTANCE.mouseX = 0;
        INSTANCE.mouseY = 0;
        INSTANCE.mouseDX = 0;
        INSTANCE.mouseDY = 0;
        INSTANCE.currentScrollDelta = 0;

        return false;
    }

    public static int NumberEventsMouse() {
        return INSTANCE.mouseEvents.size();
    }

    public static void PushMousePositionEvent(int mouseX, int mouseY, int mouseDX, int mouseDY) {
        INSTANCE.mouseEvents.add(new MouseEvent(-1, false, mouseX, mouseY, mouseDX, mouseDY, 0));
    }
    public static void PushMouseButtonEvent(int button, boolean buttonState) {
        INSTANCE.mouseEvents.add(new MouseEvent(button, buttonState, GetMouseX(), GetMouseY(), GetMouseDX(), GetMouseDY(), 0));
    }
    public static void PushKeyboardEvent(int key, boolean state, int modifiers, char character) {
        INSTANCE.keyboardEvents.add(new KeyboardEvent(key, state, modifiers, character));
    }
    public static void PushScrollEvent(int delta) {
        INSTANCE.mouseEvents.add(new MouseEvent(-1, false, GetMouseX(), GetMouseY(), GetMouseDX(), GetMouseDY(), delta));
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
    public static int GetEventMouseScroll() {
        return INSTANCE.currentScrollDelta;
    }
    public static int GetEventMouseButton() {
        return INSTANCE.currentMouseButton;
    }
    public static boolean GetEventMouseButtonState() {
        return INSTANCE.currentMouseButtonState;
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
    public static int GetMouseScroll() {
        return INSTANCE.wm.getMouseScroll();
    }
    public static boolean GetMouseButtonDown(int button) {
        return INSTANCE.wm.isMouseButtonDown(button);
    }

    public static void SetMouseGrabbed(boolean grab) {
        INSTANCE.wm.setMouseGrab(grab);
    }
    public static boolean GetMouseGrabbed() {
        return INSTANCE.wm.isMouseGrabbed();
    }

    public static int GetEventKeyboardButton() {
        return INSTANCE.currentKeyboardButton;
    }
    public static boolean GetEventKeyboardButtonState() {
        return INSTANCE.currentKeyboardButtonState;
    }
    public static int GetCurrentKeyboardButtonModifiers() {
        return INSTANCE.currentKeyboardButtonModifiers;
    }

    public static char GetEventKeyboardButtonChar() {
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
        // This should exist somewhere in the list
        Optional<NawtProvider> defaultGlfwProvider = Optional.empty();
        Optional<NawtProvider> nonGlfwProvider = Optional.empty();

        // Get all the entrypoints
        List<NawtProvider> managers = FabricLoader.getInstance().getEntrypoints("nawt", NawtProvider.class);

        // Should be at least a single Window Manager
        for (NawtProvider provider : managers) {
            if (provider.WMName().equals(Nawt.GLFW_WM_NAME)) {
                defaultGlfwProvider = Optional.of(provider);
            } else {
                nonGlfwProvider = Optional.of(provider);
                break;
            }
        }

        Optional<NawtProvider> finalDefaultGlfwProvider = defaultGlfwProvider;
        NawtProvider provider = nonGlfwProvider.or(
                () -> finalDefaultGlfwProvider).orElseThrow(
                        () -> new RuntimeException("There are no window managers, but glfw should be default")
        );

        Nawt.log("Loading " + provider.WMName() + " Window Manager");
        wm = provider.getWMSupplier().get();
        wm.create(width, height, isFullscreen);
        // Pass in a fake context, so we can just use opengl from glfw.
        try {
            GLContext.useContext(new Object());
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }

        wm.pollEvents();
        Nawt.log("Created OpenGL 3.3 context!");
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

    public static void ToggleFullscreen() {
        INSTANCE.wm.toggleFullscreen();
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
//            this.actualWidth = Display.getDisplayMode().getWidth();
//            this.actualHeight = Display.getDisplayMode().getHeight();

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

        wm.toggleFullscreen();
        Display.update();
    }

    public static int Width() {
        return INSTANCE.actualWidth;
    }

    public static int Height() {
        return INSTANCE.actualHeight;
    }

    public static void SetCursorPosition(int x, int y) {
        INSTANCE.wm.setCursorPosition(x, y);
    }
}
