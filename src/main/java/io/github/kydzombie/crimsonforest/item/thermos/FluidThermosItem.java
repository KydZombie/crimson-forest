package io.github.kydzombie.crimsonforest.item.thermos;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class FluidThermosItem extends ThermosItem implements CustomTooltipProvider {
    public static final String FLUID_KEY = TheCrimsonForest.NAMESPACE.id("fluid_type").toString();

    public FluidThermosItem(Identifier identifier, int maxMillibuckets) {
        super(identifier, maxMillibuckets);
        setTranslationKey(identifier);
        setMaxCount(1);
    }

    public int getMillibuckets(ItemStack stack) {
        return stack.getStationNbt().getInt(MILLIBUCKETS_KEY);
    }

    public void setMillibuckets(ItemStack stack, int millibuckets) {
        stack.getStationNbt().putInt(MILLIBUCKETS_KEY, millibuckets);
        if (millibuckets == 0) {
            setFluidType(stack, FluidType.NONE);
        }
    }

    public void setFluidType(ItemStack stack, FluidType fluidType) {
        stack.getStationNbt().putByte(FLUID_KEY, (byte) fluidType.ordinal());
    }

    public @NotNull FluidType getFluidType(ItemStack stack) {
        if (!stack.getStationNbt().contains(FLUID_KEY)) return FluidType.NONE;
        byte fluidType = stack.getStationNbt().getByte(FLUID_KEY);
        if (fluidType < 0 || fluidType >= FluidType.values().length) return FluidType.NONE;
        return FluidType.values()[fluidType];
    }

    /**
     * The state of stack should change
     *
     * @return true if there is liquid, so no other checks should be performed.
     */
    boolean attemptPickupBlock(ItemStack stack, FluidType currentFluidType, int currentMillibuckets, World world, int x, int y, int z, PlayerEntity user) {
        if (!world.canInteract(user, x, y, z)) return false;

        Material material = world.getMaterial(x, y, z);
        FluidType fluidType = FluidType.fromMaterial(material);
        if (fluidType == null) return false;

        if ((currentFluidType != FluidType.NONE && currentFluidType != fluidType) ||
                world.getBlockMeta(x, y, z) != 0 || // Make sure not flowing
                currentMillibuckets + BUCKET_AMOUNT > maxMillibuckets)
            return true;

        world.setBlock(x, y, z, 0);
        if (currentFluidType == FluidType.NONE) setFluidType(stack, fluidType);
        setMillibuckets(stack, currentMillibuckets + BUCKET_AMOUNT);
        user.swingHand();
        return true;
    }

    /**
     * The state of stack should change
     *
     * @return true if place was successful
     */
    boolean attemptPlace(ItemStack stack, FluidType currentFluidType, int currentMillibuckets, World world, int x, int y, int z, int side, PlayerEntity user, Vec3d var13) {
        if (currentMillibuckets < BUCKET_AMOUNT) return false;

        if (!world.isAir(x, y, z) && world.getMaterial(x, y, z).isSolid()) {
            Vec3i offset = Direction.byId(side).getVector();
            x += offset.getX();
            y += offset.getY();
            z += offset.getZ();
        }

        if (!world.isAir(x, y, z) && world.getMaterial(x, y, z).isSolid()) return false;

        if (world.dimension.evaporatesWater && currentFluidType == FluidType.WATER) {
            world.playSound(var13.x + 0.5, var13.y + 0.5, var13.z + 0.5, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);

            for (int var28 = 0; var28 < 8; var28++) {
                world.addParticle("largesmoke", (double) x + Math.random(), (double) y + Math.random(), (double) z + Math.random(), 0.0, 0.0, 0.0);
            }
        } else {
            world.setBlock(x, y, z, currentFluidType.FLOWING_BLOCK_ID, 0);
        }

        setMillibuckets(stack, currentMillibuckets - BUCKET_AMOUNT);
        user.swingHand();
        return true;
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        float var4 = 1.0F;
        float var5 = user.prevPitch + (user.pitch - user.prevPitch) * var4;
        float var6 = user.prevYaw + (user.yaw - user.prevYaw) * var4;
        double var7 = user.prevX + (user.x - user.prevX) * (double) var4;
        double var9 = user.prevY + (user.y - user.prevY) * (double) var4 + 1.62 - (double) user.standingEyeHeight;
        double var11 = user.prevZ + (user.z - user.prevZ) * (double) var4;
        Vec3d var13 = Vec3d.createCached(var7, var9, var11);
        float var14 = MathHelper.cos(-var6 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float var15 = MathHelper.sin(-var6 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float var16 = -MathHelper.cos(-var5 * (float) (Math.PI / 180.0));
        float var17 = MathHelper.sin(-var5 * (float) (Math.PI / 180.0));
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        double var21 = 5.0;
        Vec3d hitPos = var13.add((double) var18 * var21, (double) var17 * var21, (double) var20 * var21);
        HitResult hit = world.raycast(var13, hitPos, true);

        FluidType currentFluidType = getFluidType(stack);
        int currentMillibuckets = getMillibuckets(stack);

        if (hit == null) return stack;

        if (hit.type == HitResultType.BLOCK && currentFluidType != FluidType.MILK) {
            if (attemptPickupBlock(stack, currentFluidType, currentMillibuckets, world, hit.blockX, hit.blockY, hit.blockZ, user)) {
                user.swingHand();
                return stack;
            }

            if (attemptPlace(stack, currentFluidType, currentMillibuckets, world, hit.blockX, hit.blockY, hit.blockZ, hit.side, user, var13)) {
                user.swingHand();
                return stack;
            }
        }

        return stack;
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        FluidType fluidType = getFluidType(stack);
        int millibuckets = getMillibuckets(stack);
        if (fluidType == FluidType.NONE) {
            return new String[]{
                    originalTooltip,
                    I18n.getTranslation("thermos.crimsonforest.empty_text")
            };
        }
        return new String[]{
                originalTooltip,
                I18n.getTranslation(
                        "fluid_thermos.crimsonforest.fluid_text",
                        millibuckets,
                        I18n.getTranslation("fluid.crimsonforest." + fluidType.toString().toLowerCase() + ".name")
                )
        };
    }

    public enum FluidType {
        NONE(null, 0),
        MILK(null, 0),
        WATER(Material.WATER, Block.FLOWING_WATER.id),
        LAVA(Material.LAVA, Block.FLOWING_LAVA.id);

        private static final HashMap<Material, FluidType> materialToFluidTypeMap = new HashMap<>();

        static {
            for (FluidType fluidType : values()) {
                if (fluidType.MATERIAL == null) continue;
                materialToFluidTypeMap.put(fluidType.MATERIAL, fluidType);
            }
        }

        public final Material MATERIAL;
        public final int FLOWING_BLOCK_ID;

        FluidType(Material material, int flowingBlockId) {
            MATERIAL = material;
            FLOWING_BLOCK_ID = flowingBlockId;
        }

        public static @Nullable FluidType fromMaterial(Material material) {
            return materialToFluidTypeMap.get(material);
        }

        @Environment(EnvType.CLIENT)
        public float getPredicateValue() {
            return (float) ordinal() / (FluidType.values().length - 1);
        }
    }
}
