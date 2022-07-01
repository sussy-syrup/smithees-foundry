package com.sussysyrup.theforge.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.blocks.alloysmeltery.entity.AlloySmelteryControllerBlockEntity;
import com.sussysyrup.theforge.screen.AlloySmelteryFluidScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AlloySmelteryFluidScreen extends HandledScreen<AlloySmelteryFluidScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Main.MODID, "textures/gui/alloy_smeltery_controllerfluid.png");

    private static final Identifier WIDGETS = new Identifier(Main.MODID, "textures/gui/widgets.png");
    private static boolean buttonItem = false;
    private final AlloySmelteryControllerBlockEntity be;

    private TexturedButtonWidget buttonItemWidget = new TexturedButtonWidget(-500, -500, 28, 30, 0, 36, 30, WIDGETS, 256, 256, button -> buttonItem = true);

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

        renderFluid(x, y);
    }

    private void renderFluid(int x, int y) {

        

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

        return super.mouseReleased(mouseX, mouseY, button);
    }
}
