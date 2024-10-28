package io.github.kydzombie.crimsonforest.compat.ami;

import io.github.kydzombie.crimsonforest.recipe.crude.CrudeForgeRecipe;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeHandler;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class CrudeForgeRecipeHandler implements RecipeHandler<CrudeForgeRecipe> {
    @Override
    public @NotNull Class<CrudeForgeRecipe> getRecipeClass() {
        return CrudeForgeRecipe.class;
    }

    @Override
    public @NotNull String getRecipeCategoryUid() {
        return "crude_forge";
    }

    @Override
    public @NotNull RecipeWrapper getRecipeWrapper(@NotNull CrudeForgeRecipe crudeForgeRecipe) {
        return new CrudeForgeRecipeWrapper(crudeForgeRecipe);
    }

    @Override
    public boolean isRecipeValid(@NotNull CrudeForgeRecipe crudeForgeRecipe) {
        return true;
    }
}
