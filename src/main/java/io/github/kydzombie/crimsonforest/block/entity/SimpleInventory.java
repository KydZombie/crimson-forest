package io.github.kydzombie.crimsonforest.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public interface SimpleInventory extends Inventory {
    ItemStack[] getInventory();

    void setInventory(ItemStack[] inventory);

    @Override
    default int size() {
        return getInventory().length;
    }

    @Override
    default ItemStack getStack(int slot) {
        return getInventory()[slot];
    }

    @Override
    default ItemStack removeStack(int slot, int amount) {
        if (getInventory()[slot] != null) {
            ItemStack stack;
            if (getInventory()[slot].count <= amount) {
                stack = getInventory()[slot];
                getInventory()[slot] = null;
            } else {
                stack = getInventory()[slot].split(amount);
                if (getInventory()[slot].count == 0) {
                    getInventory()[slot] = null;
                }
            }
            return stack;
        } else {
            return null;
        }
    }

    @Override
    default void setStack(int slot, ItemStack stack) {
        getInventory()[slot] = stack;
        if (stack != null && stack.count > getMaxCountPerStack()) {
            stack.count = getMaxCountPerStack();
        }
    }

    @Override
    default int getMaxCountPerStack() {
        return 64;
    }

    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        if (!(this instanceof BlockEntity)) return false;

        var blockEntity = (BlockEntity) (this);
        if (player.world.getBlockEntity(blockEntity.x, blockEntity.y, blockEntity.z) != blockEntity) {
            return false;
        } else {
            return !(player.getSquaredDistance((double) blockEntity.x + 0.5, (double) blockEntity.y + 0.5, (double) blockEntity.z + 0.5) > 64.0);
        }
    }

    default void readInventoryNbt(NbtCompound nbt) {
        NbtList inventoryList = nbt.getList("items");
        var inventory = new ItemStack[size()];

        for (int var3 = 0; var3 < inventoryList.size(); ++var3) {
            NbtCompound itemNbt = (NbtCompound) inventoryList.get(var3);
            byte slot = itemNbt.getByte("slot");
            if (slot >= 0 && slot < inventory.length) {
                inventory[slot] = new ItemStack(itemNbt);
            }
        }

        setInventory(inventory);
    }

    default void writeInventoryNbt(NbtCompound nbt) {
        NbtList inventoryList = new NbtList();
        var inventory = getInventory();

        for (int slot = 0; slot < inventory.length; ++slot) {
            if (inventory[slot] != null) {
                NbtCompound itemNbt = new NbtCompound();
                itemNbt.putByte("slot", (byte) slot);
                inventory[slot].writeNbt(itemNbt);
                inventoryList.add(itemNbt);
            }
        }

        nbt.put("items", inventoryList);
    }
}
