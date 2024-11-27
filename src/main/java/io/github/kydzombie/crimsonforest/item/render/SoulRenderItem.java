package io.github.kydzombie.crimsonforest.item.render;

import io.github.kydzombie.cairn.api.item.ItemKillHandler;
import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.item.CrimsonWeaponItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class SoulRenderItem extends CrimsonWeaponItem implements ItemKillHandler {
    private final float dropChance;

    public SoulRenderItem(Identifier identifier, int durability, int attackDamage, float dropChance) {
        super(identifier, durability, attackDamage);
        this.dropChance = dropChance;
    }

    @Override
    public void onKill(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.world.isRemote) return;

        if (random.nextFloat() <= dropChance) {
            if (random.nextFloat() <= 0.1) {
                target.dropItem(new ItemStack(TheCrimsonForest.corruptedSoulItem), 0.0f);
                return;
            }
            if (target instanceof ZombieEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.zombieSoulItem), 0.0f);
            } else if (target instanceof SpiderEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.spiderSoulItem), 0.0f);
            } else if (target instanceof SkeletonEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.skeletonSoulItem), 0.0f);
            } else if (target instanceof CreeperEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.creeperSoulItem), 0.0f);
            } else if (target instanceof AnimalEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.passiveSoulItem), 0.0f);
            } else if (target instanceof SquidEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.squidSoulItem), 0.0f);
            }else if (target instanceof LivingEntity) {
                target.dropItem(new ItemStack(TheCrimsonForest.soulShardItem), 0.0f);
            }
        }
    }
}
