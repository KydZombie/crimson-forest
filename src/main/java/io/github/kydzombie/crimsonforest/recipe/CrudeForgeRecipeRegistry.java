package io.github.kydzombie.crimsonforest.recipe;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CrudeForgeRecipeRegistry {
    private static final ArrayList<CrudeForgeRecipe> recipes = new ArrayList<>();

    public static void addRecipe(CrudeForgeRecipe recipe) {
        recipes.add(recipe);
    }

    public static @Nullable CrudeForgeRecipeData getOutput(ItemStack[] inputs) {
        for (CrudeForgeRecipe recipe : recipes) {
            CrudeForgeRecipeData output = recipe.getOutput(inputs);
            if (output != null) {
                return output;
            }
        }

        return null;
    }
}
