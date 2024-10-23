package io.github.kydzombie.crimsonforest.gui.screen;

import io.github.kydzombie.crimsonforest.gui.screen.slot.OutputSlot;
import io.github.kydzombie.crimsonforest.gui.screen.slot.TunedThermosInputSlot;
import io.github.kydzombie.crimsonforest.item.EssenceContainer;
import io.github.kydzombie.crimsonforest.item.thermos.TunedThermosInventory;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class TunedThermosScreenHandler extends ScreenHandler implements TickableScreenHandler {
    public static int COOLDOWN_TICKS = 5;
    public final TunedThermosInventory thermosInventory;
    public int cooldown = 0;

    public TunedThermosScreenHandler(PlayerInventory playerInventory, TunedThermosInventory thermosInventory) {
        this.thermosInventory = thermosInventory;

        addSlot(new TunedThermosInputSlot(thermosInventory, 0, 55, 35, thermosInventory.essenceType));
        addSlot(new OutputSlot(thermosInventory, 1, 105, 35));

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
        System.out.println("Closed");
        super.onClosed(player);
        if (!player.world.isRemote) {
            for (int i = 0; i < thermosInventory.size(); i++) {
                ItemStack stack = thermosInventory.getStack(i);
                if (stack != null) {
                    player.dropItem(stack);
                }
            }
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return thermosInventory.canPlayerUse(player);
    }

    public void tick() {
        if (cooldown > 0) {
            cooldown--;
            return;
        }

        EssenceType thermosEssenceType = thermosInventory.essenceType;
        int thermosEssence = thermosInventory.thermosItem.getEssence(thermosInventory.thermosStack, thermosEssenceType);
        int thermosMaxEssence = thermosInventory.thermosItem.getMaxEssence(thermosInventory.thermosStack, thermosEssenceType);
        ItemStack inputStack = thermosInventory.getStack(0);
        ItemStack outputStack = thermosInventory.getStack(1);
        if (inputStack == null || (outputStack != null && outputStack.count >= outputStack.getMaxCount())) return;
        if (outputStack != null) return; // TODO: Stacks of vials
        if (inputStack.getItem() instanceof EssenceContainer container) {
            int containerMaxEssence = container.getMaxEssence(inputStack, thermosEssenceType);
            int containerEssence = container.getEssence(inputStack, thermosEssenceType);
            ItemStack newOutput = inputStack.copy();
            if (containerEssence > 0 && thermosEssence < thermosMaxEssence) { // Extract essence from item
                if (container.canTakeEssence(newOutput, thermosEssenceType)) {
                    int amountTaken = container.takeEssence(newOutput, thermosEssenceType, thermosMaxEssence - thermosEssence);
                    thermosInventory.thermosItem.giveEssence(thermosInventory.thermosStack, thermosEssenceType, amountTaken);
                }
            } else { // Insert essence into item
                if (container.canGiveEssence(newOutput, thermosEssenceType)) {
                    int amountTaken = thermosInventory.thermosItem.takeEssence(thermosInventory.thermosStack, thermosEssenceType, containerMaxEssence - containerEssence);
                    container.giveEssence(newOutput, thermosEssenceType, amountTaken);
                }
            }

            newOutput.count = 1;
            thermosInventory.removeStack(0, 1);
            thermosInventory.setStack(1, newOutput);

            cooldown = TunedThermosScreenHandler.COOLDOWN_TICKS;
        }
    }
}
