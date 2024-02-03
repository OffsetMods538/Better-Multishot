package io.github.offsetmonkey538.bettermultishot.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.offsetmonkey538.bettermultishot.access.ProjectileEntityAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin {
    @Unique private static int bettermultishot$enchantmentLevel = 0;

    @Shadow
    private static void shoot(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated) {

    }

    @Shadow
    private static float getSoundPitch(boolean flag, Random random) {
        return 0.0f;
    }

    @ModifyVariable(
            method = "loadProjectiles",
            at = @At(value = "STORE"),
            index = 2
    )
    private static int bettermultishot$captureEnchantmentLevel(int value) {
        bettermultishot$enchantmentLevel = value;
        return value;
    }

    @ModifyVariable(
            method = "loadProjectiles",
            at = @At("STORE"),
            index = 3
    )
    private static int bettermultishot$setProjectileAmount(int value) {
        return 1 + (2 * bettermultishot$enchantmentLevel);
    }

    @Redirect(
            method = "shootAll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"
            )
    )
    private static boolean bettermultishot$skipVanillaShooting(ItemStack instance) {
        return true;
    }

    @Inject(
            method = "shootAll",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void bettermultishot$shootAll(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, float speed, float divergence, CallbackInfo ci, List<ItemStack> list, float[] fs, int i, ItemStack projectileStack, boolean bl) {
        if (!crossbow.isEmpty()) {
            if (i == 0) {
                shoot(world, shooter, hand, crossbow, projectileStack, getSoundPitch(shooter.getRandom().nextBoolean(), shooter.getRandom()), bl, speed, divergence, 0.0f);
            } else {
                float simulated = -10.0f + i * 20.0f / list.size();
                shoot(world, shooter, hand, crossbow, projectileStack, getSoundPitch(shooter.getRandom().nextBoolean(), shooter.getRandom()), bl, speed, divergence, simulated);
            }
        }
    }

    @WrapOperation(
            method = "shoot",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            )
    )
    private static boolean bettermultishot$markProjectilesFromMultishot(World instance, Entity entity, Operation<Boolean> original, World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated) {
        if (simulated != 0.0f) ((ProjectileEntityAccess) entity).bettermultishot$setFromMultishot(true);

        return original.call(instance, entity);
    }
}
