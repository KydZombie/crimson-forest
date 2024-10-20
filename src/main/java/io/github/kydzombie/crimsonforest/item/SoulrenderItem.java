package io.github.kydzombie.crimsonforest.item;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class SoulrenderItem extends LifeEssenceCollectorItem {
    public SoulrenderItem(Identifier identifier, int damage, int millibucketsPerKill, int maxEssence, int maxDamage) {
        super(identifier, damage, millibucketsPerKill, maxEssence, maxDamage);
    }

    @Override
    protected void onKill(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.onKill(stack, target, attacker);
        if (random.nextFloat() < .25f) {
            if (target instanceof ZombieEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.zombieSoulItem), 0.0f);
            } else if (target instanceof SpiderEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.spiderSoulItem), 0.0f);
            } else if (target instanceof SkeletonEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.skeletonSoulItem), 0.0f);
            } else {
                target.dropItem(new ItemStack(TheCrimsonForest.corruptedSoulItem), 0.0f);
            }
        }
    }
}
