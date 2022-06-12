package com.sussysyrup.theforge.client.model.toolmodels;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public interface IToolTypeModel {

    void render(HashMap<String, FabricBakedModel> PART_MODELS, ItemStack stack, RenderContext context);

}
