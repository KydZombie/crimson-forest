package io.github.kydzombie.crimsonforest.recipe.crude;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CrudePressRecipeRegistry extends CrudeRecipeRegistry {
    public static final CrudePressRecipeRegistry INSTANCE = new CrudePressRecipeRegistry();

    private final ArrayList<CrudePressRecipe> recipes = new ArrayList<>();

    public void addRecipe(CrudePressRecipe recipe) {
        recipes.add(recipe);
    }

    @Override
    public @Nullable CrudeRecipeData getOutput(ItemStack[] inputs) {
        for (CrudePressRecipe recipe : recipes) {
            CrudeRecipeData output = recipe.getOutput(inputs[0]);
            if (output != null) {
                return output;
            }
        }

        return null;
    }
}
