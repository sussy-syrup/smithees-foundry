package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiAlloyRegistry;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AlloyRegistry {

    public static void init()
    {
        ApiAlloyRegistry.addAlloy(Registry.FLUID.get(new Identifier(Main.MODID, "molten_rosegold")), 20,
                new Fluid[]{Registry.FLUID.get(new Identifier(Main.MODID, "molten_gold")),
                            Registry.FLUID.get(new Identifier(Main.MODID, "molten_copper"))}, new long[]{15,3});
    }

}
