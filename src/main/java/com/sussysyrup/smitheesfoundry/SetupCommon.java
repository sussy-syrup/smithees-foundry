package com.sussysyrup.smitheesfoundry;

import com.sussysyrup.smitheesfoundry.api.block.ApiPartBenchRegistry;
import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonSetup;
import com.sussysyrup.smitheesfoundry.registry.*;

public class SetupCommon implements CommonSetup {

    @Override
    public void init() {
        //First to exist other than config
        MaterialRegistry.main();

        //Creates Default Parts
        PartRegistry.main();

        //Makes all the necessary calls for ApiPartBenchRegistry. multiple calls like this can exist as long as it's before ApiPartBenchRegistry
        PartBenchRegistry.main();

        //Fluids
        FluidRegistry.main();

        //Smelting
        SmelteryResourceRegistry.main();

        //Technically doesn't need to be here but easier to work around some calls like that
        BlocksRegistry.main();
        ApiPartBenchRegistry.main();
        ItemsRegistry.main();
    }
}
