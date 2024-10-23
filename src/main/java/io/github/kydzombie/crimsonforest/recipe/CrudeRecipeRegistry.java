package io.github.kydzombie.crimsonforest.recipe;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class CrudeRecipeRegistry {
    abstract public @Nullable CrudeRecipeData getOutput(ItemStack[] inputs);
}
