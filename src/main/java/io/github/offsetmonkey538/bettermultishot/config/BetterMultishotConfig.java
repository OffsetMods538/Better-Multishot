package io.github.offsetmonkey538.bettermultishot.config;

import blue.endless.jankson.Comment;
import top.offsetmonkey538.monkeylib538.config.Config;

import static io.github.offsetmonkey538.bettermultishot.entrypoint.BetterMultishotMain.MOD_ID;

public class BetterMultishotConfig extends Config {
    @Comment("Whether or not to make multishot arrows from bows do halved damage. Default is true.")
    public boolean nerfBowMultishot = true;

    @Comment("Whether or not to allow multiple projectiles to hit a single entity. Default is true.")
    public boolean bypassHitCooldown = true;
    public boolean disableTridentMultishot = false;
    public boolean disableBowMultishot = false;
    public boolean disableThrowablesMultishot = false;
    public int maxMultishotLevel = 3;
    public int arrowsPerLevel = 2;
    @Comment("Allowed values: \"HORIZONTAL_LINE\", \"SPREAD\". Default value: \"HORIZONTAL_LINE\"")
    public ShootingPatterns shootingPattern = ShootingPatterns.HORIZONTAL_LINE;

    @Override
    protected String getName() {
        return MOD_ID;
    }
}
