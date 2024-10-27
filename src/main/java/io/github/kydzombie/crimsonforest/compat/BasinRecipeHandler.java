package io.github.kydzombie.crimsonforest.compat;

import io.github.kydzombie.crimsonforest.recipe.BasinRecipe;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeHandler;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class BasinRecipeHandler implements RecipeHandler<BasinRecipe> {
    @Override
    public @NotNull Class<BasinRecipe> getRecipeClass() {
        return BasinRecipe.class;
    }

    @Override
    public @NotNull String getRecipeCategoryUid() {
        return "basin";
    }

    @Override
    public @NotNull RecipeWrapper getRecipeWrapper(@NotNull BasinRecipe basinRecipe) {
        return new BasinRecipeWrapper(basinRecipe);
    }

    @Override
    public boolean isRecipeValid(@NotNull BasinRecipe basinRecipe) {
        return true;
    }
}
