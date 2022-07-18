package com.sussysyrup.smitheesfoundry.client.render;

import com.sussysyrup.smitheesfoundry.api.client.render.ApiSpriteRendering;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

import java.util.List;
import java.util.Random;

public class TankItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    @Override
    public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        BlockState state = ((BlockItem) stack.getItem()).getBlock().getDefaultState();
        boolean leftHanded = false;

        BakedModel model = MinecraftClient.getInstance().getBlockRenderManager().getModel(state);

        if(mode.equals(ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND) || mode.equals(ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND)) {
            leftHanded = true;
        }

        RenderLayer renderLayer = RenderLayers.getItemLayer(stack, false);

        VertexConsumer consumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, renderLayer, true, stack.hasGlint());;
        matrices.push();

        Vec3f translate = model.getTransformation().getTransformation(mode).translation;
        Vec3f rotation = model.getTransformation().getTransformation(mode).rotation;
        Vec3f scale = model.getTransformation().getTransformation(mode).scale;

        matrices.translate(translate.getX(), translate.getY(), translate.getZ());

        matrices.translate(0.5, 0.5D, 0.5D);

        matrices.multiply(new Quaternion(rotation.getX(), rotation.getY(), rotation.getZ(), true));

        matrices.scale(scale.getX(), scale.getY(), scale.getZ());

        matrices.translate(-0.5D, -0.5D, -0.5D);

        renderBakedItemModel(model, light, overlay, matrices, consumer);

        renderContents(stack, matrices, vertexConsumers);

        matrices.pop();
    }

    private static void renderContents(ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers)
    {
        NbtCompound compound = stack.getNbt();

        if(compound == null)
        {
            return;
        }

        compound = compound.getCompound("BlockEntityTag");

        if(compound == null)
        {
            return;
        }

        FluidVariant variant = FluidVariant.fromNbt(compound.getCompound("fluidVariant"));

        if(variant.equals(FluidVariant.blank()))
        {
            return;
        }

        Fluid fluid = variant.getFluid();
        long amount = compound.getLong("amount");

        float height = 1 * (((float) amount) / ((float) (4 * FluidConstants.BUCKET)));

        Sprite sprite = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(MinecraftClient.getInstance().world, MinecraftClient.getInstance().player.getBlockPos(), fluid.getDefaultState())[0];

        matrices.push();

        matrices.translate(1, 0, 1F);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));

        matrices.translate(0, height - 0.01F, 0);

        int colour = MinecraftClient.getInstance().getBlockColors().getColor(fluid.getDefaultState().getBlockState(), MinecraftClient.getInstance().world, MinecraftClient.getInstance().player.getBlockPos(), 0);

        ApiSpriteRendering.renderColouredTileUp(matrices, sprite, 0.001F, 0.998F, 0.001F, 0.998F, colour, 1);

        matrices.translate(0, -height, 0.01F);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
        matrices.translate(-1, 0, -0.95);
        ApiSpriteRendering.renderColouredSpriteTile(matrices, sprite, 0.001F, 0.998F, 0.001F, height - 0.001F, colour, 1);

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
