package com.sussysyrup.smitheesfoundry.impl.registry;

import com.sussysyrup.smitheesfoundry.impl.block.ImplAlloySmelteryRegistry;

public class RegistryInstances {

    public static ImplAlloySmelteryRegistry alloySmelteryRegistry;

    public static void flushAndCreate()
    {
        alloySmelteryRegistry = new ImplAlloySmelteryRegistry();
    }

    public static void reload()
    {
        alloySmelteryRegistry = new ImplAlloySmelteryRegistry();
        alloySmelteryRegistry.reload();
    }
}
