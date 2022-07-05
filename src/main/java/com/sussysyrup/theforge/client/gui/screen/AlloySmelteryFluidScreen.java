package com.sussysyrup.theforge.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.blocks.alloysmeltery.entity.AlloySmelteryControllerBlockEntity;
import com.sussysyrup.theforge.networking.c2s.C2SConstants;
import com.sussysyrup.theforge.screen.AlloySmelteryFluidScreenHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AlloySmelteryFluidScreen extends HandledScreen<AlloySmelteryFluidScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Main.MODID, "textures/gui/alloy_smeltery_controllerfluid.png");

    private static final Identifier WIDGETS = new Identifier(Main.MODID, "textures/gui/widgets.png");
    private static boolean buttonItem = false;
    private final AlloySmelteryControllerBlockEntity be;

    private TexturedButtonWidget buttonItemWidget = new TexturedButtonWidget(-500, -500, 28, 30, 0, 36, 30, WIDGETS, 256, 256, button -> buttonItem = true);

    int liquidID = -1;

    public AlloySmelteryFluidScreen(AlloySmelteryFluidScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        this.be = handler.be;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        drawMouseoverTooltip(matrices, mouseX, mouseY);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        buttonItemWidget.setPos(x, y - 27);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS);
        RenderSystem.enableDepthTest();
        drawTexture(matrices, x + 28, y - 27, 27, 66, 28, 30, 256, 256);

        renderFluid(matrices, x, y);

        this.renderMouseToolTip(matrices, mouseX, mouseY);
    }

    private void renderFluid(MatrixStack matrices, int x, int y) {

        long capacity = be.fluidStorage.maxCapacity;
        int currentHeight;
        Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
        Sprite sprite;

        sprite = atlas.apply(new Identifier("theforge", "block/moltenstill_" + "molten_iron"));

        int imageWidth = 0;
        int imageHeight = 0;

        imageWidth = (int) ((1 / (sprite.getMaxU() - sprite.getMinU())) * 16);
        imageHeight = (int) ((1 / (sprite.getMaxV() - sprite.getMinV())) * 16);

        List<Integer> heights = calcHeights();

        int yShift;
        int yOffset;
        int yTrans = 0;
        float yScalingFac;
        float xScalingFac;

        Fluid fluid;

        StorageView<FluidVariant> view;

        for (int i = 0; i < heights.size() - 1; i++) {
            view = be.fluidStorage.views.get(i);

            fluid = view.getResource().getFluid();
            sprite = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(MinecraftClient.getInstance().world, new BlockPos(0, 0, 0), fluid.getDefaultState())[0];

            yShift = 16 - (heights.get(i + 1) - heights.get(i));
            xScalingFac = 2F / imageWidth;
            yOffset = yShift;
            yScalingFac = ((float) yOffset) / ((float) imageHeight);

            if(yShift < 0) {
                while (yShift < 0) {
                    yOffset = 0;
                    yScalingFac = ((float) yOffset) / ((float) imageHeight);
                    matrices.push();
                    matrices.translate(0, yTrans, 0);
                    renderRow(matrices, heights, i, sprite, yOffset, yScalingFac, xScalingFac);
                    matrices.pop();
                    yShift +=16;
                    yTrans +=16;
                }
                yOffset = yOffset + yShift;
                yScalingFac = ((float) yOffset) / ((float) imageHeight);
                matrices.push();
                matrices.translate(0, yTrans, 0);
                renderRow(matrices, heights, i, sprite, yOffset, yScalingFac, xScalingFac);
                matrices.pop();
            }
            else
            {
                renderRow(matrices, heights, i, sprite, yOffset, yScalingFac, xScalingFac);
            }
        }
    }

    private void renderRow(MatrixStack matrices, List<Integer> heights, int i, Sprite sprite, int yOffset, float yScalingFac, float xScalingFac)
    {
        Matrix4f matrix4f;
        for (int t = 0; t < 8; t++) {

            matrices.push();

            if (!(t == 7)) {
                matrices.translate(x + 7 + (t * 16), y + 69 - heights.get(i + 1), 0);
                matrix4f = matrices.peek().getPositionMatrix();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);

                BufferBuilder builder = Tessellator.getInstance().getBuffer();
                builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                builder.vertex(matrix4f, 0, 16 - yOffset, 0).texture(sprite.getMinU(), sprite.getMaxV() - yScalingFac).next();
                builder.vertex(matrix4f, 16, 16 - yOffset, 0).texture(sprite.getMaxU(), sprite.getMaxV() - yScalingFac).next();
                builder.vertex(matrix4f, 16, 0, 0).texture(sprite.getMaxU(), sprite.getMinV()).next();
                builder.vertex(matrix4f, 0, 0, 0).texture(sprite.getMinU(), sprite.getMinV()).next();
                builder.end();
                BufferRenderer.draw(builder);

            } else {
                matrices.translate(x + 7 + (t * 16), y + 69 - heights.get(i + 1), 0);
                matrix4f = matrices.peek().getPositionMatrix();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);

                BufferBuilder builder = Tessellator.getInstance().getBuffer();
                builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                builder.vertex(matrix4f, 0, 16 - yOffset, 0).texture(sprite.getMinU(), sprite.getMaxV() - yScalingFac).next();
                builder.vertex(matrix4f, 14, 16 - yOffset, 0).texture(sprite.getMaxU() - xScalingFac, sprite.getMaxV() - yScalingFac).next();
                builder.vertex(matrix4f, 14, 0, 0).texture(sprite.getMaxU() - xScalingFac, sprite.getMinV()).next();
                builder.vertex(matrix4f, 0, 0, 0).texture(sprite.getMinU(), sprite.getMinV()).next();
                builder.end();
                BufferRenderer.draw(builder);

            }

            matrices.pop();
        }
    }

    //Max height 54, min height 2. Total Fluids in smeltery = 27. You must do something special to go past 27 fluids
    private List<Integer> calcHeights() {
        List<Integer> heights = new ArrayList<>();
        List<StorageView<FluidVariant>> views = be.fluidStorage.views;
        heights.add(0);

        float precalc;
        int calc;

        for(StorageView<FluidVariant> view : views)
        {
            precalc = ((((float) view.getAmount()) / ((float) be.fluidStorage.maxCapacity)) * 54L);

            calc = (int) Math.max(2, Math.floor(precalc));
            heights.add(heights.get(heights.size() - 1) + calc);
        }

        return heights;
    }

    @Override
    protected void init() {
        super.init();
        this.addDrawableChild(buttonItemWidget);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        if(buttonItem) {
            buttonItem = false;
            MinecraftClient.getInstance().interactionManager.clickButton(getScreenHandler().syncId, 0);
        }

        buttonItem = false;

        if(!(liquidID ==-1))
        {
            PacketByteBuf buf = PacketByteBufs.create();

            buf.writeBlockPos(be.getPos());
            buf.writeInt(liquidID);

            ClientPlayNetworking.send(C2SConstants.AlloySmelteryFluidClick, buf);

            liquidID = -1;
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    public void renderMouseToolTip(MatrixStack matrices, double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);

        TranslatableText translatableText;
        Identifier fluidID;

        if((mouseX >= x + 7 && mouseX <= x+133) && (mouseY <= y+69 && mouseY >= y+15))
        {
            int refY = y+69;
            List<Integer> heights = calcHeights();
            if(heights.size() == 1)
            {
                return;
            }

            for(int i =0; i < heights.size() - 1; i++)
            {
                if(mouseY <= refY - heights.get(i) && mouseY >= refY -heights.get(i+1))
                {
                    fluidID = (Registry.FLUID.getId(be.fluidStorage.views.get(i).getResource().getFluid()));
                    translatableText = new TranslatableText("fluid." +fluidID.getNamespace() + "." + fluidID.getPath());
                    renderTooltip(matrices, translatableText, (int) mouseX, (int) mouseY);
                    liquidID = i;
                    break;
                }
                liquidID = -1;
            }
        }
    }
}
