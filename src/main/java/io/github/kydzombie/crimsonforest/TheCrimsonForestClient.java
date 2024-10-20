package io.github.kydzombie.crimsonforest;

import io.github.kydzombie.crimsonforest.block.entity.MortarAndPestleBlockEntity;
import io.github.kydzombie.crimsonforest.client.MortarAndPestleBlockEntityRenderer;
import io.github.kydzombie.crimsonforest.item.thermos.FluidThermosItem;
import io.github.kydzombie.crimsonforest.item.thermos.TunedThermosItem;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
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
        TheCrimsonForest.woodenEssenceRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/wooden_essence"));
        TheCrimsonForest.ironEssenceRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/iron_essence"));
        TheCrimsonForest.ironVinelashRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/vinelash_iron"));
        TheCrimsonForest.tarnishedSoulRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/tarnished_soul"));
        TheCrimsonForest.arcaneSoulRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/arcane_soul"));

        TheCrimsonForest.zombieSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/zombie"));
        TheCrimsonForest.spiderSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/spider"));
        TheCrimsonForest.skeletonSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/skeleton"));
        TheCrimsonForest.passiveSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/passive"));
        TheCrimsonForest.corruptedSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/corrupted"));

        TheCrimsonForest.emptyVialItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/vial/empty"));
        TheCrimsonForest.lifeVialItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/vial/life"));
        TheCrimsonForest.natureVialItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/vial/nature"));

        TheCrimsonForest.lifeInfusedIngot.setTexture(TheCrimsonForest.NAMESPACE.id("item/life_infused_ingot"));
        TheCrimsonForest.natureInfusedIngot.setTexture(TheCrimsonForest.NAMESPACE.id("item/nature_infused_ingot"));
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
                    (stack, world, entity, seed) -> {
                        EssenceType essenceType = thermos.getEssenceTypes(stack).get(0);
                        return thermos.getEssence(stack, essenceType) / (float) thermos.maxEssence;
                    });
        }
    }

    @EventListener
    private void registerBlockEntityRenderers(BlockEntityRendererRegisterEvent event) {
        event.renderers.put(MortarAndPestleBlockEntity.class, new MortarAndPestleBlockEntityRenderer());
    }
}
