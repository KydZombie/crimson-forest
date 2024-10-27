package io.github.kydzombie.crimsonforest.recipe.crude;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CrudeSoulInfuserRecipeRegistry extends CrudeRecipeRegistry {
    public static final CrudeSoulInfuserRecipeRegistry INSTANCE = new CrudeSoulInfuserRecipeRegistry();

    private final ArrayList<CrudeSoulInfuserRecipe> recipes = new ArrayList<>();

    public void addRecipe(CrudeSoulInfuserRecipe recipe) {
        recipes.add(recipe);
    }

    @Override
    public @Nullable CrudeRecipeData getOutput(ItemStack[] inputs) {
        for (CrudeSoulInfuserRecipe recipe : recipes) {
            CrudeRecipeData output = recipe.getOutput(inputs[0], inputs[1]);
            if (output != null) {
                return output;
            }
        }

        return null;
    }
}
