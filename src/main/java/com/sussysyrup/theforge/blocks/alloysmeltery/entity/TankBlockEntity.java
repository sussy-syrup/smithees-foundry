package com.sussysyrup.theforge.blocks.alloysmeltery.entity;

import com.sussysyrup.theforge.registry.BlocksRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TankBlockEntity extends BlockEntity {

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {
        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return 4 * FluidConstants.BUCKET;
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public TankBlockEntity(BlockPos pos, BlockState state) {
        super(BlocksRegistry.TANK_BLOCK_ENTITY, pos, state);
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

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.put("fluidVariant", fluidStorage.variant.toNbt());
        tag.putLong("amount", fluidStorage.amount);
    }
    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        fluidStorage.variant = FluidVariant.fromNbt(tag.getCompound("fluidVariant"));
        fluidStorage.amount = tag.getLong("amount");
    }

    public static <E extends BlockEntity> void clientTicker(World world, BlockPos pos, BlockState blockState, E e) {
    }

    public static <E extends TankBlockEntity> void serverTicker(World world, BlockPos pos, BlockState blockState, E e) {
        world.updateListeners(pos, blockState, blockState, Block.NOTIFY_LISTENERS);
    }
}
