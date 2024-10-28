package io.github.kydzombie.crimsonforest.compat.ami;

import io.github.kydzombie.crimsonforest.recipe.crude.CrudeForgeRecipe;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrudeForgeRecipeWrapper implements RecipeWrapper {
    private final CrudeForgeRecipe recipe;

    public CrudeForgeRecipeWrapper(CrudeForgeRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public List<?> getInputs() {
        return Arrays.asList(recipe.getInputs());
    }

    @Override
    public List<?> getOutputs() {
        return List.of(recipe.getOutput());
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
