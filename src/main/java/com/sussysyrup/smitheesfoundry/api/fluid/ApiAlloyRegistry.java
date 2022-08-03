package com.sussysyrup.smitheesfoundry.api.fluid;

import com.google.common.collect.ArrayListMultimap;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;

import java.util.List;

public interface ApiAlloyRegistry {

    static ApiAlloyRegistry getInstance()
    {
        return RegistryInstances.alloyRegistry;
    }

    ArrayListMultimap<Fluid, AlloyResource> getAlloyMap();
    void addAlloy(Identifier output, long outputAmount, Identifier[] fluids, long[] amount);

    void removeAlloyResource(Identifier fluidID);

    void clear();
    List<AlloyResource> getAlloyResources(Fluid firstFluid);

    void reload();
}
