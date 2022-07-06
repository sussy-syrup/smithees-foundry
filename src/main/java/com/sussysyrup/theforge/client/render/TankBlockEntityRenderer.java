package com.sussysyrup.theforge.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sussysyrup.theforge.api.render.ForgeSpriteRendering;
import com.sussysyrup.theforge.blocks.alloysmeltery.TankBlock;
import com.sussysyrup.theforge.blocks.alloysmeltery.entity.TankBlockEntity;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

import java.awt.*;

public class TankBlockEntityRenderer implements BlockEntityRenderer<TankBlockEntity> {

    public TankBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(TankBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if(entity.getWorld().getBlockState(entity.getPos()).getBlock() instanceof TankBlock) {

            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().add(entity.getWorld().getBlockState(entity.getPos()).get(TankBlock.FACING).getVector()));

            if(entity.fluidStorage == null)
            {
                return;
            }
            if(entity.fluidStorage.isResourceBlank())
            {
                return;
            }
            if(entity.fluidStorage.amount == 0)
            {
                return;
            }

            Direction direction = entity.getWorld().getBlockState(entity.getPos()).get(TankBlock.FACING);

            matrices.push();

            if (direction.equals(Direction.SOUTH)) {

            }
            if (direction.equals(Direction.EAST)) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
                matrices.translate(-1, 0, 0);
            }
            if (direction.equals(Direction.NORTH)) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
                matrices.translate(-1, 0, -1);
            }
            if (direction.equals(Direction.WEST)) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270));
                matrices.translate(0, 0, -1);
            }

            matrices.push();

            Fluid fluid = entity.fluidStorage.variant.getFluid();

            float height = 1 * (((float) entity.fluidStorage.getAmount()) / ((float) entity.fluidStorage.getCapacity()));

            Sprite sprite = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(entity.getWorld(), entity.getPos(), fluid.getDefaultState())[0];

            matrices.translate(1, 0, 1F);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));

            matrices.translate(0, height - 0.01F, 0);

            int colour = MinecraftClient.getInstance().getBlockColors().getColor(fluid.getDefaultState().getBlockState(), entity.getWorld(), entity.getPos(), 0);

            ForgeSpriteRendering.renderColouredTileUp(matrices, sprite, 0.001F, 0.998F, 0.001F, 0.998F, colour, 1);

            matrices.translate(0, -height, 0.05F);
            ForgeSpriteRendering.renderColouredSpriteTile(matrices, sprite, 0.001F, 0.998F, 0.001F, height - 0.001F, colour, 1);

            matrices.pop();
            matrices.pop();


        }
    }
}
