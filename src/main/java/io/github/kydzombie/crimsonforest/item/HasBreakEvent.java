package io.github.kydzombie.crimsonforest.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public interface HasBreakEvent {
    void onBreak(ItemStack stack, Entity user);
}
