package top.offsetmonkey538.bettermultishot.item;

import top.offsetmonkey538.bettermultishot.access.ProjectileEntityAccess;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static top.offsetmonkey538.bettermultishot.BetterMultishot.config;

public interface IMultishotItem<T extends ProjectileEntity> {

    default List<T> generateProjectiles(World world, PlayerEntity player, Hand hand, T originalProjectile, BiFunction<World, PlayerEntity, T> projectileConstructor, float roll, float speed, float divergence) {
        if (this instanceof BowItem && config.disableBowMultishot) return new ArrayList<>(0);
        if (this instanceof TridentItem && config.disableTridentMultishot) return new ArrayList<>(0);
        if (config.disableThrowablesMultishot) return new ArrayList<>(0);

        List<T> projectiles = new ArrayList<>();
        ItemStack itemInHand = player.getStackInHand(hand);
        int multishotLevel = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, itemInHand);
        int numProjectiles = 1 + (config.arrowsPerLevel * multishotLevel);

        for (int i = 1; i < numProjectiles; i++) {
            T projectile = projectileConstructor.apply(world, player);
            projectile.copyFrom(originalProjectile);
            projectile.setUuid(MathHelper.randomUuid(world.getRandom()));
            ((ProjectileEntityAccess) projectile).bettermultishot$setFromMultishot(true);

            projectiles.add(config.shootingPattern.newProjectile(
                    numProjectiles,
                    i,
                    player,
                    projectile,
                    roll,
                    speed,
                    divergence
            ));
        }

        return projectiles;
    }
}
