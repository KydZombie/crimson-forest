package io.github.kydzombie.crimsonforest.compat;

import io.github.kydzombie.crimsonforest.recipe.BasinRecipe;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.language.I18n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BasinRecipeWrapper implements RecipeWrapper {
    private final BasinRecipe recipe;

    public BasinRecipeWrapper(BasinRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public List<?> getInputs() {
        return List.of(recipe.input());
    }

    @Override
    public List<?> getOutputs() {
        return List.of(recipe.output());
    }

    @Override
    public void drawInfo(@NotNull Minecraft minecraft, int i, int i1, int i2, int i3) {
        String essenceText = I18n.getTranslation(recipe.essenceType().translationKey + ".count", recipe.essence());
        minecraft.textRenderer.draw(essenceText, 0, 0, 0xFFFFFF);
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
