package io.github.kydzombie.crimsonforest.mixin;

import com.github.telvarost.telsdrinks.item.MugBlockItem;
import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.item.VialItem;
import io.github.kydzombie.crimsonforest.item.thermos.DrinkThermosItem;
import io.github.kydzombie.crimsonforest.item.thermos.FluidInstance;
import io.github.kydzombie.crimsonforest.item.thermos.FluidThermosItem;
import io.github.kydzombie.crimsonforest.item.thermos.ThermosItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipeManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingRecipeManager.class)
public class CraftingRecipeManagerMixin {
    @Inject(method = "craft(Lnet/minecraft/inventory/CraftingInventory;)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    private void crimsonforest_addCustomRecipes(CraftingInventory craftingInventory, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack stack = crimsonforest_checkForVial(craftingInventory);
        if (stack == null) stack = crimsonforest_checkForThermos(craftingInventory);
        if (stack == null) return;
        cir.setReturnValue(stack);
    }

    @Unique
    private @Nullable ItemStack crimsonforest_checkForVial(CraftingInventory craftingInventory) {
        ItemStack vial = null;
        for (int i = 0; i < craftingInventory.size(); i++) {
            ItemStack stack = craftingInventory.getStack(i);
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
    }

    @Unique
    private @Nullable ItemStack crimsonforest_checkForThermos(CraftingInventory craftingInventory) {
        ItemStack thermos = null;
        for (int i = 0; i < craftingInventory.size(); i++) {
            ItemStack stack = craftingInventory.getStack(i);
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
            for (int i = 0; i < craftingInventory.size(); i++) {
                ItemStack stack = craftingInventory.getStack(i);
                if (stack == null || stack.getItem() instanceof ThermosItem<?>) continue;
                if (fluid == null) {
                    for (DrinkThermosItem.DrinkType value : DrinkThermosItem.DrinkType.values()) {
                        if (value.mugBlock.asItem().id == stack.getItem().id) {
                            if (value.hasHotState) {
                                if ((value.hot ? 1 : 0) != stack.getDamage()) {
                                    continue;
                                }
                            }
                            fluid = new FluidInstance<>(value, 1000);
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
                        if (fluid.millibuckets() + 1000 > thermosItem.maxMillibuckets) return null;
                        fluid = fluid.add(1000);
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
    }
}
