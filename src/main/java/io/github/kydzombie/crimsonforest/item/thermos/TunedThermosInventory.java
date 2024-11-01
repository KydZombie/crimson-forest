package io.github.kydzombie.crimsonforest.item.thermos;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import lombok.Getter;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TunedThermosInventory implements Inventory {
    @Getter
    private boolean ready;
    public ItemStack thermosStack;
    public TunedThermosItem thermosItem;
    public EssenceType essenceType;

    ItemStack[] inventory = new ItemStack[2];

    public TunedThermosInventory() {
        TheCrimsonForest.LOGGER.error("Created an empty infused thermos inventory. This should never be ran!");

        thermosStack = null;
        thermosItem = null;
        essenceType = null;
        ready = false;
    }

    public TunedThermosInventory(@NotNull ItemStack thermosStack) {
        setThermosStack(thermosStack);
//        this.thermosStack = thermosStack;
//        thermosItem = (TunedThermosItem) thermosStack.getItem();
//        essenceType = thermosItem.getEssenceTypes(thermosStack).get(0);
//        ready = true;
//        arcane = thermosStack.getItem() == TheCrimsonForest.lifeTunedArcaneThermosItem || thermosStack.getItem() == TheCrimsonForest.natureTunedArcaneThermosItem;
    }

    public void setThermosStack(ItemStack stack) {
        thermosStack = stack;
        thermosItem = (TunedThermosItem) thermosStack.getItem();
        essenceType = thermosItem.getEssenceTypes(thermosStack).get(0);
        ready = true;
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
        if (!ready) return "";
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
