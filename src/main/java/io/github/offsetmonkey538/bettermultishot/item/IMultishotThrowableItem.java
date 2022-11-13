package io.github.offsetmonkey538.bettermultishot.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.function.BiFunction;

public interface IMultishotThrowableItem {

    default void throwProjectiles(World world, PlayerEntity player, Hand hand, ThrownItemEntity originalProjectileEntity, BiFunction<World, PlayerEntity, ThrownItemEntity> thrownItemConstructor, Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        ItemStack projectileItem = player.getStackInHand(hand);
        int multishotLevel = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, projectileItem);
        int numProjectiles = 1 + (2 * multishotLevel);

        for (int i = 1; i < numProjectiles; i++) {
            ThrownItemEntity projectile = thrownItemConstructor.apply(world, player);
            projectile.copyFrom(originalProjectileEntity);
            projectile.setUuid(MathHelper.randomUuid(world.getRandom()));

            float simulated = -10.0f + i * 20.0f / numProjectiles;
            projectile.setVelocity(shooter, pitch, yaw + simulated, roll, speed, divergence);
            world.spawnEntity(projectile);
        }
    }
}
