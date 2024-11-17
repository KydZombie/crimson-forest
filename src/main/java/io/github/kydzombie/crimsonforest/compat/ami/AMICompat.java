package io.github.kydzombie.crimsonforest.compat.ami;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.item.SoulItem;
import io.github.kydzombie.crimsonforest.recipe.BasinRecipeRegistry;
import io.github.kydzombie.crimsonforest.recipe.crude.CrudeForgeRecipeRegistry;
import io.github.kydzombie.crimsonforest.recipe.crude.CrudePressRecipeRegistry;
import io.github.kydzombie.crimsonforest.recipe.crude.CrudeSoulInfuserRecipeRegistry;
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
        modRegistry.addDescription(
                new ItemStack(TheCrimsonForest.soulShardItem),
                "description.crimsonforest.soul_shard_drop"
        );
        modRegistry.addDescription(
                SoulItem.SOUL_TYPES.stream().map(ItemStack::new).toList(),
                "description.crimsonforest.soul_drop"
        );

        modRegistry.addRecipeCategories(new BasinRecipeCategory());
        modRegistry.addRecipeHandlers(new BasinRecipeHandler());
        modRegistry.addRecipes(
                BasinRecipeRegistry.INSTANCE.recipes
        );

        modRegistry.addRecipeCategories(new CrudePressRecipeCategory());
        modRegistry.addRecipeHandlers(new CrudePressRecipeHandler());
        modRegistry.addRecipes(
                CrudePressRecipeRegistry.INSTANCE.recipes
        );

        modRegistry.addRecipeCategories(new CrudeForgeRecipeCategory());
        modRegistry.addRecipeHandlers(new CrudeForgeRecipeHandler());
        modRegistry.addRecipes(
                CrudeForgeRecipeRegistry.INSTANCE.recipes
        );

        modRegistry.addRecipeCategories(new CrudeSoulInfuserRecipeCategory());
        modRegistry.addRecipeHandlers(new CrudeSoulInfuserRecipeHandler());
        modRegistry.addRecipes(
                CrudeSoulInfuserRecipeRegistry.INSTANCE.recipes
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
