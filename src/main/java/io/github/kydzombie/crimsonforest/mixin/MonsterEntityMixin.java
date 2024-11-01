package io.github.kydzombie.crimsonforest.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MonsterEntity.class)
public class MonsterEntityMixin extends MobEntity {
    public MonsterEntityMixin(World world) {
        super(world);
    }

    @Unique
    protected boolean crimsonforest_isCorrectMobForPendant(@NotNull String mobName) {
        return switch (mobName) {
            case "zombie" -> (Object) this instanceof ZombieEntity;
            case "skeleton" -> (Object) this instanceof SkeletonEntity;
            case "spider" -> (Object) this instanceof SpiderEntity;
            case "creeper" -> (Object) this instanceof CreeperEntity;
            default -> false;
        };
    }

    @ModifyReturnValue(method = "getTargetInRange", at = @At("RETURN"))
    private Entity checkForWardingAmulet(Entity original) {
        if (original instanceof PlayerEntity player) {
            ItemStack[] amulets = AccessoryAccess.getAccessories(player, "pendant");
            for (ItemStack amulet : amulets) {
                if (amulet == null) continue;
                if (amulet.getItem() == TheCrimsonForest.wardingAmuletItem) {
                    String mobName = TheCrimsonForest.wardingAmuletItem.getMob(amulet);
                    if (mobName == null) continue;
                    if (!crimsonforest_isCorrectMobForPendant(mobName)) continue;
                    double closestDistance = -1.0;
                    PlayerEntity foundPlayer = null;

                    for(int var12 = 0; var12 < world.players.size(); ++var12) {
                        PlayerEntity otherPlayer = (PlayerEntity)world.players.get(var12);
                        if (otherPlayer == player) continue;
                        double distanceToPlayer = otherPlayer.getSquaredDistance(x, y, z);
                        if ((distanceToPlayer < 16.0 * 16.0) && (closestDistance == -1.0 || distanceToPlayer < closestDistance)) {
                            closestDistance = distanceToPlayer;
                            foundPlayer = otherPlayer;
                        }
                    }

                    return foundPlayer;
                }
            }
        }
        return original;
    }
}
