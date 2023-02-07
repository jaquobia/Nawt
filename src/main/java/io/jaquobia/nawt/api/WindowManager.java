package io.jaquobia.nawt.api;

public interface WindowManager {
    void create(int width, int height, boolean fullscreen);
    void destroy();

    void toggleFullscreen();
    void swapBuffers();
    void pollEvents();

    void setTitle(String title);
    void setCursorPosition(int x, int y);

    int getWindowX();
    int getWindowY();

    int getWidth();
    int getHeight();
    int getMouseX();
    int getMouseY();
    int getMouseDX();
    int getMouseDY();

    boolean isMouseButtonDown(int button);
    boolean isMouseGrabbed();
    void setMouseGrab(boolean grab);
    void resetMouse();
    int getMouseScroll();
    boolean isKeyDown(int key);
}
