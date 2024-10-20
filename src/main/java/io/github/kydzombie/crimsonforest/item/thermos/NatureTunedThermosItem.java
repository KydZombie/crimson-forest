package io.github.kydzombie.crimsonforest.item.thermos;

import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.modificationstation.stationapi.api.util.Identifier;

public class NatureTunedThermosItem extends TunedThermosItem {
    public NatureTunedThermosItem(Identifier identifier, int maxMillibuckets) {
        super(identifier, EssenceType.NATURE, maxMillibuckets);
    }
}
