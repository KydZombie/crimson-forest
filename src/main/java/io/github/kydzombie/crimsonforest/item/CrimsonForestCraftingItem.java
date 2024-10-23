package io.github.kydzombie.crimsonforest.item;

import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class CrimsonForestCraftingItem extends TemplateItem {
    public CrimsonForestCraftingItem(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier);
    }

    @Override
    public CrimsonForestCraftingItem setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }
}
