package io.github.offsetmonkey538.bettermultishot.mixin.enchantment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.MultishotEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MultishotEnchantment.class)
public abstract class MultishotEnchantmentMixin {

    @ModifyReturnValue(
            method = "getMinPower",
            at = @At("RETURN")
    )
    @SuppressWarnings("unused")
    private int bettermultishot$changeMinPower(int original, int level) {
        return 10 + (5 * level);
    }

    @ModifyReturnValue(
            method = "getMaxLevel",
            at = @At("RETURN")
    )
    @SuppressWarnings("unused")
    private int bettermultishot$changeMaxLevel(int original) {
        return 3;
    }

    @ModifyReturnValue(
            method = "canAccept",
            at = @At("RETURN")
    )
    @SuppressWarnings("unused")
    private boolean bettermultishot$makeMultishotIncompatibleWithRiptide(boolean original, Enchantment other) {
        return original && other != Enchantments.RIPTIDE;
    }
}
