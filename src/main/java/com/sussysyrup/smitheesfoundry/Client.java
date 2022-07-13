package com.sussysyrup.smitheesfoundry;

import com.sussysyrup.smitheesfoundry.api.block.ApiPartBenchRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ApiPartRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ApiToolRegistry;
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
        ModScreenRegistry.init();

        MaterialRegistry.clientInit();

        ApiPartRegistry.clientInit();

        ApiToolRegistry.itemRenderingInit();

        ApiPartBenchRegistry.clientInit();

        ApiMoltenFluidRegistry.clientInit();

        FluidRegistry.clientInit();

        ItemsRegistry.clientInit();

        BlocksRegistry.clientInit();

        EventRegistry.clientInit();

        TextureRegistry.init();

        S2CReceivers.init();
    }

}
