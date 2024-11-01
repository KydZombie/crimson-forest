package io.github.kydzombie.crimsonforest.item;

import com.matthewperiut.accessoryapi.api.Accessory;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class WardingAmuletItem extends TemplateItem implements Accessory, CustomTooltipProvider {
    public static final String WARDED_MOB_NBT = "crimsonforest:warded_mob";

    public WardingAmuletItem(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(1);
    }

    public void setMob(ItemStack stack, @Nullable String mob) {
        stack.getStationNbt().putString(WARDED_MOB_NBT, mob);
    }

    public @Nullable String getMob(ItemStack stack) {
        if (!stack.getStationNbt().contains(WARDED_MOB_NBT)) return null;
        return stack.getStationNbt().getString(WARDED_MOB_NBT);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack item) {
        return new String[] {
                "pendant"
        };
    }

    @Override
    public String[] getTooltip(ItemStack stack, String originalTooltip) {
        String mob = getMob(stack);
        if (mob == null) return new String[] {
                originalTooltip,
                I18n.getTranslation("item.crimsonforest.warding_amulet.empty_text")
        };
        return new String[] {
                originalTooltip,
                I18n.getTranslation("item.crimsonforest.warding_amulet.protection_text", I18n.getTranslation("mob.crimsonforest." + mob + ".plural"))
        };
    }
}
