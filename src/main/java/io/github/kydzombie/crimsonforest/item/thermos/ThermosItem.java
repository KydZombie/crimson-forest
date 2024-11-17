package io.github.kydzombie.crimsonforest.item.thermos;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.Nullable;

public abstract class ThermosItem<FluidType extends Enum<FluidType>> extends TemplateItem {
    public final Class<FluidType> fluidClass;
    public static final int BUCKET_AMOUNT = 1000;
    public static final String FLUID_KEY = TheCrimsonForest.NAMESPACE.id("fluid_type").toString();
    public static final String MILLIBUCKETS_KEY = TheCrimsonForest.NAMESPACE.id("fluid_amount").toString();
    public final int maxMillibuckets;

    public ThermosItem(Identifier identifier, int maxMillibuckets, Class<FluidType> fluidClass) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(1);
        this.maxMillibuckets = maxMillibuckets;
        this.fluidClass = fluidClass;
    }

    public @Nullable FluidInstance<FluidType> getFluid(ItemStack stack) {
        if (!stack.getStationNbt().contains(FLUID_KEY) || stack.getStationNbt().getByte(FLUID_KEY) == -1) return null;
        byte drinkType = stack.getStationNbt().getByte(FLUID_KEY);
        if (drinkType < 0 || drinkType > fluidClass.getEnumConstants().length) return null;
        int millibuckets = stack.getStationNbt().getInt(MILLIBUCKETS_KEY);
        if (millibuckets < 0 || millibuckets > maxMillibuckets) return null;
        return new FluidInstance<>(fluidClass.getEnumConstants()[drinkType], millibuckets);
    }

    public void setFluid(ItemStack stack, @Nullable FluidInstance<FluidType> fluid) {
        if (fluid == null) {
            stack.getStationNbt().putByte(FLUID_KEY, (byte) -1);
            stack.getStationNbt().putInt(MILLIBUCKETS_KEY, 0);
            return;
        }

        setFluid(stack, fluid.fluidType(), fluid.millibuckets());
    }

    public void setFluid(ItemStack stack, @Nullable FluidType drinkType, int millibuckets) {
        if (drinkType == null || millibuckets <= 0) {
            stack.getStationNbt().putByte(FLUID_KEY, (byte) -1);
            stack.getStationNbt().putInt(MILLIBUCKETS_KEY, 0);
            return;
        }

        stack.getStationNbt().putByte(FLUID_KEY, (byte) drinkType.ordinal());
        stack.getStationNbt().putInt(MILLIBUCKETS_KEY, millibuckets);
    }
}
