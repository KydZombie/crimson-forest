package io.github.kydzombie.crimsonforest.mixin;

import io.github.kydzombie.crimsonforest.custom.Entangleable;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements Entangleable {
    @Shadow
    public float lastBodyYaw;
    @Shadow
    public float bodyYaw;
    @Shadow
    public float lastSwingAnimationProgress;
    @Shadow
    public float swingAnimationProgress;
    @Shadow
    public float lastWalkAnimationSpeed;
    @Shadow
    public float walkAnimationSpeed;
    @Shadow
    protected float lastWalkProgress;
    @Shadow
    protected float walkProgress;
    @Unique
    private int crimsonforest_entangledTime = 0;

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    private void stopIfEntangled(CallbackInfo ci) {
        if (crimsonforest_entangledTime > 0) {
            lastWalkProgress = walkProgress;
            lastBodyYaw = bodyYaw;
            lastSwingAnimationProgress = swingAnimationProgress;
            lastWalkAnimationSpeed = walkAnimationSpeed;
            crimsonforest_entangledTime--;
            ci.cancel();
        }
    }

    @Override
    public int getEntangledTime() {
        return crimsonforest_entangledTime;
    }

    @Override
    public void setEntangledTime(int ticks) {
        crimsonforest_entangledTime = ticks;
    }
}
