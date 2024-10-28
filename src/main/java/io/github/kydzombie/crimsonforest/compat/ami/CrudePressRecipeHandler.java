package io.github.kydzombie.crimsonforest.compat.ami;

import io.github.kydzombie.crimsonforest.recipe.crude.CrudePressRecipe;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeHandler;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class CrudePressRecipeHandler implements RecipeHandler<CrudePressRecipe> {
    @Override
    public @NotNull Class<CrudePressRecipe> getRecipeClass() {
        return CrudePressRecipe.class;
    }

    @Override
    public @NotNull String getRecipeCategoryUid() {
        return "crude_press";
    }

    @Override
    public @NotNull RecipeWrapper getRecipeWrapper(@NotNull CrudePressRecipe crudePressRecipe) {
        return new CrudePressRecipeWrapper(crudePressRecipe);
    }

    @Override
    public boolean isRecipeValid(@NotNull CrudePressRecipe crudePressRecipe) {
        return true;
    }
}
