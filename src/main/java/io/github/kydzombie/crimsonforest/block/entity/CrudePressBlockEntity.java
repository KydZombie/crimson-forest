package io.github.kydzombie.crimsonforest.block.entity;

import io.github.kydzombie.crimsonforest.recipe.CrudePressRecipeRegistry;

public class CrudePressBlockEntity extends CrudeMachineBlockEntity {
    public CrudePressBlockEntity() {
        super(1, 0.25f, CrudePressRecipeRegistry.INSTANCE);
    }

    @Override
    public String getName() {
        return "Crude Press";
    }
}
