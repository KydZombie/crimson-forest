package io.github.kydzombie.crimsonforest.gui.screen.slot;

import io.github.kydzombie.crimsonforest.item.EssenceContainer;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class TunedThermosInputSlot extends Slot {
    final EssenceType essenceType;

    public TunedThermosInputSlot(Inventory inventory, int index, int x, int y, EssenceType essenceType) {
        super(inventory, index, x, y);
        this.essenceType = essenceType;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (stack != null && stack.getItem() instanceof EssenceContainer container) {
            return container.canGiveEssence(stack, essenceType) || container.canTakeEssence(stack, essenceType);
        }
        return false;
    }
}
