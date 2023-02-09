package io.jaquobia.nawt;

import io.jaquobia.nawt.config.NawtConfig;
import io.jaquobia.nawt.integration.NawtGlassConfigImpl;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Nawt implements ModInitializer {

    public static final String modName = "Nawt";
    public static final String modID = "nawt";
    private static final Logger LOGGER = LoggerFactory.getLogger("Nawt");

    public static final String GLFW_WM_NAME = "(Default) Glfw";

    public static void log(String value) {
        if (NawtGlassConfigImpl.config.doLogging)
            LOGGER.info(value);
    }

    public static void debug(String value) {
        if (NawtGlassConfigImpl.config.doLogging)
            LOGGER.debug(value);
    }

    public static void error(String value) {
        if (NawtGlassConfigImpl.config.doLogging)
            LOGGER.error(value);
    }
    @Override
    public void onInitialize() {

    }
}
