package io.github.kydzombie.crimsonforest.gui.screen.slot;

import io.github.kydzombie.crimsonforest.item.VialItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class CrudeForgeInputSlot extends Slot {
    private boolean wasVial = false;

    public CrudeForgeInputSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        wasVial = stack != null && stack.getItem() instanceof VialItem;
        return super.canInsert(stack);
    }

    @Override
    public int getMaxItemCount() {
        if (wasVial) return 1;
        return super.getMaxItemCount();
    }
}
