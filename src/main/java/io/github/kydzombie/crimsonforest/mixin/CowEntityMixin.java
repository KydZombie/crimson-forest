package io.github.kydzombie.crimsonforest.mixin;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.item.thermos.FluidThermosItem;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public class CowEntityMixin {
    @Inject(method = "interact(Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At("HEAD"))
    private void crimsonforest_milkWithThermos(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = player.inventory.getSelectedItem();
        if (stack != null && stack.getItem() instanceof FluidThermosItem thermos) {
            FluidThermosItem.FluidType currentFluidType = thermos.getFluidType(stack);
            if (currentFluidType == FluidThermosItem.FluidType.NONE) {
                thermos.setFluidType(stack, FluidThermosItem.FluidType.MILK);
            }
            int currentMillibuckets = thermos.getMillibuckets(stack);
            if (currentMillibuckets < thermos.maxMillibuckets) {
                thermos.setMillibuckets(stack, Math.min(currentMillibuckets + FluidThermosItem.BUCKET_AMOUNT, thermos.maxMillibuckets));
                player.swingHand();
            }
        }
    }
}
