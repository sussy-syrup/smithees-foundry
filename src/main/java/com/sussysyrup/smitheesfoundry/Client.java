package com.sussysyrup.smitheesfoundry;

import com.sussysyrup.smitheesfoundry.api.client.block.ApiPartBenchRegistryClient;
import com.sussysyrup.smitheesfoundry.api.client.fluid.ApiMoltenFluidRegistryClient;
import com.sussysyrup.smitheesfoundry.api.client.item.ApiPartItemClient;
import com.sussysyrup.smitheesfoundry.api.client.item.ApiToolRegistryClient;
import com.sussysyrup.smitheesfoundry.networking.s2c.S2CReceivers;
import com.sussysyrup.smitheesfoundry.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
    //Order of loading doesn't matter here so go mad
    @Override
    public void onInitializeClient() {
        ModScreenRegistry.clientInit();

        MaterialRegistry.clientInit();

        ApiPartItemClient.clientInit();

        ApiToolRegistryClient.itemRenderingInit();

        ApiPartBenchRegistryClient.clientInit();

        ApiMoltenFluidRegistryClient.clientInit();

        FluidRegistry.clientInit();

        ItemsRegistryClient.clientInit();

        BlocksRegistryClient.clientInit();

        EventRegistry.clientInit();

        TextureRegistry.clientInit();

        S2CReceivers.clientInit();
    }

}
