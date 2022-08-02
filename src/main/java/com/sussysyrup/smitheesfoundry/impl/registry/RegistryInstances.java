package com.sussysyrup.smitheesfoundry.impl.registry;

import com.sussysyrup.smitheesfoundry.impl.block.ImplAlloySmelteryRegistry;
import com.sussysyrup.smitheesfoundry.impl.block.ImplPartBenchRegistry;

public class RegistryInstances {

    public static ImplAlloySmelteryRegistry alloySmelteryRegistry;
    public static ImplPartBenchRegistry partBenchRegistry;

    public static void flushAndCreate()
    {
        alloySmelteryRegistry = new ImplAlloySmelteryRegistry();
        partBenchRegistry = new ImplPartBenchRegistry();
    }

    public static void reload()
    {
        alloySmelteryRegistry = new ImplAlloySmelteryRegistry();
        alloySmelteryRegistry.reload();

        partBenchRegistry = new ImplPartBenchRegistry();
        partBenchRegistry.reload();
        partBenchRegistry.postReload();
    }
}
