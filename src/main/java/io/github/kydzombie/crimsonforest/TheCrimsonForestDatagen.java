package io.github.kydzombie.crimsonforest;

import emmathemartian.datagen.DataGenContext;
import emmathemartian.datagen.entrypoint.DataEntrypoint;
import emmathemartian.datagen.provider.ItemModelProvider;
import io.github.kydzombie.crimsonforest.item.thermos.FluidThermosItem;
import io.github.kydzombie.crimsonforest.magic.EssenceType;
import net.modificationstation.stationapi.api.util.Identifier;

public class TheCrimsonForestDatagen implements DataEntrypoint {
    @Override
    public void run() {
        DataGenContext context = new DataGenContext(TheCrimsonForest.NAMESPACE);
        context.run(new ItemModelProvider(context) {
            static final int TEXTURES_PER_FLUID = 5;
            static final int TEXTURES_PER_ESSENCE = 5;

            @Override
            public void run(DataGenContext context) {
                for (FluidThermosItem.FluidType fluidType : FluidThermosItem.FluidType.values()) {
                    for (int i = 0; i < TEXTURES_PER_FLUID; i++) {
                        Identifier fluidLayer = TheCrimsonForest.NAMESPACE.id("item/thermos/" + fluidType.name().toLowerCase() + "_" + i);
                        generated()
                                .texture("layer0", TheCrimsonForest.NAMESPACE.id("item/thermos/iron"))
                                .texture("layer1", fluidLayer)
                                .save("thermos/iron/" + fluidType.name().toLowerCase() + "_" + i, this, context);

                        generated()
                                .texture("layer0", TheCrimsonForest.NAMESPACE.id("item/thermos/arcane"))
                                .texture("layer1", fluidLayer)
                                .save("thermos/arcane/" + fluidType.name().toLowerCase() + "_" + i, this, context);
                    }
                }

                for (EssenceType essenceType : EssenceType.values()) {
                    for (int i = 0; i < TEXTURES_PER_ESSENCE; i++) {
                        Identifier essenceLayer = TheCrimsonForest.NAMESPACE.id("item/thermos/" + essenceType.name().toLowerCase() + "_" + i);
                        generated()
                                .texture("layer0", TheCrimsonForest.NAMESPACE.id("item/thermos/iron"))
                                .texture("layer1", essenceLayer)
                                .save("thermos/iron/" + essenceType.name().toLowerCase() + "_" + i, this, context);

                        generated()
                                .texture("layer0", TheCrimsonForest.NAMESPACE.id("item/thermos/arcane"))
                                .texture("layer1", essenceLayer)
                                .save("thermos/arcane/" + essenceType.name().toLowerCase() + "_" + i, this, context);
                    }
                }
            }
        });
    }
}
