package com.sussysyrup.smitheesfoundry.client.render;

import com.sussysyrup.smitheesfoundry.api.client.render.ApiSpriteRendering;
import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.AlloySmelteryFaucetBlock;
import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.CastingTableBlock;
import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.entity.AlloySmelteryFaucetBlockEntity;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

public class AlloySmelteryFaucetRenderer implements BlockEntityRenderer<AlloySmelteryFaucetBlockEntity> {

    public AlloySmelteryFaucetRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(AlloySmelteryFaucetBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if(entity.getWorld().getBlockState(entity.getPos()).getBlock() instanceof AlloySmelteryFaucetBlock && !entity.currentVariant.equals(FluidVariant.blank()))
        {
            Direction direction = entity.getWorld().getBlockState(entity.getPos()).get(AlloySmelteryFaucetBlock.FACING);

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

            Fluid fluid = entity.currentVariant.getFluid();

            Sprite sprite = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(entity.getWorld(), entity.getPos(), fluid.getDefaultState())[1];

            int colour = MinecraftClient.getInstance().getBlockColors().getColor(fluid.getDefaultState().getBlockState(), entity.getWorld(), entity.getPos(), 0);

            int lightCor = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos());

            if(fluid.getRegistryEntry().isIn(FluidTags.LAVA))
            {
                lightCor = 15728832;
            }

            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentCull(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));

            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
            matrices.translate(-1F, 0.625F, -1);
            matrices.translate(0.375F, 0, 0);

            ApiSpriteRendering.renderConsumerSpriteUp(matrices, sprite, consumer, 0, 0.25F, 0, 0.25F, colour, overlay, lightCor, 1);

            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));
            matrices.translate(0, 0, -0.25F);


            if(entity.getWorld().getBlockState(entity.getPos().add(0, -1, 0)).getBlock() instanceof CastingTableBlock)
            {
                ApiSpriteRendering.renderConsumerSpriteTile(matrices, sprite, consumer, 0, 0.25F, 0, 0.75F, colour, overlay, lightCor,1);

                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
                matrices.translate(-0.25F, 0, -0.125F);
                ApiSpriteRendering.renderConsumerSpriteTile(matrices, sprite, consumer, 0, 0.25F, 0.125F, 0.625F, colour, overlay, lightCor, 1);

                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
                matrices.translate(-0.125F, 0, 0);
                ApiSpriteRendering.renderConsumerSpriteTile(matrices, sprite, consumer, 0, 0.125F, 0, 0.75F, colour, overlay, lightCor, 1);

                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
                matrices.translate(-0.125F, 0, -0.25F);
                ApiSpriteRendering.renderConsumerSpriteTile(matrices, sprite, consumer, 0, 0.125F, 0, 0.75F, colour, overlay, lightCor, 1);
            }
            else {
                ApiSpriteRendering.renderConsumerSpriteTile(matrices, sprite, consumer, 0, 0.25F, 0, 0.625F, colour, overlay, lightCor, 1);

                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
                matrices.translate(-0.25F, 0, -0.125F);
                ApiSpriteRendering.renderConsumerSpriteTile(matrices, sprite, consumer, 0, 0.25F, 0.125F, 0.5F, colour, overlay, lightCor, 1);

                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
                matrices.translate(-0.125F, 0, 0);
                ApiSpriteRendering.renderConsumerSpriteTile(matrices, sprite, consumer, 0, 0.125F, 0, 0.625F, colour, overlay, lightCor, 1);

                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
                matrices.translate(-0.125F, 0, -0.25F);
                ApiSpriteRendering.renderConsumerSpriteTile(matrices, sprite, consumer, 0, 0.125F, 0, 0.625F, colour, overlay, lightCor, 1);
            }
            matrices.pop();
            matrices.pop();
        }
    }
}
