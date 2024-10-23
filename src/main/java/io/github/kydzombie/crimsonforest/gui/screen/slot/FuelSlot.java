package io.github.kydzombie.crimsonforest.gui.screen.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.modificationstation.stationapi.api.recipe.FuelRegistry;

public class FuelSlot extends Slot {
    public FuelSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return FuelRegistry.getFuelTime(stack) > 0;
    }
}
