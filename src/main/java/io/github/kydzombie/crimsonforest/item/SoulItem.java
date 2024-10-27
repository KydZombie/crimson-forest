package io.github.kydzombie.crimsonforest.item;

import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;

public class SoulItem extends TemplateItem {
    public static final ArrayList<SoulItem> SOUL_TYPES = new ArrayList<>();

    public final int shardCount;

    public SoulItem(Identifier identifier, int shardCount) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxCount(8);
        this.shardCount = shardCount;
        SOUL_TYPES.add(this);
    }
}
