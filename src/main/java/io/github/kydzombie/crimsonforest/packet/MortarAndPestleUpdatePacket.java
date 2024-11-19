package io.github.kydzombie.crimsonforest.packet;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MortarAndPestleUpdatePacket extends Packet implements IdentifiablePacket {
    public int x;
    public int y;
    public int z;
    public long essence;

    public static ArrayList<MortarAndPestleUpdatePacket> pending = new ArrayList<>();

    public MortarAndPestleUpdatePacket() {}

    public MortarAndPestleUpdatePacket(int x, int y, int z, long essence) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.essence = essence;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.x = stream.readInt();
            this.y = stream.readInt();
            this.z = stream.readInt();
            this.essence = stream.readLong();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(this.x);
            stream.writeInt(this.y);
            stream.writeInt(this.z);
            stream.writeLong(this.essence);
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
        return 20;
    }

    @Override
    public Identifier getId() {
        return TheCrimsonForest.NAMESPACE.id("mortar_and_pestle_update");
    }
}
