package com.sussysyrup.smitheesfoundry.impl.registry;

import com.sussysyrup.smitheesfoundry.impl.block.ImplAlloySmelteryRegistry;
import com.sussysyrup.smitheesfoundry.impl.block.ImplPartBenchRegistry;
import com.sussysyrup.smitheesfoundry.impl.casting.ImplBlockCastingRegistry;

public class RegistryInstances {

    public static ImplAlloySmelteryRegistry alloySmelteryRegistry;
    public static ImplPartBenchRegistry partBenchRegistry;
    public static ImplBlockCastingRegistry blockCastingRegistry;

    public static void flushAndCreate()
    {
        alloySmelteryRegistry = new ImplAlloySmelteryRegistry();
        partBenchRegistry = new ImplPartBenchRegistry();
        blockCastingRegistry = new ImplBlockCastingRegistry();
    }

    public static void reload()
    {
        alloySmelteryRegistry = new ImplAlloySmelteryRegistry();
        alloySmelteryRegistry.reload();

        partBenchRegistry = new ImplPartBenchRegistry();
        partBenchRegistry.reload();
        partBenchRegistry.postReload();

        blockCastingRegistry = new ImplBlockCastingRegistry();
        blockCastingRegistry.reload();
    }
}
