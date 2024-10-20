package io.github.kydzombie.crimsonforest.item;

import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.item.thermos.TunedThermosItem;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VialItem extends TemplateItem implements EssenceContainer {
    public static final int ESSENCE_PER_VIAL = 250;
    private final @Nullable EssenceType essenceType;

    public VialItem(Identifier identifier, @Nullable EssenceType essenceType) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(8);
        this.essenceType = essenceType;
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        int currentEssence = ESSENCE_PER_VIAL;
        int spentEssence = 0;
        if (AccessoryAccess.hasAnyAccessoriesOfType(user, "thermos")) {
            ItemStack[] thermoses = AccessoryAccess.getAccessories(user, "thermos");
            for (ItemStack thermosStack : thermoses) {
                if (currentEssence - spentEssence <= 0) break;
                if (thermosStack.getItem() instanceof TunedThermosItem thermos) {
                    int thermosEssence = thermos.getEssence(thermosStack, essenceType);
                    if (thermosEssence <= 0) continue;
                    int spentMillibuckets = Math.min(currentEssence - spentEssence, thermos.maxEssence - thermosEssence);
                    if (spentMillibuckets > 0) {
                        thermos.setEssence(thermosStack, essenceType, thermosEssence + spentMillibuckets);
                        spentEssence += spentMillibuckets;
                    }
                }
            }
        }
        if (spentEssence > 0) {
            user.inventory.addStack(new ItemStack(TheCrimsonForest.emptyVialItem));
            stack.count--;
        }
        return stack;
    }

    @Override
    public List<EssenceType> getEssenceTypes(ItemStack stack) {
        if (essenceType == null) return List.of();
        return List.of(essenceType);
    }

    @Override
    public int getMaxEssence(ItemStack stack, EssenceType type) {
        if (type != essenceType) return 0;
        return ESSENCE_PER_VIAL;
    }

    @Override
    public int getEssence(ItemStack stack, EssenceType type) {
        if (type != essenceType) return 0;
        return ESSENCE_PER_VIAL;
    }

    @Override
    public void setEssence(ItemStack stack, EssenceType type, int value) {

    }

    @Override
    public boolean canGiveEssence(ItemStack stack, EssenceType type) {
        return false;
    }

    @Override
    public boolean canTakeEssence(ItemStack stack, EssenceType type) {
        return false;
    }
}
