package io.jaquobia.nawt.api;

import io.jaquobia.nawt.impl.NawtMinecraft;

/**
 * Represents the designs of Windowing APIs in a way that it is possible to replace the LWGJL2 Display backend of minecraft.
 * Implement this class based on the documentation above each inherited function.
 */
public interface WindowManager {

    /**
     * Used to initialize the WM,<br>
     * Don't use a constructor if you can help it, and a default (no param) constructor if you have to.
     * @param width The provided width to the app initialization.
     * @param height The provided height to the app initialization.
     * @param fullscreen Whether the window should be started in fullscreen.
     */
    void create(int width, int height, boolean fullscreen);

    /**
     * Used to destroy the WM. <br>
     * Use to clean-up the WM and related constructs before application termination.
     */
    void destroy();

    /**
     * Toggle the state of fullscreen on the window. <br>
     * The assumption is to 'not care' about the refresh rate, but a future variant might be added to provide a specific refresh rate.
     */
    void toggleFullscreen();

    /**
     * Tell the window to swap its buffers for display.
     */
    void swapBuffers();

    /**
     * Poll the window for new events. <br>
     * On event handling, use: <br>
     * {@link NawtMinecraft#PushMousePositionEvent} On a mouse move event. <br>
     * {@link NawtMinecraft#PushMouseButtonEvent} On a mouse pressed/released event, where the pushed values follow the lwjgl2 keycodes and the pressed state is a boolean. <br>
     * {@link NawtMinecraft#PushKeyboardEvent} On a key pressed/released event, the pushed values should follow the lwjgl2 keycodes and the pressed state is a boolean. <br>
     * {@link NawtMinecraft#PushScrollEvent} On a scroll event, the pushed delta should ideally be some multiple of 50 per "tick", otherwise scrolling wont work properly with gui's such as <a href="https://github.com/calmilamsy/ModMenu">ModMenu</a> and <a href="https://github.com/calmilamsy/HowManyItems-Fabric-Unofficial">HMI</a>. <br>
     * {@link NawtMinecraft#PushResizeEvent} On a framebuffer resize event, push the size of the screen in integer pixel values width/height. <br>
     * {@link NawtMinecraft#RequestClose()} When it is indicated externally that the window should be closed, such as a kill command from the OS (ALT F4) or the close button is pressed. <br>
     */
    void pollEvents();

    /**
     * Changes the title of the window.
     * @param title The new title of the window.
     */
    void setTitle(String title);

    /**
     * Places the cursor at a specified pixel coordinate.
     * @param x The new x position of the cursor.
     * @param y The new y position of the cursor.
     */
    void setCursorPosition(int x, int y);

    /**
     * @return The x position of the window.
     */
    int getWindowX();

    /**
     * @return The y position of the window.
     */
    int getWindowY();

    /**
     * @return The width of the window.
     */
    int getWidth();

    /**
     * @return The height of the window.
     */
    int getHeight();

    /**
     * @return The x position of the mouse.
     */
    int getMouseX();

    /**
     * @return The y position of the mouse.
     */
    int getMouseY();

    /**
     * @return The distance the mouse has travelled on the x axis since the last time this function was called.
     */
    int getMouseDX();

    /**
     * @return The distance the mouse has travelled on the y axis since the last time this function was called.
     */
    int getMouseDY();

    /**
     * @param button The button to get the state of.
     * @return The pressed state of the button, true if pressed/held, false if released.
     */
    boolean isMouseButtonDown(int button);

    /**
     * @return The grabbed state of the mouse, specifically if it is 'captured' by the window, not just hidden.
     */
    boolean isMouseGrabbed();

    /**
     * Changes the captured state of the mouse, true for a comlpetely captured mouse (sticks to center of window, hidden), false for uncaptured (mouse is free and visible).
     * @param grab The state to set the mouse grab.
     */
    void setMouseGrab(boolean grab);

    /**
     * Resets the mouse DX, DY, and scroll deltas to 0.
     */
    void resetMouse();

    /**
     * @return The currently cached scroll delta (Increments of 50 for mod compatibility).
     */
    int getMouseScroll();

    /**
     * @param key The key to test as a LWJGL2 keycode.
     * @return If the key is pressed/held.
     */
    boolean isKeyDown(int key);
}
