package io.github.kydzombie.crimsonforest.gui.screen;

import io.github.kydzombie.crimsonforest.item.thermos.TunedThermosInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class TunedThermosScreenHandler extends ScreenHandler {
    public final TunedThermosInventory thermosInventory;

    public static int COOLDOWN_TICKS = 5;
    public int cooldown = 0;

    public TunedThermosScreenHandler(PlayerInventory playerInventory, TunedThermosInventory thermosInventory) {
        this.thermosInventory = thermosInventory;

        // TODO: TunedThermosInput and TunedThermosOutput slots.
        addSlot(new Slot(thermosInventory, 0, 55, 35));
        addSlot(new Slot(thermosInventory, 1, 105, 35));

        int var3;
        for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlot(new Slot(playerInventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlot(new Slot(playerInventory, var3, 8 + var3 * 18, 142));
        }
    }

    @Override
    public void onClosed(PlayerEntity player) {
        for (int i = 0; i < thermosInventory.size(); i++) {
            ItemStack stack = thermosInventory.getStack(i);
            if (stack != null) {
                player.dropItem(stack);
            }
        }
        super.onClosed(player);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return thermosInventory.canPlayerUse(player);
    }
}
