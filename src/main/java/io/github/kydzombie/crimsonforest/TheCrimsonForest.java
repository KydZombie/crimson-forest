package io.github.kydzombie.crimsonforest;

import com.matthewperiut.accessoryapi.api.AccessoryRegister;
import com.mojang.datafixers.util.Pair;
import io.github.kydzombie.cairn.api.recipe.CustomCraftingRecipeManager;
import io.github.kydzombie.crimsonforest.block.*;
import io.github.kydzombie.crimsonforest.block.entity.*;
import io.github.kydzombie.crimsonforest.entity.VinelashAttackEntity;
import io.github.kydzombie.crimsonforest.fluid.FluidHelper;
import io.github.kydzombie.crimsonforest.item.*;
import io.github.kydzombie.crimsonforest.item.render.EssenceRenderItem;
import io.github.kydzombie.crimsonforest.item.render.LesserSoulRenderItem;
import io.github.kydzombie.crimsonforest.item.render.SoulRenderItem;
import io.github.kydzombie.crimsonforest.item.render.VinelashRenderItem;
import io.github.kydzombie.crimsonforest.item.thermos.*;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import io.github.kydzombie.crimsonforest.packet.BasinBlockUpdatePacket;
import io.github.kydzombie.crimsonforest.packet.PlaySoundAtPlayerPacket;
import io.github.kydzombie.crimsonforest.packet.TunedThermosInventoryPacket;
import io.github.kydzombie.crimsonforest.recipe.BasinRecipe;
import io.github.kydzombie.crimsonforest.recipe.BasinRecipeRegistry;
import io.github.kydzombie.crimsonforest.recipe.crude.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.network.packet.IdentifiablePacket;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class TheCrimsonForest implements ModInitializer {
    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    @Entrypoint.Logger
    public static final Logger LOGGER = Null.get();

    // Blocks
    public static BasinBlock stoneBasinBlock;
    public static EssenceBondedBricksBlock essenceBondedBricksBlock;
    public static MortarAndPestleBlock mortarAndPestleBlock;
    public static CrudePressBlock crudePressBlock;
    public static CrudeForgeBlock crudeForgeBlock;
    public static CrudeSoulInfuserBlock crudeSoulInfuserBlock;
    public static CrudeSoulInfuserBlock tarnishedSoulInfuserBlock;
    public static CrudePressBlock tarnishedPressBlock;
    public static CrudeForgeBlock tarnishedForgeBlock;

    // Items
    public static EssenceRenderItem woodenEssenceRenderItem;
    public static CrimsonWeaponItem ironRenderItem;
    public static EssenceRenderItem ironEssenceRenderItem;
    public static VinelashRenderItem ironVinelashRenderItem;
    public static LesserSoulRenderItem ironSoulRenderItem;
    public static CrimsonWeaponItem tarnishedRenderItem;
    public static SoulRenderItem tarnishedSoulRenderItem;
    public static CrimsonWeaponItem arcaneRenderItem;
    public static SoulRenderItem arcaneSoulRenderItem;

    public static SoulShardItem soulShardItem;
    public static SoulItem zombieSoulItem;
    public static SoulItem spiderSoulItem;
    public static SoulItem skeletonSoulItem;
    public static SoulItem creeperSoulItem;
    public static SoulItem passiveSoulItem;
    public static SoulItem squidSoulItem;
    public static SoulItem corruptedSoulItem;
    public static SoulItem endermanSoulItem;

    public static VialItem vialItem;
    public static FluidThermosItem ironThermosItem;
    public static TunedThermosItem lifeTunedIronThermosItem;
    public static TunedThermosItem natureTunedIronThermosItem;
    public static TunedThermosItem pureTunedIronThermosItem;
    public static TunedThermosItem umbralTunedIronThermosItem;
//    public static FluidThermosItem arcaneThermosItem;
//    public static TunedThermosItem lifeTunedArcaneThermosItem;
//    public static TunedThermosItem natureTunedArcaneThermosItem;

    public static DrinkThermosItem goldThermosItem;

    public static WardingAmuletItem wardingAmuletItem;

    public static CrimsonForestCraftingItem lifeStringItem;
    public static CrimsonForestCraftingItem natureStringItem;
    public static CrimsonForestCraftingItem arcaneStringItem;
    public static CrimsonForestCraftingItem soulStringItem;

    public static CrimsonForestCraftingItem lifeIngotItem;
    public static CrimsonForestCraftingItem natureIngotItem;
    public static CrimsonForestCraftingItem tarnishedIngotItem;
    public static CrimsonForestCraftingItem arcaneIngotItem;

    public static CrimsonForestCraftingItem clayPlateItem;
    public static CrimsonForestCraftingItem brickPlateItem;
    public static CrimsonForestCraftingItem ironPlateItem;
    public static CrimsonForestCraftingItem goldPlateItem;
    public static CrimsonForestCraftingItem lifePlateItem;
    public static CrimsonForestCraftingItem naturePlateItem;
    public static CrimsonForestCraftingItem tarnishedPlateItem;
    public static CrimsonForestCraftingItem arcanePlateItem;

    public static CrimsonForestCraftingItem woodenGearItem;
    public static CrimsonForestCraftingItem ironRodItem;
    public static CrimsonForestCraftingItem tarnishedRodItem;
    public static CrimsonForestCraftingItem arcaneRodItem;
    public static CrimsonForestCraftingItem lesserSoulCatcherItem;
    public static CrimsonForestCraftingItem greaterSoulCatcherItem;
    public static CrimsonForestCraftingItem biomechanicalGearItem;
    public static CrimsonForestCraftingItem biomechanicalChipItem;
    public static CrimsonForestCraftingItem biomechanicalCircuitItem;
    public static CrimsonForestCraftingItem soulGearItem;
    public static CrimsonForestCraftingItem soulChipItem;
    public static CrimsonForestCraftingItem soulCircuitItem;

    @Override
    public void onInitialize() {
        AccessoryRegister.requestSlot("pendant", 1);
        AccessoryRegister.add("thermos", "assets/crimsonforest/accessoryapi/accessory_icon_atlas.png", 0, 0);
    }

    @EventListener
    private void registerBlocks(BlockRegistryEvent event) {
        stoneBasinBlock = new BasinBlock(NAMESPACE.id("stone_basin"), Material.STONE);
        essenceBondedBricksBlock = new EssenceBondedBricksBlock(NAMESPACE.id("essence_bonded_bricks"), Material.STONE);
        mortarAndPestleBlock = new MortarAndPestleBlock(NAMESPACE.id("mortar_and_pestle"), Material.WOOD);

        crudePressBlock = new CrudePressBlock(NAMESPACE.id("crude_press"), Material.STONE);
        crudeForgeBlock = new CrudeForgeBlock(NAMESPACE.id("crude_forge"), Material.STONE);
        crudeSoulInfuserBlock = new CrudeSoulInfuserBlock(NAMESPACE.id("crude_soul_infuser"), Material.STONE);

        // TODO: Tarnished machines
//        tarnishedSoulInfuserBlock = new CrudeSoulInfuserBlock(NAMESPACE.id("tarnished_soul_infuser"), Material.STONE);
//        tarnishedPressBlock = new CrudePressBlock(NAMESPACE.id("tarnished_press"), Material.STONE);
//        tarnishedForgeBlock = new CrudeForgeBlock(NAMESPACE.id("tarnished_forge"), Material.STONE);
    }

    @EventListener
    private void registerBlockEntity(BlockEntityRegisterEvent event) {
        event.register(BasinBlockEntity.class, NAMESPACE.id("basin_block").toString());
        event.register(MortarAndPestleBlockEntity.class, NAMESPACE.id("mortar_and_pestle").toString());
        event.register(CrudePressBlockEntity.class, NAMESPACE.id("crude_press").toString());
        event.register(CrudeForgeBlockEntity.class, NAMESPACE.id("crude_forge").toString());
        event.register(CrudeSoulInfuserBlockEntity.class, NAMESPACE.id("crude_soul_infuser").toString());
    }

    @EventListener
    private void registerItems(ItemRegistryEvent event) {
        woodenEssenceRenderItem = new EssenceRenderItem(NAMESPACE.id("wooden_essence_render"), 32, 4, FluidHelper.BOTTLE_AMOUNT / 10);
        ironRenderItem = new CrimsonWeaponItem(NAMESPACE.id("iron_render"), 350, 6);
        ironEssenceRenderItem = new EssenceRenderItem(NAMESPACE.id("iron_essence_render"), 250, 6, FluidHelper.BOTTLE_AMOUNT / 5);
        ironVinelashRenderItem = new VinelashRenderItem(NAMESPACE.id("iron_vinelash_render"), 250, 6);
        ironSoulRenderItem = new LesserSoulRenderItem(NAMESPACE.id("iron_soul_render"), 125, 6, .25f);
        tarnishedRenderItem = new CrimsonWeaponItem(NAMESPACE.id("tarnished_render"), 512, 7);
        tarnishedSoulRenderItem = new SoulRenderItem(NAMESPACE.id("tarnished_soul_render"), 250, 7, .1f);
        arcaneRenderItem = new CrimsonWeaponItem(NAMESPACE.id("arcane_render"), 1024, 8);
        arcaneSoulRenderItem = new SoulRenderItem(NAMESPACE.id("arcane_soul_render"), 512, 7, .5f);

        soulShardItem = new SoulShardItem(NAMESPACE.id("soul_shard"));
        zombieSoulItem = new SoulItem(NAMESPACE.id("zombie_soul"), 2);
        spiderSoulItem = new SoulItem(NAMESPACE.id("spider_soul"), 2);
        skeletonSoulItem = new SoulItem(NAMESPACE.id("skeleton_soul"), 2);
        creeperSoulItem = new SoulItem(NAMESPACE.id("creeper_soul"), 3);
        passiveSoulItem = new SoulItem(NAMESPACE.id("passive_soul"), 1);
        squidSoulItem = new SoulItem(NAMESPACE.id("squid_soul"), 1);
        corruptedSoulItem = new SoulItem(NAMESPACE.id("corrupted_soul"), 4);
        endermanSoulItem = new SoulItem(NAMESPACE.id("enderman_soul"), 4);

        vialItem = new VialItem(NAMESPACE.id("vial"), FluidHelper.BOTTLE_AMOUNT);



        ironThermosItem = new FluidThermosItem(NAMESPACE.id("iron_thermos"), FluidHelper.BUCKET_AMOUNT * 4);

        long ironMaxEssence = FluidHelper.BOTTLE_AMOUNT * 4;
        lifeTunedIronThermosItem = new TunedThermosItem(NAMESPACE.id("life_tuned_iron_thermos"), EssenceType.LIFE, ironMaxEssence);
        natureTunedIronThermosItem = new TunedThermosItem(NAMESPACE.id("nature_tuned_iron_thermos"), EssenceType.NATURE, ironMaxEssence);
        pureTunedIronThermosItem = new TunedThermosItem(NAMESPACE.id("pure_tuned_iron_thermos"), EssenceType.PURE, ironMaxEssence);
        umbralTunedIronThermosItem = new TunedThermosItem(NAMESPACE.id("umbral_tuned_iron_thermos"), EssenceType.UMBRAL, ironMaxEssence);

//        int arcaneMaxMillibuckets = 8000;
//
//        arcaneThermosItem = new FluidThermosItem(NAMESPACE.id("arcane_thermos"), arcaneMaxMillibuckets);
//        lifeTunedArcaneThermosItem = new LifeTunedThermosItem(NAMESPACE.id("life_tuned_arcane_thermos"), arcaneMaxMillibuckets);
//        natureTunedArcaneThermosItem = new NatureTunedThermosItem(NAMESPACE.id("nature_tuned_arcane_thermos"), arcaneMaxMillibuckets);

        if (FabricLoader.getInstance().isModLoaded("telsdrinks")) {
            goldThermosItem = new DrinkThermosItem(NAMESPACE.id("gold_thermos"), FluidHelper.BOTTLE_AMOUNT * 4);
        }

        wardingAmuletItem = new WardingAmuletItem(NAMESPACE.id("warding_amulet"));

        lifeStringItem = new CrimsonForestCraftingItem(NAMESPACE.id("life_string"));
        natureStringItem = new CrimsonForestCraftingItem(NAMESPACE.id("nature_string"));
        arcaneStringItem = new CrimsonForestCraftingItem(NAMESPACE.id("arcane_string"));
        soulStringItem = new CrimsonForestCraftingItem(NAMESPACE.id("soul_string"));

        lifeIngotItem = new CrimsonForestCraftingItem(NAMESPACE.id("life_ingot"));
        natureIngotItem = new CrimsonForestCraftingItem(NAMESPACE.id("nature_ingot"));
        tarnishedIngotItem = new CrimsonForestCraftingItem(NAMESPACE.id("tarnished_ingot"));
        arcaneIngotItem = new CrimsonForestCraftingItem(NAMESPACE.id("arcane_ingot"));

        clayPlateItem = new CrimsonForestCraftingItem(NAMESPACE.id("clay_plate"));
        brickPlateItem = new CrimsonForestCraftingItem(NAMESPACE.id("brick_plate"));
        ironPlateItem = new CrimsonForestCraftingItem(NAMESPACE.id("iron_plate"));
        goldPlateItem = new CrimsonForestCraftingItem(NAMESPACE.id("gold_plate"));
        lifePlateItem = new CrimsonForestCraftingItem(NAMESPACE.id("life_plate"));
        naturePlateItem = new CrimsonForestCraftingItem(NAMESPACE.id("nature_plate"));
        tarnishedPlateItem = new CrimsonForestCraftingItem(NAMESPACE.id("tarnished_plate"));
        arcanePlateItem = new CrimsonForestCraftingItem(NAMESPACE.id("arcane_plate"));

        woodenGearItem = new CrimsonForestCraftingItem(NAMESPACE.id("wooden_gear"));
        ironRodItem = new CrimsonForestCraftingItem(NAMESPACE.id("iron_rod"));
        tarnishedRodItem = new CrimsonForestCraftingItem(NAMESPACE.id("tarnished_rod"));
        arcaneRodItem = new CrimsonForestCraftingItem(NAMESPACE.id("arcane_rod"));
        lesserSoulCatcherItem = new CrimsonForestCraftingItem(NAMESPACE.id("lesser_soul_catcher")).setMaxCount(1);
        greaterSoulCatcherItem = new CrimsonForestCraftingItem(NAMESPACE.id("greater_soul_catcher")).setMaxCount(1);
        biomechanicalGearItem = new CrimsonForestCraftingItem(NAMESPACE.id("biomechanical_gear"));
        biomechanicalChipItem = new CrimsonForestCraftingItem(NAMESPACE.id("biomechanical_chip"));
        biomechanicalCircuitItem = new CrimsonForestCraftingItem(NAMESPACE.id("biomechanical_circuit"));
        soulGearItem = new CrimsonForestCraftingItem(NAMESPACE.id("soul_gear"));
        soulChipItem = new CrimsonForestCraftingItem(NAMESPACE.id("soul_chip"));
        soulCircuitItem = new CrimsonForestCraftingItem(NAMESPACE.id("soul_circuit"));

        BasinRecipeRegistry.INSTANCE.addRecipe(new BasinRecipe(new ItemStack(Item.STRING), EssenceType.LIFE, FluidHelper.BOTTLE_AMOUNT / 10, new ItemStack(lifeStringItem)));
        BasinRecipeRegistry.INSTANCE.addRecipe(new BasinRecipe(new ItemStack(Item.STRING), EssenceType.NATURE, FluidHelper.BOTTLE_AMOUNT / 10, new ItemStack(natureStringItem)));
        BasinRecipeRegistry.INSTANCE.addRecipe(new BasinRecipe(new ItemStack(Block.BRICKS), EssenceType.LIFE, FluidHelper.BOTTLE_AMOUNT / 2, new ItemStack(essenceBondedBricksBlock)));
        BasinRecipeRegistry.INSTANCE.addRecipe(new BasinRecipe(new ItemStack(Block.BRICKS), EssenceType.NATURE, FluidHelper.BOTTLE_AMOUNT / 2, new ItemStack(essenceBondedBricksBlock)));
        BasinRecipeRegistry.INSTANCE.addRecipe(new BasinRecipe(new ItemStack(woodenGearItem), EssenceType.LIFE, FluidHelper.BOTTLE_AMOUNT / 3, new ItemStack(biomechanicalGearItem)));
        BasinRecipeRegistry.INSTANCE.addRecipe(new BasinRecipe(new ItemStack(woodenGearItem), EssenceType.NATURE, FluidHelper.BOTTLE_AMOUNT / 3, new ItemStack(biomechanicalGearItem)));
        BasinRecipeRegistry.INSTANCE.addRecipe(new BasinRecipe(new ItemStack(Item.IRON_INGOT), EssenceType.LIFE, FluidHelper.BOTTLE_AMOUNT * 3 / 2, new ItemStack(lifeIngotItem)));
        BasinRecipeRegistry.INSTANCE.addRecipe(new BasinRecipe(new ItemStack(Item.IRON_INGOT), EssenceType.NATURE, FluidHelper.BOTTLE_AMOUNT * 3 / 2, new ItemStack(natureIngotItem)));
        BasinRecipeRegistry.INSTANCE.addRecipe(new BasinRecipe(new ItemStack(ironThermosItem), EssenceType.LIFE, FluidHelper.BOTTLE_AMOUNT / 10, new ItemStack(lifeTunedIronThermosItem)));
        BasinRecipeRegistry.INSTANCE.addRecipe(new BasinRecipe(new ItemStack(ironThermosItem), EssenceType.NATURE, FluidHelper.BOTTLE_AMOUNT / 10, new ItemStack(natureTunedIronThermosItem)));

        CrudePressRecipeRegistry.INSTANCE.addRecipe(new CrudePressRecipe(new ItemStack(Item.CLAY), new ItemStack(clayPlateItem), 60));
        CrudePressRecipeRegistry.INSTANCE.addRecipe(new CrudePressRecipe(new ItemStack(Item.BRICK), new ItemStack(brickPlateItem), 120));
        CrudePressRecipeRegistry.INSTANCE.addRecipe(new CrudePressRecipe(new ItemStack(Item.IRON_INGOT, 2), new ItemStack(ironPlateItem), 80));
        CrudePressRecipeRegistry.INSTANCE.addRecipe(new CrudePressRecipe(new ItemStack(Item.GOLD_INGOT, 2), new ItemStack(goldPlateItem), 80));
        CrudePressRecipeRegistry.INSTANCE.addRecipe(new CrudePressRecipe(new ItemStack(lifeIngotItem, 2), new ItemStack(lifePlateItem), 200));
        CrudePressRecipeRegistry.INSTANCE.addRecipe(new CrudePressRecipe(new ItemStack(natureIngotItem, 2), new ItemStack(naturePlateItem), 200));
        CrudePressRecipeRegistry.INSTANCE.addRecipe(new CrudePressRecipe(new ItemStack(tarnishedIngotItem, 2), new ItemStack(tarnishedPlateItem), 400));
        for (SoulItem soulType : SoulItem.SOUL_TYPES) {
            CrudePressRecipeRegistry.INSTANCE.addRecipe(new CrudePressRecipe(new ItemStack(soulType), new ItemStack(soulShardItem, soulType.shardCount), 200));
        }

        CrudeForgeRecipeRegistry.INSTANCE.addRecipe(new CrudeForgeRecipe(new ItemStack(arcaneStringItem), 120, new ItemStack(lifeStringItem), new ItemStack(natureStringItem)));
        CrudeForgeRecipeRegistry.INSTANCE.addRecipe(new CrudeForgeRecipe(new ItemStack(lifeIngotItem), 120, new ItemStack(Item.IRON_INGOT), vialItem.asStack(EssenceType.LIFE, FluidHelper.BOTTLE_AMOUNT / 5)));
        CrudeForgeRecipeRegistry.INSTANCE.addRecipe(new CrudeForgeRecipe(new ItemStack(natureIngotItem), 120, new ItemStack(Item.IRON_INGOT), vialItem.asStack(EssenceType.NATURE, FluidHelper.BOTTLE_AMOUNT / 5)));
        CrudeForgeRecipeRegistry.INSTANCE.addRecipe(new CrudeForgeRecipe(new ItemStack(tarnishedIngotItem), 240, new ItemStack(lifeIngotItem), new ItemStack(natureIngotItem)));

        CrudeSoulInfuserRecipeRegistry.INSTANCE.addRecipe(new CrudeSoulInfuserRecipe(new ItemStack(soulStringItem), new ItemStack(arcaneStringItem), new ItemStack(soulShardItem, 2), 100));
        CrudeSoulInfuserRecipeRegistry.INSTANCE.addRecipe(new CrudeSoulInfuserRecipe(new ItemStack(greaterSoulCatcherItem), new ItemStack(lesserSoulCatcherItem), new ItemStack(soulShardItem, 4), 400));
        CrudeSoulInfuserRecipeRegistry.INSTANCE.addRecipe(new CrudeSoulInfuserRecipe(new ItemStack(soulGearItem), new ItemStack(biomechanicalGearItem), new ItemStack(soulShardItem, 2), 200));
        for (var pair : new Pair[]{ new Pair<>(zombieSoulItem, "zombie"), new Pair<>(skeletonSoulItem, "skeleton"), new Pair<>(spiderSoulItem, "spider"), new Pair<>(creeperSoulItem, "creeper") }) {
            SoulItem soulItem = (SoulItem) pair.getFirst();
            String mobName = (String) pair.getSecond();
            ItemStack stack = new ItemStack(wardingAmuletItem);
            wardingAmuletItem.setMob(stack, mobName);
            CrudeSoulInfuserRecipeRegistry.INSTANCE.addRecipe(new CrudeSoulInfuserRecipe(stack, new ItemStack(wardingAmuletItem), new ItemStack(soulItem, 8), 800));
        }
    }

    @EventListener
    private void registerRecipes(RecipeRegisterEvent event) {
        if (event.recipeId == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED.type()) {
            if (FabricLoader.getInstance().isModLoaded("telsdrinks")) {
                CraftingRegistry.addShapedRecipe(new ItemStack(goldThermosItem), " S ", "PBP", " P ", 'S', squidSoulItem, 'P', goldPlateItem, 'B', Item.BUCKET);
            }
        } else if (event.recipeId == CustomCraftingRecipeManager.RECIPE_ID) {
            CustomCraftingRecipeManager.registerRecipe((inventory) -> {
                ItemStack vial = null;
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack stack = inventory.getStack(i);
                    if (stack == null) continue;
                    if (stack.getItem() instanceof VialItem) {
                        // If multiple thermi are used, it is invalid
                        if (vial != null) return null;
                        vial = stack;
                        break;
                    }
                }
                if (vial == null) return null;

                return new ItemStack(vial.getItem());
            });

            CustomCraftingRecipeManager.registerRecipe((inventory) -> {
                ItemStack thermos = null;
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack stack = inventory.getStack(i);
                    if (stack == null) continue;
                    if (stack.getItem() instanceof ThermosItem<?>) {
                        // If multiple thermi are used, it is invalid
                        if (thermos != null) return null;
                        thermos = stack;
                        break;
                    }
                }
                if (thermos == null) return null;

                if (thermos.getItem() instanceof DrinkThermosItem thermosItem) {
                    boolean found = false;
                    var fluid = thermosItem.getFluid(thermos);
                    for (int i = 0; i < inventory.size(); i++) {
                        ItemStack stack = inventory.getStack(i);
                        if (stack == null || stack.getItem() instanceof ThermosItem<?>) continue;
                        if (fluid == null) {
                            for (DrinkThermosItem.DrinkType value : DrinkThermosItem.DrinkType.values()) {
                                if (value.mugBlock.asItem().id == stack.getItem().id) {
                                    if (value.hasHotState) {
                                        if ((value.hot ? 1 : 0) != stack.getDamage()) {
                                            continue;
                                        }
                                    }
                                    fluid = new FluidInstance<>(value, FluidHelper.BOTTLE_AMOUNT);
                                    found = true;
                                }
                            }
                        } else {
                            if (fluid.fluidType().mugBlock.asItem().id == stack.getItem().id) {
                                if (fluid.fluidType().hasHotState) {
                                    if ((fluid.fluidType().hot ? 1 : 0) != stack.getDamage()) {
                                        return null;
                                    }
                                }
                                if (fluid.drops() + FluidHelper.BOTTLE_AMOUNT > thermosItem.maxDrops) return null;
                                fluid = fluid.add(FluidHelper.BOTTLE_AMOUNT);
                                found = true;
                            } else {
                                return null;
                            }
                        }
                    }
                    if (found) {
                        ItemStack newThermos = new ItemStack(thermos.getItem());
                        thermosItem.setFluid(newThermos, fluid);
                        return newThermos;
                    }
                }

                return new ItemStack(thermos.getItem());
            });
        }
    }

    @EventListener
    private void registerEntities(EntityRegister event) {
        event.register(VinelashAttackEntity.class, NAMESPACE.id("vinelash_attack").toString());
    }

    @EventListener
    private void registerPackets(PacketRegisterEvent event) {
        IdentifiablePacket.register(NAMESPACE.id("basin_block_update"), true, false, BasinBlockUpdatePacket::new);
        IdentifiablePacket.register(NAMESPACE.id("tuned_thermos_inventory"), true, false, TunedThermosInventoryPacket::new);

        IdentifiablePacket.register(NAMESPACE.id("play_sound_at_player"), true, false, PlaySoundAtPlayerPacket::new);
    }

//    @EventListener
//    private void a(ItemUsedInCraftingEvent event) {
//        if (event.itemUsed != null && event.itemUsed.getItem() == Item.STICK) {
//            for (int i = 0; i < event.craftingMatrix.size(); i++) {
//                System.out.print(i + ": ");
//                ItemStack stack =event.craftingMatrix.getStack(i);
//                if (stack != null) {
//                    System.out.println(stack.toString());
//                } else {
//                    System.out.println("Null stack");
//                }
//
//            }
//        }
//    }
}
