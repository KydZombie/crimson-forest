package io.github.kydzombie.crimsonforest.block.entity;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;

public class MortarAndPestleBlockEntity extends BlockEntity {
    public static String ESSENCE_NBT = TheCrimsonForest.NAMESPACE.id("essence").toString();
    public static final int MAX_ESSENCE = 1000;
    private int essence = 0;

    public int getEssence() {
        return essence;
    }

    public void setEssence(int essence) {
        this.essence = Math.max(Math.min(MAX_ESSENCE, essence), 0);
        markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        essence = nbt.getInt(ESSENCE_NBT);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt(ESSENCE_NBT, essence);
    }
}
