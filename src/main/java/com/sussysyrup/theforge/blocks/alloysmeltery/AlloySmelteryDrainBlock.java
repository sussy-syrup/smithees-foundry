package com.sussysyrup.theforge.blocks.alloysmeltery;

import com.sussysyrup.theforge.blocks.alloysmeltery.entity.AlloySmelteryDrainBlockEntity;
import com.sussysyrup.theforge.registry.BlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AlloySmelteryDrainBlock extends BlockWithEntity {

    public AlloySmelteryDrainBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AlloySmelteryDrainBlockEntity) {
            player.openHandledScreen((AlloySmelteryDrainBlockEntity)blockEntity);
        }

        return ActionResult.CONSUME;
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AlloySmelteryDrainBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? checkType(type, BlocksRegistry.ALLOY_SMELTERY_DRAIN_BLOCK_ENTITY, AlloySmelteryDrainBlockEntity::clientTicker) : checkType(type, BlocksRegistry.ALLOY_SMELTERY_DRAIN_BLOCK_ENTITY, AlloySmelteryDrainBlockEntity::serverTicker);
    }
}
