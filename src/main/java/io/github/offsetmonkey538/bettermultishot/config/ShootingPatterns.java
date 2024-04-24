package io.github.offsetmonkey538.bettermultishot.config;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public enum ShootingPatterns {
    HORIZONTAL_LINE {
        @Override
        public <T extends ProjectileEntity> T newProjectile(int numProjectiles, int currentProjectile, PlayerEntity player, T projectile, float initialRoll, float initialSpeed, float initialDivergence) {
            float projectileRotationDegrees = -10.0f + currentProjectile * 20.0f / numProjectiles;

            Vec3d vec3d = player.getOppositeRotationVector(1.0F);
            Quaternionf quaternionf = new Quaternionf().setAngleAxis(Math.toRadians(projectileRotationDegrees), vec3d.x, vec3d.y, vec3d.z);
            Vec3d vec3d2 = player.getRotationVec(1.0F);
            Vector3f vector3f = vec3d2.toVector3f().rotate(quaternionf);

            // Copied from bukkit forum (https://bukkit.org/threads/how-do-i-get-yaw-and-pitch-from-a-vector.50317/)
            double distance = Math.sqrt(vector3f.z() * vector3f.z() + vector3f.x() * vector3f.x());
            float pitch = (float) -Math.toDegrees(Math.atan2(vector3f.y(), distance));
            float yaw = (float) -Math.toDegrees(Math.atan2(vector3f.x(), vector3f.z()));

            projectile.setVelocity(player, pitch, yaw, initialRoll, initialSpeed, initialDivergence);

            return projectile;
        }
    },
    SPREAD {
        @Override
        public <T extends ProjectileEntity> T newProjectile(int numProjectiles, int currentProjectile, PlayerEntity player, T projectile, float initialRoll, float initialSpeed, float initialDivergence) {
            final Vec3d rotationVector = player.getRotationVec(1.0f);

            // Copied from bukkit forum (https://bukkit.org/threads/how-do-i-get-yaw-and-pitch-from-a-vector.50317/)
            double distance = Math.sqrt(rotationVector.z * rotationVector.z + rotationVector.x * rotationVector.x);
            float pitch = (float) -Math.toDegrees(Math.atan2(rotationVector.y, distance));
            float yaw = (float) -Math.toDegrees(Math.atan2(rotationVector.x, rotationVector.z));

            projectile.setVelocity(player, pitch, yaw, initialRoll, initialSpeed, initialDivergence + Math.max(5.0f, numProjectiles * 0.1f));

            return projectile;
        }
    };

    public abstract <T extends ProjectileEntity> T newProjectile(int numProjectiles, int currentProjectile, PlayerEntity player, T projectile, float initialRoll, float initialSpeed, float initialDivergence);
}
