package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.api.fluid.FluidProperties;
import com.sussysyrup.theforge.api.fluid.ForgeMoltenFluidRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.awt.*;

public class FluidRegistry {

    public static void init()
    {
        ForgeMoltenFluidRegistry.registerFluid("molten_iron", new FluidProperties("molten_iron", "iron", 300));
        ForgeMoltenFluidRegistry.registerFluid("molten_copper", new FluidProperties("molten_copper", "copper", 150));
        ForgeMoltenFluidRegistry.registerFluid("molten_netherite", new FluidProperties("molten_netherite","netherite", 900));
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
    }

}
