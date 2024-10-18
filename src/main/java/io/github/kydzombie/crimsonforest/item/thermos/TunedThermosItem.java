package io.github.kydzombie.crimsonforest.item.thermos;

import com.matthewperiut.accessoryapi.api.Accessory;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class TunedThermosItem extends ThermosItem implements Accessory, CustomTooltipProvider {
    private static final String[] ACCESSORY_TYPES = new String[] { "thermos" };
    public final EssenceType essenceType;

    public TunedThermosItem(Identifier identifier, EssenceType essenceType, int maxMillibuckets) {
        super(identifier, maxMillibuckets);
        setTranslationKey(identifier);
        setMaxCount(1);
        this.essenceType = essenceType;
    }

    @Override
    public String[] getAccessoryTypes(ItemStack item) {
        return ACCESSORY_TYPES;
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        int millibuckets = getMillibuckets(stack);
        if (millibuckets == 0) {
            return new String[] {
                    originalTooltip,
                    I18n.getTranslation("thermos.crimsonforest.empty_text")
            };
        }
        return new String[] {
                originalTooltip,
                I18n.getTranslation(
                        "essence_thermos.crimsonforest.fluid_text",
                        millibuckets
                )
        };
    }
}
