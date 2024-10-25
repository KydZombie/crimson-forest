package io.github.kydzombie.crimsonforest.recipe;

import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class BasinRecipeRegistry {
    public static final BasinRecipeRegistry INSTANCE = new BasinRecipeRegistry();

    private final ArrayList<BasinRecipe> recipes = new ArrayList<>();

    public void addRecipe(BasinRecipe recipe) {
        recipes.add(recipe);
    }

    public @Nullable BasinRecipe getRecipe(@NotNull ItemStack input, @NotNull EssenceType essenceType) {
        for (BasinRecipe recipe : recipes) {
            if (recipe.input().isItemEqual(input) && recipe.essenceType() == essenceType) {
                return recipe;
            }
        }

        return null;
    }

}
