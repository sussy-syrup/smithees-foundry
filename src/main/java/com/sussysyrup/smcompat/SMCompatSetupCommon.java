package com.sussysyrup.smcompat;

import com.sussysyrup.smcompat.betterend.SMBetterEndSetupCommon;
import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonSetup;

public class SMCompatSetupCommon implements CommonSetup {

    @Override
    public void init() {
        if(SMCompatPreLaunch.modidList.contains("betterend"))
        {
            SMBetterEndSetupCommon.initialise();
        }
    }
}
