package io.github.offsetmonkey538.bettermultishot.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.offsetmonkey538.bettermultishot.access.ProjectileEntityAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

import static io.github.offsetmonkey538.bettermultishot.entrypoint.BetterMultishotMain.config;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyExpressionValue(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"
            ),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/LimbAnimator;setSpeed(F)V"
                    ),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"
                    )
            )
    )
    private boolean makeMultishotProjectilesBypassHitCooldown(boolean original, DamageSource source) {
        final Entity sourceEntity = source.getSource();
        if (!(sourceEntity instanceof ProjectileEntity)) return original;
        if (!((ProjectileEntityAccess) sourceEntity).bettermultishot$isFromMultishot()) return original;
        if (!config.bypassHitCooldown) return original;
        return true;
    }
}
