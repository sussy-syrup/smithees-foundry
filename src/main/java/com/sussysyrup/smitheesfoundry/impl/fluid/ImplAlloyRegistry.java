package com.sussysyrup.smitheesfoundry.impl.fluid;

import com.google.common.collect.ArrayListMultimap;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.fluid.AlloyResource;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiAlloyRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.PreAlloyResource;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImplAlloyRegistry implements ApiAlloyRegistry {

    private ArrayListMultimap<Fluid, AlloyResource> reloadMultimap = ArrayListMultimap.create();
    private static ArrayListMultimap<Identifier, PreAlloyResource> multimap = ArrayListMultimap.create();

    @Override
    public ArrayListMultimap<Fluid, AlloyResource> getAlloyMap() {
        return reloadMultimap;
    }

    @Override
    public void addAlloy(Identifier output, long outputAmount, Identifier[] fluids, long[] amount)
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

        List<Long> amounts = new ArrayList();
        List<Identifier> identifiers = new ArrayList();

        identifiers.addAll(Arrays.stream(fluids).toList());
        identifiers.add(output);
        
        amounts.addAll(Arrays.asList(ArrayUtils.toObject(amount)));
        amounts.add(outputAmount);

        multimap.put(fluids[0], new PreAlloyResource(identifiers, amounts));
    }

    @Override
    public void removeAlloyResource(Identifier fluidID) {
        multimap.removeAll(fluidID);
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
        converter();
    }
    
    private void converter()
    {
        for(Identifier firstFluidID : multimap.keySet()) {
            
            Fluid firstFluid = Registry.FLUID.get(firstFluidID);
            
            for(PreAlloyResource preAlloyResource : multimap.get(firstFluidID)) {

                List<Identifier> fluidIDs = preAlloyResource.identifiers();
                List<Long> fluidAmounts = preAlloyResource.amounts();
                
                int endIndex = fluidIDs.size() - 1;
                
                AlloyResource inner = new AlloyResource(Registry.FLUID.get(fluidIDs.get(endIndex - 1)), fluidAmounts.get(endIndex - 1), null, Registry.FLUID.get(fluidIDs.get(endIndex)), fluidAmounts.get(endIndex));
                AlloyResource outer = null;

                for (int i = endIndex - 2; i >= 0; i--) {
                    outer = new AlloyResource(Registry.FLUID.get(fluidIDs.get(i)), fluidAmounts.get(i), inner, null, 0);

                    inner = outer;
                }

                reloadMultimap.put(firstFluid, outer);
            }
        }
    }
}
