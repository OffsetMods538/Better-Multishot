package top.offsetmonkey538.bettermultishot.mixin.entity;

import net.minecraft.component.type.ItemEnchantmentsComponent;
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
import top.offsetmonkey538.bettermultishot.access.TridentEntityAccess;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity implements TridentEntityAccess {
    @Shadow @Final private static TrackedData<Byte> LOYALTY;

    protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> type, World world, ItemStack stack) {
        super(type, world, stack);
    }


    @Override
    public void bettermultishot$fromMultishot() {
        this.dataTracker.set(LOYALTY, (byte) 0);

        final ItemEnchantmentsComponent enchantments = EnchantmentHelper.getEnchantments(getItemStack());

        final ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(enchantments);
        builder.remove(enchantment -> enchantment.value() == Enchantments.LOYALTY);

        EnchantmentHelper.set(getItemStack(), builder.build());
    }
}
