package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.fluid.FluidProperties;
import com.sussysyrup.theforge.api.fluid.ForgeMoltenFluidRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.awt.*;

public class FluidRegistry {

    public static void init()
    {
        ForgeMoltenFluidRegistry.registerFluid("molten_iron", new FluidProperties("molten_iron", "iron", 300));
        ForgeMoltenFluidRegistry.registerFluid("molten_copper", new FluidProperties("molten_copper", "copper", 150));
        ForgeMoltenFluidRegistry.registerFluid("molten_netherite", new FluidProperties("molten_netherite","netherite", 900));
        ForgeMoltenFluidRegistry.registerFluid("molten_gold", new FluidProperties("molten_gold",null, 450));
    }

    public static void postInit()
    {
        ForgeMoltenFluidRegistry.getFluidProperties("molten_iron").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_iron")));
        ForgeMoltenFluidRegistry.getFluidProperties("molten_copper").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_copper")));
        ForgeMoltenFluidRegistry.getFluidProperties("molten_netherite").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_netherite")));
        ForgeMoltenFluidRegistry.getFluidProperties("molten_gold").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_gold")));
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        ForgeMoltenFluidRegistry.registerColours("molten_iron",
                new Color(79, 0, 0),
                new Color(87, 0, 0),
                new Color(100, 0, 0),
                new Color(115, 0, 0),
                new Color(134, 0, 0),
                new Color(169, 0, 0),
                new Color(176, 0, 0));

        ForgeMoltenFluidRegistry.registerColours("molten_copper",
                new Color(96, 24, 0),
                new Color(115, 25, 0),
                new Color(131, 34, 0),
                new Color(145, 41, 0),
                new Color(166, 44, 1),
                new Color(180, 36, 0),
                new Color(204, 52, 0));

        ForgeMoltenFluidRegistry.registerColours("molten_netherite",
                new Color(39, 28, 29),
                new Color(49, 41, 42),
                new Color(60, 50, 50),
                new Color(70, 59, 59),
                new Color(79, 68, 68),
                new Color(88, 76, 76),
                new Color(95, 86, 86));

        ForgeMoltenFluidRegistry.registerColours("molten_gold",
                new Color(180, 120, 28),
                new Color(204, 142, 39),
                new Color(211, 150, 50),
                new Color(249, 189, 35),
                new Color(245, 204, 39),
                new Color(255, 236, 79),
                new Color(255, 253, 144));
    }

}
