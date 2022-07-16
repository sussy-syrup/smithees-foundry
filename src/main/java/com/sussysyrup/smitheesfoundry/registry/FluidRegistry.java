package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.fluid.FluidProperties;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.awt.*;

public class FluidRegistry {

    public static void init()
    {
        ApiMoltenFluidRegistry.registerFluid("molten_iron", new FluidProperties("molten_iron", "iron", 300));
        ApiMoltenFluidRegistry.registerFluid("molten_copper", new FluidProperties("molten_copper", "copper", 150));
        ApiMoltenFluidRegistry.registerFluid("molten_netherite", new FluidProperties("molten_netherite","netherite", 900));
        ApiMoltenFluidRegistry.registerFluid("molten_gold", new FluidProperties("molten_gold",null, 450));
        ApiMoltenFluidRegistry.registerFluid("molten_rosegold", new FluidProperties("molten_rosegold",null, 300));
    }

    public static void postInit()
    {
        ApiMoltenFluidRegistry.getFluidProperties("molten_iron").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_iron")));
        ApiMoltenFluidRegistry.getFluidProperties("molten_copper").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_copper")));
        ApiMoltenFluidRegistry.getFluidProperties("molten_netherite").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_netherite")));
        ApiMoltenFluidRegistry.getFluidProperties("molten_gold").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_gold")));
        ApiMoltenFluidRegistry.getFluidProperties("molten_rosegold").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_rosegold")));
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        ApiMoltenFluidRegistry.registerColours("molten_iron",
                new Color(79, 0, 0),
                new Color(87, 0, 0),
                new Color(100, 0, 0),
                new Color(115, 0, 0),
                new Color(134, 0, 0),
                new Color(169, 0, 0),
                new Color(176, 0, 0));

        ApiMoltenFluidRegistry.registerColours("molten_copper",
                new Color(96, 24, 0),
                new Color(115, 25, 0),
                new Color(131, 34, 0),
                new Color(145, 41, 0),
                new Color(166, 44, 1),
                new Color(180, 36, 0),
                new Color(204, 52, 0));

        ApiMoltenFluidRegistry.registerColours("molten_netherite",
                new Color(39, 28, 29),
                new Color(49, 41, 42),
                new Color(60, 50, 50),
                new Color(70, 59, 59),
                new Color(79, 68, 68),
                new Color(88, 76, 76),
                new Color(95, 86, 86));

        ApiMoltenFluidRegistry.registerColours("molten_gold",
                new Color(180, 120, 28),
                new Color(204, 142, 39),
                new Color(211, 150, 50),
                new Color(249, 189, 35),
                new Color(245, 204, 39),
                new Color(255, 236, 79),
                new Color(255, 253, 144));

        ApiMoltenFluidRegistry.registerColours("molten_rosegold",
                new Color(159, 76, 16),
                new Color(182, 93, 25),
                new Color(191, 101, 32),
                new Color(223, 132, 21),
                new Color(225, 144, 24),
                new Color(236, 166, 55),
                new Color(242, 183, 103));
    }

}
