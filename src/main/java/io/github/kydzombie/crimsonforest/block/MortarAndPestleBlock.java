package io.github.kydzombie.crimsonforest.block;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.block.entity.MortarAndPestleBlockEntity;
import io.github.kydzombie.crimsonforest.item.VialItem;
import io.github.kydzombie.crimsonforest.item.thermos.NatureTunedThermosItem;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;

public class MortarAndPestleBlock extends TemplateBlockWithEntity {
    public static final int ESSENCE_PER_FLOWER = 50;

    public MortarAndPestleBlock(Identifier identifier, Material material) {
        super(identifier, material);
        setTranslationKey(TheCrimsonForest.NAMESPACE.id("mortar_and_pestle"));
        setHardness(0.25f);
        setSoundGroup(WOOD_SOUND_GROUP);
        setBoundingBox(.3125f, 0, .3125f, .6875f, .25f, .6875f);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        ItemStack stack = player.getHand();
        if (stack == null) return false;
        MortarAndPestleBlockEntity blockEntity = ((MortarAndPestleBlockEntity) world.getBlockEntity(x, y, z));
        int essence = blockEntity.getEssence();
        if (stack.itemId == Block.ROSE.id || stack.itemId == Block.DANDELION.id) {
            if (essence >= MortarAndPestleBlockEntity.MAX_ESSENCE) return false;
            blockEntity.setEssence(Math.min(essence + ESSENCE_PER_FLOWER, MortarAndPestleBlockEntity.MAX_ESSENCE));
            stack.split(1);
            player.inventory.markDirty();
            return true;
        } else if (stack.getItem() instanceof NatureTunedThermosItem thermos) {
            int millibuckets = thermos.getMillibuckets(stack);
            if (essence == 0) {
                int takenEssence = Math.min(MortarAndPestleBlockEntity.MAX_ESSENCE, millibuckets);
                if (takenEssence > 0) {
                    thermos.setMillibuckets(stack, millibuckets - takenEssence);
                    blockEntity.setEssence(essence + takenEssence);
                    return true;
                }
            } else {
                int takenEssence = Math.min(essence, thermos.maxMillibuckets - millibuckets);
                if (takenEssence > 0) {
                    thermos.setMillibuckets(stack, millibuckets + takenEssence);
                    blockEntity.setEssence(essence - takenEssence);
                    return true;
                }
            }
        } else if (stack.getItem() == TheCrimsonForest.emptyVialItem) {
            if (essence >= VialItem.MILLIBUCKETS_PER_VIAL) {
                player.inventory.setStack(player.inventory.selectedSlot, new ItemStack(TheCrimsonForest.natureVialItem));
                player.inventory.markDirty();
                blockEntity.setEssence(essence - VialItem.MILLIBUCKETS_PER_VIAL);
                return true;
            }
        }
        return super.onUse(world, x, y, z, player);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new MortarAndPestleBlockEntity();
    }
}
