package io.github.kydzombie.crimsonforest.packet;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import io.github.kydzombie.crimsonforest.mixin.NbtCompoundAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.glasslauncher.mods.alwaysmoreitems.network.NetworkHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BasinBlockUpdatePacket extends Packet implements IdentifiablePacket {
    public int x;
    public int y;
    public int z;
    public EssenceType essenceType;
    public int essence;
    public NbtCompound itemNbt;
    private int dataLength;

    public static ArrayList<BasinBlockUpdatePacket> pending = new ArrayList<>();

    public BasinBlockUpdatePacket() {}

    public BasinBlockUpdatePacket(int x, int y, int z, EssenceType essenceType, int essence, NbtCompound itemNbt) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.essenceType = essenceType;
        this.essence = essence;
        this.itemNbt = itemNbt;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            x = stream.readInt();
            y = stream.readInt();
            z = stream.readInt();
            essenceType = switch (stream.readInt()) {
                case 0 -> null;
                case 1 -> EssenceType.LIFE;
                case 2 -> EssenceType.NATURE;
                default -> null;
            };
            essence = stream.readInt();
            itemNbt = new NbtCompound();
            ((NbtCompoundAccessor) itemNbt).invokeRead(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(x);
            stream.writeInt(y);
            stream.writeInt(z);
            if (essenceType != null) {
                switch (essenceType) {
                    case LIFE -> stream.writeInt(1);
                    case NATURE -> stream.writeInt(2);
                }
            } else {
                stream.writeInt(0);
            }
            stream.writeInt(essence);
            this.dataLength = NetworkHelper.writeAndGetNbtLength(this.itemNbt, stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        if (!networkHandler.isServerSide()) {
            clientApply(networkHandler);
        }
    }

    @Environment(EnvType.CLIENT)
    private void clientApply(NetworkHandler networkHandler) {
        pending.add(this);
    }

    @Override
    public int size() {
        return 16 + dataLength;
    }

    @Override
    public Identifier getId() {
        return TheCrimsonForest.NAMESPACE.id("basin_block_update");
    }
}
