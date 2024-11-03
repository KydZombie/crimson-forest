package io.github.kydzombie.crimsonforest.packet;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.gui.screen.TunedThermosScreenHandler;
import io.github.kydzombie.crimsonforest.mixin.NbtCompoundAccessor;
import net.glasslauncher.mods.alwaysmoreitems.network.NetworkHelper;
import net.minecraft.client.network.ClientNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.util.Identifier;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class TunedThermosInventoryPacket extends Packet implements IdentifiablePacket {
    public NbtCompound itemNbt;
    private int dataLength;

    public TunedThermosInventoryPacket() {

    }

    public TunedThermosInventoryPacket(NbtCompound itemNbt) {
        this.itemNbt = itemNbt;
    }

    @Override
    public void read(DataInputStream stream) {
        itemNbt = new NbtCompound();
        ((NbtCompoundAccessor) itemNbt).invokeRead(stream);
    }

    @Override
    public void write(DataOutputStream stream) {
        dataLength = NetworkHelper.writeAndGetNbtLength(itemNbt, stream);
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        if (networkHandler == null) return;
        if (!networkHandler.isServerSide()) {
            PlayerEntity player = PlayerHelper.getPlayerFromPacketHandler(networkHandler);
            if (player.currentScreenHandler instanceof TunedThermosScreenHandler screenHandler) {
                screenHandler.thermosInventory.setThermosStack(new ItemStack(itemNbt));
            }
        }
    }

    @Override
    public int size() {
        return dataLength;
    }

    @Override
    public Identifier getId() {
        return TheCrimsonForest.NAMESPACE.id("tuned_thermos_inventory");
    }
}
