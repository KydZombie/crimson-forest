package io.github.kydzombie.crimsonforest.recipe;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

// TODO: Decide if this class should be extendable
public final class CrudeRecipeData {
    private final @NotNull ItemStack outputStack;
    private final @Nullable ItemStack @NotNull [] inputStacksTaken;
    private final int cookTicks;

    public CrudeRecipeData(@NotNull ItemStack outputStack, @Nullable ItemStack @NotNull [] inputStacksTaken, int cookTicks) {
        this.outputStack = outputStack;
        this.inputStacksTaken = inputStacksTaken;
        this.cookTicks = cookTicks;
    }

    public @NotNull ItemStack outputStack() {
        return outputStack;
    }

    public @Nullable ItemStack @NotNull [] inputStacksTaken() {
        return inputStacksTaken;
    }

    public int cookTicks() {
        return cookTicks;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CrudeRecipeData) obj;
        return Objects.equals(this.outputStack, that.outputStack) &&
                Arrays.equals(this.inputStacksTaken, that.inputStacksTaken) &&
                this.cookTicks == that.cookTicks;
    }

    @Override
    public int hashCode() {
        return Objects.hash(outputStack, Arrays.hashCode(inputStacksTaken), cookTicks);
    }

    @Override
    public String toString() {
        return "CrudeRecipeData[" +
                "outputStack=" + outputStack + ", " +
                "inputStacksTaken=" + Arrays.toString(inputStacksTaken) + ", " +
                "cookTicks=" + cookTicks + ']';
    }

}
