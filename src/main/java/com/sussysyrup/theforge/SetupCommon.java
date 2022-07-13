package com.sussysyrup.theforge;

import com.sussysyrup.theforge.api.entrypoints.CommonSetup;
import com.sussysyrup.theforge.registry.*;

public class SetupCommon implements CommonSetup {

    @Override
    public void init() {
        //First to exist other than config
        MaterialRegistry.init();

        //Creates Default Parts
        PartRegistry.init();

        //Makes all the necessary calls for ForgePartBenchRegistry. multiple calls like this can exist as long as its before ForgePartBenchRegistry
        PartBenchRegistry.init();

        //Fluids
        FluidRegistry.init();

        //Smelting
        SmelteryResourceRegistry.init();
    }
}
