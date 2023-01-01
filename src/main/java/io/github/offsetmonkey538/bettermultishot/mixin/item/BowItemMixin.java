package io.github.offsetmonkey538.bettermultishot.mixin.item;

import io.github.offsetmonkey538.bettermultishot.config.BetterMultishotConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static net.minecraft.entity.projectile.PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;

@Mixin(
        value = BowItem.class,
        // Higher priority means it's applied later.
        priority = 2000
)
public abstract class BowItemMixin {
    @Unique private Entity bettermultishot$cachedShooter;
    @Unique private float bettermultishot$cachedPitch;
    @Unique private float bettermultishot$cachedYaw;
    @Unique private float bettermultishot$cachedRoll;
    @Unique private float bettermultishot$cachedSpeed;
    @Unique private float bettermultishot$cachedDivergence;

    @ModifyArg(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V"
            )
    )
    private Entity bettermultishot$captureSetVelocityArgs(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        bettermultishot$cachedShooter    = shooter;
        bettermultishot$cachedPitch      = pitch;
        bettermultishot$cachedYaw        = yaw;
        bettermultishot$cachedRoll       = roll;
        bettermultishot$cachedSpeed      = speed;
        bettermultishot$cachedDivergence = divergence;

        return shooter;
    }

    @Inject(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void bettermultishot$useMultishot(
            ItemStack bow,
            World world,
            LivingEntity user,
            int remainingUseTicks,
            CallbackInfo ci,
            PlayerEntity playerEntity,
            boolean bl,
            ItemStack arrowType,
            int i,
            float f,
            boolean bl2,
            ArrowItem arrowItem,
            PersistentProjectileEntity arrowEntity
    ) {
        int multishotLevel = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, bow);
        int numArrows = 1 + (2 * multishotLevel);

        for (int j = 1; j < numArrows; j++) {
            PersistentProjectileEntity arrow = new ArrowEntity(EntityType.ARROW, world);
            arrow.copyFrom(arrowEntity);
            if (BetterMultishotConfig.NERF_BOW_MULTISHOT.get()) arrow.setDamage(arrow.getDamage() / 2);
            arrow.setUuid(MathHelper.randomUuid(world.getRandom()));
            arrow.pickupType = CREATIVE_ONLY;

            float simulated = -10.0f + j * 20.0f / numArrows;
            arrow.setVelocity(
                    bettermultishot$cachedShooter,
                    bettermultishot$cachedPitch,
                    bettermultishot$cachedYaw + simulated,
                    bettermultishot$cachedRoll,
                    bettermultishot$cachedSpeed,
                    bettermultishot$cachedDivergence
            );
            world.spawnEntity(arrow);
        }
    }
}
