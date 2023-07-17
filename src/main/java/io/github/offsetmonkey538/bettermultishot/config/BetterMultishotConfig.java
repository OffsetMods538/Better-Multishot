package io.github.offsetmonkey538.bettermultishot.config;

import top.offsetmonkey538.monkeyconfig538.Config;
import top.offsetmonkey538.monkeyconfig538.annotation.ConfigEntry;

import static io.github.offsetmonkey538.bettermultishot.entrypoint.BetterMultishotMain.*;

public class BetterMultishotConfig extends Config {
    @ConfigEntry("Set to true if you want multishot arrows from bows to do half the damage. Set to false if now. Default is true.")
    public static boolean nerfBowMultishot = true;

    @Override
    protected String getName() {
        return MOD_ID;
    }
}
