package com.sussysyrup.smitheesfoundry.api.fluid;

import com.google.common.collect.ArrayListMultimap;
import com.sussysyrup.smitheesfoundry.Main;
import net.minecraft.fluid.Fluid;

import java.util.List;

public class ApiAlloyRegistry {

    public static ArrayListMultimap<Fluid, AlloyResource> multimap = ArrayListMultimap.create();

    public static void addAlloy(Fluid output, long outputAmount, Fluid[] fluids, long[] amount)
    {
        if(fluids.length != amount.length)
        {
            Main.LOGGER.error("invalid alloy");
            return;
        }

        if(fluids.length <= 1)
        {
            Main.LOGGER.error("invalid alloy");
            return;
        }



        int endIndex = fluids.length-1;

        AlloyResource inner = new AlloyResource(fluids[endIndex], amount[endIndex], null, output, outputAmount);
        AlloyResource outer = null;

        for (int i = endIndex-1; i-- > 0; ) {
            outer = new AlloyResource(fluids[i], amount[i], inner, null, 0);

            inner = outer;
        }
        
        multimap.put(fluids[0], outer);
    }
    public static List<AlloyResource> getAlloyResources(Fluid firstFluid)
    {
        return multimap.get(firstFluid);
    }
}
