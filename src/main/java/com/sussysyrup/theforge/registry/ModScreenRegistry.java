package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.client.gui.screen.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class ModScreenRegistry {

    public static void init()
    {
        ScreenRegistry.register(ModScreenHandlerRegistry.FORGE_SCREEN_HANDLER, ForgeScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.PARTBENCH_SCREEN_HANDLER, PartBenchScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.REPAIR_ANVIL_SCREEN_HANDLER, RepairAnvilScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.ALLOY_SMELTERY_SCREEN_HANDLER, AlloySmelteryInvScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.ALLOY_SMELTERYFLUID_SCREEN_HANDLER, AlloySmelteryFluidScreen::new);
    }
}
