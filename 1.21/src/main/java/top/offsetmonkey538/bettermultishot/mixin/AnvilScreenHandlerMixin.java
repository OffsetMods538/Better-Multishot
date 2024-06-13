package top.offsetmonkey538.bettermultishot.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import top.offsetmonkey538.bettermultishot.item.IMultishotItem;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {

    @WrapOperation(
            method = "updateResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getCount()I",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/enchantment/Enchantment;getAnvilCost()I"
                    )
            )
    )
    private int bettermultishot$ignoreMultishotThrowableItems(ItemStack instance, Operation<Integer> original) {
        if (instance.getItem() instanceof IMultishotItem) return 0;
        return original.call(instance);
    }
}
