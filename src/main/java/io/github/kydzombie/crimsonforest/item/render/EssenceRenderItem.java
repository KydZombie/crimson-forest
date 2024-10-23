package io.github.kydzombie.crimsonforest.item.render;

import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.item.CrimsonWeaponItem;
import io.github.kydzombie.crimsonforest.item.EssenceContainer;
import io.github.kydzombie.crimsonforest.item.HasBreakEvent;
import io.github.kydzombie.crimsonforest.item.thermos.LifeTunedThermosItem;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

public class EssenceRenderItem extends CrimsonWeaponItem implements EssenceContainer, CustomTooltipProvider, HasBreakEvent {
    public static final String BLOOD_DRIP_TICKS_NBT = "crimsonforest:blood_drip_ticks";
    private static final int BLOOD_DRIP_TICKS_PER_KILL = 160;
    private static final String VIAL_NBT = "crimsonforest:has_vial";
    private static final String ESSENCE_NBT = "crimsonforest:essence";
    private final int millibucketsPerKill;

    public EssenceRenderItem(Identifier identifier, int durability, int attackDamage, int millibucketsPerKill) {
        super(identifier, durability, attackDamage);
        this.millibucketsPerKill = millibucketsPerKill;
    }

    @Override
    protected void onKill(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (giveEssence(stack, EssenceType.LIFE, millibucketsPerKill) > 0) {
            attacker.world.playSound(attacker, "crimsonforest:item.fill_vial", 1.0F, random.nextFloat(0.9f, 1.1f));
        }
        stack.getStationNbt().putInt(BLOOD_DRIP_TICKS_NBT, BLOOD_DRIP_TICKS_PER_KILL);
    }

    @Override
    public void onBreak(ItemStack stack, Entity user) {
        if (hasVial(stack)) {
            ItemStack vialStack = TheCrimsonForest.vialItem.asStack(EssenceType.LIFE, getEssence(stack, EssenceType.LIFE));
            if (user instanceof PlayerEntity player) {
                player.inventory.addStack(vialStack);
            } else {
                user.dropItem(vialStack, 0);
            }
        }
    }

    public boolean hasVial(ItemStack stack) {
        return stack.getStationNbt().getBoolean(VIAL_NBT);
    }

    public void putVial(ItemStack stack, int value) {
        stack.getStationNbt().putBoolean(VIAL_NBT, true);
        stack.getStationNbt().putInt(ESSENCE_NBT, value);
    }

    public ItemStack removeVial(ItemStack stack) {
        if (!hasVial(stack)) {
            return null;
        }
        int essence = getEssence(stack, EssenceType.LIFE);
        setEssence(stack, EssenceType.LIFE, 0);
        stack.getStationNbt().putBoolean(VIAL_NBT, false);
        return TheCrimsonForest.vialItem.asStack(EssenceType.LIFE, essence);
    }

    @Override
    public List<EssenceType> getEssenceTypes(ItemStack stack) {
        if (hasVial(stack)) {
            return List.of(EssenceType.LIFE);
        } else {
            return List.of();
        }
    }

    @Override
    public int getMaxEssence(ItemStack stack, EssenceType type) {
        if (type != EssenceType.LIFE || !hasVial(stack)) return 0;
        return TheCrimsonForest.vialItem.maxEssence;
    }

    @Override
    public int getEssence(ItemStack stack, EssenceType type) {
        if (type != EssenceType.LIFE || !hasVial(stack)) return 0;
        return stack.getStationNbt().getInt(ESSENCE_NBT);
    }

    @Override
    public void setEssence(ItemStack stack, EssenceType type, int value) {
        if (type != EssenceType.LIFE || !hasVial(stack)) return;
        stack.getStationNbt().putInt(ESSENCE_NBT, value);
    }

    @Override
    public boolean canGiveEssence(ItemStack stack, EssenceType type) {
        return false;
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        if (!hasVial(stack)) {
            return new String[]{
                    originalTooltip,
                    I18n.getTranslation("essence_render.crimsonforest.no_vial_text")
            };
        }

        return new String[]{
                originalTooltip,
                I18n.getTranslation("essence_render.crimsonforest.essence_text", +getEssence(stack, EssenceType.LIFE))
        };
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        boolean hasVial = hasVial(stack);
        if (!hasVial) {
            for (int i = 0; i < user.inventory.size(); i++) {
                ItemStack invStack = user.inventory.getStack(i);
                if (invStack == null) continue;
                if (invStack.itemId == TheCrimsonForest.vialItem.id) {
                    int vialEssence = TheCrimsonForest.vialItem.getEssence(invStack, EssenceType.LIFE);
                    if (vialEssence >=
                            TheCrimsonForest.vialItem.getMaxEssence(invStack, EssenceType.LIFE))
                        continue;

                    user.inventory.removeStack(i, 1);
                    world.playSound(user, "crimsonforest:item.replace_vial", 1.0F, random.nextFloat(0.95f, 1.05f));
                    putVial(stack, vialEssence);
                    user.swingHand();
                    return stack;
                }
            }
        } else {
            world.playSound(user, "crimsonforest:item.replace_vial", 1.0F, random.nextFloat(0.8f, 0.9f));
            user.inventory.addStack(removeVial(stack));
            user.swingHand();
        }
        return stack;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        NbtCompound nbt = stack.getStationNbt();
        int bloodDripTicks = nbt.getInt(BLOOD_DRIP_TICKS_NBT);
        if (bloodDripTicks > 0) {
            nbt.putInt(BLOOD_DRIP_TICKS_NBT, bloodDripTicks - 1);
        }
        if (entity instanceof PlayerEntity player) {
            ItemStack[] accessories = AccessoryAccess.getAccessories(player, "thermos");
            for (ItemStack accessoryStack : accessories) {
                if (accessoryStack == null) continue;
                if (accessoryStack.getItem() instanceof LifeTunedThermosItem thermos) {
                    int essenceGiven = thermos.giveEssence(accessoryStack, EssenceType.LIFE, getEssence(stack, EssenceType.LIFE));

                    if (essenceGiven > 0) {
                        takeEssence(stack, EssenceType.LIFE, essenceGiven);
                        break;
                    }
                }
            }
        }
    }
}
