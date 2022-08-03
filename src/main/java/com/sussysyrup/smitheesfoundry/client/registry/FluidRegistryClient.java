package com.sussysyrup.smitheesfoundry.client.registry;

import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class FluidRegistryClient {
    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        ApiMoltenFluidRegistry.getInstance().registerColours("molten_iron",
                new Color(79, 0, 0),
                new Color(87, 0, 0),
                new Color(100, 0, 0),
                new Color(115, 0, 0),
                new Color(134, 0, 0),
                new Color(169, 0, 0),
                new Color(176, 0, 0));

        ApiMoltenFluidRegistry.getInstance().registerColours("molten_copper",
                new Color(96, 24, 0),
                new Color(115, 25, 0),
                new Color(131, 34, 0),
                new Color(145, 41, 0),
                new Color(166, 44, 1),
                new Color(180, 36, 0),
                new Color(204, 52, 0));

        ApiMoltenFluidRegistry.getInstance().registerColours("molten_netherite",
                new Color(39, 28, 29),
                new Color(49, 41, 42),
                new Color(60, 50, 50),
                new Color(70, 59, 59),
                new Color(79, 68, 68),
                new Color(88, 76, 76),
                new Color(95, 86, 86));

        ApiMoltenFluidRegistry.getInstance().registerColours("molten_gold",
                new Color(180, 120, 28),
                new Color(204, 142, 39),
                new Color(211, 150, 50),
                new Color(249, 189, 35),
                new Color(245, 204, 39),
                new Color(255, 236, 79),
                new Color(255, 253, 144));

        ApiMoltenFluidRegistry.getInstance().registerColours("molten_rosegold",
                new Color(159, 76, 16),
                new Color(182, 93, 25),
                new Color(191, 101, 32),
                new Color(223, 132, 21),
                new Color(225, 144, 24),
                new Color(236, 166, 55),
                new Color(242, 183, 103));
    }
}
