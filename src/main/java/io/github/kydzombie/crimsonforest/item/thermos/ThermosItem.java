package io.github.kydzombie.crimsonforest.item.thermos;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.Nullable;

public abstract class ThermosItem<FluidType extends Enum<FluidType>> extends TemplateItem {
    public final Class<FluidType> fluidClass;
    public static final String FLUID_KEY = TheCrimsonForest.NAMESPACE.id("fluid_type").toString();
    public static final String MILLIBUCKETS_KEY = TheCrimsonForest.NAMESPACE.id("fluid_amount").toString();
    public final long maxDrops;

    public ThermosItem(Identifier identifier, long maxDrops, Class<FluidType> fluidClass) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(1);
        this.maxDrops = maxDrops;
        this.fluidClass = fluidClass;
    }

    public @Nullable FluidInstance<FluidType> getFluid(ItemStack stack) {
        if (!stack.getStationNbt().contains(FLUID_KEY) || stack.getStationNbt().getByte(FLUID_KEY) == -1) return null;
        byte drinkType = stack.getStationNbt().getByte(FLUID_KEY);
        if (drinkType < 0 || drinkType > fluidClass.getEnumConstants().length) return null;
        long drops = stack.getStationNbt().getLong(MILLIBUCKETS_KEY);
        if (drops < 0 || drops > maxDrops) return null;
        return new FluidInstance<>(fluidClass.getEnumConstants()[drinkType], drops);
    }

    public void setFluid(ItemStack stack, @Nullable FluidInstance<FluidType> fluid) {
        if (fluid == null) {
            stack.getStationNbt().putByte(FLUID_KEY, (byte) -1);
            stack.getStationNbt().putInt(MILLIBUCKETS_KEY, 0);
            return;
        }

        setFluid(stack, fluid.fluidType(), fluid.drops());
    }

    public void setFluid(ItemStack stack, @Nullable FluidType drinkType, long drops) {
        if (drinkType == null || drops <= 0) {
            stack.getStationNbt().putByte(FLUID_KEY, (byte) -1);
            stack.getStationNbt().putLong(MILLIBUCKETS_KEY, 0);
            return;
        }

        stack.getStationNbt().putByte(FLUID_KEY, (byte) drinkType.ordinal());
        stack.getStationNbt().putLong(MILLIBUCKETS_KEY, drops);
    }
}
