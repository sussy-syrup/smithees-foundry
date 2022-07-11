package com.sussysyrup.theforge.blocks.alloysmeltery.entity;

import com.sussysyrup.theforge.registry.BlocksRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CastingTableBlockEntity extends BlockEntity {

    public long capacity = 0;
    public boolean isCasting = false;

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>() {

        @Override
        public long insert(FluidVariant insertedVariant, long maxAmount, TransactionContext transaction) {
            if(!isCasting)
            {
                startCasting(insertedVariant);
            }

            long superVal = super.insert(insertedVariant, maxAmount, transaction);

            return superVal;
        }

        @Override
        public long extract(FluidVariant extractedVariant, long maxAmount, TransactionContext transaction) {
            return 0;
        }

        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return capacity;
        }

        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public CastingTableBlockEntity(BlockPos pos, BlockState state) {
        super(BlocksRegistry.CASTING_TABLE_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("fluidVariant", fluidStorage.variant.toNbt());
        nbt.putLong("amount", fluidStorage.amount);
        nbt.putLong("capacity", fluidStorage.getCapacity());
        nbt.putBoolean("isCasting", isCasting);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluidVariant"));
        fluidStorage.amount = nbt.getLong("amount");
        capacity = nbt.getLong("capacity");
        isCasting = nbt.getBoolean("isCasting");
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

    public static <E extends BlockEntity> void clientTicker(World world, BlockPos pos, BlockState blockState, E e) {
    }

    public static <E extends CastingTableBlockEntity> void serverTicker(World world, BlockPos pos, BlockState blockState, E e) {

        if(e.isCasting && e.fluidStorage.amount == e.fluidStorage.getCapacity())
        {
            e.tickCasting();
        }

        world.updateListeners(pos, blockState, blockState, Block.NOTIFY_LISTENERS);
    }

    protected void startCasting(FluidVariant variant)
    {
        isCasting = true;
    }

    protected void finishCasting()
    {
        fluidStorage.amount = 0;
        capacity = 0;
        fluidStorage.variant = FluidVariant.blank();
        isCasting = false;
    }

    protected void tickCasting()
    {

    }
}
