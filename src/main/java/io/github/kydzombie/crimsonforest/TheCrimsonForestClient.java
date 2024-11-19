package io.github.kydzombie.crimsonforest;

import io.github.kydzombie.crimsonforest.block.entity.*;
import io.github.kydzombie.crimsonforest.client.BasinBlockEntityRenderer;
import io.github.kydzombie.crimsonforest.client.MortarAndPestleBlockEntityRenderer;
import io.github.kydzombie.crimsonforest.client.entity.VinelashAttackEntityRenderer;
import io.github.kydzombie.crimsonforest.entity.VinelashAttackEntity;
import io.github.kydzombie.crimsonforest.gui.screen.ingame.CrudeForgeScreen;
import io.github.kydzombie.crimsonforest.gui.screen.ingame.CrudePressScreen;
import io.github.kydzombie.crimsonforest.gui.screen.ingame.CrudeSoulInfuserScreen;
import io.github.kydzombie.crimsonforest.gui.screen.ingame.TunedThermosScreen;
import io.github.kydzombie.crimsonforest.item.render.EssenceRenderItem;
import io.github.kydzombie.crimsonforest.item.thermos.FluidThermosItem;
import io.github.kydzombie.crimsonforest.item.thermos.TunedThermosInventory;
import io.github.kydzombie.crimsonforest.item.thermos.TunedThermosItem;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.model.ItemModelPredicateProviderRegistryEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class TheCrimsonForestClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

    }

    @EventListener
    private void registerTextures(TextureRegisterEvent event) {
        TheCrimsonForest.vialItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/vial/empty"));
        for (EssenceType essenceType : EssenceType.values()) {
            essenceType.vialPartialTexture = Atlases.getGuiItems().addTexture(TheCrimsonForest.NAMESPACE.id("item/vial/" + essenceType.name().toLowerCase() + "_partial")).index;
            essenceType.vialFullTexture = Atlases.getGuiItems().addTexture(TheCrimsonForest.NAMESPACE.id("item/vial/" + essenceType.name().toLowerCase() + "_full")).index;
        }

        TheCrimsonForest.ironRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/iron"));
        TheCrimsonForest.ironVinelashRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/iron_vinelash"));
        TheCrimsonForest.ironSoulRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/iron_soul"));
        TheCrimsonForest.tarnishedRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/tarnished"));
        TheCrimsonForest.tarnishedSoulRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/tarnished_soul"));
        TheCrimsonForest.arcaneRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/arcane"));
        TheCrimsonForest.arcaneSoulRenderItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/render/arcane_soul"));

        TheCrimsonForest.soulShardItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/shard"));
        TheCrimsonForest.zombieSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/zombie"));
        TheCrimsonForest.spiderSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/spider"));
        TheCrimsonForest.skeletonSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/skeleton"));
        TheCrimsonForest.creeperSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/creeper"));
        TheCrimsonForest.passiveSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/passive"));
        TheCrimsonForest.squidSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/squid"));
        TheCrimsonForest.corruptedSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/corrupted"));
        TheCrimsonForest.endermanSoulItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/soul/enderman"));

        TheCrimsonForest.wardingAmuletItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/warding_amulet"));

        TheCrimsonForest.lifeStringItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/life_string"));
        TheCrimsonForest.natureStringItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/nature_string"));
        TheCrimsonForest.arcaneStringItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/arcane_string"));
        TheCrimsonForest.soulStringItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/soul_string"));

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

        TheCrimsonForest.woodenGearItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/wooden_gear"));
        TheCrimsonForest.ironRodItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/iron_rod"));
        TheCrimsonForest.tarnishedRodItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/tarnished_rod"));
        TheCrimsonForest.arcaneRodItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/arcane_rod"));
        TheCrimsonForest.lesserSoulCatcherItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/lesser_soul_catcher"));
        TheCrimsonForest.greaterSoulCatcherItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/greater_soul_catcher"));
        TheCrimsonForest.biomechanicalGearItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/biomechanical_gear"));
        TheCrimsonForest.biomechanicalChipItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/biomechanical_chip"));
        TheCrimsonForest.biomechanicalCircuitItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/biomechanical_circuit"));
        TheCrimsonForest.soulGearItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/soul_gear"));
        TheCrimsonForest.soulChipItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/soul_chip"));
        TheCrimsonForest.soulCircuitItem.setTexture(TheCrimsonForest.NAMESPACE.id("item/component/soul_circuit"));
    }

    @EventListener
    private void registerItemModelPredicates(ItemModelPredicateProviderRegistryEvent event) {
        for (EssenceRenderItem renderItem : new EssenceRenderItem[]{TheCrimsonForest.woodenEssenceRenderItem, TheCrimsonForest.ironEssenceRenderItem}) {
            event.registry.register(renderItem, TheCrimsonForest.NAMESPACE.id("vial"),
                    (stack, world, entity, seed) -> {
                        if (!renderItem.hasVial(stack)) return 0;
                        return Math.max(0.01f, renderItem.getEssence(stack, EssenceType.LIFE) / (float) renderItem.getMaxEssence(stack, EssenceType.LIFE));
                    });
            event.registry.register(renderItem, TheCrimsonForest.NAMESPACE.id("blood_drip"),
                    (stack, world, entity, seed) -> renderItem.isDripping(stack) ? 1 : 0);
        }

        for (FluidThermosItem thermos : new FluidThermosItem[]{
                TheCrimsonForest.ironThermosItem,
//                TheCrimsonForest.arcaneThermosItem
        }) {
            event.registry.register(thermos, TheCrimsonForest.NAMESPACE.id("fluid_type"),
                    (stack, world, entity, seed) -> {
                var fluid = thermos.getFluid(stack);
                if (fluid == null) return 0;
                return fluid.fluidType().getPredicateValue();
            });

            event.registry.register(thermos, TheCrimsonForest.NAMESPACE.id("fluid_amount"),
                    (stack, world, entity, seed) -> {
                var fluid = thermos.getFluid(stack);
                if (fluid == null) return 0;
                return fluid.drops() / (float) thermos.maxDrops;
            });
        }

        for (TunedThermosItem thermos : new TunedThermosItem[]{
                TheCrimsonForest.lifeTunedIronThermosItem, TheCrimsonForest.natureTunedIronThermosItem,
                TheCrimsonForest.pureTunedIronThermosItem, TheCrimsonForest.umbralTunedIronThermosItem,
//                TheCrimsonForest.lifeTunedArcaneThermosItem, TheCrimsonForest.natureTunedArcaneThermosItem
        }) {
            event.registry.register(thermos, TheCrimsonForest.NAMESPACE.id("fluid_amount"),
                    (stack, world, entity, seed) -> {
                        EssenceType essenceType = thermos.getEssenceTypes(stack).get(0);
                        return thermos.getEssence(stack, essenceType) / (float) thermos.maxEssence;
                    });
        }

        if (FabricLoader.getInstance().isModLoaded("telsdrinks")) {
            event.registry.register(TheCrimsonForest.goldThermosItem, TheCrimsonForest.NAMESPACE.id("hot"),
                    (stack, world, entity, seed) -> {
                var fluid = TheCrimsonForest.goldThermosItem.getFluid(stack);
                if (fluid == null) return 0;
                return fluid.fluidType().hasHotTexture() ? 1 : 0;
            });
            event.registry.register(TheCrimsonForest.goldThermosItem, TheCrimsonForest.NAMESPACE.id("fluid_amount"),
                    (stack, world, entity, seed) -> {
                var fluid = TheCrimsonForest.goldThermosItem.getFluid(stack);
                if (fluid == null) return 0;
                return fluid.drops() / (float) TheCrimsonForest.goldThermosItem.maxDrops;
            });
        }
    }

    @EventListener
    private void registerItemColors(ItemColorsRegisterEvent event) {
        if (FabricLoader.getInstance().isModLoaded("telsdrinks")) {
            event.itemColors.register((stack, tintIndex) -> {
                if (tintIndex != 1) return 0xFFFFFF;
                var fluid = TheCrimsonForest.goldThermosItem.getFluid(stack);
                if (fluid == null) return 0xFFFFFF;
                return fluid.fluidType().color;
            }, TheCrimsonForest.goldThermosItem);
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

    @EventListener
    private void registerGuiHandlers(GuiHandlerRegistryEvent event) {
        event.registry.registerValueNoMessage(TheCrimsonForest.NAMESPACE.id("crude_press"), BiTuple.of(
                (PlayerEntity player, Inventory inventory) -> new CrudePressScreen(player.inventory,
                        (CrudePressBlockEntity) inventory), CrudePressBlockEntity::new)
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
