package io.github.offsetmonkey538.bettermultishot.mixin.item.throwable;

import io.github.offsetmonkey538.bettermultishot.item.IMultishotThrowableItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SnowballItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(
        value = SnowballItem.class,
        // Higher priority means it's applied later.
        priority = 2000
)
public abstract class SnowballItemMixin implements IMultishotThrowableItem {
    @Unique private float bettermultishot$cachedRoll;
    @Unique private float bettermultishot$cachedSpeed;
    @Unique private float bettermultishot$cachedDivergence;

    @ModifyArg(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/thrown/SnowballEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V"
            )
    )
    private Entity bettermultishot$captureSetVelocityArgs(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        bettermultishot$cachedRoll = roll;
        bettermultishot$cachedSpeed = speed;
        bettermultishot$cachedDivergence = divergence;

        return shooter;
    }

    @Inject(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void bettermultishot$useMultishot(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, ItemStack projectile, SnowballEntity projectileEntity) {
        this.throwProjectiles(
                world,
                user,
                hand,
                projectileEntity,
                SnowballEntity::new,
                bettermultishot$cachedRoll,
                bettermultishot$cachedSpeed,
                bettermultishot$cachedDivergence
        );
    }
}
