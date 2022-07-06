package com.sussysyrup.theforge.blocks.alloysmeltery;

import com.sussysyrup.theforge.blocks.alloysmeltery.entity.CastingTableBlockEntity;
import com.sussysyrup.theforge.registry.BlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CastingTableBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    private static VoxelShape composeShape(BlockState state) {
        VoxelShape shape = VoxelShapes.cuboid(0, 0, 0, 0.1875, 0.8125, 0.1875);

        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0, 0.8125, 1, 0.8125, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0, 0, 1, 0.8125, 0.1875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0.8125, 0.1875, 0.8125, 1));

        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.8125, 0, 1, 1, 0.0625));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.8125, 0.9375, 1, 1, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.8125, 0, 0.0625, 1, 1));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.9375, 0.8125, 0, 1, 1, 1));

        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0.8125, 0, 1, 0.875, 1));

        return shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return composeShape(state);
    }

    public CastingTableBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CastingTableBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? checkType(type, BlocksRegistry.CASTING_TABLE_ENTITY, CastingTableBlockEntity::clientTicker) : checkType(type, BlocksRegistry.CASTING_TABLE_ENTITY, CastingTableBlockEntity::serverTicker);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
