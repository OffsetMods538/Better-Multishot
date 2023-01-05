package io.github.offsetmonkey538.bettermultishot.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Quaternionf;
import org.joml.Vector3f;

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
            Vec3d vec3d = player.getOppositeRotationVector(1.0F);
            Quaternionf quaternionf = (new Quaternionf()).setAngleAxis(simulated * 0.017453292F, vec3d.x, vec3d.y, vec3d.z);
            Vec3d vec3d2 = player.getRotationVec(1.0F);
            Vector3f vector3f = vec3d2.toVector3f().rotate(quaternionf);

            // Copied from bukkit forum (https://bukkit.org/threads/how-do-i-get-yaw-and-pitch-from-a-vector.50317/)
            double distance = Math.sqrt(vector3f.z() * vector3f.z() + vector3f.x() * vector3f.x());
            float pitch = (float) -Math.toDegrees(Math.atan2(vector3f.y(), distance));
            float yaw = (float) -Math.toDegrees(Math.atan2(vector3f.x(), vector3f.z()));

            projectile.setVelocity(player, pitch, yaw, roll, speed, divergence);

            world.spawnEntity(projectile);
        }
    }
}
