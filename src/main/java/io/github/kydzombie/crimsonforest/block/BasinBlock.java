package io.github.kydzombie.crimsonforest.block;

import io.github.kydzombie.crimsonforest.block.entity.BasinBlockEntity;
import io.github.kydzombie.crimsonforest.item.EssenceContainer;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

public class BasinBlock extends TemplateBlockWithEntity {
    public BasinBlock(Identifier identifier, Material material) {
        super(identifier, material);
        setTranslationKey(identifier);
        setHardness(1.5F);
        setResistance(10.0F);
        setSoundGroup(STONE_SOUND_GROUP);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if (world.getBlockEntity(x, y, z) instanceof BasinBlockEntity basin) {
            if (!basin.canPlayerUse(player)) return false;
            ItemStack stack = player.getHand();
            ItemStack basinStack = basin.getStack(0);
            if (stack == null) {
                if (basinStack == null) return false;
                player.inventory.addStack(basinStack);
                basin.setStack(0, null);
                return true;
            } else if (stack.getItem() instanceof EssenceContainer container) {
                boolean multiple = stack.count > 1;
                ItemStack newStack = stack;
                if (multiple) {
                    newStack = stack.copy();
                    newStack.count = 1;
                }

                List<EssenceType> containerEssenceTypes = container.getEssenceTypes(stack);
                EssenceType basinEssenceType = basin.getEssenceType();
                int basinEssence = basin.getEssence();

                System.out.println("basinEssenceType = " + basinEssenceType);
                System.out.println("basinEssence = " + basinEssence);

                if (basinEssenceType != null) {
                    if (!containerEssenceTypes.isEmpty() && !containerEssenceTypes.contains(basinEssenceType))
                        return false;
                } else {
                    if (containerEssenceTypes.isEmpty()) return false;
                }

                for (EssenceType essenceType : EssenceType.values()) {
                    if (basinEssenceType != null && essenceType != basinEssenceType) continue;
                    if (!containerEssenceTypes.isEmpty()) {
                        if (!containerEssenceTypes.contains(essenceType)) continue;
                    }

                    if (container.canGiveEssence(stack, essenceType)) {
                        System.out.println("Can give");
                        int givenEssence = container.giveEssence(newStack, essenceType, basinEssence);
                        if (givenEssence > 0) {
                            basin.setEssence(essenceType, basinEssence - givenEssence);
                            if (multiple) {
                                player.inventory.removeStack(player.inventory.selectedSlot, 1);
                                if (!player.inventory.addStack(newStack)) {
                                    player.dropItem(newStack, false);
                                }
                            }
                            return true;
                        }
                    }

                    if (container.canTakeEssence(stack, essenceType)) {
                        System.out.println("Can take");
                        int takenEssence = container.takeEssence(newStack, essenceType, BasinBlockEntity.MAX_ESSENCE - basinEssence);
                        if (takenEssence > 0) {
                            basin.setEssence(essenceType, basinEssence + takenEssence);
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
            if (basinStack != null) return false;
            ItemStack taken = player.inventory.removeStack(player.inventory.selectedSlot, 1);
            basin.setStack(0, taken);
            return true;
        }
        return false;
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new BasinBlockEntity();
    }
}
