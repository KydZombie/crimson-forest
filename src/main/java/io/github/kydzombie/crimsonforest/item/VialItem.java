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

public class VialItem extends TemplateItem {
    public static final int MILLIBUCKETS_PER_VIAL = 250;
    public final @Nullable EssenceType essenceType;

    public VialItem(Identifier identifier, @Nullable EssenceType essenceType) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(1);
        this.essenceType = essenceType;
    }

    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        int currentEssence = MILLIBUCKETS_PER_VIAL;
        int spentEssence = 0;
        if (AccessoryAccess.hasAnyAccessoriesOfType(user, "thermos")) {
            ItemStack[] thermoses = AccessoryAccess.getAccessories(user, "thermos");
            for (ItemStack thermosStack : thermoses) {
                if (currentEssence - spentEssence <= 0) break;
                if (thermosStack.getItem() instanceof TunedThermosItem thermos) {
                    if (thermos.essenceType != essenceType) continue;
                    int millibuckets = thermos.getMillibuckets(thermosStack);
                    int spentMillibuckets = Math.min(currentEssence - spentEssence, thermos.maxMillibuckets - millibuckets);
                    if (spentMillibuckets > 0) {
                        thermos.setMillibuckets(thermosStack, millibuckets + spentMillibuckets);
                        spentEssence += spentMillibuckets;
                    }
                }
            }
        }
        if (spentEssence > 0) {
            return new ItemStack(TheCrimsonForest.emptyVialItem);
        } else {
            return stack;
        }
    }
}
