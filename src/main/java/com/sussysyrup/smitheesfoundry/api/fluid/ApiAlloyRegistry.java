package com.sussysyrup.smitheesfoundry.api.fluid;

import com.google.common.collect.ArrayListMultimap;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.fluid.Fluid;

import java.util.List;

public interface ApiAlloyRegistry {

    static ApiAlloyRegistry getInstance()
    {
        return RegistryInstances.alloyRegistry;
    }

    ArrayListMultimap<Fluid, AlloyResource> getAlloyMap();
    void addAlloy(Fluid output, long outputAmount, Fluid[] fluids, long[] amount);

    void removeAlloyResource(Fluid firstFluid);

    void clear();
    List<AlloyResource> getAlloyResources(Fluid firstFluid);

    void reload();
}
