package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.screen.ForgeScreenHandler;
import com.sussysyrup.theforge.screen.PartBenchScreenHandler;
import com.sussysyrup.theforge.screen.RepairAnvilScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlerRegistry {

    public static ScreenHandlerType<ForgeScreenHandler> FORGE_SCREEN_HANDLER;
    public static ScreenHandlerType<PartBenchScreenHandler> PARTBENCH_SCREEN_HANDLER;
    public static ScreenHandlerType<RepairAnvilScreenHandler> REPAIR_ANVIL_SCREEN_HANDLER;

    public static void init()
    {

        FORGE_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(Main.MODID, "forge_screen_handler"), ForgeScreenHandler::new);
        PARTBENCH_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(Main.MODID, "partbench_screen_handler"), PartBenchScreenHandler::new);
        REPAIR_ANVIL_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(Main.MODID, "repair_anvil_screen_handler"), RepairAnvilScreenHandler::new);
    }
}
