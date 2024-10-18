package io.github.kydzombie.crimsonforest.magic;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;

public enum EssenceType {
    LIFE("essence." + TheCrimsonForest.NAMESPACE.id("life")),
    NATURE("essence." + TheCrimsonForest.NAMESPACE.id("nature"));

    public final String translationKey;

    EssenceType(String translationKey) {
        this.translationKey = translationKey;
    }
}
