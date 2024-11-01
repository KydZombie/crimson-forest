package io.github.kydzombie.crimsonforest.item;

import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface EssenceContainer {
    List<EssenceType> getEssenceTypes(ItemStack stack);

    int getMaxEssence(ItemStack stack, EssenceType type);

    int getEssence(ItemStack stack, EssenceType type);

    void setEssence(ItemStack stack, EssenceType type, int value);

    default boolean canGiveEssence(ItemStack stack, EssenceType type) {
        return true;
    }

    default int giveEssence(ItemStack stack, EssenceType type, int amount, boolean simulate) {
        int current = getEssence(stack, type);
        int given = Math.min(amount, getMaxEssence(stack, type) - current);
        if (!simulate) {
            setEssence(stack, type, current + given);
        }
        return given;
    }

    default int giveEssence(ItemStack stack, EssenceType type, int amount) {
        return giveEssence(stack, type, amount, false);
    }

    default boolean canTakeEssence(ItemStack stack, EssenceType type) {
        return true;
    }

    default int takeEssence(ItemStack stack, EssenceType type, int amount, boolean simulate) {
        int current = getEssence(stack, type);
        int taken = Math.min(amount, current);
        if (!simulate) {
            setEssence(stack, type, current - taken);
        }
        return taken;
    }

    default int takeEssence(ItemStack stack, EssenceType type, int amount) {
        return takeEssence(stack, type, amount, false);
    }
}
