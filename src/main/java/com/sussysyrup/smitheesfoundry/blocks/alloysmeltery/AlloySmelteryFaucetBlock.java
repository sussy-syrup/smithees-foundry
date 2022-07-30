package com.sussysyrup.smitheesfoundry.blocks.alloysmeltery;

import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.entity.AlloySmelteryFaucetBlockEntity;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AlloySmelteryFaucetBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    private static VoxelShape composeShape(BlockState state) {
        if(state.get(FACING).equals(Direction.NORTH)) {
            VoxelShape voxelShape = VoxelShapes.cuboid(0.25, 0.4375, 0, 0.375, 0.625, 0.25);
            voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0.625, 0.4375, 0, 0.75, 0.625, 0.25));
            voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0.25, 0.3125, 0, 0.75, 0.4375, 0.25));

            return voxelShape;
        }
        if(state.get(FACING).equals(Direction.SOUTH)) {
            VoxelShape voxelShape = VoxelShapes.cuboid(0.25, 0.4375, 0.75, 0.375, 0.625, 1);
            voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0.625, 0.4375, 0.75, 0.75, 0.625, 1));
            voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0.25, 0.3125, 0.75, 0.75, 0.4375, 1));

            return voxelShape;
        }
        if(state.get(FACING).equals(Direction.EAST)) {
            VoxelShape voxelShape = VoxelShapes.cuboid(0.75, 0.4375, 0.25, 1, 0.625, 0.375);
            voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0.75, 0.4375, 0.625, 1, 0.625, 0.75));
            voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0.75, 0.3125, 0.25, 1, 0.4375, 0.75));

            return voxelShape;
        }
        else {
            VoxelShape voxelShape = VoxelShapes.cuboid(0, 0.4375, 0.25, 0.25, 0.625, 0.375);
            voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0, 0.4375, 0.625, 0.25, 0.625, 0.75));
            voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0, 0.3125, 0.25, 0.25, 0.4375, 0.75));

            return voxelShape;
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return composeShape(state);
    }

    public AlloySmelteryFaucetBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AlloySmelteryFaucetBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AlloySmelteryFaucetBlockEntity f) {
            if(f.casting)
            {
                f.stopCasting();
            }
            else
            {
                f.startCasting();
            }
        }

        return ActionResult.CONSUME;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction dir = ctx.getSide();
        if(dir.equals(Direction.UP) || dir.equals(Direction.DOWN))
        {
            dir = ctx.getPlayerFacing();
        }
        return (BlockState)this.getDefaultState().with(FACING, dir.getOpposite());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? checkType(type, BlocksRegistry.ALLOY_SMELTERY_FAUCET_BLOCK_ENTITY, AlloySmelteryFaucetBlockEntity::clientTicker) : checkType(type, BlocksRegistry.ALLOY_SMELTERY_FAUCET_BLOCK_ENTITY, AlloySmelteryFaucetBlockEntity::serverTicker);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
