package io.github.offsetmonkey538.bettermultishot.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import io.github.offsetmonkey538.bettermultishot.item.IMultishotItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static net.minecraft.entity.projectile.PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;

@Mixin(
        value = TridentItem.class,
        priority = 2000
)
public abstract class TridentItemMixin implements IMultishotItem<TridentEntity> {
    @Unique private float bettermultishot$cachedRoll;
    @Unique private float bettermultishot$cachedSpeed;
    @Unique private float bettermultishot$cachedDivergence;

    @ModifyArg(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/projectile/TridentEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V"
            )
    )
    private Entity bettermultishot$captureSetVelocityArgs(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        bettermultishot$cachedRoll = roll;
        bettermultishot$cachedSpeed = speed;
        bettermultishot$cachedDivergence = divergence;

        return shooter;
    }

    @ModifyReceiver(
            method = "onStoppedUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            )
    )
    @SuppressWarnings("unused")
    private World bettermultishot$useMultishot(World world, Entity originalTrident, ItemStack stack, World world1, LivingEntity user, int remainingUseTicks) {
        this.generateProjectiles(
                world,
                (PlayerEntity) user,
                user.getActiveHand(),
                (TridentEntity) originalTrident,
                ((world2, playerEntity) -> new TridentEntity(world2, playerEntity, stack)),
                bettermultishot$cachedRoll,
                bettermultishot$cachedSpeed,
                bettermultishot$cachedDivergence
        ).forEach((entity) -> {
            entity.pickupType = CREATIVE_ONLY;
            world.spawnEntity(entity);
        });
        return world;
    }
}
