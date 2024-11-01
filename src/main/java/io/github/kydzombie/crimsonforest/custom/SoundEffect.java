package io.github.kydzombie.crimsonforest.custom;

public enum SoundEffect {
    FILL_VIAL("crimsonforest:item.fill_vial", 1.0f, 0.9f, 1.1f),
    ADD_VIAL("crimsonforest:item.replace_vial", 1.0F, 0.95f, 1.05f),
    REMOVE_VIAL("crimsonforest:item.replace_vial", 1.0F, 0.8f, 0.9f);

    public final String path;
    public final float volume;
    public final float minPitch;
    public final float maxPitch;
    SoundEffect(String path, float volume, float minPitch, float maxPitch) {
        this.path = path;
        this.volume = volume;
        this.minPitch = minPitch;
        this.maxPitch = maxPitch;
    }
};