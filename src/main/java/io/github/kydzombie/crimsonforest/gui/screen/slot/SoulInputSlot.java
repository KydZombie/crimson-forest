package io.github.kydzombie.crimsonforest.gui.screen.slot;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.item.SoulItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class SoulInputSlot extends Slot {
    public SoulInputSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (stack == null) return false;
        return stack.getItem() == TheCrimsonForest.soulShardItem || stack.getItem() instanceof SoulItem;
    }
}
