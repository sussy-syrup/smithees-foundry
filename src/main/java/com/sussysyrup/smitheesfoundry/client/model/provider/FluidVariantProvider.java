package com.sussysyrup.smitheesfoundry.client.model.provider;

import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import com.sussysyrup.smitheesfoundry.util.ClientUtil;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelVariantProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class FluidVariantProvider implements ModelVariantProvider {

    @Override
    public @Nullable UnbakedModel loadModelVariant(ModelIdentifier modelId, ModelProviderContext context) throws ModelProviderException {
        Identifier id = new Identifier(modelId.getNamespace(), modelId.getPath());

        if(ApiMoltenFluidRegistry.getPreFluidRegistry().containsKey(id.getPath()))
        {
            return JsonUnbakedModel.deserialize(ClientUtil.createFluidJsonString(id.getPath()));
        }
        if(ApiMoltenFluidRegistry.getBucketIDs().contains(id))
        {
            return JsonUnbakedModel.deserialize(ClientUtil.createBucketJsonString(id.getPath()));
        }

        return null;
    }
}
