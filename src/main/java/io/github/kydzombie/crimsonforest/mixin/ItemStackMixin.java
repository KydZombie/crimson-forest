package io.github.kydzombie.crimsonforest.mixin;

import io.github.kydzombie.crimsonforest.item.HasBreakEvent;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public int count;

    @Shadow
    public abstract Item getItem();

    @Inject(method = "damage", at = @At(value = "FIELD", target = "Lnet/minecraft/item/ItemStack;count:I", ordinal = 0))
    private void a(int amount, Entity entity, CallbackInfo ci) {
        if (getItem() instanceof HasBreakEvent item) {
            System.out.println(count);
            item.onBreak(ItemStack.class.cast(this), entity);
        }
    }
}
