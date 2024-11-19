package io.github.kydzombie.crimsonforest.item;

import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.glasslauncher.mods.alwaysmoreitems.api.SubItemProvider;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VialItem extends TemplateItem implements HasItemEssenceStorage, CustomTooltipProvider {
    private static final String ESSENCE_TYPE_NBT = "crimsonforest:essence_type";
    private static final String ESSENCE_AMOUNT_NBT = "crimsonforest:essence_amount";
    public final long maxEssence;

    public VialItem(Identifier identifier, long maxEssence) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(8);
        this.maxEssence = maxEssence;
        setHasSubtypes(true);
    }

    @SubItemProvider
    public List<ItemStack> getSubItems() {
        ArrayList<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(this));
        for (EssenceType essence : EssenceType.values()) {
            System.out.println("essence = " + essence);
            list.add(asStack(essence, maxEssence));
        }
        return list;
    }

    @Override
    public int getTextureId(ItemStack itemStack) {
        List<EssenceType> essenceTypes = getEssenceTypes(itemStack);
        if (essenceTypes.isEmpty()) return textureId;
        @NotNull EssenceType primary = essenceTypes.get(0);
        boolean partial = getEssence(itemStack, primary) >= maxEssence;
        if (partial) {
            return primary.vialFullTexture;
        } else {
            return primary.vialPartialTexture;
        }
    }

    public ItemStack asStack(EssenceType type, long amount) {
        ItemStack vialStack = new ItemStack(this);
        if (amount != 0) {
            setEssence(vialStack, type, amount);
        }
        return vialStack;
    }

    @Override
    public List<EssenceType> getEssenceTypes(ItemStack stack) {
        int essenceType = stack.getStationNbt().getByte(ESSENCE_TYPE_NBT);
        if (essenceType == 0) return List.of();
        return List.of(essenceType == 1 ? EssenceType.LIFE : EssenceType.NATURE);
    }

    @Override
    public long getMaxEssence(ItemStack stack, EssenceType type) {
        List<EssenceType> essenceTypes = getEssenceTypes(stack);
        if (essenceTypes.isEmpty()) return maxEssence;
        if (type != essenceTypes.get(0)) return 0;
        return maxEssence;
    }

    @Override
    public long getEssence(ItemStack stack, EssenceType type) {
        List<EssenceType> essenceTypes = getEssenceTypes(stack);
        if (essenceTypes.isEmpty()) return 0;
        if (type != essenceTypes.get(0)) return 0;
        return stack.getStationNbt().getLong(ESSENCE_AMOUNT_NBT);
    }

    @Override
    public void setEssence(ItemStack stack, EssenceType type, long value) {
        List<EssenceType> essenceTypes = getEssenceTypes(stack);
        if (essenceTypes.isEmpty()) {
            stack.getStationNbt().putByte(ESSENCE_TYPE_NBT, (byte) (type == EssenceType.LIFE ? 1 : 2));
        } else if (type != essenceTypes.get(0)) return;
        stack.getStationNbt().putLong(ESSENCE_AMOUNT_NBT, value);
        if (value <= 0) stack.getStationNbt().putByte(ESSENCE_TYPE_NBT, (byte) 0);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.getStationNbt().contains(ESSENCE_TYPE_NBT) &&
                stack.getStationNbt().getByte(ESSENCE_TYPE_NBT) == 0 &&
                entity instanceof PlayerEntity player) {
            player.inventory.setStack(slot, new ItemStack(this, stack.count));
        }
    }

    @Override
    public boolean canGiveEssence(ItemStack stack, EssenceType type) {
        List<EssenceType> essenceTypes = getEssenceTypes(stack);
        return essenceTypes.isEmpty() || type == essenceTypes.get(0);
    }

    @Override
    public boolean canTakeEssence(ItemStack stack, EssenceType type) {
        List<EssenceType> essenceTypes = getEssenceTypes(stack);
        return !essenceTypes.isEmpty() && type == essenceTypes.get(0);
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        List<EssenceType> essenceTypes = getEssenceTypes(stack);
        if (essenceTypes.isEmpty()) return new String[]{
                I18n.getTranslation(getTranslationKey() + ".empty")
        };
        return new String[]{
                I18n.getTranslation(getTranslationKey() + "." + essenceTypes.get(0).name().toLowerCase()),
                I18n.getTranslation("vial.crimsonforest.essence_text", getEssence(stack, essenceTypes.get(0)))
        };
    }
}
