package io.github.offsetmonkey538.bettermultishot.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.function.BiFunction;

public interface IMultishotThrowableItem {

    default void throwProjectiles(World world, PlayerEntity player, Hand hand, ThrownItemEntity originalProjectileEntity, BiFunction<World, PlayerEntity, ThrownItemEntity> thrownItemConstructor, float roll, float speed, float divergence) {
        ItemStack projectileItem = player.getStackInHand(hand);
        int multishotLevel = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, projectileItem);
        int numProjectiles = 1 + (2 * multishotLevel);

        for (int i = 1; i < numProjectiles; i++) {
            ThrownItemEntity projectile = thrownItemConstructor.apply(world, player);
            projectile.copyFrom(originalProjectileEntity);
            projectile.setUuid(MathHelper.randomUuid(world.getRandom()));

            float simulated = -10.0f + i * 20.0f / numProjectiles;

            // Copied from CrossbowItem
            Vec3d vec3d = player.getOppositeRotationVector(1.0f);
            Quaternion quaternion = new Quaternion(new Vec3f(vec3d), simulated, true);
            Vec3d vec3d2 = player.getRotationVec(1.0f);
            Vec3f vec3f = new Vec3f(vec3d2);
            vec3f.rotate(quaternion);

            // Copied from bukkit forum (https://bukkit.org/threads/how-do-i-get-yaw-and-pitch-from-a-vector.50317/)
            double distance = Math.sqrt(vec3f.getZ() * vec3f.getZ() + vec3f.getX() * vec3f.getX());
            float pitch = (float) -Math.toDegrees(Math.atan2(vec3f.getY(), distance));
            float yaw = (float) -Math.toDegrees(Math.atan2(vec3f.getX(), vec3f.getZ()));

            projectile.setVelocity(player, pitch, yaw, roll, speed, divergence);

            world.spawnEntity(projectile);
        }
    }
}
