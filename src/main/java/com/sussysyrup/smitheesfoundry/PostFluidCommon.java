package com.sussysyrup.smitheesfoundry;

import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonFluidPost;
import com.sussysyrup.smitheesfoundry.registry.AlloyRegistry;
import com.sussysyrup.smitheesfoundry.registry.CastingRegistry;
import com.sussysyrup.smitheesfoundry.registry.FluidRegistry;

public class PostFluidCommon implements CommonFluidPost {
    @Override
    public void init() {
        AlloyRegistry.main();
        CastingRegistry.preInit();
    }
}
