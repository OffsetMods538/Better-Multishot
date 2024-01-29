package io.github.offsetmonkey538.bettermultishot.mixin.entity;

import io.github.offsetmonkey538.bettermultishot.access.TridentEntityAccess;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity implements TridentEntityAccess {
    @Shadow @Final private static TrackedData<Byte> LOYALTY;

    protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> type, World world, ItemStack stack) {
        super(type, world, stack);
    }


    @Override
    public void bettermultishot$fromMultishot() {
        this.dataTracker.set(LOYALTY, (byte) 0);

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(getItemStack());
        enchantments.remove(Enchantments.LOYALTY);

        EnchantmentHelper.set(enchantments, getItemStack());
    }
}
