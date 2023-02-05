package io.jaquobia.nawt.integration;

import io.jaquobia.nawt.config.NawtConfig;
import net.glasslauncher.mods.api.gcapi.api.GConfig;

public class NawtGlassConfigImpl {
    @GConfig(value = "config", visibleName = "Nawt Configuration")
    public static final NawtConfig config = new NawtConfig();
}
