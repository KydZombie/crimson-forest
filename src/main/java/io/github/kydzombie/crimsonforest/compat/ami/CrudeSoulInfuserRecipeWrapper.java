package io.github.kydzombie.crimsonforest.compat.ami;

import io.github.kydzombie.crimsonforest.recipe.crude.CrudeForgeRecipe;
import io.github.kydzombie.crimsonforest.recipe.crude.CrudeSoulInfuserRecipe;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrudeSoulInfuserRecipeWrapper implements RecipeWrapper {
    private final CrudeSoulInfuserRecipe recipe;

    public CrudeSoulInfuserRecipeWrapper(CrudeSoulInfuserRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public List<?> getInputs() {
        return List.of(recipe.input(), recipe.soulStack());
    }

    @Override
    public List<?> getOutputs() {
        return List.of(recipe.output());
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int i, int i1, int i2, int i3) {

    }

    @Override
    public void drawAnimations(@NotNull Minecraft minecraft, int i, int i1) {

    }

    @Override
    public @Nullable ArrayList<Object> getTooltip(int i, int i1) {
        return null;
    }

    @Override
    public boolean handleClick(@NotNull Minecraft minecraft, int i, int i1, int i2) {
        return false;
    }
}
