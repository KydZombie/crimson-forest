package io.github.kydzombie.crimsonforest.block;

import io.github.kydzombie.crimsonforest.TheCrimsonForest;
import io.github.kydzombie.crimsonforest.block.entity.CrudePressBlockEntity;
import io.github.kydzombie.crimsonforest.block.entity.CrudeSoulInfuserBlockEntity;
import io.github.kydzombie.crimsonforest.gui.screen.CrudePressScreenHandler;
import io.github.kydzombie.crimsonforest.gui.screen.CrudeSoulInfuserScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.Properties;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

public class CrudeSoulInfuserBlock extends TemplateBlockWithEntity {
    public CrudeSoulInfuserBlock(Identifier identifier, Material material) {
        super(identifier, material);
        setTranslationKey(identifier);
        setHardness(5.0f);
        setDefaultState(getStateManager().getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction direction = context.getHorizontalPlayerFacing().getOpposite();
        return getDefaultState().with(Properties.HORIZONTAL_FACING, direction);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        CrudeSoulInfuserBlockEntity blockEntity = (CrudeSoulInfuserBlockEntity) world.getBlockEntity(x, y, z);
        GuiHelper.openGUI(player, TheCrimsonForest.NAMESPACE.id("crude_soul_infuser"), blockEntity, new CrudeSoulInfuserScreenHandler(player.inventory, blockEntity));
        return true;
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new CrudeSoulInfuserBlockEntity();
    }
}
