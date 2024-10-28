package io.github.kydzombie.crimsonforest.compat.ami;

import io.github.kydzombie.crimsonforest.recipe.crude.CrudeForgeRecipe;
import io.github.kydzombie.crimsonforest.recipe.crude.CrudeSoulInfuserRecipe;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeHandler;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class CrudeSoulInfuserRecipeHandler implements RecipeHandler<CrudeSoulInfuserRecipe> {
    @Override
    public @NotNull Class<CrudeSoulInfuserRecipe> getRecipeClass() {
        return CrudeSoulInfuserRecipe.class;
    }

    @Override
    public @NotNull String getRecipeCategoryUid() {
        return "crude_soul_infuser";
    }

    @Override
    public @NotNull RecipeWrapper getRecipeWrapper(@NotNull CrudeSoulInfuserRecipe crudeForgeRecipe) {
        return new CrudeSoulInfuserRecipeWrapper(crudeForgeRecipe);
    }

    @Override
    public boolean isRecipeValid(@NotNull CrudeSoulInfuserRecipe crudeForgeRecipe) {
        return true;
    }
}
