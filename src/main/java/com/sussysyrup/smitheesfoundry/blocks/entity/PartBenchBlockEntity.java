package com.sussysyrup.smitheesfoundry.blocks.entity;

import com.sussysyrup.smitheesfoundry.api.block.ApiPartBenchRegistry;
import com.sussysyrup.smitheesfoundry.api.blockentity.InventoryCraftingBlockEntity;
import com.sussysyrup.smitheesfoundry.screen.PartBenchScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class PartBenchBlockEntity extends InventoryCraftingBlockEntity {

    public PartBenchBlockEntity(BlockPos blockPos, BlockState blockState, int size)
    {
        super(ApiPartBenchRegistry.PART_BENCH_BLOCK_ENTITY, blockPos, blockState, size);
    }

    public PartBenchBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ApiPartBenchRegistry.PART_BENCH_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("smitheesfoundry.container.partbench");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new PartBenchScreenHandler(syncId, playerInventory, this);
    }

}

