package io.github.kydzombie.crimsonforest.recipe.crude;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CrudePressRecipe {
    final ItemStack input;
    final ItemStack output;
    final int cookTime;

    public CrudePressRecipe(ItemStack input, ItemStack output, int cookTime) {
        this.input = input;
        this.output = output;
        this.cookTime = cookTime;
    }

    public @Nullable CrudeRecipeData getOutput(ItemStack input) {
        if (input == null) return null;
        if (input.isItemEqual(this.input) && input.count >= this.input.count) {
            return new CrudeRecipeData(output.copy(), new ItemStack[]{this.input.copy()}, cookTime);
        }
        return null;
    }
}
