package io.github.kydzombie.crimsonforest.item.thermos;

import com.matthewperiut.accessoryapi.api.Accessory;
import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.gui.screen.TunedThermosScreenHandler;
import io.github.kydzombie.crimsonforest.item.EssenceContainer;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import io.github.kydzombie.crimsonforest.packet.TunedThermosInventoryPacket;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

public abstract class TunedThermosItem extends TemplateItem implements EssenceContainer, Accessory, CustomTooltipProvider {
    public static final String ESSENCE_KEY = TheCrimsonForest.NAMESPACE.id("essence_amount").toString();
    private static final String[] ACCESSORY_TYPES = new String[]{"thermos"};
    public final int maxEssence;
    private final EssenceType essenceType;

    public TunedThermosItem(Identifier identifier, EssenceType essenceType, int maxEssence) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(1);
        this.essenceType = essenceType;
        this.maxEssence = maxEssence;
    }

    @Override
    public List<EssenceType> getEssenceTypes(ItemStack stack) {
        return List.of(essenceType);
    }

    @Override
    public int getMaxEssence(ItemStack stack, EssenceType type) {
        if (type != essenceType) return 0;
        return maxEssence;
    }

    @Override
    public int getEssence(ItemStack stack, EssenceType type) {
        if (type != essenceType) return 0;
        return stack.getStationNbt().getInt(ESSENCE_KEY);
    }

    @Override
    public void setEssence(ItemStack stack, EssenceType type, int value) {
        if (type != essenceType) return;
        stack.getStationNbt().putInt(ESSENCE_KEY, value);
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if (user.isSneaking()) {
            TunedThermosInventory thermosInventory = new TunedThermosInventory(stack);
            GuiHelper.openGUI(user, TheCrimsonForest.NAMESPACE.id("tuned_thermos"), thermosInventory, new TunedThermosScreenHandler(user.inventory, thermosInventory));
            if (!world.isRemote) {
                PacketHelper.sendTo(user, new TunedThermosInventoryPacket(stack.writeNbt(new NbtCompound())));
            }
            user.swingHand();
        }
        return super.use(stack, world, user);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack item) {
        return ACCESSORY_TYPES;
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        int essence = getEssence(stack, essenceType);
        if (essence == 0) {
            return new String[]{
                    originalTooltip,
                    I18n.getTranslation("thermos.crimsonforest.empty_text")
            };
        }
        return new String[]{
                originalTooltip,
                I18n.getTranslation(
                        "essence_thermos.crimsonforest.fluid_text",
                        essence
                )
        };
    }
}
