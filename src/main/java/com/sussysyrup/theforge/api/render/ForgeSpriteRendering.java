package com.sussysyrup.theforge.api.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sussysyrup.theforge.Main;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class ForgeSpriteRendering {

    public static void renderTileUp(MatrixStack matrices, Sprite sprite, float x, float xSize, float y, float ySize)
    {
        matrices.push();

        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));

        renderSpriteTile(matrices, sprite, x, xSize, y, ySize);

        matrices.pop();
    }

    public static void renderColouredTileUp(MatrixStack matrices, Sprite sprite, float x, float xSize, float y, float ySize, int colour, float a)
    {
        matrices.push();

        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));

        renderColouredSpriteTile(matrices, sprite, x, xSize, y, ySize, colour, a);

        matrices.pop();
    }

    public static void renderSpriteTile(MatrixStack matrices, Sprite sprite, float x, float xSize, float y, float ySize)
    {
        renderColouredSpriteTile(matrices, sprite, x, xSize, y, ySize, -1, 1);
    }

    public static void renderColouredSpriteTile(MatrixStack matrices, Sprite sprite, float x, float xSize, float y, float ySize, int colour, float a)
    {
        BufferBuilder builder = Tessellator.getInstance().getBuffer();

        float r = (float)(colour >> 16 & 0xFF) / 255.0f;
        float g = (float)(colour >> 8 & 0xFF) / 255.0f;
        float b = (float)(colour & 0xFF) / 255.0f;
        int light = 0;

        if(x + xSize > 1 || y + ySize > 1)
        {
            Main.LOGGER.error("cannot render fluid tile which pulls from over 1");
            return;
        }

        int imageWidth = (int) ((1 / (sprite.getMaxU() - sprite.getMinU())) * 16);
        int imageHeight = (int) ((1 / (sprite.getMaxV() - sprite.getMinV())) * 16);

        float uScalingMin = (((x) * 16) / ((float) imageWidth));
        float vScalingMin = (((y) * 16) / ((float) imageHeight));
        float uScalingMax = (((x + xSize) * 16) / ((float) imageWidth));
        float vScalingMax = (((y + ySize) * 16) / ((float) imageHeight));

        RenderSystem.setShader(GameRenderer::getPositionTexLightmapColorShader);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);


        Matrix4f matrix4f = matrices.peek().getPositionMatrix();

        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_LIGHT_COLOR);

        builder.vertex(matrix4f, x, y + ySize, 0).texture(sprite.getMinU() + uScalingMin, sprite.getMinV() + vScalingMax).light(light).color(r, g, b, a).next();
        builder.vertex(matrix4f, x + xSize, y + ySize, 0).texture(sprite.getMinU() + uScalingMax, sprite.getMinV() + vScalingMax).light(light).color(r, g, b, a).next();
        builder.vertex(matrix4f, x + xSize, y, 0).texture(sprite.getMinU() + uScalingMax, sprite.getMinV() + vScalingMin).light(light).color(r, g, b, a).next();
        builder.vertex(matrix4f, x, y, 0).texture(sprite.getMinU() + uScalingMin, sprite.getMinV() + vScalingMin).light(light).color(r, g, b, a).next();

        builder.end();
        BufferRenderer.draw(builder);
    }

    public static void renderConsumerSpriteUp(MatrixStack matrices, Sprite sprite, VertexConsumer consumer, float x, float xSize, float y, float ySize, int colour, int overlay, int light, float a)
    {
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
        renderConsumerSpriteTile(matrices, sprite, consumer, x, xSize, y, ySize, colour, overlay, light, a);
        matrices.pop();
    }

    public static void renderConsumerSpriteTile(MatrixStack matrices, Sprite sprite, VertexConsumer consumer, float x, float xSize, float y, float ySize, int colour, int overlay, int light, float a)
    {

        float r = (float)(colour >> 16 & 0xFF) / 255.0f;
        float g = (float)(colour >> 8 & 0xFF) / 255.0f;
        float b = (float)(colour & 0xFF) / 255.0f;

        if(x + xSize > 1 || y + ySize > 1)
        {
            Main.LOGGER.error("cannot render fluid tile with dimension greater than 1");
            return;
        }

        int imageWidth = (int) ((1 / (sprite.getMaxU() - sprite.getMinU())) * 16);
        int imageHeight = (int) ((1 / (sprite.getMaxV() - sprite.getMinV())) * 16);

        float uScalingMin = (((x) * 16) / ((float) imageWidth));
        float vScalingMin = (((y) * 16) / ((float) imageHeight));
        float uScalingMax = (((x + xSize) * 16) / ((float) imageWidth));
        float vScalingMax = (((y + ySize) * 16) / ((float) imageHeight));

        RenderSystem.setShader(GameRenderer::getRenderTypeEntityTranslucentCullShader);
        RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);

        Matrix4f matrix4f = matrices.peek().getPositionMatrix();

        consumer.vertex(matrix4f, x, y + ySize, 0).color(r, g, b, a).texture(sprite.getMinU() + uScalingMin, sprite.getMinV() + vScalingMax).overlay(overlay).light(light).normal(0, 0, 1).next();
        consumer.vertex(matrix4f, x + xSize, y + ySize, 0).color(r, g, b, a).texture(sprite.getMinU() + uScalingMax, sprite.getMinV() + vScalingMax).overlay(overlay).light(light).normal(0, 0, 1).next();
        consumer.vertex(matrix4f, x + xSize, y, 0).color(r, g, b, a).texture(sprite.getMinU() + uScalingMax, sprite.getMinV() + vScalingMin).overlay(overlay).light(light).normal(0, 0, 1).next();
        consumer.vertex(matrix4f, x, y, 0).color(r, g, b, a).texture(sprite.getMinU() + uScalingMin, sprite.getMinV() + vScalingMin).overlay(overlay).light(light).normal(0, 0, 1).next();
    }
}
