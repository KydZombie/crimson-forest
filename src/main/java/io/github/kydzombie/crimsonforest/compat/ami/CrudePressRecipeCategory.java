package io.github.kydzombie.crimsonforest.compat.ami;

import net.glasslauncher.mods.alwaysmoreitems.api.gui.*;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeCategory;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.glasslauncher.mods.alwaysmoreitems.gui.DrawableHelper;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

public class CrudePressRecipeCategory implements RecipeCategory {
    @NotNull
    protected final AnimatedDrawable flame;
    @NotNull
    protected final AnimatedDrawable arrow;

    @NotNull
    private final AMIDrawable background = DrawableHelper.createDrawable("/assets/crimsonforest/gui/crude_press.png", 55, 16, 82, 54);

    public CrudePressRecipeCategory() {
        StaticDrawable flameDrawable = DrawableHelper.createDrawable("/assets/crimsonforest/gui/crude_press.png", 176, 0, 14, 14);
        this.flame = DrawableHelper.createAnimatedDrawable(flameDrawable, 300, AnimatedDrawable.StartDirection.TOP, true);
        StaticDrawable arrowDrawable = DrawableHelper.createDrawable("/assets/crimsonforest/gui/crude_press.png", 176, 14, 24, 17);
        this.arrow = DrawableHelper.createAnimatedDrawable(arrowDrawable, 200, AnimatedDrawable.StartDirection.LEFT, false);
    }

    @Override
    public @NotNull String getUid() {
        return "crude_press";
    }

    @Override
    public @NotNull String getTitle() {
        return "Crude Press";
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
        this.flame.draw(minecraft, 2, 20);
        this.arrow.draw(minecraft, 24, 18);
    }

    @Override
    public void setRecipe(@NotNull RecipeLayout recipeLayout, @NotNull RecipeWrapper recipeWrapper) {
        GuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 0, 0);
        guiItemStacks.init(2, false, 60, 18);
        guiItemStacks.setFromRecipe(0, recipeWrapper.getInputs());
        guiItemStacks.setFromRecipe(2, recipeWrapper.getOutputs());
    }
}
