package com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.entity;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.casting.ApiBlockCastingRegistry;
import com.sussysyrup.smitheesfoundry.api.casting.BlockCastingResource;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class CastingBasinBlockEntity extends BlockEntity {

    public long capacity = 0;
    public boolean isCasting = false;

    public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public ItemStack castingStack = ItemStack.EMPTY;

    public int solidifyTick = 0;

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

    public CastingBasinBlockEntity(BlockPos pos, BlockState state) {
        super(BlocksRegistry.CASTING_BASIN_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("fluidVariant", fluidStorage.variant.toNbt());
        nbt.putLong("amount", fluidStorage.amount);
        nbt.putLong("capacity", fluidStorage.getCapacity());
        nbt.putBoolean("isCasting", isCasting);
        Inventories.writeNbt(nbt, inventory);
        NbtCompound compound = new NbtCompound();
        castingStack.writeNbt(compound);
        nbt.put("casting_stack", compound);
        nbt.putInt("solidify", solidifyTick);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluidVariant"));
        fluidStorage.amount = nbt.getLong("amount");
        capacity = nbt.getLong("capacity");
        isCasting = nbt.getBoolean("isCasting");
        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
        Inventories.readNbt(nbt, inventory);
        castingStack = ItemStack.fromNbt(nbt.getCompound("casting_stack"));
        solidifyTick = nbt.getInt("solidify");
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

    public static <E extends CastingBasinBlockEntity> void clientTicker(World world, BlockPos pos, BlockState blockState, E e) {
    }

    public static <E extends CastingBasinBlockEntity> void serverTicker(World world, BlockPos pos, BlockState blockState, E e) {

        if(e.isCasting && e.fluidStorage.amount == e.fluidStorage.getCapacity())
        {
            e.tickCasting();
        }

        world.updateListeners(pos, blockState, blockState, Block.NOTIFY_LISTENERS);
    }

    protected void startCasting(FluidVariant variant) {
        isCasting = true;

        BlockCastingResource resource;

        if (inventory.get(0).isEmpty()) {
            resource = ApiBlockCastingRegistry.getInstance().getCastingResource(Items.AIR);

            for (Fluid fluid : resource.fluidItemMap().keySet()) {
                if (variant.isOf(fluid)) {
                    capacity = FluidConstants.BLOCK;
                    castingStack = resource.fluidItemMap().get(fluid).getDefaultStack();
                }
            }
        } else {
            resource = ApiBlockCastingRegistry.getInstance().getCastingResource(inventory.get(0).getItem());

            if (resource != null) {
                for (Fluid fluid : resource.fluidItemMap().keySet()) {
                    if (variant.isOf(fluid)) {
                        capacity = FluidConstants.BLOCK;
                        castingStack = resource.fluidItemMap().get(fluid).getDefaultStack();
                    }
                }
            }
        }

        if (capacity == 0) {
            isCasting = false;
        }
    }

    protected void finishCasting()
    {
        world.playSound(null, this.getPos(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1f, 1f);
        fluidStorage.amount = 0;
        capacity = 0;
        fluidStorage.variant = FluidVariant.blank();
        isCasting = false;
        castingStack = ItemStack.EMPTY;
        solidifyTick = 0;
    }

    protected void tickCasting()
    {
        if(solidifyTick >= (((float) fluidStorage.amount) / ((float) FluidConstants.INGOT)) * 60F)
        {

            inventory.set(0, castingStack);

            finishCasting();
        }

        solidifyTick++;
    }
}
