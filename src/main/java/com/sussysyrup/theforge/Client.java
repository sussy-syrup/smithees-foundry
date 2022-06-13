package com.sussysyrup.theforge;

import com.sussysyrup.theforge.api.block.ForgePartBenchRegistry;
import com.sussysyrup.theforge.api.item.ForgePartRegistry;
import com.sussysyrup.theforge.api.item.ForgeToolRegistry;
import com.sussysyrup.theforge.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreenRegistry.init();

        //SETUP
        MaterialRegistry.clientInit();
        ForgePartRegistry.clientInit();
        ForgeToolRegistry.itemRenderingInit();
        ForgePartBenchRegistry.clientInit();

        //PROCESSING
        ItemsRegistry.clientInit();
        BlocksRegistry.clientInit();

        EventRegistry.clientInit();

        TextureRegistry.init();
    }

}
