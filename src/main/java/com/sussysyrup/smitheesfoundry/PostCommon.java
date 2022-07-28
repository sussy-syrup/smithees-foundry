package com.sussysyrup.smitheesfoundry;

import com.sussysyrup.smitheesfoundry.api.block.ApiPartBenchRegistry;
import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonPost;
import com.sussysyrup.smitheesfoundry.networking.c2s.C2SReceivers;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import com.sussysyrup.smitheesfoundry.registry.EventRegistry;
import com.sussysyrup.smitheesfoundry.registry.ItemsRegistry;
import com.sussysyrup.smitheesfoundry.registry.ModScreenHandlerRegistry;

public class PostCommon implements CommonPost {

    @Override
    public void init() {
        BlocksRegistry.main();
        ApiPartBenchRegistry.main();
        ItemsRegistry.main();
        EventRegistry.main();
        ModScreenHandlerRegistry.main();
        C2SReceivers.main();
    }
}
