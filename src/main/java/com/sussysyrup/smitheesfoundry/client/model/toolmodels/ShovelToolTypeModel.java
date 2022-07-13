package com.sussysyrup.smitheesfoundry.client.model.toolmodels;

import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;

public class ShovelToolTypeModel implements IToolTypeModel {

    public void render(HashMap<String, FabricBakedModel> PART_MODELS, ItemStack stack, RenderContext context)
    {
        NbtCompound nbt = stack.getNbt();

        if(!(nbt == null)) {
            if(nbt.contains(ToolItem.HEAD_KEY))
            PART_MODELS.get(ApiMaterialRegistry.getMaterial(nbt.getString(ToolItem.HEAD_KEY)).getName() + "_shovel_head").emitItemQuads(null, null, context);
            if(nbt.contains(ToolItem.BINDING_KEY))
            PART_MODELS.get(ApiMaterialRegistry.getMaterial(nbt.getString(ToolItem.BINDING_KEY)).getName() + "_shovel_binding").emitItemQuads(null, null, context);
            if(nbt.contains(ToolItem.HANDLE_KEY))
            PART_MODELS.get(ApiMaterialRegistry.getMaterial(nbt.getString(ToolItem.HANDLE_KEY)).getName() +"_shovel_handle").emitItemQuads(null, null, context);
        }
        else
        {
            PART_MODELS.get("stone_shovel_head").emitItemQuads(null, null, context);
            PART_MODELS.get("wood_shovel_binding").emitItemQuads(null, null, context);
            PART_MODELS.get("wood_shovel_handle").emitItemQuads(null, null, context);
        }
    }


}
