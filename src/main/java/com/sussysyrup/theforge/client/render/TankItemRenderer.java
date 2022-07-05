package com.sussysyrup.theforge.client.render;

import com.sussysyrup.theforge.Main;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

import java.util.List;
import java.util.Random;

//TODO BROKEN
public class TankItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        BlockState state = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
        boolean leftHanded = false;

        BakedModel model = MinecraftClient.getInstance().getBlockRenderManager().getModel(state);
        //model = MinecraftClient.getInstance().getItemRenderer().getModel(new ItemStack(Items.DIAMOND), MinecraftClient.getInstance().world, MinecraftClient.getInstance().player, 0);

        if(mode.equals(ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND) || mode.equals(ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND)) {
            leftHanded = true;
        }

        RenderLayer renderLayer = RenderLayers.getItemLayer(stack, false);

        VertexConsumer consumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, renderLayer, true, stack.hasGlint());;

        matrices.push();

        model.getTransformation().getTransformation(mode).apply(leftHanded, matrices);

        matrices.translate(0.5D, 0.5D, 0.5D);
        /**
        if(mode.equals(ModelTransformation.Mode.GROUND)) {
            matrices.translate(1, 0, 1);
        }
        if(mode.equals(ModelTransformation.Mode.GUI))
        {
            matrices.translate(-1.5, 0, -1.65);
        }
        if(mode.equals(ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND))
        {
            matrices.translate(-2.75, 0, -0.7);
        }
        if(mode.equals(ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND))
        {
            matrices.translate(-0.7, 0, -2.75);
        }
        if(mode.equals(ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND))
        {
            matrices.translate(0.65, 0.65, -0.65);
        }
        if(mode.equals(ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND))
        {
            matrices.translate(0, 0.65, -1.65);
            matrices.translate(-0.85, 0, -0.85);
        }
        **/
        renderBakedItemModel(model, light, overlay, matrices, consumer);

        matrices.pop();
    }

    //Copy of renderBakedItemModel
    private static void renderBakedItemModel(BakedModel model, int light, int overlay, MatrixStack matrices, VertexConsumer vertices) {
        Random random = new Random();
        long l = 42L;
        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            renderBakedItemQuads(matrices, vertices, model.getQuads(null, direction, random), light, overlay);
        }
        random.setSeed(42L);
        renderBakedItemQuads(matrices, vertices, model.getQuads(null, null, random), light, overlay);
    }

    //Copy of ItemRenderer.renderBakedItemQuads
    private static void renderBakedItemQuads(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, int light, int overlay) {
        MatrixStack.Entry entry = matrices.peek();
        for (BakedQuad bakedQuad : quads) {
            int i = -1;
            float f = (float)(i >> 16 & 0xFF) / 255.0f;
            float g = (float)(i >> 8 & 0xFF) / 255.0f;
            float h = (float)(i & 0xFF) / 255.0f;
            vertices.quad(entry, bakedQuad, f, g, h, light, overlay);
        }
    }
}
