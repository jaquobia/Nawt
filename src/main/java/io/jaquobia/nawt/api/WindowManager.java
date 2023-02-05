package io.jaquobia.nawt.api;

public interface WindowManager {
    void create(int width, int height);
    void destroy();

    void toggleFullscreen();
    void swapBuffers();
    void pollEvents();

    int getWindowX();
    int getWindowY();

    int getWidth();
    int getHeight();
}
