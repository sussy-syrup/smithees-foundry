package com.sussysyrup.theforge.client.model.provider;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.item.ForgeToolRegistry;
import com.sussysyrup.theforge.client.model.ToolModel;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ToolModelProvider implements ModelResourceProvider {



    @Override
    public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) throws ModelProviderException {

        if(resourceId.getNamespace().equals(Main.MODID)) {

            String[] p1 = resourceId.getPath().split("/");
            String[] p2 = p1[1].split("_");

            if (ForgeToolRegistry.getTools().contains(p2[1])) {
                return new ToolModel(p2[1]);
            }

        }
        return null;
    }
}
