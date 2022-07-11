package com.sussysyrup.theforge.api.casting;

import com.sussysyrup.theforge.api.item.CastItem;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ForgeCastingRegistry {

    private static Map<String, CastItem> typeCastItemMap = new HashMap<>();
    private static Map<Item, String> itemTypeMap = new HashMap<>();
    private static Map<String, CastingResource> typeCastingResourceMap = new HashMap<>();

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
}
