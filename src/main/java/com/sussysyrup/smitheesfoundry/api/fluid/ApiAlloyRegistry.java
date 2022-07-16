package com.sussysyrup.smitheesfoundry.api.fluid;

import com.google.common.collect.ArrayListMultimap;
import net.minecraft.fluid.Fluid;

import java.util.List;

public class ApiAlloyRegistry {

    private static ArrayListMultimap<Fluid, AlloyResource> multimap = ArrayListMultimap.create();

    public static void addAlloy(Fluid output, Fluid... fluids)
    {
        
    }

    public static List<AlloyResource> getAlloyResources(Fluid firstFluid)
    {
        return multimap.get(firstFluid);
    }
}
