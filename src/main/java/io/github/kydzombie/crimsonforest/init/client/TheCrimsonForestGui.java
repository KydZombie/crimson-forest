package io.github.kydzombie.crimsonforest.init.client;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.block.entity.CrudeForgeBlockEntity;
import io.github.kydzombie.crimsonforest.block.entity.CrudePressBlockEntity;
import io.github.kydzombie.crimsonforest.block.entity.CrudeSoulInfuserBlockEntity;
import io.github.kydzombie.crimsonforest.gui.screen.ingame.CrudeForgeScreen;
import io.github.kydzombie.crimsonforest.gui.screen.ingame.CrudePressScreen;
import io.github.kydzombie.crimsonforest.gui.screen.ingame.CrudeSoulInfuserScreen;
import io.github.kydzombie.crimsonforest.gui.screen.ingame.TunedThermosScreen;
import io.github.kydzombie.crimsonforest.item.thermos.TunedThermosInventory;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class TheCrimsonForestGui {
    @EventListener
    private void registerGuiHandlers(GuiHandlerRegistryEvent event) {
        event.registry.registerValueNoMessage(TheCrimsonForest.NAMESPACE.id("crude_press"), BiTuple.of(
                (PlayerEntity player, Inventory inventory) -> new CrudePressScreen(player.inventory,
                        (CrudePressBlockEntity) inventory), CrudeForgeBlockEntity::new)
        );

        event.registry.registerValueNoMessage(TheCrimsonForest.NAMESPACE.id("crude_forge"), BiTuple.of(
                (PlayerEntity player, Inventory inventory) -> new CrudeForgeScreen(player.inventory,
                        (CrudeForgeBlockEntity) inventory),
                CrudeForgeBlockEntity::new)
        );

        event.registry.registerValueNoMessage(TheCrimsonForest.NAMESPACE.id("crude_soul_infuser"), BiTuple.of(
                (PlayerEntity player, Inventory inventory) -> new CrudeSoulInfuserScreen(player.inventory,
                        (CrudeSoulInfuserBlockEntity) inventory),
                CrudeSoulInfuserBlockEntity::new)
        );

        event.registry.registerValueNoMessage(TheCrimsonForest.NAMESPACE.id("tuned_thermos"), BiTuple.of(
                (PlayerEntity player, Inventory inventory) -> new TunedThermosScreen(player.inventory,
                        (TunedThermosInventory) inventory),
                TunedThermosInventory::new));
    }
}
