package io.github.kydzombie.crimsonforest.mixin;

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
            var fluid = thermos.getFluid(stack);
            if (fluid == null) {
                thermos.setFluid(stack, FluidThermosItem.BucketFluid.MILK, FluidThermosItem.BUCKET_AMOUNT);
                player.swingHand();
            } else {
                FluidThermosItem.BucketFluid currentFluidType = fluid.fluidType();
                int currentMillibuckets = fluid.millibuckets();
                if (currentMillibuckets < thermos.maxMillibuckets) {
                    thermos.setFluid(stack, currentFluidType, Math.min(currentMillibuckets + FluidThermosItem.BUCKET_AMOUNT, thermos.maxMillibuckets));
                    player.swingHand();
                }
            }

        }
    }
}
