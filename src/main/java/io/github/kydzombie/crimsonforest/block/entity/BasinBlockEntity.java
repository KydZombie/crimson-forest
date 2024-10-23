package io.github.kydzombie.crimsonforest.block.entity;

import io.github.kydzombie.crimsonforest.magic.EssenceType;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class BasinBlockEntity extends BlockEntity implements SimpleInventory {
    @Getter
    @Setter
    ItemStack[] inventory = new ItemStack[1];
    @Getter
    private EssenceType essenceType;
    public static final int MAX_ESSENCE = 75;
    @Getter
    private int essence = 0;

    @Environment(EnvType.CLIENT)
    public ItemEntity renderedItem = null;

    public void setEssence(EssenceType essenceType, int essence) {
        if (essenceType == null || essence <= 0) {
            this.essenceType = null;
            this.essence = 0;
        } else {
            this.essenceType = essenceType;
            this.essence = essence;
        }
        markDirty();
    }

    @Override
    public String getName() {
        return "Basin";
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            renderedItem = null;
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        readInventoryNbt(nbt);
        int essenceType = nbt.contains("essence_type") ? nbt.getByte("essence_type") : -1;
        if (essence == -1) {
            this.essenceType = null;
        } else {
            this.essenceType = EssenceType.values()[essenceType];
        }
        essence = nbt.getInt("essence_amount");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        writeInventoryNbt(nbt);
        if (essenceType == null) {
            nbt.putByte("essence_type", (byte) -1);
        } else {
            nbt.putByte("essence_type", (byte) essenceType.ordinal());
        }
        nbt.putInt("essence_amount", essence);
    }
}
