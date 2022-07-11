package com.sussysyrup.theforge.blocks.alloysmeltery.entity;

import com.sussysyrup.theforge.blocks.alloysmeltery.AlloySmelteryFaucetBlock;
import com.sussysyrup.theforge.registry.BlocksRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AlloySmelteryFaucetBlockEntity extends BlockEntity {

    public boolean casting = false;
    public FluidVariant currentVariant = FluidVariant.blank();

    public AlloySmelteryFaucetBlockEntity(BlockPos pos, BlockState state) {
        super(BlocksRegistry.ALLOY_SMELTERY_FAUCET_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("CASTING", casting);
        nbt.put("CURRENTV", currentVariant.toNbt());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        casting = nbt.getBoolean("CASTING");
        currentVariant = FluidVariant.fromNbt(nbt.getCompound("CURRENTV"));
    }

    public void stopCasting() {
        casting = false;
        currentVariant = FluidVariant.blank();
    }

    public void startCasting() {
        tickCasting();
        Storage<FluidVariant> storage = FluidStorage.SIDED.find(world, pos.add(0, -1, 0), Direction.UP);

        if(storage == null)
        {
            return;
        }
        casting = true;
        currentVariant = FluidVariant.blank();
    }

    protected void tickCasting() {
        Storage<FluidVariant> storage;
        Storage<FluidVariant> target;

        long resourceAmount = 0;
        long extractedAmount = 0;
        FluidVariant variant = FluidVariant.blank();

        try (Transaction transaction = Transaction.openOuter()) {

            storage = FluidStorage.SIDED.find(world, getPos().add(world.getBlockState(pos).get(AlloySmelteryFaucetBlock.FACING).getVector()), world.getBlockState(pos).get(AlloySmelteryFaucetBlock.FACING).getOpposite());

            if(storage == null)
            {
                transaction.abort();
                stopCasting();
                return;
            }

            for (StorageView<FluidVariant> view : storage.iterable(transaction)) {
                if (view.isResourceBlank()) continue;
                variant = view.getResource();

                resourceAmount = view.extract(variant, view.getAmount(), transaction);
                transaction.abort();
                break;
            }
        }

        if(resourceAmount >= 250)
        {
            try (Transaction transaction = Transaction.openOuter())
            {
                target = FluidStorage.SIDED.find(world, getPos().add(0, -1, 0), Direction.UP);

                if(target == null)
                {
                    transaction.abort();
                    stopCasting();
                    return;
                }

                extractedAmount = target.insert(variant, 250L, transaction);

                if(extractedAmount == 0)
                {
                    transaction.abort();
                    stopCasting();
                    return;
                }

                storage.extract(variant, extractedAmount, transaction);
                transaction.commit();

                currentVariant = variant;
            }
        }
        else
        {
            stopCasting();
        }
    }

    @javax.annotation.Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }


    public static <E extends AlloySmelteryFaucetBlockEntity> void serverTicker(World world, BlockPos pos, BlockState blockState, E e) {
        if(e.casting)
        {
            e.tickCasting();
        }
        world.updateListeners(pos, blockState, blockState, Block.NOTIFY_LISTENERS);
    }

    public static <E extends AlloySmelteryFaucetBlockEntity> void clientTicker(World world, BlockPos pos, BlockState blockState, E e) {
    }
}
