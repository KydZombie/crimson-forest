package io.github.kydzombie.crimsonforest;

import io.github.kydzombie.crimsonforest.block.entity.BasinBlockEntity;
import io.github.kydzombie.crimsonforest.block.entity.MortarAndPestleBlockEntity;
import io.github.kydzombie.crimsonforest.client.BasinBlockEntityRenderer;
import io.github.kydzombie.crimsonforest.client.MortarAndPestleBlockEntityRenderer;
import io.github.kydzombie.crimsonforest.client.entity.VinelashAttackEntityRenderer;
import io.github.kydzombie.crimsonforest.entity.VinelashAttackEntity;
import io.github.kydzombie.crimsonforest.item.render.EssenceRenderItem;
import io.github.kydzombie.crimsonforest.item.thermos.FluidThermosItem;
import io.github.kydzombie.crimsonforest.item.thermos.TunedThermosItem;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.fabricmc.api.ClientModInitializer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.model.ItemModelPredicateProviderRegistryEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;

import java.util.List;

public class TheCrimsonForestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

    }

    @EventListener
    private void registerTextures(TextureRegisterEvent event) {
        TheCrimsonForest.ironVinelashRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/iron_vinelash"));
        TheCrimsonForest.ironSoulRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/iron_soul"));
        TheCrimsonForest.tarnishedSoulRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/tarnished_soul"));
        TheCrimsonForest.arcaneSoulRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/arcane_soul"));

        TheCrimsonForest.soulShardItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/shard"));
        TheCrimsonForest.zombieSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/zombie"));
        TheCrimsonForest.spiderSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/spider"));
        TheCrimsonForest.skeletonSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/skeleton"));
        TheCrimsonForest.creeperSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/creeper"));
        TheCrimsonForest.passiveSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/passive"));
        TheCrimsonForest.corruptedSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/corrupted"));
        TheCrimsonForest.endermanSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/enderman"));

        TheCrimsonForest.natureStringItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/nature_string"));

        TheCrimsonForest.lifeIngotItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/ingot/life"));
        TheCrimsonForest.natureIngotItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/ingot/nature"));
        TheCrimsonForest.tarnishedIngotItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/ingot/tarnished"));
        TheCrimsonForest.arcaneIngotItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/ingot/arcane"));

        TheCrimsonForest.clayPlateItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/plate/clay"));
        TheCrimsonForest.brickPlateItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/plate/brick"));
        TheCrimsonForest.ironPlateItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/plate/iron"));
        TheCrimsonForest.goldPlateItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/plate/gold"));
        TheCrimsonForest.lifePlateItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/plate/life"));
        TheCrimsonForest.naturePlateItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/plate/nature"));
        TheCrimsonForest.tarnishedPlateItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/plate/tarnished"));
        TheCrimsonForest.arcanePlateItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/plate/arcane"));

        TheCrimsonForest.biomechanicalGearItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/biomechanical_gear"));
        TheCrimsonForest.biomechanicalChipItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/biomechanical_chip"));
        TheCrimsonForest.biomechanicalCircuitItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/biomechanical_circuit"));
        TheCrimsonForest.soulGearItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/soul_gear"));
        TheCrimsonForest.soulChipItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/soul_chip"));
        TheCrimsonForest.soulCircuitItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/soul_circuit"));
    }

    @EventListener
    private void registerItemModelPredicates(ItemModelPredicateProviderRegistryEvent event) {
        event.registry.register(TheCrimsonForest.vialItem, TheCrimsonForest.NAMESPACE.id("essence_type"), (stack, world, entity, seed) -> {
            List<EssenceType> essenceTypes = TheCrimsonForest.vialItem.getEssenceTypes(stack);
            if (essenceTypes.isEmpty()) return 0;
            return essenceTypes.get(0) == EssenceType.LIFE ? 0.5f : 1;
        });
        event.registry.register(TheCrimsonForest.vialItem, TheCrimsonForest.NAMESPACE.id("full"), (stack, world, entity, seed) -> {
            List<EssenceType> essenceTypes = TheCrimsonForest.vialItem.getEssenceTypes(stack);
            if (essenceTypes.isEmpty()) return 0;
            return TheCrimsonForest.vialItem.getEssence(stack, essenceTypes.get(0)) == TheCrimsonForest.vialItem.maxEssence ? 1 : 0;
        });

        for (FluidThermosItem thermos : new FluidThermosItem[]{TheCrimsonForest.ironThermosItem, TheCrimsonForest.arcaneThermosItem}) {
            event.registry.register(thermos, TheCrimsonForest.NAMESPACE.id("fluid_type"),
                    (stack, world, entity, seed) -> thermos.getFluidType(stack).getPredicateValue());

            event.registry.register(thermos, TheCrimsonForest.NAMESPACE.id("fluid_amount"),
                    (stack, world, entity, seed) -> thermos.getMillibuckets(stack) / (float) thermos.maxMillibuckets);
        }

        for (TunedThermosItem thermos : new TunedThermosItem[]{
                TheCrimsonForest.lifeTunedIronThermosItem, TheCrimsonForest.natureTunedIronThermosItem,
                TheCrimsonForest.lifeTunedArcaneThermosItem, TheCrimsonForest.natureTunedArcaneThermosItem
        }) {
            event.registry.register(thermos, TheCrimsonForest.NAMESPACE.id("fluid_amount"),
                    (stack, world, entity, seed) -> {
                        EssenceType essenceType = thermos.getEssenceTypes(stack).get(0);
                        return thermos.getEssence(stack, essenceType) / (float) thermos.maxEssence;
                    });
        }

        for (EssenceRenderItem renderItem : new EssenceRenderItem[]{TheCrimsonForest.woodenEssenceRenderItem, TheCrimsonForest.ironEssenceRenderItem}) {
            event.registry.register(renderItem, TheCrimsonForest.NAMESPACE.id("vial"),
                    (stack, world, entity, seed) -> {
                        if (!renderItem.hasVial(stack)) return 0;
                        return Math.max(0.01f, renderItem.getEssence(stack, EssenceType.LIFE) / (float) renderItem.getMaxEssence(stack, EssenceType.LIFE));
                    });
            event.registry.register(renderItem, TheCrimsonForest.NAMESPACE.id("blood_drip"),
                    (stack, world, entity, seed) -> stack.getStationNbt().getInt(EssenceRenderItem.BLOOD_DRIP_TICKS_NBT) > 0 ? 1 : 0);
        }
    }

    @EventListener
    private void registerBlockEntityRenderers(BlockEntityRendererRegisterEvent event) {
        event.renderers.put(BasinBlockEntity.class, new BasinBlockEntityRenderer());
        event.renderers.put(MortarAndPestleBlockEntity.class, new MortarAndPestleBlockEntityRenderer());
    }

    @EventListener
    private void registerEntityRenderers(EntityRendererRegisterEvent event) {
        event.renderers.put(VinelashAttackEntity.class, new VinelashAttackEntityRenderer());
    }
}
