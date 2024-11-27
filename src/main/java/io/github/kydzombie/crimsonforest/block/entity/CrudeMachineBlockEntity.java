package io.github.kydzombie.crimsonforest.block.entity;

import io.github.kydzombie.cairn.api.gui.SyncField;
import io.github.kydzombie.cairn.api.storage.AutoNbt;
import io.github.kydzombie.cairn.api.storage.HasItemStorage;
import io.github.kydzombie.cairn.api.storage.ItemStorage;
import io.github.kydzombie.crimsonforest.recipe.crude.CrudeRecipeData;
import io.github.kydzombie.crimsonforest.recipe.crude.CrudeRecipeRegistry;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.recipe.FuelRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public abstract class CrudeMachineBlockEntity extends BlockEntity implements HasItemStorage {
    private static final int FUEL_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private final float FUEL_MULTIPLIER;

    @SyncField("cookTotalTime")
    public int cookTotalTime = 0;
    @SyncField("cookTime")
    public int cookTime = 0;
    @SyncField("fuelTotalTime")
    public int fuelTotalTime = 0;
    @SyncField("fuelTime")
    public int fuelTime = 0;
    @AutoNbt
    @Getter
    private final ItemStorage itemStorage;
    CrudeRecipeRegistry recipeRegistry;

    public CrudeMachineBlockEntity(int inputsSize, float fuelMultiplier, CrudeRecipeRegistry recipeRegistry) {
        itemStorage = new ItemStorage(inputsSize + 2);
        this.FUEL_MULTIPLIER = fuelMultiplier;
        this.recipeRegistry = recipeRegistry;
    }

    protected void takeInputItems(@NotNull CrudeRecipeData output) {
        ItemStack[] inputStacksTaken = output.inputStacksTaken();
        for (int i = 0; i < inputStacksTaken.length; i++) {
            ItemStack stack = inputStacksTaken[i];
            if (stack == null) continue;
            removeStack(2 + i, stack.count);
        }
    }

    protected @Nullable CrudeRecipeData getOutput(ItemStack[] inputItems) {
        CrudeRecipeData output = recipeRegistry.getOutput(inputItems);
        if (output != null) {
            ItemStack existingOutput = getStack(OUTPUT_SLOT);
            ItemStack outputStack = output.outputStack();
            if (existingOutput != null) {
                if (!outputStack.isItemEqual(existingOutput) ||
                        existingOutput.count + outputStack.count > existingOutput.getMaxCount()) {
                    output = null;
                }
            }

        }
        return output;
    }

    @Override
    public void tick() {
        ItemStorage inputItems = itemStorage.copy(2);
        CrudeRecipeData output = getOutput(inputItems.getStacks());

        if (output != null) {
            this.cookTotalTime = output.cookTicks();
        }

        if (output != null && fuelTime == 0) {
            int itemFuelTime = (int) Math.floor(FuelRegistry.getFuelTime(getStack(FUEL_SLOT)) * FUEL_MULTIPLIER);
            if (itemFuelTime != 0) {
                fuelTotalTime = itemFuelTime;
                fuelTime = itemFuelTime;
                removeStack(FUEL_SLOT, OUTPUT_SLOT);
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
                    ItemStack stack = getStack(OUTPUT_SLOT);
                    if (stack != null) {
                        stack.count += output.outputStack().count;
                    } else {
                        setStack(OUTPUT_SLOT, output.outputStack().copy());
                    }

                    takeInputItems(output);

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

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.cookTime = nbt.getShort("cook_time");
        this.fuelTime = nbt.getShort("fuel_time");
        this.fuelTotalTime = nbt.getShort("fuel_total_time");
    }

    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.putShort("cook_time", (short) this.cookTime);
        nbt.putShort("fuel_time", (short) this.fuelTime);
        nbt.putShort("fuel_total_time", (short) this.fuelTotalTime);
    }
}
