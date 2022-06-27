package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.screen.AlloySmelteryInvScreenHandler;
import com.sussysyrup.theforge.screen.ForgeScreenHandler;
import com.sussysyrup.theforge.screen.PartBenchScreenHandler;
import com.sussysyrup.theforge.screen.RepairAnvilScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlerRegistry {

    public static ScreenHandlerType<ForgeScreenHandler> FORGE_SCREEN_HANDLER;
    public static ScreenHandlerType<PartBenchScreenHandler> PARTBENCH_SCREEN_HANDLER;
    public static ScreenHandlerType<RepairAnvilScreenHandler> REPAIR_ANVIL_SCREEN_HANDLER;
    public static ScreenHandlerType<AlloySmelteryInvScreenHandler> ALLOY_SMELTERY_SCREEN_HANDLER;

    public static void init()
    {
        FORGE_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "forge_screen_handler"), new ScreenHandlerType<ForgeScreenHandler>(ForgeScreenHandler::new));
        PARTBENCH_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "partbench_screen_handler"), new ScreenHandlerType<PartBenchScreenHandler>(PartBenchScreenHandler::new));
        REPAIR_ANVIL_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "repair_anvil_screen_handler"), new ScreenHandlerType<RepairAnvilScreenHandler>(RepairAnvilScreenHandler::new));

        ALLOY_SMELTERY_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "alloy_smeltery_screen_handler"), new ExtendedScreenHandlerType<>(AlloySmelteryInvScreenHandler::new));
    }
}
