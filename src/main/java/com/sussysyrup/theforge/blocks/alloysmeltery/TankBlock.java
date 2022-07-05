package com.sussysyrup.theforge.blocks.alloysmeltery;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.blocks.alloysmeltery.entity.TankBlockEntity;
import com.sussysyrup.theforge.registry.BlocksRegistry;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TankBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public TankBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));


    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof TankBlockEntity) {
            if(player.getStackInHand(hand).getItem() instanceof BucketItem bucketItem)
            {

                ContainerItemContext handContext = ContainerItemContext.ofPlayerHand(player, Hand.MAIN_HAND);

                Storage<FluidVariant> bucketStorage = handContext.find(FluidStorage.ITEM);

                SingleVariantStorage<FluidVariant> tankStorage = ((TankBlockEntity) blockEntity).fluidStorage;
                FluidVariant tankVariant;

                try(Transaction transaction = Transaction.openOuter())
                {
                    for(StorageView<FluidVariant> view : bucketStorage.iterable(transaction))
                    {
                        if(tankStorage.isResourceBlank())
                        {
                            if(view.isResourceBlank())
                            {
                                continue;
                            }
                            if(view.getAmount() >= FluidConstants.BUCKET)
                            {
                                tankStorage.insert(view.getResource(), FluidConstants.BUCKET, transaction);

                                view.extract(view.getResource(), FluidConstants.BUCKET, transaction);

                                break;
                            }
                            else
                            {
                                continue;
                            }
                        }
                        else
                        {
                            tankVariant = tankStorage.variant;

                            if(view.isResourceBlank())
                            {
                                if(tankStorage.amount >= FluidConstants.BUCKET)
                                {
                                    bucketStorage.insert(tankVariant, FluidConstants.BUCKET, transaction);
                                    tankStorage.extract(tankVariant, FluidConstants.BUCKET, transaction);
                                }
                            }
                            else
                            {
                                if(tankVariant.equals(view.getResource()))
                                if(tankStorage.getCapacity()-tankStorage.amount >= FluidConstants.BUCKET)
                                {
                                    view.extract(tankVariant, FluidConstants.BUCKET, transaction);
                                    tankStorage.insert(tankVariant, FluidConstants.BUCKET, transaction);
                                }
                            }
                        }
                    }

                    transaction.commit();
                }
            }
        }

        return ActionResult.SUCCESS;
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
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        BlockEntity be = world.getBlockEntity(pos);
        if(be instanceof TankBlockEntity)
        {
            ItemStack stack = new ItemStack(this.asItem());
            be.setStackNbt(stack);
            return stack;
        }

        return super.getPickStack(world, pos, state);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        BlockEntity blockEntity = builder.getNullable(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof TankBlockEntity be) {
            builder = builder.putDrop(new Identifier(Main.MODID, "entity"), (context, consumer) -> {
                ItemStack stack = new ItemStack(this.asItem());
                be.setStackNbt(stack);
                consumer.accept(stack);
            });

        }
        return super.getDroppedStacks(state, builder);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TankBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? checkType(type, BlocksRegistry.TANK_BLOCK_ENTITY, TankBlockEntity::clientTicker) : checkType(type, BlocksRegistry.TANK_BLOCK_ENTITY, TankBlockEntity::serverTicker);
    }
}
