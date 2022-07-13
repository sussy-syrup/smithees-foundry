package com.sussysyrup.smitheesfoundry.api.fluid;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.*;

import java.util.Optional;
import java.util.Random;

public abstract class AbstractMoltenMetalFluid extends FlowableFluid {

    private final String name;

    public AbstractMoltenMetalFluid(String name)
    {
        this.name = name;
    }

    private FlowableFluid flowing;
    private FlowableFluid still;
    private Item bucketItem;
    private Block fluidBlock;

    @Override
    public Fluid getFlowing() {
        return flowing;
    }

    @Override
    public Fluid getStill() {
        return still;
    }

    @Override
    protected boolean isInfinite() {
        return false;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        world.syncWorldEvent(WorldEvents.LAVA_EXTINGUISHED, pos, 0);
    }

    @Override
    protected int getFlowSpeed(WorldView world) {
        return world.getDimension().isUltrawarm() ? 4 : 2;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return world.getDimension().isUltrawarm() ? 1 : 2;
    }

    @Override
    public Item getBucketItem() {
        return bucketItem;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return state.getHeight(world, pos) >= 0.44444445f && fluid.isIn(FluidTags.WATER);
    }

    @Override
    public int getTickRate(WorldView world) {
        return world.getDimension().isUltrawarm() ? 10 : 30;
    }

    @Override
    protected float getBlastResistance() {
        return 100;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return fluidBlock.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

    @Override
    public void onRandomTick(World world, BlockPos pos, FluidState state, Random random) {
        if (!world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            return;
        }
        int i = random.nextInt(3);
        if (i > 0) {
            BlockPos blockPos = pos;
            for (int j = 0; j < i; ++j) {
                if (!world.canSetBlock(blockPos = blockPos.add(random.nextInt(3) - 1, 1, random.nextInt(3) - 1))) {
                    return;
                }
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.isAir()) {
                    if (!this.canLightFire(world, blockPos)) continue;
                    world.setBlockState(blockPos, AbstractFireBlock.getState(world, blockPos));
                    return;
                }
                if (!blockState.getMaterial().blocksMovement()) continue;
                return;
            }
        } else {
            for (int k = 0; k < 3; ++k) {
                BlockPos blockPos2 = pos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                if (!world.canSetBlock(blockPos2)) {
                    return;
                }
                if (!world.isAir(blockPos2.up()) || !this.hasBurnableBlock(world, blockPos2)) continue;
                world.setBlockState(blockPos2.up(), AbstractFireBlock.getState(world, blockPos2));
            }
        }
    }

    @Override
    protected void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        super.randomDisplayTick(world, pos, state, random);
        if (random.nextInt(200) == 0) {
            world.playSound(pos.getX(), (double)pos.getY(), (double)pos.getZ(), SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false);
        }
    }

    private boolean canLightFire(WorldView world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (!this.hasBurnableBlock(world, pos.offset(direction))) continue;
            return true;
        }
        return false;
    }

    private boolean hasBurnableBlock(WorldView world, BlockPos pos) {
        if (pos.getY() >= world.getBottomY() && pos.getY() < world.getTopY() && !world.isChunkLoaded(pos)) {
            return false;
        }
        return world.getBlockState(pos).getMaterial().isBurnable();
    }

    @Override
    protected boolean hasRandomTicks() {
        return true;
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL_LAVA);
    }

    public void setFlowing(FlowableFluid flowing) {
        this.flowing = flowing;
    }

    public void setStill(FlowableFluid still) {
        this.still = still;
    }

    public void setBucketItem(Item bucketItem) {
        this.bucketItem = bucketItem;
    }

    public void setFluidBlock(Block fluidBlock) {
        this.fluidBlock = fluidBlock;
    }


    public static class Flowing extends AbstractMoltenMetalFluid
    {

        public Flowing(String name) {
            super(name);
        }

        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends AbstractMoltenMetalFluid {

        public Still(String name) {
            super(name);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
