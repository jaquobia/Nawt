package io.jaquobia.nawt.impl.glfw;

import io.github.jaquobia.Glfw;
import io.github.jaquobia.GlfwCallback;
import io.jaquobia.nawt.Nawt;
import io.jaquobia.nawt.api.WindowManager;
import io.jaquobia.nawt.impl.NawtMinecraft;
import org.lwjgl.input.Keyboard;

import static io.jaquobia.nawt.impl.glfw.LwjglToGlfwHelper.translateKeyToGlfw;
import static io.jaquobia.nawt.impl.glfw.LwjglToGlfwHelper.translateKeyToLWJGL;

public class IntegratedGlfwWM implements WindowManager, GlfwCallback {

    static boolean glfwInit = Glfw.glfwInit(); // A nice way to initialize glfw and report it at the same time

    public long window;
    public int x, y, width, height;
    public int old_x, old_y, old_width, old_height; // used for fullscreen
    public int mouseX = 0, mouseY = 0;
    public int mouseLX = 0, mouseLY = 0; // used for Mouse.DX() mouse.DY()
    public int mouseDX = 0, mouseDY = 0;

    public int mouseScroll = 0;

    public boolean currentMouseButtonState = false;
    public int currentMouseButton = 0;
    public boolean currentKeyboardButtonState = false;
    public int currentKeyboardButton = 0;
    public int currentKeyboardButtonModifiers = 0;

    public boolean delegateToCharCallback = false;
    public char currentKeyboardButtonCharacter = '\0';

    public boolean fullscreen = false;

    /// WindowManager functions
    @Override
    public void create(int width, int height) {
        if (window != 0) {
            return; // we already created a window
        }
        Glfw.glfwWindowHint(Glfw.GLFW_OPENGL_PROFILE, Glfw.GLFW_OPENGL_COMPAT_PROFILE);
        Glfw.glfwWindowHint(Glfw.GLFW_CONTEXT_VERSION_MAJOR, 3);
        Glfw.glfwWindowHint(Glfw.GLFW_CONTEXT_VERSION_MINOR, 3);
        long monitor = fullscreen ? Glfw.glfwGetPrimaryMonitor() : 0;
        window = Glfw.glfwCreateWindow(width, height, "Minecraft b1.7.3", monitor, 0);
        Glfw.glfwSetCallback(this);
        Nawt.LOGGER.info("Created Glfw Window!");

        Glfw.glfwMakeContextCurrent(window);
        Glfw.glfwShowWindow(window);
    }

    @Override
    public void destroy() {
        Nawt.LOGGER.info("Terminating GLFW window");
        Glfw.glfwDestroyWindow(window);
        Glfw.glfwTerminate();
    }

    @Override
    public void toggleFullscreen() {
        this.fullscreen = !this.fullscreen;
        if (fullscreen) {
            old_x = x;
            old_y = y;
            old_width = width;
            old_height = height;
            Glfw.glfwSetCurrentWindowMonitor(window);
        } else {
            this.x = old_x;
            this.y = old_y;
            this.width = old_width;
            this.height = old_height;
            Glfw.glfwSetWindowMonitor(window, 0, old_x, old_y, old_width, old_height, Glfw.GLFW_DONT_CARE);
        }
    }

    @Override
    public void swapBuffers() {
        Glfw.glfwSwapBuffers(window);
    }

    @Override
    public void pollEvents() {
        Glfw.glfwPollEvents();
    }

    @Override
    public void setTitle(String title) {
        Glfw.glfwSetWindowTitle(window, title);
    }

    @Override
    public int getWindowX() {
        return x;
    }
    @Override
    public int getWindowY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getMouseX() {
        return mouseX;
    }

    @Override
    public int getMouseY() {
        return mouseY;
    }

    @Override
    public boolean isMouseButtonDown(int button) {
        return Glfw.glfwGetMouseButton(window, button) >= Glfw.GLFW_PRESS;
    }

    @Override
    public boolean isMouseGrabbed() {
        return Glfw.glfwGetInputMode(window, Glfw.GLFW_CURSOR) == Glfw.GLFW_CURSOR_HIDDEN;
    }

    @Override
    public void setMouseGrab(boolean grab) {
        if (grab) {
            Glfw.glfwSetInputMode(window, Glfw.GLFW_CURSOR, Glfw.GLFW_CURSOR_DISABLED);
        } else {
            Glfw.glfwSetInputMode(window, Glfw.GLFW_CURSOR, Glfw.GLFW_CURSOR_NORMAL);
        }
        resetMouse();
    }

    @Override
    public void resetMouse() {
        mouseDX = mouseDY = mouseScroll = 0;
    }

    @Override
    public int getMouseScroll() {
        int lMouseScroll = mouseScroll;
        mouseScroll = 0;
        return lMouseScroll;
    }

    @Override
    public boolean isKeyDown(int key) {
        if (key == Keyboard.KEY_NONE) return false;
        return Glfw.glfwGetKey(window, translateKeyToGlfw(key)) >= Glfw.GLFW_PRESS;
    }
    /// End WM functions

    /// Start Glfw Callbacks
    @Override
    public void error(int error, String description) {
        boolean mouse_grab = isMouseGrabbed();
        setMouseGrab(false);
        Nawt.LOGGER.error(String.format("GlfwError(%d): %s", error, description));
        setMouseGrab(mouse_grab);
    }

    @Override
    public void monitor(long monitor, boolean connected) {
    }

    @Override
    public void joystick(int i, int i1) {

    }

    @Override
    public void windowPos(long window, int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void windowSize(long window, int width, int height) {
    }

    @Override
    public void windowClose(long window) {
        if (window == this.window) {
            NawtMinecraft.RequestClose();
        }
    }

    @Override
    public void windowRefresh(long window) {

    }

    @Override
    public void windowFocus(long window, boolean focused) {

    }

    @Override
    public void windowIconify(long window, boolean iconified) {

    }

    @Override
    public void windowMaximize(long window, boolean maximized) {

    }

    @Override
    public void windowFramebufferSize(long window, int width, int height) {
        this.width = width;
        this.height = height;
        NawtMinecraft.Resize(width, height);
    }

    @Override
    public void windowContentScale(long l, float v, float v1) {

    }

    @Override
    public void key(long window, int key, int scancode, int action, int mods) {
        // Set up dummy lwjgl states
        currentKeyboardButtonState = action >= Glfw.GLFW_PRESS;
        currentKeyboardButton = key;
        currentKeyboardButtonModifiers = mods;

        // Delegate if shift-modifiable
        delegateToCharCallback = action != Glfw.GLFW_RELEASE && key >= Glfw.GLFW_KEY_APOSTROPHE && key <= Glfw.GLFW_KEY_GRAVE_ACCENT;
        if (delegateToCharCallback) {
            return;
        }

        // Key is non-shift-modifiable
        currentKeyboardButtonCharacter = currentKeyboardButton == Glfw.GLFW_KEY_SPACE ? ' ' : '\0';

        NawtMinecraft.PushKeyboardEvent(translateKeyToLWJGL(currentMouseButton), currentKeyboardButtonState, currentKeyboardButtonModifiers, currentKeyboardButtonCharacter);
    }

    public String getKeyName() {
        return Glfw.glfwGetKeyName(currentKeyboardButton, Glfw.glfwGetScancode(currentKeyboardButton));
    }

    @Override
    public void character(long window, int codepoint) {
        if (delegateToCharCallback) {
            int modifier = currentKeyboardButtonModifiers; // maybe expand on this later
            if ((modifier & Glfw.GLFW_MOD_CONTROL) > 0 && currentKeyboardButton == Glfw.GLFW_KEY_V) {
                currentKeyboardButtonCharacter = 22;
            } else
                currentKeyboardButtonCharacter = (char) codepoint;
        }
        NawtMinecraft.PushKeyboardEvent(translateKeyToLWJGL(currentMouseButton), currentKeyboardButtonState, currentKeyboardButtonModifiers, currentKeyboardButtonCharacter);
    }

    @Override
    public void characterMods(long l, int i, int i1) {
    }

    @Override
    public void drop(long l, String[] strings) {

    }

    @Override
    public void mouseButton(long window, int button, boolean pressed, int mods) {
        currentMouseButtonState = pressed;
        currentMouseButton = button;
        NawtMinecraft.PushMouseButtonEvent(button, pressed);
    }

    public int getMouseDX() {
        int temp = this.mouseDX;
        this.mouseDX = 0;
        return temp;
    }

    public int getMouseDY() {
        int temp = this.mouseDY;
        this.mouseDY = 0;
        return temp;
    }

    @Override
    public void cursorPos(long window, double x, double y) {
        this.mouseLX = this.mouseX;
        this.mouseLY = this.mouseY;
        this.mouseX = (int) x;
        this.mouseY = this.height - (int) y;
        this.mouseDX += this.mouseX - this.mouseLX;
        this.mouseDY += this.mouseY - this.mouseLY;

        NawtMinecraft.PushMousePositionEvent(this.mouseX, this.mouseY);
    }

    @Override
    public void cursorEnter(long window, boolean entered) {

    }

    @Override
    public void scroll(long window, double scrollX, double scrollY) {
        int delta = (int)scrollY;
        mouseScroll += delta;
        NawtMinecraft.PushScrollEvent(delta);
    }
    /// END GLFW CALLBACKS
}
