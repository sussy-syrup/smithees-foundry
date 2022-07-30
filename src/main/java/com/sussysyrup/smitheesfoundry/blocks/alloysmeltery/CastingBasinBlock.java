package com.sussysyrup.smitheesfoundry.blocks.alloysmeltery;

import com.sussysyrup.smitheesfoundry.api.item.CastItem;
import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.entity.CastingBasinBlockEntity;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CastingBasinBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    private static VoxelShape composeShape(BlockState state) {
        VoxelShape shape = VoxelShapes.cuboid(0.0625, 0.0625, 0.0625, 0.125, 0.9375, 0.9375);
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.875, 0.0625, 0.0625, 0.9375, 0.9375, 0.9375));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.0625, 0.0625, 0.9375, 0.9375, 0.125));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.0625, 0.875, 0.9375, 0.9375, 0.9375));

        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.0625, 0.0625, 0.9375, 0.125, 0.9375));

        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.125, 0.1875, 0.0625, 0.25));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.0625, 0.1875));

        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0, 0.125, 0.875, 0.0625, 0.25));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.0625, 0.1875));

        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.8125, 0, 0.75, 0.875, 0.0625, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.75, 0, 0.8125, 0.875, 0.0625, 0.875));

        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.75, 0.1875, 0.0625, 0.875));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.8125, 0.25, 0.0625, 0.875));

        return shape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {

        return composeShape(state);
    }

    public CastingBasinBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient)
        {
            return ActionResult.SUCCESS;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CastingBasinBlockEntity castingBE) {

            if(castingBE.isCasting)
            {
                return ActionResult.CONSUME;
            }

            DefaultedList<ItemStack> inv = castingBE.inventory;

            ItemStack handStack = player.getStackInHand(hand);

            if(inv.get(0).isEmpty())
            {
                if(handStack.getItem() instanceof BlockItem) {
                    inv.set(0, handStack.split(1));
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
                    return ActionResult.CONSUME;
                }
            }
            else
            {
                player.giveItemStack(inv.get(0).split(1));
                return ActionResult.CONSUME;
            }

        }

        return ActionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CastingBasinBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        if(world.getBlockEntity(pos) instanceof CastingBasinBlockEntity be)
        {
            Inventory inventory = new SimpleInventory(1);
            inventory.setStack(0, be.inventory.get(0));

            ItemScatterer.spawn(world, pos, inventory);
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, BlocksRegistry.CASTING_BASIN_ENTITY, CastingBasinBlockEntity::clientTicker) : checkType(type, BlocksRegistry.CASTING_BASIN_ENTITY, CastingBasinBlockEntity::serverTicker);
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
