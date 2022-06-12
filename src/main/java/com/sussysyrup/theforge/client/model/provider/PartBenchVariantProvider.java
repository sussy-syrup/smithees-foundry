package com.sussysyrup.theforge.client.model.provider;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.block.ForgePartBenchRegistry;
import com.sussysyrup.theforge.client.model.PartBenchModel;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelVariantProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class PartBenchVariantProvider implements ModelVariantProvider {

    @Override
    public @Nullable UnbakedModel loadModelVariant(ModelIdentifier modelId, ModelProviderContext context) throws ModelProviderException {
        Identifier id = new Identifier(modelId.getNamespace(), modelId.getPath());

        if(ForgePartBenchRegistry.getPartBenchWoodMap().containsKey(id))
        {
            if(modelId.getVariant().equals("inventory"))
            {
                Identifier wood = ForgePartBenchRegistry.getPartBenchWood(id);

                return new PartBenchModel(wood.getNamespace(), wood.getPath());
            }
            else
            {
                Identifier wood = ForgePartBenchRegistry.getPartBenchWood(id);

                return new PartBenchModel(wood.getNamespace(), wood.getPath());
            }
        }

        return null;
    }
}
