package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiAlloyRegistry;
import net.minecraft.util.Identifier;

public class AlloyRegistry {

    public static void main()
    {
        ApiAlloyRegistry.getInstance().addAlloy(new Identifier(Main.MODID, "molten_rosegold"), 20,
                new Identifier[]{new Identifier(Main.MODID, "molten_gold"),
                            new Identifier(Main.MODID, "molten_copper")}, new long[]{15,5});
    }

}
