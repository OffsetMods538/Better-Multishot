package io.github.offsetmonkey538.bettermultishot.config;

import blue.endless.jankson.Comment;
import top.offsetmonkey538.monkeylib538.config.Config;

import static io.github.offsetmonkey538.bettermultishot.entrypoint.BetterMultishotMain.MOD_ID;

public class BetterMultishotConfig extends Config {
    @Comment("Whether or not to make multishot arrows from bows do halved damage. Default is true.")
    public boolean nerfBowMultishot = true;

    @Comment("Whether or not to allow multiple projectiles to hit a single entity. Default is true.")
    public boolean bypassHitCooldown = true;

    @Override
    protected String getName() {
        return MOD_ID;
    }
}
