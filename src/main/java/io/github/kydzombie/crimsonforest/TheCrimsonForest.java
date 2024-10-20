package io.github.kydzombie.crimsonforest;

import com.matthewperiut.accessoryapi.api.AccessoryRegister;
import io.github.kydzombie.crimsonforest.block.CrudeForgeBlock;
import io.github.kydzombie.crimsonforest.block.MortarAndPestleBlock;
import io.github.kydzombie.crimsonforest.block.entity.CrudeForgeBlockEntity;
import io.github.kydzombie.crimsonforest.block.entity.MortarAndPestleBlockEntity;
import io.github.kydzombie.crimsonforest.item.SoulItem;
import io.github.kydzombie.crimsonforest.item.render.EssenceRenderItem;
import io.github.kydzombie.crimsonforest.item.VialItem;
import io.github.kydzombie.crimsonforest.item.render.SoulRenderItem;
import io.github.kydzombie.crimsonforest.item.render.VinelashRenderItem;
import io.github.kydzombie.crimsonforest.item.thermos.FluidThermosItem;
import io.github.kydzombie.crimsonforest.item.thermos.LifeTunedThermosItem;
import io.github.kydzombie.crimsonforest.item.thermos.NatureTunedThermosItem;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import io.github.kydzombie.crimsonforest.recipe.CrudeForgeRecipe;
import io.github.kydzombie.crimsonforest.recipe.CrudeForgeRecipeRegistry;
import net.fabricmc.api.ModInitializer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class TheCrimsonForest implements ModInitializer {
    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    @Entrypoint.Logger
    public static final Logger LOGGER = Null.get();

    @Override
    public void onInitialize() {
        System.out.println("Hello World!");
        AccessoryRegister.add("thermos", "assets/crimsonforest/accessoryapi/accessory_icon_atlas.png", 0, 0);
    }

    public static MortarAndPestleBlock mortarAndPestleBlock;

    public static CrudeForgeBlock crudeForgeBlock;

    @EventListener
    private void registerBlocks(BlockRegistryEvent event) {
        mortarAndPestleBlock = new MortarAndPestleBlock(NAMESPACE.id("mortar_and_pestle"), Material.WOOD);

        crudeForgeBlock = new CrudeForgeBlock(NAMESPACE.id("crude_forge"), Material.STONE);
    }

    @EventListener
    private void registerBlockEntity(BlockEntityRegisterEvent event) {
        event.register(MortarAndPestleBlockEntity.class, NAMESPACE.id("mortar_and_pestle").toString());
        event.register(CrudeForgeBlockEntity.class, NAMESPACE.id("crude_forge").toString());
    }

    public static EssenceRenderItem woodenEssenceRenderItem;
    public static EssenceRenderItem ironEssenceRenderItem;
    public static VinelashRenderItem ironVinelashRenderItem;
    public static SoulRenderItem tarnishedSoulRenderItem;
    public static SoulRenderItem arcaneSoulRenderItem;

    public static SoulItem zombieSoulItem;
    public static SoulItem spiderSoulItem;
    public static SoulItem skeletonSoulItem;
    public static SoulItem passiveSoulItem;
    public static SoulItem corruptedSoulItem;

    public static VialItem emptyVialItem;
    public static VialItem lifeVialItem;
    public static VialItem natureVialItem;

    public static FluidThermosItem ironThermosItem;
    public static LifeTunedThermosItem lifeTunedIronThermosItem;
    public static NatureTunedThermosItem natureTunedIronThermosItem;

    public static FluidThermosItem arcaneThermosItem;
    public static LifeTunedThermosItem lifeTunedArcaneThermosItem;
    public static NatureTunedThermosItem natureTunedArcaneThermosItem;

    public static TemplateItem lifeInfusedIngot;
    public static TemplateItem natureInfusedIngot;

    @EventListener
    private void registerItems(ItemRegistryEvent event) {
        woodenEssenceRenderItem = new EssenceRenderItem(NAMESPACE.id("wooden_essence_render"), 32, 4, 100, 1000);
        ironEssenceRenderItem = new EssenceRenderItem(NAMESPACE.id("iron_essence_render"), 250, 6, 150, 2500);
        ironVinelashRenderItem = new VinelashRenderItem(NAMESPACE.id("iron_vinelash_render"), 250, 6);
        tarnishedSoulRenderItem = new SoulRenderItem(NAMESPACE.id("tarnished_soul_render"), 512, 7, .1f);
        arcaneSoulRenderItem = new SoulRenderItem(NAMESPACE.id("arcane_soul_render"), 1024, 7, .5f);

        zombieSoulItem = new SoulItem(NAMESPACE.id("zombie_soul"));
        spiderSoulItem = new SoulItem(NAMESPACE.id("spider_soul"));
        skeletonSoulItem = new SoulItem(NAMESPACE.id("skeleton_soul"));
        passiveSoulItem = new SoulItem(NAMESPACE.id("passive_soul"));
        corruptedSoulItem = new SoulItem(NAMESPACE.id("corrupted_soul"));

        emptyVialItem = new VialItem(NAMESPACE.id("empty_vial"), null);
        lifeVialItem = new VialItem(NAMESPACE.id("life_vial"), EssenceType.LIFE);
        natureVialItem = new VialItem(NAMESPACE.id("nature_vial"), EssenceType.NATURE);

        ironThermosItem = new FluidThermosItem(NAMESPACE.id("iron_thermos"), 8000);
        lifeTunedIronThermosItem = new LifeTunedThermosItem(NAMESPACE.id("life_tuned_iron_thermos"), 8000);
        natureTunedIronThermosItem = new NatureTunedThermosItem(NAMESPACE.id("nature_tuned_iron_thermos"), 8000);

        arcaneThermosItem = new FluidThermosItem(NAMESPACE.id("arcane_thermos"), 16000);
        lifeTunedArcaneThermosItem = new LifeTunedThermosItem(NAMESPACE.id("life_tuned_arcane_thermos"), 16000);
        natureTunedArcaneThermosItem = new NatureTunedThermosItem(NAMESPACE.id("nature_tuned_arcane_thermos"), 16000);

        lifeInfusedIngot = (TemplateItem) new TemplateItem(NAMESPACE.id("life_infused_ingot")).setTranslationKey(NAMESPACE.id("life_infused_ingot"));
        natureInfusedIngot = (TemplateItem) new TemplateItem(NAMESPACE.id("nature_infused_ingot")).setTranslationKey(NAMESPACE.id("nature_infused_ingot"));

        CrudeForgeRecipeRegistry.addRecipe(new CrudeForgeRecipe(new ItemStack(lifeInfusedIngot), 120, new ItemStack(Item.IRON_INGOT), new ItemStack(lifeVialItem)));
        CrudeForgeRecipeRegistry.addRecipe(new CrudeForgeRecipe(new ItemStack(natureInfusedIngot), 120, new ItemStack(Item.IRON_INGOT), new ItemStack(natureVialItem)));
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
