package io.github.offsetmonkey538.bettermultishot.mixin.item.throwable;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import io.github.offsetmonkey538.bettermultishot.item.IMultishotItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.SnowballItem;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(
        value = SnowballItem.class,
        // Higher priority means it's applied later.
        priority = 2000
)
public abstract class SnowballItemMixin implements IMultishotItem<SnowballEntity> {
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

    @ModifyReceiver(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            )
    )
    @SuppressWarnings("unused")
    private World bettermultishot$useMultishot(World world, Entity snowball, World world1, PlayerEntity user, Hand hand) {
        this.generateProjectiles(
                world,
                user,
                hand,
                (SnowballEntity) snowball,
                SnowballEntity::new,
                bettermultishot$cachedRoll,
                bettermultishot$cachedSpeed,
                bettermultishot$cachedDivergence
        ).forEach(world::spawnEntity);
        return world;
    }
}
