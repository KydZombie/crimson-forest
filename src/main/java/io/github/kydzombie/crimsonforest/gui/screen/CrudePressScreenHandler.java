package io.github.kydzombie.crimsonforest.gui.screen;

import io.github.kydzombie.cairn.api.gui.SyncField;
import io.github.kydzombie.cairn.api.gui.Syncable;
import io.github.kydzombie.cairn.api.gui.SyncedBlockEntity;
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

@Syncable
public class CrudePressScreenHandler extends ScreenHandler {
    @SyncedBlockEntity
    private final CrudePressBlockEntity blockEntity;

    @SyncField("cookTotalTime")
    int cookTotalTime;
    @SyncField("cookTime")
    int cookTime;
    @SyncField("fuelTotalTime")
    int fuelTotalTime;
    @SyncField("fuelTime")
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

    @Override
    public boolean canUse(PlayerEntity player) {
        return blockEntity.canPlayerUse(player);
    }
}
