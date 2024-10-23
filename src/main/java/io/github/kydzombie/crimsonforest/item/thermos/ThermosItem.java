package io.github.kydzombie.crimsonforest.item.thermos;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public abstract class ThermosItem extends TemplateItem {
    public static final int BUCKET_AMOUNT = 1000;
    public static final String MILLIBUCKETS_KEY = TheCrimsonForest.NAMESPACE.id("fluid_amount").toString();
    public final int maxMillibuckets;

    public ThermosItem(Identifier identifier, int maxMillibuckets) {
        super(identifier);
        this.maxMillibuckets = maxMillibuckets;
    }
}
