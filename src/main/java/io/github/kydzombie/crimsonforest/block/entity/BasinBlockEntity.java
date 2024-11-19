package io.github.kydzombie.crimsonforest.block.entity;

import io.github.kydzombie.crimsonforest.fluid.FluidHelper;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import io.github.kydzombie.crimsonforest.packet.BasinBlockUpdatePacket;
import io.github.kydzombie.crimsonforest.recipe.BasinRecipe;
import io.github.kydzombie.crimsonforest.recipe.BasinRecipeRegistry;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.Packet;

public class BasinBlockEntity extends BlockEntity implements SimpleInventory {
    @Getter
    @Setter
    ItemStack[] inventory = new ItemStack[1];
    @Getter
    private EssenceType essenceType;
    public static final long MAX_ESSENCE = FluidHelper.BOTTLE_AMOUNT * 2;
    @Getter
    private long essence = 0;
    /**
     * The progress of the recipe, in essence spent
     */
    private int recipeProgress = 0;

    public ItemEntity renderedItem = null;

    public void setEssence(EssenceType essenceType, long essence) {
        if (essenceType == null || essence <= 0) {
            this.essenceType = null;
            this.essence = 0;
        } else {
            this.essenceType = essenceType;
            this.essence = essence;
        }
        markDirty();
    }

    @Override
    public String getName() {
        return "Basin";
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            if (inventory[0] == null || renderedItem == null || renderedItem.stack == null || !renderedItem.stack.isItemEqual(inventory[0])) {
                resetRenderedItem();
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private void resetRenderedItem() {
        renderedItem = null;
    }

    @Override
    public void tick() {
        super.tick();

        if (inventory[0] == null) {
            recipeProgress = 0;
            return;
        }

        if (essenceType == null || essence <= 0) {
            return;
        }

        BasinRecipe recipe = BasinRecipeRegistry.INSTANCE.getRecipe(inventory[0], essenceType);

        if (recipe == null) {
            recipeProgress = 0;
            return;
        }

        recipeProgress++;
        setEssence(essenceType, essence - 1);
        world.addParticle("smoke", x + 0.5, y + 1.25, z + 0.5, 0.0, 0.0, 0.0);

        if (recipeProgress >= recipe.essence()) {
            setStack(0, recipe.output().copy());
            recipeProgress = 0;
        }
    }

    @Override
    public Packet createUpdatePacket() {
        return new BasinBlockUpdatePacket(x, y, z, essenceType, essence, inventory[0] == null ? new NbtCompound() : inventory[0].writeNbt(new NbtCompound()));
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        readInventoryNbt(nbt);
        byte essenceType = nbt.contains("essence_type") ? nbt.getByte("essence_type") : -1;
        if (essenceType < 0) {
            this.essenceType = null;
        } else {
            this.essenceType = EssenceType.values()[essenceType];
        }
        essence = nbt.getLong("essence_amount");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        writeInventoryNbt(nbt);
        if (essenceType == null) {
            nbt.putByte("essence_type", (byte) -1);
        } else {
            nbt.putByte("essence_type", (byte) essenceType.ordinal());
        }
        nbt.putLong("essence_amount", essence);
    }
}
