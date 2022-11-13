package io.github.offsetmonkey538.bettermultishot.config;

import com.kyanite.paragon.api.ConfigOption;
import com.kyanite.paragon.api.interfaces.configtypes.JSONModConfig;

import static io.github.offsetmonkey538.bettermultishot.entrypoint.BetterMultishotMain.MOD_ID;

public class BetterMultishotConfig implements JSONModConfig {
    public static final ConfigOption<Boolean> NERF_BOW_MULTISHOT = new ConfigOption<>("nerf bow multishot", true);

    @Override
    public String getModId() {
        return MOD_ID;
    }
}
