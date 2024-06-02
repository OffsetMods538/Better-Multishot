package top.offsetmonkey538.bettermultishot.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import top.offsetmonkey538.bettermultishot.access.ProjectileEntityAccess;

import java.util.List;

import static net.minecraft.entity.passive.ParrotEntity.getSoundPitch;
import static top.offsetmonkey538.bettermultishot.BetterMultishot.config;

@Mixin(RangedWeaponItem.class)
public abstract class RangedWeaponItemMixin {
    @ModifyVariable(
            method = "load",
            at = @At("STORE"),
            ordinal = 0
    )
    private static int bettermultishot$setProjectileAmount(int value, @Local(ordinal = 0, argsOnly = true) ItemStack weapon) {
        return 1 + (config.arrowsPerLevel * EnchantmentHelper.getLevel(Enchantments.MULTISHOT, weapon));
    }

    @WrapOperation(
            method = "shootAll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            )
    )
    private boolean bettermultishot$modifyProjectileVelocity(
            World instance, Entity entity, Operation<Boolean> original,

            World world,
            LivingEntity shooter,
            Hand hand,
            ItemStack crossbow,
            List<ItemStack> list,
            float speed,
            float divergence,
            boolean critical,
            @Nullable LivingEntity target,
            @Local int projectileIndex
    ) {
        if (!(entity instanceof ProjectileEntity projectile)) return false;
        if (!(shooter instanceof PlayerEntity player)) return false;

        ((PersistentProjectileEntity) projectile).pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
        if (projectileIndex > 0) ((ProjectileEntityAccess) projectile).bettermultishot$setFromMultishot(true);

        projectile = config.shootingPattern.newProjectile(
                list.size(),
                projectileIndex,
                player,
                projectile,
                0,
                speed,
                divergence
        );

        return original.call(instance, projectile);
    }
}
