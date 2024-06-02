package top.offsetmonkey538.bettermultishot.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.bettermultishot.item.ModItemTags;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow public abstract ItemStack getDefaultStack();

    @ModifyReturnValue(
            method = "getEnchantability",
            at = @At("RETURN")
    )
    private int makeItemsEnchantableWithMultishot(int original) {
        if (this.getDefaultStack().isIn(ModItemTags.MULTISHOT_ENCHANTABLE)) return 1;
        return original;
    }
}
