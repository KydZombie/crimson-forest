package io.github.kydzombie.crimsonforest.mixin;

import io.github.kydzombie.crimsonforest.gui.screen.TickableScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerMixin {
    @Shadow
    public ScreenHandler currentScreenHandler;

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        if (currentScreenHandler instanceof TickableScreenHandler handler) {
            handler.tick();
        }
    }
}
