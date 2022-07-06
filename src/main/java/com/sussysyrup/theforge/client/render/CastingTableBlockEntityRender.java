package com.sussysyrup.theforge.client.render;

import com.sussysyrup.theforge.blocks.alloysmeltery.entity.CastingTableBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class CastingTableBlockEntityRender implements BlockEntityRenderer<CastingTableBlockEntity> {

    public CastingTableBlockEntityRender(BlockEntityRendererFactory.Context context)
    {}

    @Override
    public void render(CastingTableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

    }
}
