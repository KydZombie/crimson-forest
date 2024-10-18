package io.github.kydzombie.crimsonforest;

import io.github.kydzombie.crimsonforest.block.entity.MortarAndPestleBlockEntity;
import io.github.kydzombie.crimsonforest.client.MortarAndPestleBlockEntityRenderer;
import io.github.kydzombie.crimsonforest.item.thermos.FluidThermosItem;
import io.github.kydzombie.crimsonforest.item.thermos.TunedThermosItem;
import net.fabricmc.api.ClientModInitializer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.model.ItemModelPredicateProviderRegistryEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;

public class TheCrimsonForestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

    }

    @EventListener
    private void registerTextures(TextureRegisterEvent event) {
        TheCrimsonForest.lifeEssenceCollectorItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/life_essence_collector"));
        TheCrimsonForest.emptyVialItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/vial/empty"));
        TheCrimsonForest.lifeVialItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/vial/life"));
        TheCrimsonForest.natureVialItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/vial/nature"));
    }

    @EventListener
    private void registerItemModelPredicates(ItemModelPredicateProviderRegistryEvent event) {
        for (FluidThermosItem thermos : new FluidThermosItem[] { TheCrimsonForest.ironThermosItem, TheCrimsonForest.arcaneThermosItem }) {
            event.registry.register(thermos, TheCrimsonForest.NAMESPACE.id("fluid_type"),
                    (stack, world, entity, seed) -> thermos.getFluidType(stack).getPredicateValue());

            event.registry.register(thermos, TheCrimsonForest.NAMESPACE.id("fluid_amount"),
                    (stack, world, entity, seed) -> thermos.getMillibuckets(stack) / (float) thermos.maxMillibuckets);
        }

        for (TunedThermosItem thermos : new TunedThermosItem[] {
                TheCrimsonForest.lifeTunedIronThermosItem, TheCrimsonForest.natureTunedIronThermosItem,
                TheCrimsonForest.lifeTunedArcaneThermosItem, TheCrimsonForest.natureTunedArcaneThermosItem
        }) {
            event.registry.register(thermos, TheCrimsonForest.NAMESPACE.id("fluid_amount"),
                    (stack, world, entity, seed) -> thermos.getMillibuckets(stack) / (float) thermos.maxMillibuckets);
        }
    }

    @EventListener
    private void registerBlockEntityRenderers(BlockEntityRendererRegisterEvent event) {
        event.renderers.put(MortarAndPestleBlockEntity.class, new MortarAndPestleBlockEntityRenderer());
    }
}
