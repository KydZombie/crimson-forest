package io.github.kydzombie.crimsonforest.gui.screen;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.block.entity.CrudePressBlockEntity;
import io.github.kydzombie.crimsonforest.gui.screen.slot.FuelSlot;
import io.github.kydzombie.crimsonforest.gui.screen.slot.OutputSlot;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.slot.Slot;

public class CrudePressScreenHandler extends ScreenHandler {
    private final CrudePressBlockEntity blockEntity;

    int cookTotalTime;
    int cookTime;
    int fuelTotalTime;
    int fuelTime;

    public CrudePressScreenHandler(PlayerInventory playerInventory, CrudePressBlockEntity blockEntity) {
        this.blockEntity = blockEntity;

        addSlot(new FuelSlot(blockEntity, 0, 56, 53)); // Fuel
        addSlot(new OutputSlot(blockEntity, 1, 116, 35)); // Output
        addSlot(new Slot(blockEntity, 2, 56, 17)); // Input

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

    @Environment(EnvType.SERVER)
    @Override
    public void addListener(ScreenHandlerListener listener) {
        super.addListener(listener);
        listener.onPropertyUpdate(this, 0, blockEntity.cookTotalTime);
        listener.onPropertyUpdate(this, 1, blockEntity.cookTime);
        listener.onPropertyUpdate(this, 2, blockEntity.fuelTotalTime);
        listener.onPropertyUpdate(this, 3, blockEntity.fuelTime);
    }

    @Override
    public void sendContentUpdates() {
        super.sendContentUpdates();

        for (int id = 0; id < this.listeners.size(); id++) {
            ScreenHandlerListener listener = (ScreenHandlerListener) this.listeners.get(id);
            if (cookTotalTime != blockEntity.cookTotalTime) {
                listener.onPropertyUpdate(this, 0, blockEntity.cookTotalTime);
            }

            if (cookTime != blockEntity.cookTime) {
                listener.onPropertyUpdate(this, 1, blockEntity.cookTime);
            }

            if (fuelTotalTime != blockEntity.fuelTotalTime) {
                listener.onPropertyUpdate(this, 2, blockEntity.fuelTotalTime);
            }

            if (fuelTime != blockEntity.fuelTime) {
                listener.onPropertyUpdate(this, 3, blockEntity.fuelTime);
            }
        }

        cookTotalTime = blockEntity.cookTotalTime;
        cookTime = blockEntity.cookTime;
        fuelTotalTime = blockEntity.fuelTotalTime;
        fuelTime = blockEntity.fuelTime;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void setProperty(int id, int value) {
        switch (id) {
            case 0:
                blockEntity.cookTotalTime = value;
                break;
            case 1:
                blockEntity.cookTime = value;
                break;
            case 2:
                blockEntity.fuelTotalTime = value;
                break;
            case 3:
                blockEntity.fuelTime = value;
                break;
            default:
                TheCrimsonForest.LOGGER.error("Invalid setProperty() on Crude Forge: {} with value {}", id, value);
                break;
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return blockEntity.canPlayerUse(player);
    }
}
