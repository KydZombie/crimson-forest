package io.github.kydzombie.crimsonforest.item.thermos;

public record FluidInstance<T>(T fluidType, int millibuckets) {
    public FluidInstance<T> add(int millibuckets) {
        return new FluidInstance<>(fluidType, this.millibuckets + millibuckets);
    }
}
