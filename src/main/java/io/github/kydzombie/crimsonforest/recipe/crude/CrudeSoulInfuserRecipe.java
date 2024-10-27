package io.github.kydzombie.crimsonforest.recipe.crude;

import net.minecraft.item.ItemStack;

public record CrudeSoulInfuserRecipe(ItemStack output, ItemStack input, ItemStack soulStack, int cookTicks) {
    public CrudeRecipeData getOutput(ItemStack input, ItemStack soulStack) {
        if (input == null || soulStack == null) return null;
        if (input.isItemEqual(this.input) && soulStack.isItemEqual(this.soulStack)) {
            return new CrudeRecipeData(output, new ItemStack[]{ this.input, this.soulStack }, cookTicks);
        }
        return null;
    }
}
