package com.sussysyrup.smcompat;

import com.sussysyrup.smcompat.betterend.SMBetterEndClient;
import net.fabricmc.api.ClientModInitializer;

public class SMCompatClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        if(SMCompatPreLaunch.betterEnd)
        {
            SMBetterEndClient.initialise();
        }
    }
}
