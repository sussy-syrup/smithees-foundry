package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.casting.CastingResource;
import com.sussysyrup.theforge.api.casting.ForgeCastingRegistry;
import com.sussysyrup.theforge.api.fluid.FluidProperties;
import com.sussysyrup.theforge.api.fluid.ForgeMoltenFluidRegistry;
import com.sussysyrup.theforge.api.fluid.ForgeSmelteryResourceRegistry;
import com.sussysyrup.theforge.api.fluid.SmelteryResource;
import com.sussysyrup.theforge.api.item.ForgePartRegistry;
import com.sussysyrup.theforge.api.item.ForgeToolRegistry;
import com.sussysyrup.theforge.api.material.ForgeMaterialRegistry;
import com.sussysyrup.theforge.api.material.Material;
import com.sussysyrup.theforge.api.material.MaterialResource;
import com.sussysyrup.theforge.api.item.PartItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.List;

public class EventRegistry {

    public static void init()
    {
        ServerLifecycleEvents.SERVER_STARTED.register((server) ->
        {
            List<Item> items = Registry.ITEM.stream().toList();

            //Casting - Depends on materials and parts existing
            CastingRegistry.preInit();
            CastingRegistry.init();

            for(Item item : items)
            {
                setupMaterials(item);
                setupSmelteryMelting(item);
                setupCasting(item);
            }
        });
    }

    private static void setupMaterials(Item item)
    {
        MaterialResource recipe;
        String fluidID;
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
                break;
            }
        }
    }

    private static void setupSmelteryMelting(Item item)
    {
        for(TagKey<Item> tag : ForgeSmelteryResourceRegistry.getPreSmelteryResourceMap().keySet())
        {
            if(item.getRegistryEntry().isIn(tag))
            {
                ForgeSmelteryResourceRegistry.registerSmelteryResource(item, ForgeSmelteryResourceRegistry.getPreSmelteryResourceMap().get(tag));
                break;
            }
        }
        FluidProperties fluidProperties;

        CastingResource castingResource;
        if(item instanceof PartItem partItem)
        {
            for(String key : ForgeMoltenFluidRegistry.getFluidRegistry().keySet())
            {
                fluidProperties = ForgeMoltenFluidRegistry.getFluidProperties(key);
                if(partItem.getMaterialId().equals(fluidProperties.getMaterialID()))
                {
                    ForgeSmelteryResourceRegistry.registerSmelteryResource(item, new SmelteryResource(key, ((long) ForgePartRegistry.getPartCost(partItem.getPartName()).floatValue()) * FluidConstants.INGOT));

                    castingResource = ForgeCastingRegistry.getCastingResource(partItem.getPartName());
                    if(castingResource == null)
                    {
                        FluidProperties finalFluidProperties = fluidProperties;

                        castingResource = new CastingResource(((long) ForgePartRegistry.getPartCost(partItem.getPartName()).floatValue()) * FluidConstants.INGOT, new HashMap<Fluid,Item>(){{
                            put(finalFluidProperties.getFluid() ,partItem);
                        }});
                        ForgeCastingRegistry.addCastingResource(partItem.getPartName(), castingResource);
                    }
                    else
                    {
                        HashMap<Fluid,Item> map = castingResource.fluidItemMap();
                        map.put(fluidProperties.getFluid(), partItem);

                        /**
                        Long fluidValue = castingResource.fluidValue();
                        ForgeCastingRegistry.removeCastingResource(partItem.getPartName());
                        ForgeCastingRegistry.addCastingResource(partItem.getPartName(), new CastingResource(fluidValue, map));
                         **/
                    }
                    break;
                }
            }
        }
    }

    private static void setupCasting(Item item)
    {
        if(item instanceof PartItem partItem)
        {
            ForgeCastingRegistry.addItemToType(partItem.getPartName(), partItem);
        }
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
