package io.github.kydzombie.crimsonforest.item.render;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.item.CrimsonWeaponItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class SoulRenderItem extends CrimsonWeaponItem {
    private final float dropChance;

    public SoulRenderItem(Identifier identifier, int durability, int attackDamage, float dropChance) {
        super(identifier, durability, attackDamage);
        this.dropChance = dropChance;
    }

    @Override
    protected void onKill(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.onKill(stack, target, attacker);
        if (random.nextFloat() <= dropChance) {
            if (target instanceof ZombieEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.zombieSoulItem), 0.0f);
            } else if (target instanceof SpiderEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.spiderSoulItem), 0.0f);
            } else if (target instanceof SkeletonEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.skeletonSoulItem), 0.0f);
            } else if (target instanceof AnimalEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.passiveSoulItem), 0.0f);
            } else {
                target.dropItem(new ItemStack(TheCrimsonForest.corruptedSoulItem), 0.0f);
            }
        }
    }
}
