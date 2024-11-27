package io.github.kydzombie.crimsonforest.block.entity;

import io.github.kydzombie.cairn.api.block.entity.UpdatePacketReceiver;
import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import lombok.Getter;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;

public class MortarAndPestleBlockEntity extends BlockEntity implements UpdatePacketReceiver<MortarAndPestleBlockEntity.MortarAndPestleData> {
    public static final int MAX_ESSENCE = 1000;
    public static String ESSENCE_NBT = TheCrimsonForest.NAMESPACE.id("essence").toString();
    @Getter
    private long essence = 0;

    public void setEssence(long essence) {
        this.essence = Math.max(Math.min(MAX_ESSENCE, essence), 0);
        markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        essence = nbt.getLong(ESSENCE_NBT);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putLong(ESSENCE_NBT, essence);
    }

    @Override
    public void receiveUpdateData(MortarAndPestleData data) {
        essence = data.essence;
    }

    @Override
    public MortarAndPestleData createUpdateData() {
        return new MortarAndPestleData(essence);
    }

    public record MortarAndPestleData(long essence) { }
}
