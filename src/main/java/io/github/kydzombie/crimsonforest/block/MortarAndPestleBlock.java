package io.github.kydzombie.crimsonforest.block;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.block.entity.MortarAndPestleBlockEntity;
import io.github.kydzombie.crimsonforest.item.HasItemEssenceStorage;
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
    public static final int ESSENCE_PER_FLOWER = 10;

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
        long essence = blockEntity.getEssence();
        if (stack.itemId == Block.ROSE.id || stack.itemId == Block.DANDELION.id) {
            if (essence >= MortarAndPestleBlockEntity.MAX_ESSENCE) return false;
            blockEntity.setEssence(Math.min(essence + ESSENCE_PER_FLOWER, MortarAndPestleBlockEntity.MAX_ESSENCE));
            stack.split(1);
            player.inventory.markDirty();
            return true;
        } else if (stack.getItem() instanceof HasItemEssenceStorage container) {
            boolean multiple = stack.count > 1;
            ItemStack newStack = stack;
            if (multiple) {
                newStack = stack.copy();
                newStack.count = 1;
            }

            if (container.canGiveEssence(stack, EssenceType.NATURE)) {
                long givenEssence = container.giveEssence(newStack, EssenceType.NATURE, essence);
                if (givenEssence > 0) {
                    blockEntity.setEssence(essence - givenEssence);
                    if (multiple) {
                        player.inventory.removeStack(player.inventory.selectedSlot, 1);
                        if (!player.inventory.addStack(newStack)) {
                            player.dropItem(newStack, false);
                        }
                    }
                    return true;
                }
            }

            if (container.canTakeEssence(stack, EssenceType.NATURE)) {
                long takenEssence = container.takeEssence(newStack, EssenceType.NATURE, MortarAndPestleBlockEntity.MAX_ESSENCE - essence);
                if (takenEssence > 0) {
                    blockEntity.setEssence(essence + takenEssence);
                    if (multiple) {
                        player.inventory.removeStack(player.inventory.selectedSlot, 1);
                        if (!player.inventory.addStack(newStack)) {
                            player.dropItem(newStack, false);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new MortarAndPestleBlockEntity();
    }
}
