package io.github.kydzombie.crimsonforest.recipe.crude;

import io.github.kydzombie.crimsonforest.item.VialItem;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrudeForgeRecipe {
    private final ItemStack output;
    private final int cookTicks;
    private final ItemStack[] inputs;

    public CrudeForgeRecipe(ItemStack output, int cookTicks, ItemStack... inputs) {
        this.output = output;
        this.cookTicks = cookTicks;
        this.inputs = inputs;
    }

    public @Nullable CrudeRecipeData getOutput(ItemStack[] inputs) {
        ItemStack[] stacksTaken = new ItemStack[inputs.length];
        // Max 1 required per recipe with this system.
        for (int i = 0; i < inputs.length; i++) {
            ItemStack required = this.inputs[i];
            ItemStack given = inputs[i];

            if (required == null || given == null) {
                if (required == null && given == null) {
                    continue;
                } else {
                    return null;
                }
            }

            if (required.getItem() instanceof VialItem vialItem && given.getItem() instanceof VialItem givenVialItem) {
                List<EssenceType> requiredTypes = vialItem.getEssenceTypes(required);
                List<EssenceType> givenTypes = givenVialItem.getEssenceTypes(given);
                if (requiredTypes.size() != givenTypes.size()) return null;
                if (!requiredTypes.isEmpty()) {
                    if (requiredTypes.get(0) != givenTypes.get(0)) return null;
                    EssenceType essenceType = vialItem.getEssenceTypes(required).get(0);
                    if (vialItem.getEssence(given, essenceType) < vialItem.getEssence(required, essenceType))
                        return null;
                }
            } else if (!required.isItemEqual(given)) {
                return null;
            }

            stacksTaken[i] = this.inputs[i].copy();
        }

        return new CrudeRecipeData(output.copy(), stacksTaken, cookTicks);
    }
}
