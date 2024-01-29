package io.github.offsetmonkey538.bettermultishot.config;

import blue.endless.jankson.Comment;
import top.offsetmonkey538.monkeylib538.config.Config;

import static io.github.offsetmonkey538.bettermultishot.entrypoint.BetterMultishotMain.MOD_ID;

public class BetterMultishotConfig extends Config {
    @Comment("Set to true if you want multishot arrows from bows to do half the damage. Set to false if now. Default is true.")
    public boolean nerfBowMultishot = true;

    @Override
    protected String getName() {
        return MOD_ID;
    }
}
