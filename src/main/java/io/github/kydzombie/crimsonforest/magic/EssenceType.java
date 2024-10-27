package io.github.kydzombie.crimsonforest.magic;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;

public enum EssenceType {
    LIFE("essence.crimsonforest.life", 0xAC3232),
    NATURE("essence.crimsonforest.nature", 0x37946E);

    public final String translationKey;
    public final int color;

    EssenceType(String translationKey, int color) {
        this.translationKey = translationKey;
        this.color = color;
    }
}
