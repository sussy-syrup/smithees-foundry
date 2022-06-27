package com.sussysyrup.theforge.client.model.provider;

import com.sussysyrup.theforge.api.fluid.ForgeMoltenFluidRegistry;
import com.sussysyrup.theforge.util.Util;
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

        if(ForgeMoltenFluidRegistry.getPreFluidRegistry().containsKey(id.getPath()))
        {
            return JsonUnbakedModel.deserialize(Util.createFluidJsonString(id.getPath()));
        }
        if(ForgeMoltenFluidRegistry.getBucketIDs().contains(id))
        {
            return JsonUnbakedModel.deserialize(Util.createBucketJsonString(id.getPath()));
        }

        return null;
    }
}
