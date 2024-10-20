package io.github.kydzombie.crimsonforest.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class CrimsonWeaponItem extends TemplateItem {
    private final int attackDamage;

    public CrimsonWeaponItem(Identifier identifier, int durability, int attackDamage) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(1);
        setMaxDamage(durability);
        setHandheld();
        this.attackDamage = attackDamage;
    }

    @Override
    public int getAttackDamage(Entity attackedEntity) {
        return attackDamage;
    }

    protected void onKill(ItemStack stack, LivingEntity target, LivingEntity attacker) {}

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.deathTime == 0 && target.lastHealth > 0 && target.health <= 0) {
            onKill(stack, target, attacker);
        }

        stack.damage(1, attacker);
        return true;
    }
}
