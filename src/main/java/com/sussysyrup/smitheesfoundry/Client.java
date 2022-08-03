package com.sussysyrup.smitheesfoundry;

import com.sussysyrup.smitheesfoundry.client.registry.*;
import com.sussysyrup.smitheesfoundry.impl.client.registryInstances.ClientRegistryInstances;
import com.sussysyrup.smitheesfoundry.networking.s2c.S2CReceivers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
    //Order of loading doesn't matter here so go mad
    @Override
    public void onInitializeClient() {
        ClientRegistryInstances.flushAndCreate();

        ModScreenRegistry.clientInit();

        MaterialRegistryClient.clientInit();

        FluidRegistryClient.clientInit();

        ItemsRegistryClient.clientInit();

        BlocksRegistryClient.clientInit();

        EventRegistryClient.clientInit();

        TextureRegistry.clientInit();

        S2CReceivers.clientInit();

        ClientRegistryInstances.preReload();
        ClientRegistryInstances.registerReloader();
    }

}
