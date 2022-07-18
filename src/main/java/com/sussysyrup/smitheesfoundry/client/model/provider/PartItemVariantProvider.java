package com.sussysyrup.smitheesfoundry.client.model.provider;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.item.ApiPartRegistry;
import com.sussysyrup.smitheesfoundry.util.ClientUtil;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelVariantProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import org.jetbrains.annotations.Nullable;

public class PartItemVariantProvider implements ModelVariantProvider {

    @Override
    public @Nullable UnbakedModel loadModelVariant(ModelIdentifier modelId, ModelProviderContext context) throws ModelProviderException {
        if(modelId.getNamespace().equals(Main.MODID))
        {
            if (ApiPartRegistry.getPartNames().contains(modelId.getPath())) {
                if (!ApiPartRegistry.getCustomResourcesIdentifiers().contains(modelId.getPath()));
                {

                    String modelJson = ClientUtil.createPartJsonString("partitem", modelId.getPath());

                    if ("".equals(modelJson)) return null;

                    JsonUnbakedModel model = JsonUnbakedModel.deserialize(modelJson);
                    return model;
                }
            }
        }
        return null;
    }
}
