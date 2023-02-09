package io.jaquobia.nawt.config;

import blue.endless.jankson.Comment;
import net.glasslauncher.mods.api.gcapi.api.ConfigName;

public class NawtConfig {
    @ConfigName("Do Logging")
    @Comment("Toggles logging, useful in case of spam")
    public Boolean doLogging = true;
}
