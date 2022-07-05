package com.sussysyrup.theforge.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sussysyrup.theforge.blocks.alloysmeltery.AlloySmelteryControllerBlock;
import com.sussysyrup.theforge.blocks.alloysmeltery.entity.AlloySmelteryControllerBlockEntity;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;

import java.util.function.Function;

public class AlloySmelteryBlockEntityRenderer implements BlockEntityRenderer<AlloySmelteryControllerBlockEntity> {

    public AlloySmelteryBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(AlloySmelteryControllerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if(!(entity.getWorld().getBlockState(entity.getPos()).getBlock() instanceof AlloySmelteryControllerBlock))
        {
            return;
        }
        if(!entity.isValid())
        {
            return;
        }

        if(!entity.fluidStorage.views.isEmpty()) {
            StorageView<FluidVariant> view = entity.fluidStorage.views.get(entity.fluidStorage.views.size() - 1);

            int length = entity.oldLength;
            int width = entity.oldWidth;
            int widthCorrect = entity.widthCorrection;

            float yShift = entity.oldHeight * (((float) entity.fluidStorage.getCurrentCapacity()) / ((float) entity.fluidStorage.maxCapacity));

            Fluid fluid = view.getResource().getFluid();

            Sprite sprite = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(entity.getWorld(), entity.getPos(), fluid.getDefaultState())[0];

            Direction direction = entity.getWorld().getBlockState(entity.getPos()).get(AlloySmelteryControllerBlock.FACING);

            matrices.push();

            if (direction.equals(Direction.SOUTH)) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270));
                matrices.translate(0, 0, -1);
            }
            if (direction.equals(Direction.EAST)) {
            }
            if (direction.equals(Direction.NORTH)) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
                matrices.translate(-1, 0, 0);
            }
            if (direction.equals(Direction.WEST)) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
                matrices.translate(-1, 0, -1);
            }

            for(int z = 0; z < length; z++) {
                for (int x = 0; x < width; x++) {

                    matrices.push();

                    matrices.translate(x + 1, yShift, z - widthCorrect);

                    matrices.scale(0.0625F, 1, 0.0625F);

                    Matrix4f matrix4f;

                    matrix4f = matrices.peek().getPositionMatrix();
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.enableDepthTest();
                    RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);

                    BufferBuilder builder = Tessellator.getInstance().getBuffer();
                    builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                    builder.vertex(matrix4f, 0, 0, 16).texture(sprite.getMinU(), sprite.getMaxV()).next();
                    builder.vertex(matrix4f, 16, 0, 16).texture(sprite.getMaxU(), sprite.getMaxV()).next();
                    builder.vertex(matrix4f, 16, 0, 0).texture(sprite.getMaxU(), sprite.getMinV()).next();
                    builder.vertex(matrix4f, 0, 0, 0).texture(sprite.getMinU(), sprite.getMinV()).next();
                    builder.end();
                    BufferRenderer.draw(builder);

                    matrices.pop();
                }
            }

            matrices.pop();
        }
    }
}
