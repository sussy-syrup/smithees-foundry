package com.sussysyrup.smcompat.betterend;

import com.sussysyrup.smcompat.betterend.client.registry.BEFluidRegistryClient;
import com.sussysyrup.smcompat.betterend.client.registry.BEMaterialRegistryClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class SMBetterEndClient {

    public static void initialise()
    {
        BEMaterialRegistryClient.initialise();
        BEFluidRegistryClient.clientInit();
    }

}
