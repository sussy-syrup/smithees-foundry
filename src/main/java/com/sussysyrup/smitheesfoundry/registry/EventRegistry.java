package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.api.casting.CastingResource;
import com.sussysyrup.smitheesfoundry.api.casting.ApiCastingRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.FluidProperties;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiSmelteryResourceRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.SmelteryResource;
import com.sussysyrup.smitheesfoundry.api.item.ApiPartRegistry;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import com.sussysyrup.smitheesfoundry.api.material.MaterialResource;
import com.sussysyrup.smitheesfoundry.api.item.PartItem;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.List;

public class EventRegistry {

    public static void main()
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
        for(TagKey<Item> tag : ApiMaterialRegistry.getPreMaterialResourceMap().keySet())
        {
            recipe = ApiMaterialRegistry.getPreMaterialResource(tag);

            if(item.getRegistryEntry().isIn(tag))
            {
                ApiMaterialRegistry.registerMaterialResource(Registry.ITEM.getId(item).toString(), recipe);

                Material material = ApiMaterialRegistry.getMaterial(recipe.materialId());

                if(material.isMetal())
                {
                    fluidID = material.getFluidID();

                    ApiSmelteryResourceRegistry.registerSmelteryResource(item, new SmelteryResource(fluidID, (long) (recipe.materialValue() * FluidConstants.INGOT)));
                }
                break;
            }
        }
    }

    private static void setupSmelteryMelting(Item item)
    {
        for(TagKey<Item> tag : ApiSmelteryResourceRegistry.getPreSmelteryResourceMap().keySet())
        {
            if(item.getRegistryEntry().isIn(tag))
            {
                ApiSmelteryResourceRegistry.registerSmelteryResource(item, ApiSmelteryResourceRegistry.getPreSmelteryResourceMap().get(tag));
                break;
            }
        }
        FluidProperties fluidProperties;

        CastingResource castingResource;
        if(item instanceof PartItem partItem)
        {
            for(String key : ApiMoltenFluidRegistry.getFluidRegistry().keySet())
            {
                fluidProperties = ApiMoltenFluidRegistry.getFluidProperties(key);
                if(partItem.getMaterialId().equals(fluidProperties.getMaterialID()))
                {
                    ApiSmelteryResourceRegistry.registerSmelteryResource(item, new SmelteryResource(key, ((long) ApiPartRegistry.getPartCost(partItem.getPartName()).floatValue()) * FluidConstants.INGOT));

                    castingResource = ApiCastingRegistry.getCastingResource(partItem.getPartName());
                    if(castingResource == null)
                    {
                        FluidProperties finalFluidProperties = fluidProperties;

                        castingResource = new CastingResource(((long) ApiPartRegistry.getPartCost(partItem.getPartName()).floatValue()) * FluidConstants.INGOT, new HashMap<Fluid,Item>(){{
                            put(finalFluidProperties.getFluid() ,partItem);
                        }});
                        ApiCastingRegistry.addCastingResource(partItem.getPartName(), castingResource);
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
            ApiCastingRegistry.addItemToType(partItem.getPartName(), partItem);
        }
    }

}
