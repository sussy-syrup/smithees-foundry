package com.sussysyrup.theforge.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.screen.PartBenchScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PartBenchScreen extends HandledScreen<PartBenchScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Main.MODID, "textures/gui/part_bench_block.png");
    private static final Identifier WIDGETS = new Identifier(Main.MODID, "textures/gui/widgets.png");
    private static boolean buttonUp = false;
    private static boolean buttonDown = true;

    private TexturedButtonWidget buttonDownWidget = new TexturedButtonWidget(this.width / 2, this.height / 4, 18, 18, 18, 0, 18, WIDGETS, 64, 64, button -> buttonDown = true);
    private TexturedButtonWidget buttonUpWidget = new TexturedButtonWidget(this.width / 4, this.height / 4, 18, 18, 0, 0, 18, WIDGETS, 64, 64, button -> buttonUp = true);

    public PartBenchScreen(PartBenchScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
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

        buttonDownWidget.setPos(x + 153, y + 43);
        buttonUpWidget.setPos(x + 153, y + 7);
    }

    @Override
    protected void init() {
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2 + (backgroundWidth - textRenderer.getWidth(title)) / 4;
        titleY = playerInventoryTitleY;

        this.addDrawableChild(buttonDownWidget);
        this.addDrawableChild(buttonUpWidget);

        super.init();
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        if(buttonUp) {
            buttonUp = false;
            MinecraftClient.getInstance().interactionManager.clickButton(getScreenHandler().syncId, 0);
        }
        if(buttonDown)
        {
            buttonDown = false;
            MinecraftClient.getInstance().interactionManager.clickButton(getScreenHandler().syncId, 1);
        }

        buttonUp = false;
        buttonDown = false;

        return super.mouseReleased(mouseX, mouseY, button);
    }
}
