package com.sussysyrup.smitheesfoundry.blocks;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.blocks.entity.PartBenchBlockEntity;
import com.sussysyrup.smitheesfoundry.impl.block.ImplPartBenchRegistry;
import com.sussysyrup.smitheesfoundry.util.Util;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PartBenchBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    private final String wood;

    private static VoxelShape composeShape() {
        VoxelShape voxelShape = VoxelShapes.cuboid(0.125, 0.0, 0.125, 0.3125, 0.8125, 0.3125);

        voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0.6875, 0.0, 0.125, 0.875, 0.8125, 0.3125));

        voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0.125, 0.0, 0.6875, 0.3125, 0.8125, 0.875));

        voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0.6875, 0.0, 0.6875, 0.875, 0.8125, 0.875));

        voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0, 0.8125, 0, 1, 0.9375, 1));
        voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0, 0.9375, 0, 0.3125, 1, 1));
        voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0.6875, 0.9375, 0, 1, 1, 1));
        voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0, 0.9375, 0.6875, 1, 1, 1));
        voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0, 0.9375, 0, 1, 1, 0.3125));
        return voxelShape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return composeShape();
    }

    public PartBenchBlock(Settings settings, String wood) {
        super(settings);
        this.wood = wood;
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {

        return new PartBenchBlockEntity(pos, state, 20);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PartBenchBlockEntity) {
            player.openHandledScreen((PartBenchBlockEntity)blockEntity);
        }

        return ActionResult.CONSUME;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? checkType(type, ImplPartBenchRegistry.PART_BENCH_BLOCK_ENTITY, PartBenchBlockEntity::clientTicker) : checkType(type, ImplPartBenchRegistry.PART_BENCH_BLOCK_ENTITY, PartBenchBlockEntity::serverTicker);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        if(world.getBlockEntity(pos) instanceof PartBenchBlockEntity be)
        {
            Inventory inventory = new SimpleInventory(2);
            inventory.setStack(0, be.getStack(0));
            inventory.setStack(1, be.getStack(1));

            ItemScatterer.spawn(world, pos, inventory);
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block." + Main.MODID + ".part_bench_block", new TranslatableText(Util.woodTranslationKey(wood)));
    }
}
