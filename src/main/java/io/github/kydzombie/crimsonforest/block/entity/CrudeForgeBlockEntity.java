package io.github.kydzombie.crimsonforest.block.entity;

import io.github.kydzombie.crimsonforest.item.VialItem;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import io.github.kydzombie.crimsonforest.recipe.CrudeForgeRecipeRegistry;
import io.github.kydzombie.crimsonforest.recipe.CrudeRecipeData;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrudeForgeBlockEntity extends CrudeMachineBlockEntity implements SimpleInventory {
    public CrudeForgeBlockEntity() {
        super(2, 0.25f, CrudeForgeRecipeRegistry.INSTANCE);
    }

    @Override
    public String getName() {
        return "Crude Forge";
    }

    @Override
    protected void takeInputItems(@NotNull CrudeRecipeData output) {
        ItemStack[] inputStacksTaken = output.inputStacksTaken();
        for (int i = 0; i < inputStacksTaken.length; i++) {
            ItemStack stack = inputStacksTaken[i];
            if (stack == null) continue;
            if (stack.getItem() instanceof VialItem vialItem) {
                ItemStack existingStack = inventory[2 + i];
                EssenceType essenceType = vialItem.getEssenceTypes(stack).get(0);
                vialItem.takeEssence(existingStack, essenceType, vialItem.getEssence(stack, essenceType));
            } else {
                removeStack(2 + i, stack.count);
            }
        }
    }

    @Override
    protected @Nullable CrudeRecipeData getOutput(ItemStack[] inputItems) {
        CrudeRecipeData output = super.getOutput(inputItems);
        if (inputItems[1] != null && inputItems[1].getItem() instanceof VialItem) {
            if (inputItems[1].count > 1) {
                return null;
            }
        }
        return output;
    }
}
