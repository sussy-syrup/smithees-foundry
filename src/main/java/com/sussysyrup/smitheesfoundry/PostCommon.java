package com.sussysyrup.smitheesfoundry;

import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonPost;
import com.sussysyrup.smitheesfoundry.networking.c2s.C2SReceivers;
import com.sussysyrup.smitheesfoundry.registry.ModScreenHandlerRegistry;

public class PostCommon implements CommonPost {

    @Override
    public void init() {
        ModScreenHandlerRegistry.main();
        C2SReceivers.main();
    }
}
