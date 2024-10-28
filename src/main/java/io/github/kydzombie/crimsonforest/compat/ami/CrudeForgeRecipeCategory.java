package io.github.kydzombie.crimsonforest.compat.ami;

import net.glasslauncher.mods.alwaysmoreitems.api.gui.*;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeCategory;
import net.glasslauncher.mods.alwaysmoreitems.api.recipe.RecipeWrapper;
import net.glasslauncher.mods.alwaysmoreitems.gui.DrawableHelper;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

public class CrudeForgeRecipeCategory implements RecipeCategory {
    @NotNull
    protected final AnimatedDrawable flame;
    @NotNull
    protected final AnimatedDrawable arrow;

    @NotNull
    private final AMIDrawable background = DrawableHelper.createDrawable("/assets/crimsonforest/gui/crude_forge.png", 22, 16, 116, 54);

    public CrudeForgeRecipeCategory() {
        StaticDrawable flameDrawable = DrawableHelper.createDrawable("/assets/crimsonforest/gui/crude_forge.png", 176, 0, 14, 14);
        this.flame = DrawableHelper.createAnimatedDrawable(flameDrawable, 300, AnimatedDrawable.StartDirection.TOP, true);
        StaticDrawable arrowDrawable = DrawableHelper.createDrawable("/assets/crimsonforest/gui/crude_forge.png", 176, 14, 24, 17);
        this.arrow = DrawableHelper.createAnimatedDrawable(arrowDrawable, 200, AnimatedDrawable.StartDirection.LEFT, false);
    }

    @Override
    public @NotNull String getUid() {
        return "crude_forge";
    }

    @Override
    public @NotNull String getTitle() {
        return "Crude Forge";
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
        this.flame.draw(minecraft, 16, 20);
        this.arrow.draw(minecraft, 57, 18);
    }

    @Override
    public void setRecipe(@NotNull RecipeLayout recipeLayout, @NotNull RecipeWrapper recipeWrapper) {
        GuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 0, 0);
        guiItemStacks.init(1, true, 28, 0);
        guiItemStacks.init(2, false, 93, 18);
        guiItemStacks.setFromRecipe(0, recipeWrapper.getInputs().get(0));
        guiItemStacks.setFromRecipe(1, recipeWrapper.getInputs().get(1));
        guiItemStacks.setFromRecipe(2, recipeWrapper.getOutputs());
    }
}
