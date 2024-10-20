package io.github.kydzombie.crimsonforest.recipe;

import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ForgeRecipeRegistry {
    private static final ArrayList<ForgeRecipe> recipes = new ArrayList<>();

    public static void addRecipe(final ForgeRecipe recipe) {
        recipes.add(recipe);
    }

    public static @Nullable ForgeRecipeData getOutput(ItemStack[] inputs, EssenceType essenceType) {
        for (ForgeRecipe recipe : recipes) {
            ForgeRecipeData output = recipe.getOutput(inputs, essenceType);
            if (output != null) {
                return output;
            }
        }

        return null;
    }
}
