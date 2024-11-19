package io.github.kydzombie.crimsonforest.item;

import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface HasItemEssenceStorage {
    List<EssenceType> getEssenceTypes(ItemStack stack);

    long getMaxEssence(ItemStack stack, EssenceType type);

    long getEssence(ItemStack stack, EssenceType type);

    void setEssence(ItemStack stack, EssenceType type, long value);

    default boolean canGiveEssence(ItemStack stack, EssenceType type) {
        return true;
    }

    default long giveEssence(ItemStack stack, EssenceType type, long amount, boolean simulate) {
        long current = getEssence(stack, type);
        long given = Math.min(amount, getMaxEssence(stack, type) - current);
        if (!simulate) {
            setEssence(stack, type, current + given);
        }
        return given;
    }

    default long giveEssence(ItemStack stack, EssenceType type, long amount) {
        return giveEssence(stack, type, amount, false);
    }

    default boolean canTakeEssence(ItemStack stack, EssenceType type) {
        return true;
    }

    default long takeEssence(ItemStack stack, EssenceType type, long amount, boolean simulate) {
        long current = getEssence(stack, type);
        long taken = Math.min(amount, current);
        if (!simulate) {
            setEssence(stack, type, current - taken);
        }
        return taken;
    }

    default long takeEssence(ItemStack stack, EssenceType type, long amount) {
        return takeEssence(stack, type, amount, false);
    }
}
