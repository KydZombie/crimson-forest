package io.github.kydzombie.crimsonforest.block.entity;

import io.github.kydzombie.crimsonforest.recipe.CrudeForgeRecipeData;
import io.github.kydzombie.crimsonforest.recipe.CrudeForgeRecipeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.modificationstation.stationapi.api.recipe.FuelRegistry;

import java.util.Arrays;

public class CrudeForgeBlockEntity extends BlockEntity implements Inventory {
    ItemStack[] inventory = new ItemStack[4];
    private static final float FUEL_MULTIPLIER = 0.25f;
    int cookTotalTime = 0;
    int cookTime = 0;
    int fuelTotalTime = 0;
    int fuelTime = 0;

    @Override
    public int size() {
        return 4;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory[slot];
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
        return "Crude Forge";
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public void tick() {
        ItemStack[] inputItems = Arrays.copyOfRange(inventory, 2, inventory.length);
        CrudeForgeRecipeData output = CrudeForgeRecipeRegistry.getOutput(inputItems);
        if (output != null) {
            this.cookTotalTime = output.cookTicks();
        }

        if (output != null && fuelTime == 0) {
            int itemFuelTime = (int) Math.floor(FuelRegistry.getFuelTime(inventory[0]) * FUEL_MULTIPLIER);
            if (itemFuelTime != 0) {
                fuelTotalTime = itemFuelTime;
                fuelTime = itemFuelTime;
                removeStack(0, 1);
                markDirty();
            } else {
                fuelTotalTime = 0;
            }
        }

        if (fuelTime > 0) {
            fuelTime--;
            if (output != null) {
                cookTime++;

                if (cookTime >= cookTotalTime) {
                    if (inventory[1] != null) {
                        inventory[1].count += output.stack().count;
                    } else {
                        inventory[1] = output.stack().copy();
                    }
                    removeStack(2, 1);
                    removeStack(3, 1);
                    cookTime = 0;
                }
                markDirty();
                return;
            }
        }

        cookTime = 0;
    }

    @Environment(EnvType.CLIENT)
    public int getCookTimeDelta(int multiplier) {
        if (cookTotalTime <= 0) return 0;
        return cookTime * multiplier / cookTotalTime;
    }

    @Environment(EnvType.CLIENT)
    public int getFuelTimeDelta(int multiplier) {
        if (fuelTotalTime == 0) return 0;

        return fuelTime * multiplier / fuelTotalTime;
    }

    public boolean isBurning() {
        return fuelTime > 0;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(x, y, z) != this) {
            return false;
        } else {
            return !(player.getSquaredDistance((double) x + 0.5, (double) y + 0.5, (double) z + 0.5) > 64.0);
        }
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        NbtList var2 = nbt.getList("items");
        this.inventory = new ItemStack[this.size()];

        for(int var3 = 0; var3 < var2.size(); ++var3) {
            NbtCompound var4 = (NbtCompound)var2.get(var3);
            byte var5 = var4.getByte("slot");
            if (var5 >= 0 && var5 < this.inventory.length) {
                this.inventory[var5] = new ItemStack(var4);
            }
        }

        this.cookTime = nbt.getShort("cook_time");
        this.fuelTime = nbt.getShort("fuel_time");
        this.fuelTotalTime = nbt.getShort("fuel_total_time");
    }

    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("cook_time", (short)this.cookTime);
        nbt.putShort("fuel_time", (short)this.fuelTime);
        nbt.putShort("fuel_total_time", (short)this.fuelTotalTime);

        NbtList var2 = new NbtList();

        for(int var3 = 0; var3 < this.inventory.length; ++var3) {
            if (this.inventory[var3] != null) {
                NbtCompound var4 = new NbtCompound();
                var4.putByte("slot", (byte)var3);
                this.inventory[var3].writeNbt(var4);
                var2.add(var4);
            }
        }

        nbt.put("items", var2);
    }
}
