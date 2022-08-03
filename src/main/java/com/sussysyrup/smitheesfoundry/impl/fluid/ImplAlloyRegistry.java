package com.sussysyrup.smitheesfoundry.impl.fluid;

import com.google.common.collect.ArrayListMultimap;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.fluid.AlloyResource;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiAlloyRegistry;
import net.minecraft.fluid.Fluid;

import java.util.List;

public class ImplAlloyRegistry implements ApiAlloyRegistry {

    private ArrayListMultimap<Fluid, AlloyResource> reloadMultimap = ArrayListMultimap.create();
    private static ArrayListMultimap<Fluid, AlloyResource> multimap = ArrayListMultimap.create();

    @Override
    public ArrayListMultimap<Fluid, AlloyResource> getAlloyMap() {
        return reloadMultimap;
    }

    @Override
    public void addAlloy(Fluid output, long outputAmount, Fluid[] fluids, long[] amount)
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

        for (int i = endIndex-1; i >= 0; i--) {
            outer = new AlloyResource(fluids[i], amount[i], inner, null, 0);

            inner = outer;
        }
        
        multimap.put(fluids[0], outer);
    }

    @Override
    public void removeAlloyResource(Fluid firstFluid) {
        multimap.removeAll(firstFluid);
    }

    @Override
    public void clear() {
        multimap.clear();
    }

    @Override
    public List<AlloyResource> getAlloyResources(Fluid firstFluid)
    {
        return reloadMultimap.get(firstFluid);
    }

    @Override
    public void reload() {
        reloadMultimap.putAll(multimap);
    }
}
