package com.sussysyrup.smitheesfoundry.impl.client.item;

import com.sussysyrup.smitheesfoundry.client.model.provider.PartItemVariantProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;

@Environment(EnvType.CLIENT)
public class ImplPartItemClient {

    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        //Variant called during loadBakedModelJson. Resource called afterwards hence we go with variant
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> new PartItemVariantProvider());
    }
}
