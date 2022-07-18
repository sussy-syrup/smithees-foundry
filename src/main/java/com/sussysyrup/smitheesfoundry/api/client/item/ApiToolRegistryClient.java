package com.sussysyrup.smitheesfoundry.api.client.item;

import com.sussysyrup.smitheesfoundry.api.item.ApiToolRegistry;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.client.model.provider.ToolModelProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;

@Environment(EnvType.CLIENT)
public class ApiToolRegistryClient {
    @Environment(EnvType.CLIENT)
    public static void itemRenderingInit()
    {
        for(String materialId : ApiMaterialRegistry.getKeys().stream().toList())
        {
            for(String partName : ApiToolRegistry.getPreToolRenderedParts())
            {
                String partId = materialId + "_" + partName;
                ApiToolRegistry.addToolRenderedParts(partId);
            }
        }

        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new ToolModelProvider());
    }
}
