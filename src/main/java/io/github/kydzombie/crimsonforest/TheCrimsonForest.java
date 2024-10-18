package io.github.kydzombie.crimsonforest;

import com.matthewperiut.accessoryapi.api.AccessoryRegister;
import io.github.kydzombie.crimsonforest.block.MortarAndPestleBlock;
import io.github.kydzombie.crimsonforest.block.entity.MortarAndPestleBlockEntity;
import io.github.kydzombie.crimsonforest.item.LifeEssenceCollectorItem;
import io.github.kydzombie.crimsonforest.item.VialItem;
import io.github.kydzombie.crimsonforest.item.thermos.FluidThermosItem;
import io.github.kydzombie.crimsonforest.item.thermos.LifeTunedThermosItem;
import io.github.kydzombie.crimsonforest.item.thermos.NatureTunedThermosItem;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.fabricmc.api.ModInitializer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
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

    @EventListener
    private void registerBlocks(BlockRegistryEvent event) {
        mortarAndPestleBlock = new MortarAndPestleBlock(NAMESPACE.id("mortar_and_pestle"), Material.WOOD);
    }

    @EventListener
    private void registerBlockEntity(BlockEntityRegisterEvent event) {
        event.register(MortarAndPestleBlockEntity.class, NAMESPACE.id("mortar_and_pestle").toString());
    }

    public static LifeEssenceCollectorItem lifeEssenceCollectorItem;

    public static VialItem emptyVialItem;
    public static VialItem lifeVialItem;
    public static VialItem natureVialItem;

    public static FluidThermosItem ironThermosItem;
    public static LifeTunedThermosItem lifeTunedIronThermosItem;
    public static NatureTunedThermosItem natureTunedIronThermosItem;

    public static FluidThermosItem arcaneThermosItem;
    public static LifeTunedThermosItem lifeTunedArcaneThermosItem;
    public static NatureTunedThermosItem natureTunedArcaneThermosItem;

    @EventListener
    private void registerItems(ItemRegistryEvent event) {
        lifeEssenceCollectorItem = new LifeEssenceCollectorItem(NAMESPACE.id("life_essence_collector"));

        emptyVialItem = new VialItem(NAMESPACE.id("empty_vial"), null);
        lifeVialItem = new VialItem(NAMESPACE.id("life_vial"), EssenceType.LIFE);
        natureVialItem = new VialItem(NAMESPACE.id("nature_vial"), EssenceType.NATURE);

        ironThermosItem = new FluidThermosItem(NAMESPACE.id("iron_thermos"), 8000);
        lifeTunedIronThermosItem = new LifeTunedThermosItem(NAMESPACE.id("life_tuned_iron_thermos"), 8000);
        natureTunedIronThermosItem = new NatureTunedThermosItem(NAMESPACE.id("nature_tuned_iron_thermos"), 8000);

        arcaneThermosItem = new FluidThermosItem(NAMESPACE.id("arcane_thermos"), 16000);
        lifeTunedArcaneThermosItem = new LifeTunedThermosItem(NAMESPACE.id("life_tuned_arcane_thermos"), 16000);
        natureTunedArcaneThermosItem = new NatureTunedThermosItem(NAMESPACE.id("nature_tuned_arcane_thermos"), 16000);
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
