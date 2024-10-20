package io.github.kydzombie.crimsonforest.item.thermos;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TunedThermosInventory implements Inventory {
    @NotNull public final ItemStack thermosStack;
    @NotNull public final TunedThermosItem tunedThermosItem;

    ItemStack[] inventory = new ItemStack[2];

    public TunedThermosInventory() {
        TheCrimsonForest.LOGGER.error("Created an empty infused thermos inventory. This should never be ran!");

        //noinspection DataFlowIssue
        thermosStack = null;
        //noinspection DataFlowIssue
        tunedThermosItem = null;
    }

    public TunedThermosInventory(@NotNull ItemStack thermosStack) {
        this.thermosStack = thermosStack;
        tunedThermosItem = (TunedThermosItem) thermosStack.getItem();
//        arcane = thermosStack.getItem() == TheCrimsonForest.lifeTunedArcaneThermosItem || thermosStack.getItem() == TheCrimsonForest.natureTunedArcaneThermosItem;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory[slot];
    }

    public ItemStack removeStack(int slot, int amount) {
        if (this.inventory[slot] != null) {
            ItemStack var3;
            if (this.inventory[slot].count <= amount) {
                var3 = this.inventory[slot];
                this.inventory[slot] = null;
            } else {
                var3 = this.inventory[slot].split(amount);
                if (this.inventory[slot].count == 0) {
                    this.inventory[slot] = null;
                }
            }
            return var3;
        } else {
            return null;
        }
    }

    public void setStack(int slot, ItemStack stack) {
        this.inventory[slot] = stack;
        if (stack != null && stack.count > this.getMaxCountPerStack()) {
            stack.count = this.getMaxCountPerStack();
        }
    }

    @Override
    public String getName() {
        return I18n.getTranslation(thermosStack.getTranslationKey() + ".name");
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return player.getHand() != null && player.getHand().getItem() instanceof TunedThermosItem;
    }
}
