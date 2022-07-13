package com.sussysyrup.theforge;

import com.sussysyrup.theforge.api.block.ForgePartBenchRegistry;
import com.sussysyrup.theforge.api.entrypoints.CommonPost;
import com.sussysyrup.theforge.networking.c2s.C2SReceivers;
import com.sussysyrup.theforge.registry.BlocksRegistry;
import com.sussysyrup.theforge.registry.EventRegistry;
import com.sussysyrup.theforge.registry.ItemsRegistry;
import com.sussysyrup.theforge.registry.ModScreenHandlerRegistry;

public class PostCommon  implements CommonPost {

    @Override
    public void init() {
        BlocksRegistry.init();
        ForgePartBenchRegistry.init();
        ItemsRegistry.init();
        EventRegistry.init();
        ModScreenHandlerRegistry.init();
        C2SReceivers.init();
    }
}
