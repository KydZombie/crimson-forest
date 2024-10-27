package io.github.kydzombie.crimsonforest.block.entity;

import io.github.kydzombie.crimsonforest.recipe.crude.CrudeSoulInfuserRecipeRegistry;

public class CrudeSoulInfuserBlockEntity extends CrudeMachineBlockEntity {
    public CrudeSoulInfuserBlockEntity() {
        super(2, 0.25f, CrudeSoulInfuserRecipeRegistry.INSTANCE);
    }

    @Override
    public String getName() {
        return "Crude Soul Infuser";
    }
}
