package io.github.kydzombie.crimsonforest.compat.ami;

import net.glasslauncher.mods.alwaysmoreitems.api.gui.*;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeCategory;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.glasslauncher.mods.alwaysmoreitems.gui.DrawableHelper;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class BasinRecipeCategory implements RecipeCategory {
    public static final int recipeWidth = 82;
    public static final int recipeHeight = 34;

    @NotNull
    private final AMIDrawable background;

    @Nonnull
    protected final AnimatedDrawable arrow;

    public BasinRecipeCategory() {
        background = DrawableHelper.createBlankDrawable(recipeWidth, recipeHeight);

        StaticDrawable arrowDrawable = DrawableHelper.createDrawable("/gui/furnace.png", 176, 14, 24, 17);
        arrow = DrawableHelper.createAnimatedDrawable(arrowDrawable, 200, AnimatedDrawable.StartDirection.LEFT, false);
    }

    @Override
    public @NotNull String getUid() {
        return "basin";
    }

    @Override
    public @NotNull String getTitle() {
        return "Stone Basin";
    }

    @Override
    public @NotNull AMIDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

    }

    @Override
    public void drawAnimations(Minecraft minecraft) {
        this.arrow.draw(minecraft, 24, 16);
    }

    @Override
    public void setRecipe(@NotNull RecipeLayout recipeLayout, @NotNull RecipeWrapper recipeWrapper) {
        GuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, true, 0, 16);
        guiItemStacks.init(1, false, 60, 16);
        guiItemStacks.setFromRecipe(0, recipeWrapper.getInputs());
        guiItemStacks.setFromRecipe(1, recipeWrapper.getOutputs());
    }
}
