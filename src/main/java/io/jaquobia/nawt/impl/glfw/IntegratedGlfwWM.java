package io.jaquobia.nawt.impl.glfw;

import io.github.jaquobia.Glfw;
import io.github.jaquobia.GlfwCallback;
import io.jaquobia.nawt.Nawt;
import io.jaquobia.nawt.api.WindowManager;
import io.jaquobia.nawt.impl.NawtMinecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

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

        // Pass in a fake context, so we can just use opengl.
        try {
            GLContext.useContext(new Object());
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }

        GL11.glViewport(0, 0, width, height);

        Nawt.LOGGER.info("Attached OpenGL 3.3 to window!");
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
    /// End WM functions

    /// Start Glfw Callbacks
    @Override
    public void error(int error, String description) {
        Nawt.LOGGER.error(String.format("GlfwError(%d): %s", error, description));
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
            NawtMinecraft.Terminate();
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
        Nawt.LOGGER.info("resizing " + width + " " + height);
        this.width = width;
        this.height = height;
//        openScreen(currentScreen);
        GL11.glViewport(0, 0, width, height);
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
    }

    @Override
    public void cursorEnter(long window, boolean entered) {

    }

    @Override
    public void scroll(long window, double scrollX, double scrollY) {
        mouseScroll = (int)scrollY;
    }
    /// END GLFW CALLBACKS
}
