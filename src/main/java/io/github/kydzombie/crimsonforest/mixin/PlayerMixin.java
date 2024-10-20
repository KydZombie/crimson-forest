package io.github.kydzombie.crimsonforest.mixin;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.gui.screen.TunedThermosScreenHandler;
import io.github.kydzombie.crimsonforest.item.EssenceContainer;
import io.github.kydzombie.crimsonforest.item.VialItem;
import io.github.kydzombie.crimsonforest.item.thermos.TunedThermosInventory;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerMixin {
    @Shadow public ScreenHandler currentScreenHandler;

    // TODO: Make this use interfaces and stuff. Probably in a new library mod?
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        if (currentScreenHandler instanceof TunedThermosScreenHandler tunedThermosScreenHandler) {
            if (tunedThermosScreenHandler.cooldown > 0) {
                tunedThermosScreenHandler.cooldown--;
                return;
            }

            TunedThermosInventory thermosInventory = tunedThermosScreenHandler.thermosInventory;
            EssenceType thermosEssenceType = thermosInventory.tunedThermosItem.getEssenceTypes(thermosInventory.thermosStack).get(0);
            int thermosEssence = thermosInventory.tunedThermosItem.getEssence(thermosInventory.thermosStack, thermosEssenceType);
            int thermosMaxEssence = thermosInventory.tunedThermosItem.getMaxEssence(thermosInventory.thermosStack, thermosEssenceType);
            ItemStack inputStack = thermosInventory.getStack(0);
            ItemStack outputStack = thermosInventory.getStack(1);
            if (inputStack == null || (outputStack != null && outputStack.count >= outputStack.getMaxCount())) return;
            if (inputStack.getItem() instanceof EssenceContainer container) {
                // Vials are special
                if (inputStack.getItem() instanceof VialItem vialItem) {
                    if (vialItem == TheCrimsonForest.emptyVialItem && thermosEssence >= VialItem.ESSENCE_PER_VIAL) {
                        thermosInventory.removeStack(0, 1);
                        thermosInventory.tunedThermosItem.takeEssence(thermosInventory.thermosStack, thermosEssenceType, VialItem.ESSENCE_PER_VIAL);
                        if (outputStack == null) {
                            thermosInventory.setStack(1, new ItemStack(thermosEssenceType == EssenceType.LIFE ? TheCrimsonForest.lifeVialItem : TheCrimsonForest.natureVialItem));
                        } else {
                            outputStack.count++;
                        }
                    } else if (thermosEssence < thermosMaxEssence) {
                        if (inputStack.getItem() == (thermosEssenceType == EssenceType.LIFE ? TheCrimsonForest.lifeVialItem : TheCrimsonForest.natureVialItem)) {
                            thermosInventory.removeStack(0, 1);
                            thermosInventory.tunedThermosItem.giveEssence(thermosInventory.thermosStack, thermosEssenceType, VialItem.ESSENCE_PER_VIAL);
                            if (outputStack == null) {
                                thermosInventory.setStack(1, new ItemStack(TheCrimsonForest.emptyVialItem));
                            } else {
                                outputStack.count++;
                            }
                        }
                    }
                } else {
                    if (!container.getEssenceTypes(inputStack).contains(thermosEssenceType)) return;
                    int containerMaxEssence = container.getMaxEssence(inputStack, thermosEssenceType);
                    int containerEssence = container.getEssence(inputStack, thermosEssenceType);
                    // Extract essence from item
                    if (outputStack != null) return;
                    if (containerEssence > 0 && thermosEssence < thermosMaxEssence) {
                        if (container.canTakeEssence(inputStack, thermosEssenceType)) {
                            int amountTaken = container.takeEssence(inputStack, thermosEssenceType, thermosMaxEssence - thermosEssence);
                            thermosInventory.tunedThermosItem.giveEssence(thermosInventory.thermosStack, thermosEssenceType, amountTaken);
                        }
                        thermosInventory.setStack(0, null);
                        thermosInventory.setStack(1, inputStack);
                    } else { // Insert essence into item
                        if (container.canGiveEssence(inputStack, thermosEssenceType)) {
                            int amountTaken = thermosInventory.tunedThermosItem.takeEssence(thermosInventory.thermosStack, thermosEssenceType, containerMaxEssence - containerEssence);
                            container.giveEssence(inputStack, thermosEssenceType, amountTaken);
                        }
                        thermosInventory.setStack(0, null);
                        thermosInventory.setStack(1, inputStack);
                        return;
                    }
                }

                tunedThermosScreenHandler.cooldown = TunedThermosScreenHandler.COOLDOWN_TICKS;
            }
        }
    }
}
