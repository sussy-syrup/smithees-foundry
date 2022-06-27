package com.sussysyrup.theforge.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.fluid.FluidProperties;
import com.sussysyrup.theforge.api.fluid.ForgeMoltenFluidRegistry;
import com.sussysyrup.theforge.api.fluid.ForgeSmelteryResourceRegistry;
import com.sussysyrup.theforge.api.fluid.SmelteryResource;
import com.sussysyrup.theforge.blocks.alloysmeltery.entity.AlloySmelteryControllerBlockEntity;
import com.sussysyrup.theforge.screen.AlloySmelteryInvScreenHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AlloySmelteryInvScreen extends HandledScreen<AlloySmelteryInvScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Main.MODID, "textures/gui/alloy_smeltery_controller.png");

    private static final Identifier WIDGETS = new Identifier(Main.MODID, "textures/gui/widgets.png");
    private static boolean buttonUp = false;
    private static boolean buttonDown = true;
    private final AlloySmelteryControllerBlockEntity be;

    private TexturedButtonWidget buttonDownWidget = new TexturedButtonWidget(this.width / 2, this.height / 4, 18, 18, 18, 0, 18, WIDGETS, 64, 64, button -> buttonDown = true);
    private TexturedButtonWidget buttonUpWidget = new TexturedButtonWidget(this.width / 4, this.height / 4, 18, 18, 0, 0, 18, WIDGETS, 64, 64, button -> buttonUp = true);

    public AlloySmelteryInvScreen(AlloySmelteryInvScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        this.be = handler.getBlockEntity();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        int cookTime;
        SmelteryResource smelteryResource;
        FluidProperties fluidProperties;
        int unitSize;

        int x1;
        int y1;
        int sideStep = 0;
        int verticalStep = 0;

        for(int i = be.itemPageShift * 21; i < be.smeltTicks.size(); i++)
        {
            if(verticalStep > 2)
            {
                break;
            }
            if(be.smeltTicks.get(i) == 0)
            {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, WIDGETS);
                x1 = x + 31;
                y1 = y + 17;
                drawTexture(matrices, x1 + (sideStep * 20), y1 + (verticalStep * 18), 36, 1, 2, 16, 64, 64);
                sideStep++;
                if(sideStep > 6)
                {
                    sideStep =0;
                    verticalStep++;
                }
                continue;
            }

            smelteryResource = ForgeSmelteryResourceRegistry.getSmelteryResource(be.itemInventory.getStack(i).getItem());
            fluidProperties = ForgeMoltenFluidRegistry.getFluidProperties(smelteryResource.fluidID());
            cookTime = (int) (fluidProperties.getCookTime() * (smelteryResource.fluidValue() / FluidConstants.INGOT));
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        buttonDownWidget.setPos(x + 155, y + 52);
        buttonUpWidget.setPos(x + 155, y + 16);
    }

    @Override
    protected void init() {
        super.init();
        this.addDrawableChild(buttonDownWidget);
        this.addDrawableChild(buttonUpWidget);
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
