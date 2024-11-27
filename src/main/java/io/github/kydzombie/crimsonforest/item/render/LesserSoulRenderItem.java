package io.github.kydzombie.crimsonforest.item.render;

import io.github.kydzombie.cairn.api.item.ItemKillHandler;
import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.item.CrimsonWeaponItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Identifier;

public class LesserSoulRenderItem extends CrimsonWeaponItem implements ItemKillHandler {
    private final float dropChance;

    public LesserSoulRenderItem(Identifier identifier, int durability, int attackDamage, float dropChance) {
        super(identifier, durability, attackDamage);
        this.dropChance = dropChance;
    }

    @Override
    public void onKill(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker.world.isRemote) return;

        if (target instanceof LivingEntity) {
            if (random.nextFloat() <= dropChance) {
                target.dropItem(new ItemStack(TheCrimsonForest.soulShardItem), 0.0f);
            }
        }
    }
}
