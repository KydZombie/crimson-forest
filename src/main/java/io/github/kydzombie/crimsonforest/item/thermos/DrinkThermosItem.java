package io.github.kydzombie.crimsonforest.item.thermos;

import com.github.telvarost.telsdrinks.block.Kettle;
import com.github.telvarost.telsdrinks.block.Mug;
import com.github.telvarost.telsdrinks.blockentity.KettleBlockEntity;
import com.github.telvarost.telsdrinks.events.BlockListener;
import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import net.minecraft.block.Block;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class DrinkThermosItem extends ThermosItem<DrinkThermosItem.DrinkType> implements CustomTooltipProvider {
    public enum DrinkType {
        STEAMED_MILK(true, 0xFFFFFF, BlockListener.MILK_KETTLE, BlockListener.CUP_OF_MILK, 1),
        APPLE_CIDER(false, 0xA05B27, BlockListener.APPLE_KETTLE, BlockListener.APPLE_CIDER, 1),
        HOT_CIDER(true, 0xA05B27, BlockListener.APPLE_KETTLE, BlockListener.APPLE_CIDER, 2),
        POISON(false, 0x348937, BlockListener.POISON_KETTLE, BlockListener.POISON, -1),
        HOT_COCOA(true, 0x492A14, BlockListener.BITTER_KETTLE, BlockListener.BITTER_WATER, 1),
        HOT_CHOCOLATE(true, 0x4D2D16, null, BlockListener.HOT_CHOCOLATE, false, 2),
        LATTE(true, 0xAD724A, null, BlockListener.LATTE, false, 3),
        MOCHA(true, 0x754320, null, BlockListener.MOCHA, false, 4),
        PUMPKIN_SPICE_LATTE(true, 0xC39374, null, BlockListener.PUMPKIN_SPICE_LATTE, false, 6),
        DANDELION_TEA(true, 0x467097, null, BlockListener.DANDELION_TEA, false, 1),
        ROSE_TEA(true, 0x654397, null, BlockListener.ROSE_TEA, false, 1);

        public final boolean hot;
        public final int color;
        public final Block kettleBlock;
        public final Block mugBlock;
        public final boolean hasHotState;
        public final int healAmount;

        DrinkType(boolean hot, int color, Block kettleBlock, Block mugBlock, int healAmount) {
            this.hot = hot;
            this.color = color;
            this.kettleBlock = kettleBlock;
            this.mugBlock = mugBlock;
            this.hasHotState = false;
            this.healAmount = healAmount;
        }

        DrinkType(boolean hot, int color, Block kettleBlock, Block mugBlock, boolean hasHotState, int healAmount) {
            this.hot = hot;
            this.color = color;
            this.kettleBlock = kettleBlock;
            this.mugBlock = mugBlock;
            this.hasHotState = hasHotState;
            this.healAmount = healAmount;
        }

        public boolean hasHotTexture() {
            if (this == POISON) return false;
            return hot;
        }
    }

    public DrinkThermosItem(Identifier identifier, int maxMillibuckets) {
        super(identifier, maxMillibuckets, DrinkType.class);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        @Nullable var fluid = getFluid(stack);
        if (fluid == null) return super.use(stack, world, user);
        world.playSound(user.x, user.y, user.z, "telsdrinks:drink", 1.0F, 1.0F);

        int healAmount = fluid.fluidType().healAmount;
        if (healAmount > 0) {
            user.heal(healAmount);
        } else if (healAmount < 0) {
            user.damage(null, -healAmount);
        }

        setFluid(stack, fluid.fluidType(), fluid.millibuckets() - 1000);

        return super.use(stack, world, user);
    }

    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        var fluid = getFluid(stack);
        DrinkType currentDrinkType = fluid == null ? null : fluid.fluidType();
        int currentMillibuckets = fluid == null ? 0 : fluid.millibuckets();
        if (currentMillibuckets >= maxMillibuckets) return false;
        Block block = world.getBlockState(x, y, z).getBlock();
        System.out.println(block.getTranslatedName());
        if (block instanceof Kettle) {
            for (DrinkType drinkType : DrinkType.values()) {
                if (currentDrinkType != null && drinkType != currentDrinkType) continue;
                if (drinkType.kettleBlock != block) continue;
                boolean hot = world.getBlockId(x, y - 1, z) == Block.LAVA.id || world.getBlockId(x, y - 1, z) == Block.FLOWING_LAVA.id || world.getBlockId(x, y - 1, z) == Block.FIRE.id || world.getBlockId(x, y - 1, z) == Block.LIT_FURNACE.id;
                if (drinkType != DrinkType.POISON && hot != drinkType.hot) continue;
                KettleBlockEntity blockEntity = (KettleBlockEntity) world.getBlockEntity(x, y, z);
                if (blockEntity.takeLiquidOut()) {
                    setFluid(stack, drinkType, currentMillibuckets + 1000);
                } else {
                    return false;
                }
                return true;
            }
        } else if (block instanceof Mug) {
            for (DrinkType drinkType : DrinkType.values()) {
                if (currentDrinkType != null && drinkType != currentDrinkType) continue;
                if (drinkType.mugBlock != block) continue;
                BlockState state = world.getBlockState(x, y, z);
                if (state.get(Mug.EMPTY) || drinkType.hasHotState && state.get(Mug.HOT) != drinkType.hot) continue;
                setFluid(stack, drinkType, currentMillibuckets + 1000);
                world.setBlock(x, y, z, BlockListener.EMPTY_MUG.id);
                return true;
            }
        }

        return super.useOnBlock(stack, user, world, x, y, z, side);
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        var fluid = getFluid(stack);
        if (fluid == null) {
            return new String[]{
                    originalTooltip,
                    I18n.getTranslation("thermos.crimsonforest.empty_text")
            };
        }

        return new String[]{
                originalTooltip,
                I18n.getTranslation(
                        "fluid_thermos.crimsonforest.fluid_text",
                        fluid.millibuckets(),
                        I18n.getTranslation("drink.crimsonforest." + fluid.fluidType().toString().toLowerCase() + ".name")
                )
        };
    }
}
