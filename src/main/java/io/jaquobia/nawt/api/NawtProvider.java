package io.jaquobia.nawt.api;

import java.util.function.Supplier;

/**
 * Any entrypoint for a WM should implement this interface
 */
public interface NawtProvider {

    /**
     * Provides a WM supplier, expects a NonNull value
     * @return A supplier of a NonNull WM
     */
    Supplier<WindowManager> getWMSupplier();

    /**
     * Provides the name of the window manager <br>
     * Used in the logs to provide feedback on the selected WM
     * @return The name of the window manager
     */
    String WMName();
}
