package io.github.kydzombie.crimsonforest.item.thermos;

public record FluidInstance<T>(T fluidType, long drops) {
    public FluidInstance<T> add(long drops) {
        return new FluidInstance<>(fluidType, this.drops + drops);
    }
}
