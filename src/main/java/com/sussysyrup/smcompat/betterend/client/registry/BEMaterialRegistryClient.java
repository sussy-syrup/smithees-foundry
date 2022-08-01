package com.sussysyrup.smcompat.betterend.client.registry;

import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class BEMaterialRegistryClient {

    public static void initialise()
    {
        ApiMaterialRegistry.registerColours("eternalcrystal",
                new Color(53, 57, 152),
                new Color(50, 90, 100),
                new Color(104, 140,215),
                new Color(130, 191, 215),
                new Color(109, 198, 215),
                new Color(100, 183, 202),
                new Color(95, 167, 153));

        //Diamond 2.0
        ApiMaterialRegistry.registerColours("terminite",
                new Color(1, 76, 71),
                new Color(1, 98, 91),
                new Color(1, 133,123),
                new Color(0, 168, 148),
                new Color(52, 200, 189),
                new Color(113, 243, 232),
                new Color(161, 248, 240));

        //Diamond 1.5
        ApiMaterialRegistry.registerColours("thallasium",
                new Color(32, 73, 94),
                new Color(55, 128, 153),
                new Color(68, 154,175),
                new Color(85, 176, 191),
                new Color(137, 221, 213),
                new Color(164, 232, 218),
                new Color(207, 249, 226));

        ApiMaterialRegistry.registerColours("aeternium",
                new Color(12, 16, 16),
                new Color(17, 46, 42),
                new Color(63, 82,81),
                new Color(85, 123, 129),
                new Color(123, 153, 149),
                new Color(175, 187, 185),
                new Color(186, 203, 200));
    }

}
