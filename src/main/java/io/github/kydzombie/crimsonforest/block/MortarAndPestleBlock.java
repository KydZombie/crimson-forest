package io.github.kydzombie.crimsonforest.block;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.block.entity.MortarAndPestleBlockEntity;
import io.github.kydzombie.crimsonforest.item.VialItem;
import io.github.kydzombie.crimsonforest.item.thermos.NatureTunedThermosItem;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
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
            if (essence == 0) {
                int takenEssence = thermos.takeEssence(stack, EssenceType.NATURE, MortarAndPestleBlockEntity.MAX_ESSENCE - essence);
                if (takenEssence > 0) {
                    blockEntity.setEssence(essence + takenEssence);
                    return true;
                }
            } else {
                int givenEssence = thermos.giveEssence(stack, EssenceType.NATURE, essence);
                if (givenEssence > 0) {
                    blockEntity.setEssence(essence - givenEssence);
                    return true;
                }
            }
        } else if (stack.getItem() == TheCrimsonForest.emptyVialItem) {
            if (essence >= VialItem.ESSENCE_PER_VIAL) {
                stack.count--;
                player.inventory.addStack(new ItemStack(TheCrimsonForest.natureVialItem));
                if (stack.count <= 0) {
                    player.inventory.setStack(player.inventory.selectedSlot, null);
                }

                player.inventory.markDirty();
                blockEntity.setEssence(essence - VialItem.ESSENCE_PER_VIAL);
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
