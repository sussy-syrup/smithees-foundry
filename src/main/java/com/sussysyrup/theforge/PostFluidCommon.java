package com.sussysyrup.theforge;

import com.sussysyrup.theforge.api.entrypoints.CommonFluidPost;
import com.sussysyrup.theforge.registry.FluidRegistry;

public class PostFluidCommon implements CommonFluidPost {
    @Override
    public void init() {
        FluidRegistry.postInit();
    }
}
