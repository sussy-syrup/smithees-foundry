package com.sussysyrup.theforge.blocks.entity;

import com.sussysyrup.theforge.api.blockentity.InventoryCraftingBlockEntity;
import com.sussysyrup.theforge.registry.BlocksRegistry;
import com.sussysyrup.theforge.screen.ForgeScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class ForgeBlockEntity extends InventoryCraftingBlockEntity {

    public ForgeBlockEntity(BlockPos blockPos, BlockState blockState, int size) {
        super(BlocksRegistry.FORGE_BLOCK_ENTITY, blockPos, blockState, size);
    }

    public ForgeBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlocksRegistry.FORGE_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("theforge.container.forge");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ForgeScreenHandler(syncId, playerInventory, this);
    }

}
