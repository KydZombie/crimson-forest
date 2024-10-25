package io.github.kydzombie.crimsonforest.recipe.crude;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CrudeForgeRecipeRegistry extends CrudeRecipeRegistry {
    public static final CrudeForgeRecipeRegistry INSTANCE = new CrudeForgeRecipeRegistry();

    private final ArrayList<CrudeForgeRecipe> recipes = new ArrayList<>();

    public void addRecipe(CrudeForgeRecipe recipe) {
        recipes.add(recipe);
    }

    public @Nullable CrudeRecipeData getOutput(ItemStack[] inputs) {
        for (CrudeForgeRecipe recipe : recipes) {
            CrudeRecipeData output = recipe.getOutput(inputs);
            if (output != null) {
                return output;
            }
        }

        return null;
    }
}
