package com.sussysyrup.smitheesfoundry.client.render;

import com.sussysyrup.smitheesfoundry.api.render.ApiSpriteRendering;
import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.AlloySmelteryFaucetBlock;
import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.CastingTableBlock;
import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.entity.CastingTableBlockEntity;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

public class CastingTableBlockEntityRender implements BlockEntityRenderer<CastingTableBlockEntity> {

    public CastingTableBlockEntityRender(BlockEntityRendererFactory.Context context)
    {}

    float oldAmount = 0;

    @Override
    public void render(CastingTableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if(entity.getWorld().getBlockState(entity.getPos()).getBlock() instanceof CastingTableBlock) {
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

            if(entity.isCasting)
            {
                float alpha = 1;

                if(entity.solidifyTick > 1)
                {
                    alpha = 1 - (((float) entity.solidifyTick) / ((((float) entity.fluidStorage.amount) / ((float) FluidConstants.INGOT)) * 60F));
                }

                matrices.push();

                float offset = (0.0625F * (((float) entity.fluidStorage.getAmount()) / ((float) entity.fluidStorage.getCapacity())));

                matrices.translate(0, 0.87495 + offset, 0);

                Fluid fluid = entity.fluidStorage.variant.getFluid();

                if(fluid == null)
                {
                    fluid = Fluids.LAVA;
                }

                Sprite sprite = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(entity.getWorld(), entity.getPos(), fluid.getDefaultState())[0];
                int colour = MinecraftClient.getInstance().getBlockColors().getColor(fluid.getDefaultState().getBlockState(), entity.getWorld(), entity.getPos(), 0);

                ApiSpriteRendering.renderConsumerSpriteUp(matrices, sprite, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentCull(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)), 0, 1F, 0, 1F, colour, overlay, light, alpha);

                matrices.pop();

                if(entity.solidifyTick > 1)
                {
                    matrices.push();

                    matrices.translate(0.5, 0.90, 0.71875);

                    matrices.scale(1.75F, 2, 1.75F);

                    matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90));

                    MinecraftClient.getInstance().getItemRenderer().renderItem(entity.castingStack, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
                    matrices.pop();
                }
            }

            matrices.push();

            matrices.translate(0.5, 0.90625, 0.71875);

            matrices.scale(1.75F, 2, 1.75F);

            matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(90));

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.inventory.get(0), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.inventory.get(1), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

            matrices.pop();

            matrices.pop();
        }
    }
}
