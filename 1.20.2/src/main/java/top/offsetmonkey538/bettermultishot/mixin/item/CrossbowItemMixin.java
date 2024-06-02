package top.offsetmonkey538.bettermultishot.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import top.offsetmonkey538.bettermultishot.access.ProjectileEntityAccess;
import top.offsetmonkey538.bettermultishot.config.ShootingPatterns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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

import static top.offsetmonkey538.bettermultishot.BetterMultishot.config;

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

    @Shadow
    private static PersistentProjectileEntity createArrow(World world, LivingEntity entity, ItemStack crossbow, ItemStack arrow) {
        return null;
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
        return 1 + (config.arrowsPerLevel * bettermultishot$enchantmentLevel);
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
    private static void bettermultishot$shootAll(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, float speed, float divergence, CallbackInfo ci, List<ItemStack> list, float[] fs, int i, ItemStack projectileStack, boolean isCreative) {
        if (!crossbow.isEmpty()) {
            if (i == 0) {
                shoot(world, shooter, hand, crossbow, projectileStack, getSoundPitch(shooter.getRandom().nextBoolean(), shooter.getRandom()), isCreative, speed, divergence, 0.0f);
                return;
            }

            if (config.shootingPattern != ShootingPatterns.HORIZONTAL_LINE && shooter instanceof PlayerEntity player) {
                final boolean isRocket = projectileStack.isOf(Items.FIREWORK_ROCKET);
                ProjectileEntity projectile;
                if (isRocket) {
                    projectile = new FireworkRocketEntity(world, projectileStack, shooter, shooter.getX(), shooter.getEyeY() - 0.15F, shooter.getZ(), true);
                } else {
                    projectile = createArrow(world, shooter, crossbow, projectileStack);

                    //noinspection DataFlowIssue
                    ((PersistentProjectileEntity) projectile).pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                }

                projectile = config.shootingPattern.newProjectile(
                        list.size(),
                        i,
                        player,
                        projectile,
                        0,
                        speed,
                        divergence
                );

                world.spawnEntity(projectile);
                world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, getSoundPitch(shooter.getRandom().nextBoolean(), shooter.getRandom()));

                return;
            }
            float simulated = -10.0f + i * 20.0f / list.size();
            shoot(world, shooter, hand, crossbow, projectileStack, getSoundPitch(shooter.getRandom().nextBoolean(), shooter.getRandom()), isCreative, speed, divergence, simulated);
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
