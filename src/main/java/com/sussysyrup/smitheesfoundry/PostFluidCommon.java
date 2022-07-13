package com.sussysyrup.smitheesfoundry;

import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonFluidPost;
import com.sussysyrup.smitheesfoundry.registry.FluidRegistry;

public class PostFluidCommon implements CommonFluidPost {
    @Override
    public void init() {
        FluidRegistry.postInit();
    }
}
