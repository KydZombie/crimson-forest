package io.github.kydzombie.crimsonforest.init.client;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.block.entity.CrudeForgeBlockEntity;
import io.github.kydzombie.crimsonforest.gui.screen.ingame.ForgeScreen;
import io.github.kydzombie.crimsonforest.gui.screen.ingame.TunedThermosScreen;
import io.github.kydzombie.crimsonforest.item.thermos.TunedThermosInventory;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class TheCrimsonForestGui {
    @EventListener
    private void registerGuiHandlers(GuiHandlerRegistryEvent event) {
        event.registry.registerValueNoMessage(TheCrimsonForest.NAMESPACE.id("forge"), BiTuple.of(this::openForge, CrudeForgeBlockEntity::new));
        event.registry.registerValueNoMessage(TheCrimsonForest.NAMESPACE.id("tuned_thermos"), BiTuple.of(this::openTunedThermos, TunedThermosInventory::new));
    }

    public Screen openForge(PlayerEntity player, Inventory inventory) {
        return new ForgeScreen(player.inventory, (CrudeForgeBlockEntity) inventory);
    }

    public Screen openTunedThermos(PlayerEntity player, Inventory inventory) {
        return new TunedThermosScreen(player.inventory, (TunedThermosInventory) inventory);
    }
}
