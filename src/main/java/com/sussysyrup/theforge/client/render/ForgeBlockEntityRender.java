package com.sussysyrup.theforge.client.render;

import com.sussysyrup.theforge.blocks.ForgeBlock;
import com.sussysyrup.theforge.blocks.entity.ForgeBlockEntity;
import com.sussysyrup.theforge.registry.BlocksRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

public class ForgeBlockEntityRender implements BlockEntityRenderer<ForgeBlockEntity> {

    public ForgeBlockEntityRender(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(ForgeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        BlockPos pos = entity.getPos();

        if(entity.getWorld().getBlockState(pos).getBlock().equals(BlocksRegistry.FORGE_BLOCK)) {

            Direction direction = entity.getWorld().getBlockState(pos).get(ForgeBlock.FACING);

            matrices.push();

            if(direction.equals(Direction.SOUTH))
            {

            }
            if(direction.equals(Direction.EAST))
            {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
                matrices.translate(-1, 0, 0);
            }
            if(direction.equals(Direction.NORTH))
            {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
                matrices.translate(-1, 0, -1);
            }
            if(direction.equals(Direction.WEST))
            {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270));
                matrices.translate(0, 0, -1);
            }

            matrices.push();

            matrices.translate(0.25, 0.9, 0.82);

            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));

            matrices.scale(0.58F, 0.58F, 0.58F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(0), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

            matrices.pop();

            matrices.push();

            matrices.translate(0.54, 0.9, 0.60);

            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));

            matrices.scale(0.58F, 0.58F, 0.58F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(1), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

            matrices.pop();

            matrices.push();

            matrices.translate(0.75, 0.9, 0.32);

            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));

            matrices.scale(0.58F, 0.58F, 0.58F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(2), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

            matrices.pop();

            matrices.push();

            matrices.translate(0.25, 0.9, 0.4);

            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));

            matrices.scale(0.58F, 0.58F, 0.58F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(3), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

            matrices.pop();

            matrices.push();

            matrices.translate(0.8, 0.9, 0.85);

            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-90));

            matrices.scale(0.58F, 0.58F, 0.58F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(4), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

            matrices.pop();

            matrices.push();

            double offset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 6.0;
            // Move the item
            matrices.translate(0.5, 1.25 + offset, 0.5);

            // Rotate the item
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 4));

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(5), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

            matrices.pop();

            matrices.pop();
        }
    }
}
