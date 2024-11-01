package io.github.kydzombie.crimsonforest;

import emmathemartian.datagen.DataGenContext;
import emmathemartian.datagen.entrypoint.DataEntrypoint;
import emmathemartian.datagen.provider.BlockStateProvider;
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

                Identifier goldLayer = TheCrimsonForest.NAMESPACE.id("item/thermos/gold");
                for (int i = 0; i < TEXTURES_PER_FLUID; i++) {
                    Identifier drinkLayer = TheCrimsonForest.NAMESPACE.id("item/thermos/drink_" + i);
                    generated()
                            .texture("layer0", goldLayer)
                            .texture("layer1", drinkLayer)
                            .save("thermos/gold/drink_" + i, this, context);

                    generated()
                            .texture("layer0", goldLayer)
                            .texture("layer1", drinkLayer)
                            .texture("layer2", TheCrimsonForest.NAMESPACE.id("item/thermos/drink_hot"))
                            .save("thermos/gold/drink_" + i + "_hot", this, context);
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

                for (String materialType : new String[]{"wooden", "iron"}) {
                    handheld()
                            .texture("layer0", TheCrimsonForest.NAMESPACE.id("item/render/" + materialType))
                            .texture("layer1", TheCrimsonForest.NAMESPACE.id("item/render/essence/no_vial"))
                            .save("render/essence/" + materialType + "_no_vial", this, context);

                    handheld()
                            .texture("layer0", TheCrimsonForest.NAMESPACE.id("item/render/" + materialType))
                            .texture("layer1", TheCrimsonForest.NAMESPACE.id("item/render/essence/no_vial"))
                            .texture("layer2", TheCrimsonForest.NAMESPACE.id("item/render/essence/blood_drip"))
                            .save("render/essence/" + materialType + "_no_vial_blood_drip", this, context);

                    for (int i = 0; i < 3; i++) {
                        handheld()
                                .texture("layer0", TheCrimsonForest.NAMESPACE.id("item/render/" + materialType))
                                .texture("layer1", TheCrimsonForest.NAMESPACE.id("item/render/essence/vial_" + i))
                                .save("render/essence/" + materialType + "_vial_" + i, this, context);
                        handheld()
                                .texture("layer0", TheCrimsonForest.NAMESPACE.id("item/render/" + materialType))
                                .texture("layer1", TheCrimsonForest.NAMESPACE.id("item/render/essence/vial_" + i))
                                .texture("layer2", TheCrimsonForest.NAMESPACE.id("item/render/essence/blood_drip"))
                                .save("render/essence/" + materialType + "_vial_" + i + "_blood_drip", this, context);
                    }
                }

                for (String essenceType : new String[]{"life", "nature"}) {
                    for (String fillAmount : new String[]{"partial", "full"}) {
                        handheld().texture("layer0", TheCrimsonForest.NAMESPACE.id("item/vial/" + essenceType + "_" + fillAmount))
                                .save("vial/" + essenceType + "_" + fillAmount, this, context);
                    }
                }
            }
        });

        context.run(new BlockStateProvider(context) {
            @Override
            public void run(DataGenContext context) {

            }
        });
    }
}
