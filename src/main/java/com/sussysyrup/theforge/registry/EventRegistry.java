package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.fluid.ForgeMoltenFluidRegistry;
import com.sussysyrup.theforge.api.fluid.ForgeSmelteryResourceRegistry;
import com.sussysyrup.theforge.api.fluid.SmelteryResource;
import com.sussysyrup.theforge.api.item.ForgeToolRegistry;
import com.sussysyrup.theforge.api.material.ForgeMaterialRegistry;
import com.sussysyrup.theforge.api.material.Material;
import com.sussysyrup.theforge.api.material.MaterialResource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class EventRegistry {

    public static void init()
    {
        ServerLifecycleEvents.SERVER_STARTED.register((server) ->
        {
            List<Item> items = Registry.ITEM.stream().toList();
            MaterialResource recipe;
            String fluidID;

            for(Item item : items)
            {
                for(TagKey<Item> tag : ForgeMaterialRegistry.getPreMaterialResourceMap().keySet())
                {
                    recipe = ForgeMaterialRegistry.getPreMaterialResource(tag);

                    if(item.getRegistryEntry().isIn(tag))
                    {
                        ForgeMaterialRegistry.registerMaterialResource(Registry.ITEM.getId(item).toString(), recipe);

                        Material material = ForgeMaterialRegistry.getMaterial(recipe.materialId());

                        if(material.isMetal())
                        {
                            fluidID = material.getFluidID();

                            ForgeSmelteryResourceRegistry.registerSmelteryResource(item, new SmelteryResource(fluidID, (long) (recipe.materialValue() * FluidConstants.INGOT)));
                        }
                    }
                }
            }
        });
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        ClientSpriteRegistryCallback.event(new Identifier("textures/atlas/blocks.png")).register((spriteAtlasTexture, registry) ->
        {
            for(String s : ForgeToolRegistry.getToolRenderedParts()) {
                registry.register(new Identifier(Main.MODID, "item/partrender_" + s));
            }
            for(String s : ForgeMoltenFluidRegistry.getPreFluidRegistry().keySet())
            {
                registry.register(new Identifier(Main.MODID, "block/moltenflow_" + s));
            }
        });
    }
}
