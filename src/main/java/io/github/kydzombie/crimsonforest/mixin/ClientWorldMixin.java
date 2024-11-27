package io.github.kydzombie.crimsonforest.mixin;

import io.github.kydzombie.crimsonforest.block.entity.BasinBlockEntity;
import io.github.kydzombie.crimsonforest.packet.BasinBlockUpdatePacket;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ClientWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.storage.WorldStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin extends World {
    public ClientWorldMixin(WorldStorage dimensionData, String name, Dimension dimension, long seed) {
        super(dimensionData, name, dimension, seed);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void updateBlockEntities(CallbackInfo ci) {
        try {
            if (!BasinBlockUpdatePacket.pending.isEmpty()) {
                for (BasinBlockUpdatePacket packet : BasinBlockUpdatePacket.pending) {
                    if (getBlockEntity(packet.x, packet.y, packet.z) instanceof BasinBlockEntity blockEntity) {
                        blockEntity.setEssence(packet.essenceType, packet.essence);
                        if (!packet.itemNbt.values().isEmpty()) {
                            blockEntity.setStack(0, new ItemStack(packet.itemNbt));
                        }
                        BasinBlockUpdatePacket.pending.remove(packet);

                    }
                }
            }
        } catch (Exception ignored) {}
    }
}
