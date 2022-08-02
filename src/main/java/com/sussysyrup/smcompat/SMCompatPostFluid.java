package com.sussysyrup.smcompat;

import com.sussysyrup.smcompat.betterend.SMBetterEndPostFluid;
import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonFluidPost;

public class SMCompatPostFluid implements CommonFluidPost {
    @Override
    public void init() {
        if(SMCompatPreLaunch.betterEnd)
        {
            SMBetterEndPostFluid.initialise();

        }
    }
}
