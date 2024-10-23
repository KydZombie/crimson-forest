package io.github.kydzombie.crimsonforest.block;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class EssenceBondedBricksBlock extends TemplateBlock {
    public EssenceBondedBricksBlock(Identifier identifier, Material material) {
        super(identifier, material);
        setTranslationKey(identifier);
        setHardness(3.0F);
        setResistance(10.0F);
        setSoundGroup(STONE_SOUND_GROUP);
    }
}
