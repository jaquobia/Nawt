package io.jaquobia.nawt.integration;

import io.github.prospector.modmenu.api.ModMenuApi;
import io.jaquobia.nawt.Nawt;
import net.minecraft.client.gui.screen.ScreenBase;

import java.util.function.Function;

public class NawtModMenuImpl implements ModMenuApi {
    @Override
    public String getModId() {
        return Nawt.modID;
    }

    @Override
    public Function<ScreenBase, ? extends ScreenBase> getConfigScreenFactory() {
        return ModMenuApi.super.getConfigScreenFactory();
    }
}
