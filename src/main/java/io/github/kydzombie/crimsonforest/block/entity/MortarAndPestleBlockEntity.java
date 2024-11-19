package io.github.kydzombie.crimsonforest.block.entity;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.packet.MortarAndPestleUpdatePacket;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.Packet;

public class MortarAndPestleBlockEntity extends BlockEntity {
    public static final int MAX_ESSENCE = 1000;
    public static String ESSENCE_NBT = TheCrimsonForest.NAMESPACE.id("essence").toString();
    private long essence = 0;

    public long getEssence() {
        return essence;
    }

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
    public Packet createUpdatePacket() {
        return new MortarAndPestleUpdatePacket(x, y, z, essence);
    }
}
