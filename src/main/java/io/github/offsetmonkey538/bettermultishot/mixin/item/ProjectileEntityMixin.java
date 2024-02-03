package io.github.offsetmonkey538.bettermultishot.mixin.item;

import io.github.offsetmonkey538.bettermultishot.access.ProjectileEntityAccess;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin implements ProjectileEntityAccess {
    @Unique
    private boolean spawnedFromMultishot = false;


    @Override
    public boolean bettermultishot$isFromMultishot() {
        return spawnedFromMultishot;
    }

    @Override
    public void bettermultishot$setFromMultishot(boolean value) {
        spawnedFromMultishot = value;
    }
}
