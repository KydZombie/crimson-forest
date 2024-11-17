package io.github.kydzombie.crimsonforest.magic;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import net.modificationstation.stationapi.api.util.Identifier;

public enum EssenceType {
    LIFE(TheCrimsonForest.NAMESPACE.id("life"), 0xAC3232),
    NATURE(TheCrimsonForest.NAMESPACE.id("nature"), 0x37946E),
    PURE(TheCrimsonForest.NAMESPACE.id("pure"), 0x5FCDE4),
    UMBRAL(TheCrimsonForest.NAMESPACE.id("umbral"), 0x5B6EE1);

    public final Identifier identifier;
    public final int color;
    public int vialPartialTexture;
    public int vialFullTexture;

    EssenceType(Identifier identifier, int color) {
        this.identifier = identifier;
        this.color = color;
    }
}
