package io.github.kydzombie.crimsonforest.item.thermos;

import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.modificationstation.stationapi.api.util.Identifier;

public class LifeTunedThermosItem extends TunedThermosItem {
    public LifeTunedThermosItem(Identifier identifier, int maxMillibuckets) {
        super(identifier, EssenceType.LIFE, maxMillibuckets);
    }
}
