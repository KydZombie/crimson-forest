package io.github.kydzombie.crimsonforest.item;

import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class SoulShardItem extends TemplateItem {
    public SoulShardItem(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(16);
    }
}
