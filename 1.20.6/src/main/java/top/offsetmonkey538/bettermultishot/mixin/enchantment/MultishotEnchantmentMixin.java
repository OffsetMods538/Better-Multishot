package top.offsetmonkey538.bettermultishot.mixin.enchantment;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.MultishotEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import top.offsetmonkey538.bettermultishot.item.ModItemTags;

import static top.offsetmonkey538.bettermultishot.BetterMultishot.config;

@Mixin(MultishotEnchantment.class)
public abstract class MultishotEnchantmentMixin {

    @ModifyVariable(
            method = "<init>",
            at = @At("HEAD"),
            argsOnly = true
    )
    private static Enchantment.Properties modifyProperties(Enchantment.Properties value){
        return new Enchantment.Properties(
                ModItemTags.MULTISHOT_ENCHANTABLE,
                value.primaryItems(),
                value.weight(),
                config.maxMultishotLevel,
                Enchantment.leveledCost(10, 5),
                Enchantment.leveledCost(50, 5),
                value.anvilCost(),
                value.requiredFeatures(),
                value.slots()
        );
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
