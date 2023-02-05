package io.jaquobia.nawt.api;

public interface WindowManager {
    void create(int width, int height);
    void destroy();

    void toggleFullscreen();
    void swapBuffers();
    void pollEvents();

    void setTitle(String title);

    int getWindowX();
    int getWindowY();

    int getWidth();
    int getHeight();

    boolean isMouseButtonDown(int button);
    boolean isMouseGrabbed();
    void setMouseGrab(boolean grab);
}
