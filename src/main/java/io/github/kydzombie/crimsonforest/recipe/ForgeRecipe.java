package io.github.kydzombie.crimsonforest.recipe;

import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ForgeRecipe {
    private final ItemStack output;
    private final EssenceType essenceType;
    private final int essenceCost;
    private final ItemStack[] inputs;
    private final int cookTicks;

    public ForgeRecipe(ItemStack output, EssenceType essenceType, int cost, int cookTicks, ItemStack... inputs) {
        this.output = output;
        this.essenceType = essenceType;
        this.essenceCost = cost;
        this.inputs = inputs;
        this.cookTicks = cookTicks;
    }

    public @Nullable ForgeRecipeData getOutput(ItemStack[] inputs, EssenceType essenceType) {
        if (this.essenceType != essenceType) return null;

        // Max 1 required per recipe with this system.
        for (int i = 0; i < inputs.length; i++) {
            ItemStack required = this.inputs[i];
            ItemStack given = inputs[i];

            if (required == null) {
                if (given == null) {
                    continue;
                } else {
                    return null;
                }
            }

            if (!required.isItemEqual(given)) {
                return null;
            }
        }

        return new ForgeRecipeData(output, essenceType, essenceCost, cookTicks);
    }
}

