package io.github.kydzombie.crimsonforest.compat;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.recipe.BasinRecipeRegistry;
import net.glasslauncher.mods.alwaysmoreitems.api.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.Identifier;

public class AMICompat implements ModPluginProvider {
    @Override
    public String getName() {
        return "Crimson Forest";
    }

    @Override
    public Identifier getId() {
        return TheCrimsonForest.NAMESPACE.id("crimsonforest");
    }

    @Override
    public void onAMIHelpersAvailable(AMIHelpers amiHelpers) {

    }

    @Override
    public void onItemRegistryAvailable(ItemRegistry itemRegistry) {

    }

    @Override
    public void register(ModRegistry modRegistry) {
        modRegistry.addDescription(
                new ItemStack(TheCrimsonForest.vialItem),
                "description.crimsonforest.vial"
        );
        modRegistry.addDescription(
                new ItemStack(TheCrimsonForest.mortarAndPestleBlock),
                "description.crimsonforest.mortar_and_pestle"
        );
        modRegistry.addDescription(
                new ItemStack(TheCrimsonForest.stoneBasinBlock),
                "description.crimsonforest.stone_basin_1",
                "description.crimsonforest.stone_basin_2"
        );

        modRegistry.addRecipeCategories(new BasinRecipeCategory());
        modRegistry.addRecipeHandlers(new BasinRecipeHandler());
        modRegistry.addRecipes(
                BasinRecipeRegistry.INSTANCE.recipes
        );
    }

    @Override
    public void onRecipeRegistryAvailable(RecipeRegistry recipeRegistry) {
    }

    @Override
    public SyncableRecipe deserializeRecipe(NbtCompound nbtCompound) {
        return null;
    }
}
