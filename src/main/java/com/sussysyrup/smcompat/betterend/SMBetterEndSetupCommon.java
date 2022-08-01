package com.sussysyrup.smcompat.betterend;

import com.sussysyrup.smcompat.SMCompatMain;
import com.sussysyrup.smcompat.betterend.registry.BEFluidRegistry;
import com.sussysyrup.smcompat.betterend.registry.BEMaterialRegistry;

public class SMBetterEndSetupCommon {

    public static void initialise()
    {
        SMCompatMain.COMPATLOG.info("beginning BetterEnd integration");
        BEMaterialRegistry.initialise();

        BEFluidRegistry.main();
    }
}
