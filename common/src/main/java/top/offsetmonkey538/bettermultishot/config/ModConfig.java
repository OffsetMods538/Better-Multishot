package top.offsetmonkey538.bettermultishot.config;

import blue.endless.jankson.Comment;
import net.minecraft.enchantment.EnchantmentHelper;
import top.offsetmonkey538.monkeylib538.config.Config;

import static top.offsetmonkey538.bettermultishot.BetterMultishot.MOD_ID;

public class ModConfig extends Config {
    @Comment("Whether or not to make multishot arrows from bows do halved damage. Default is true.")
    public boolean nerfBowMultishot = true;
    @Comment("Whether or not to allow multiple projectiles to hit a single entity. Default is true.")
    public boolean bypassHitCooldown = true;
    public boolean disableTridentMultishot = false;
    public boolean disableBowMultishot = false;
    public boolean disableThrowablesMultishot = false;
    @Comment("The maximum level of multishot an item can have. Default is 3. NOTE: As of Minecraft version 1.21 this value is hardcoded to 3 as I have not found a good way to change the maximum level through code.")
    public int maxMultishotLevel = 3;
    public int arrowsPerLevel = 2;
    @Comment("Allowed values: \"HORIZONTAL_LINE\", \"SPREAD\". Default value: \"HORIZONTAL_LINE\"")
    public ShootingPatterns shootingPattern = ShootingPatterns.HORIZONTAL_LINE;

    @Override
    protected String getName() {
        return MOD_ID;
    }
}
