package top.offsetmonkey538.bettermultishot.config;

import blue.endless.jankson.Comment;
import blue.endless.jankson.JsonArray;
import net.minecraft.enchantment.EnchantmentHelper;
import top.offsetmonkey538.monkeylib538.config.Config;
import top.offsetmonkey538.monkeylib538.config.Datafixer;

import java.util.ArrayList;
import java.util.List;

import static top.offsetmonkey538.bettermultishot.BetterMultishot.MOD_ID;

public class ModConfig extends Config {
    @Comment("Whether or not to make multishot arrows from bows do halved damage. Default is true.")
    public boolean nerfBowMultishot = true;
    @Comment("Whether or not to allow multiple projectiles to hit a single entity. Default is true.")
    public boolean bypassHitCooldown = true;
    @Comment("The maximum level of multishot an item can have. Default is 3. NOTE: As of Minecraft version 1.21 this value is hardcoded to 3 as I have not found a good way to change the maximum level through code.")
    public int maxMultishotLevel = 3;
    public int arrowsPerLevel = 2;
    @Comment("Allowed values: \"HORIZONTAL_LINE\", \"SPREAD\". Default value: \"HORIZONTAL_LINE\"")
    public ShootingPatterns shootingPattern = ShootingPatterns.HORIZONTAL_LINE;
    public List<String> disabledItems = new ArrayList<>();

    @Override
    protected String getName() {
        return MOD_ID;
    }

    @Override
    protected int getConfigVersion() {
        return 1;
    }

    @Override
    protected List<Datafixer> getDatafixers() {
        return List.of(
                (original, jankson) -> {
                    final List<String> disabledItems = new ArrayList<>();
                    if (original.getBoolean("disableTridentMultishot", false)) disabledItems.add("trident");
                    if (original.getBoolean("disableBowMultishot", false)) disabledItems.add("bow");
                    if (original.getBoolean("disableThrowablesMultishot", false)) disabledItems.addAll(List.of("TODOOOOOOOOO PLS FIX MEEEEEEE"));
                    original.put("disabledItems", new JsonArray(disabledItems, jankson.getMarshaller()));
                }
        );
    }
}
