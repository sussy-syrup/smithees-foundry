package com.sussysyrup.smitheesfoundry.api.casting;

import com.sussysyrup.smitheesfoundry.api.item.CastItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ApiCastingRegistry {

    public static HashMap<Fluid, Item> ingotFluidMap = new HashMap<>();
    public static HashMap<Fluid, Item> nuggetFluidMap = new HashMap<>();
    private static Map<String, CastItem> typeCastItemMap = new HashMap<>();
    public static Map<Item, String> itemTypeMap = new HashMap<>();
    public static Map<String, CastingResource> typeCastingResourceMap = new HashMap<>();

    public static void addCastItem(String type, CastItem item)
    {
        typeCastItemMap.put(type, item);
    }

    public static void removeCastItem(String type)
    {
        typeCastItemMap.remove(type);
    }

    public static void addItemToType(String type, Item item)
    {
        itemTypeMap.put(item, type);
    }

    public static void removeItemToType(Item item)
    {
        itemTypeMap.remove(item);
    }

    public static String getTypeFromItem(Item item)
    {
        return itemTypeMap.get(item);
    }

    public static CastItem getCastItem(String type)
    {
        return typeCastItemMap.get(type);
    }

    public static void addCastingResource(String type, CastingResource castingResource)
    {
        typeCastingResourceMap.put(type, castingResource);
    }

    public static void removeCastingResource(String type)
    {
        typeCastingResourceMap.remove(type);
    }

    public static CastingResource getCastingResource(String type)
    {
        return typeCastingResourceMap.get(type);
    }

    public static void init()
    {
        addCastingResource("ingot", new CastingResource(FluidConstants.INGOT, ingotFluidMap));
        addCastingResource("nugget", new CastingResource(FluidConstants.NUGGET, nuggetFluidMap));

        for(Item item : ingotFluidMap.values())
        {
            addItemToType("ingot", item);
        }

        for(Item item : nuggetFluidMap.values())
        {
            addItemToType("nugget", item);
        }
    }
}
