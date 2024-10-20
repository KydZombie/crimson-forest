package io.github.kydzombie.crimsonforest.recipe;

import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.item.ItemStack;

public record ForgeRecipeData(ItemStack stack, EssenceType essenceType, int cost, int cookTicks) {
}
