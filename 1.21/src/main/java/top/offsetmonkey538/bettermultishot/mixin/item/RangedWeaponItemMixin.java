package top.offsetmonkey538.bettermultishot.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import top.offsetmonkey538.bettermultishot.access.ProjectileEntityAccess;
import top.offsetmonkey538.monkeylib538.utils.EnchantmentUtils;

import java.util.List;

import static top.offsetmonkey538.bettermultishot.BetterMultishot.config;

@Mixin(RangedWeaponItem.class)
public abstract class RangedWeaponItemMixin {
    @ModifyVariable(
            method = "load",
            at = @At("STORE"),
            ordinal = 0
    )
    private static int bettermultishot$setProjectileAmount(int value, @Local(ordinal = 0, argsOnly = true) ItemStack weapon, @Local(ordinal = 0, argsOnly = true) LivingEntity shooter) {
        return 1 + (config.arrowsPerLevel * EnchantmentUtils.INSTANCE.getLevel("multishot", shooter.getWorld(), weapon));
    }

    @WrapOperation(
            method = "shootAll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            )
    )
    private boolean bettermultishot$modifyProjectileVelocity(
            ServerWorld instance, Entity entity, Operation<Boolean> original,
            ServerWorld world,
            LivingEntity shooter,
            Hand hand,
            ItemStack stack,
            List<ItemStack> projectiles,
            float speed,
            float divergence,
            boolean critical,
            @Nullable LivingEntity target,
            @Local int projectileIndex
    ) {
        if (!(entity instanceof ProjectileEntity projectile)) return false;
        if (!(shooter instanceof PlayerEntity player)) return false;

        if (projectileIndex <= 0) return original.call(instance, entity);


        if (projectile instanceof PersistentProjectileEntity persistent) persistent.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
        ((ProjectileEntityAccess) projectile).bettermultishot$setFromMultishot(true);


        projectile = config.shootingPattern.newProjectile(
                projectiles.size(),
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
