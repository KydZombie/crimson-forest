package io.github.kydzombie.crimsonforest.recipe;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CrudeForgeRecipe {
    private final CrudeForgeRecipeData data;
    private final ItemStack[] inputs;

    public CrudeForgeRecipe(ItemStack output, int cookTicks, ItemStack... inputs) {
        this.data = new CrudeForgeRecipeData(output, cookTicks);
        this.inputs = inputs;
    }

    public @Nullable CrudeForgeRecipeData getOutput(ItemStack[] inputs) {
        // Max 1 required per recipe with this system.
        for (int i = 0; i < inputs.length; i++) {
            ItemStack required = this.inputs[i];
            ItemStack given = inputs[i];

            if (required == null || given == null) {
                if (required == null && given == null) {
                    continue;
                } else {
                    return null;
                }
            }

            if (!required.isItemEqual(given)) {
                return null;
            }
        }

        return data;
    }
}
