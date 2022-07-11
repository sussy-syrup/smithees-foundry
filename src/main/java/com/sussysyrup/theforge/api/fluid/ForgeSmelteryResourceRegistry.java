package com.sussysyrup.theforge.api.fluid;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;

import java.util.HashMap;
import java.util.Map;

public class ForgeSmelteryResourceRegistry {

    private static Map<Item, SmelteryResource> smelteryResourceMap = new HashMap<>();

    public static Map<Item, SmelteryResource> getSmelteryResourceMap()
    {
        return smelteryResourceMap;
    }

    public static void registerSmelteryResource(Item item, SmelteryResource smelteryResource)
    {
        smelteryResourceMap.put(item, smelteryResource);
    }

    public static void removeSmelteryResource(Item item)
    {
        smelteryResourceMap.remove(item);
    }

    public static SmelteryResource getSmelteryResource(Item item)
    {
        return smelteryResourceMap.get(item);
    }

    private static Map<TagKey<Item>,SmelteryResource> preSmelteryResourceMap = new HashMap<>();

    public static Map<TagKey<Item>,SmelteryResource> getPreSmelteryResourceMap()
    {
        return preSmelteryResourceMap;
    }

    public static void addPreSmelteryResource(TagKey<Item> tag,SmelteryResource resource)
    {
        preSmelteryResourceMap.put(tag, resource);
    }
}
