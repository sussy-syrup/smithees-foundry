package com.sussysyrup.theforge.blocks.alloysmeltery.entity;

import com.sussysyrup.theforge.registry.BlocksRegistry;
import com.sussysyrup.theforge.screen.AlloySmelteryFluidScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AlloySmelteryDrainBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ISlave {

    public boolean valid = false;
    public AlloySmelteryControllerBlockEntity master;
    public BlockPos masterPos = new BlockPos(0, 0, 0);

    public AlloySmelteryDrainBlockEntity(BlockPos pos, BlockState state) {
        super(BlocksRegistry.ALLOY_SMELTERY_DRAIN_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putLong("MASTER", masterPos.asLong());
        nbt.putBoolean("VALID", valid);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        masterPos = BlockPos.fromLong(nbt.getLong("MASTER"));
        valid = nbt.getBoolean("VALID");
    }
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(masterPos);
        buf.writeBlockPos(masterPos);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("theforge.container.alloysmeltery");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        if(valid) {
            return new AlloySmelteryFluidScreenHandler(syncId, inv, master);
        }
        return null;
    }

    @Override
    public void addMaster(BlockPos pos) {
        if(world.getBlockEntity(pos) instanceof AlloySmelteryControllerBlockEntity be)
        {
            valid = true;
            master = be;
            masterPos = master.getPos();
            markDirty();
        }
    }

    @Override
    public void removeMaster() {
        master = null;
        valid = false;
        masterPos = new BlockPos(0, 0, 0);
        markDirty();
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

    public static <E extends AlloySmelteryDrainBlockEntity> void serverTicker(World world, BlockPos pos, BlockState blockState, E e) {
        if(e.valid)
        {
            e.master = ((AlloySmelteryControllerBlockEntity) world.getBlockEntity(e.masterPos));
        }
        if(e.master == null)
        {
            e.removeMaster();
        }
        world.updateListeners(pos, blockState, blockState, Block.NOTIFY_LISTENERS);
    }

    public static <E extends AlloySmelteryDrainBlockEntity> void clientTicker(World world, BlockPos pos, BlockState blockState, E e) {
        if(e.valid)
        {
            e.master = ((AlloySmelteryControllerBlockEntity) world.getBlockEntity(e.masterPos));
        }
        else
        {
            e.master = null;
        }
    }
}
