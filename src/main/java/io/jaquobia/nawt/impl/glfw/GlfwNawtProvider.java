package io.jaquobia.nawt.impl.glfw;

import io.jaquobia.nawt.Nawt;
import io.jaquobia.nawt.api.NawtProvider;
import io.jaquobia.nawt.api.WindowManager;

import java.util.function.Supplier;

public class GlfwNawtProvider implements NawtProvider {
    @Override
    public Supplier<WindowManager> getWMSupplier() {
        return IntegratedGlfwWM::new;
    }

    @Override
    public String WMName() {
        return Nawt.GLFW_WM_NAME;
    }
}
