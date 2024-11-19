package io.github.kydzombie.crimsonforest.recipe;

import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record BasinRecipe(@NotNull ItemStack input, @NotNull EssenceType essenceType, long essence, @NotNull ItemStack output) {
}
