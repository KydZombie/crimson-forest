package io.github.kydzombie.crimsonforest.item.render;

import io.github.kydzombie.crimsonforest.entity.VinelashAttackEntity;
import io.github.kydzombie.crimsonforest.item.CrimsonWeaponItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;

public class VinelashRenderItem extends CrimsonWeaponItem {
    public VinelashRenderItem(Identifier identifier, int durability, int attackDamage) {
        super(identifier, durability, attackDamage);
    }

//    @Override
//    public void useOnEntity(ItemStack stack, LivingEntity entity) {
//        super.useOnEntity(stack, entity);
//        if (entity instanceof Entangleable entangleable) {
//            entangleable.setEntangledTime(80);
//        }
//    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if (!world.isRemote) {
            // TODO: Figure out better projectile
            VinelashAttackEntity attack = new VinelashAttackEntity(world, user);
            world.spawnEntity(attack);
        }
        user.swingHand();
        return super.use(stack, world, user);
    }
}
