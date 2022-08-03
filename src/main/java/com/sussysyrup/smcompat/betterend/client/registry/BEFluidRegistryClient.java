package com.sussysyrup.smcompat.betterend.client.registry;

import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;

import java.awt.*;

public class BEFluidRegistryClient {

    public static void clientInit()
    {
        ApiMoltenFluidRegistry.getInstance().registerColours("molten_thallasium",
                new Color(72, 123, 144),
                new Color(75, 148, 173),
                new Color(88, 164,185),
                new Color(95, 186, 201),
                new Color(137, 221, 213),
                new Color(164, 232, 218),
                new Color(207, 249, 226));

        ApiMoltenFluidRegistry.getInstance().registerColours("molten_terminite",
                new Color(21, 96, 91),
                new Color(21, 118, 121),
                new Color(1, 133,123),
                new Color(0, 168, 148),
                new Color(52, 200, 189),
                new Color(113, 243, 232),
                new Color(161, 248, 240));

        ApiMoltenFluidRegistry.getInstance().registerColours("molten_aeternium",
                new Color(25, 70, 56),
                new Color(27, 76, 62),
                new Color(33, 82,51),
                new Color(55, 123, 99),
                new Color(83, 153, 109),
                new Color(135, 187, 145),
                new Color(146, 203, 160));
    }

}
