package com.sussysyrup.smitheesfoundry.client.registry;

import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class MaterialRegistryClient {
    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        ApiMaterialRegistry.registerColours("wood",
                new Color(40, 30 ,11),
                new Color(73, 54, 21),
                new Color(89, 67, 25),
                new Color(107, 81, 31),
                new Color(117, 88, 33),
                new Color(134, 101, 38),
                new Color(137, 103, 39));

        ApiMaterialRegistry.registerColours("stone",
                new Color(24, 24 ,24),
                new Color(73, 73, 73),
                new Color(108, 108, 108),
                new Color(127, 127, 127),
                new Color(137, 137, 137),
                new Color(154, 154, 154),
                new Color(173, 173, 173));

        ApiMaterialRegistry.registerColours("flint",
                new Color(14, 14 ,14),
                new Color(34, 32, 32),
                new Color(46, 45, 45),
                new Color(61, 60, 60),
                new Color(86, 86, 86),
                new Color(120, 120, 120),
                new Color(140, 140, 140));

        ApiMaterialRegistry.registerColours("iron",
                new Color(24, 24 ,24),
                new Color(68, 68, 68),
                new Color(150, 150, 150),
                new Color(193, 193, 193),
                new Color(216, 216, 216),
                new Color(236, 236, 236),
                new Color(255, 255, 255));

        ApiMaterialRegistry.registerColours("copper",
                new Color(109, 52 ,53),
                new Color(156, 69, 61),
                new Color(138, 65, 61),
                new Color(156, 78, 49),
                new Color(193, 90, 54),
                new Color(231, 124, 86),
                new Color(252, 153, 130));

        ApiMaterialRegistry.registerColours("diamond",
                new Color(8, 37 ,32),
                new Color(14, 63, 54),
                new Color(38, 138, 119),
                new Color(39, 178, 154),
                new Color(43, 199, 172),
                new Color(51, 235, 203),
                new Color(58, 254, 220));

        ApiMaterialRegistry.registerColours("netherite",
                new Color(17, 17 ,17),
                new Color(39, 28, 29),
                new Color(49, 41, 42),
                new Color(60, 50, 50),
                new Color(59, 57, 59),
                new Color(90, 87, 90),
                new Color(115, 113, 115));

        ApiMaterialRegistry.registerColours("rosegold",
                new Color(86, 44 ,37),
                new Color(122, 64, 50),
                new Color(152, 86, 68),
                new Color(160, 100, 80),
                new Color(183, 124, 108),
                new Color(194, 135, 119),
                new Color(228, 180, 168));
    }
}
