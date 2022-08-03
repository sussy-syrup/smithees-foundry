package com.sussysyrup.smitheesfoundry.impl.fluid;

import com.sussysyrup.smitheesfoundry.api.fluid.ApiSmelteryResourceRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.SmelteryResource;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;

import java.util.HashMap;
import java.util.Map;

public class ImplSmelteryResourceRegistry implements ApiSmelteryResourceRegistry {

    private Map<Item, SmelteryResource> reloadSmelteryResourceMap = new HashMap<>();
    private static Map<Item, SmelteryResource> smelteryResourceMap = new HashMap<>();

    private Map<TagKey<Item>,SmelteryResource> reloadTagSmelteryResourceMap = new HashMap<>();
    private static Map<TagKey<Item>,SmelteryResource> tagSmelteryResourceMap = new HashMap<>();

    public  Map<Item, SmelteryResource> getSmelteryResourceMap()
    {
        return reloadSmelteryResourceMap;
    }

    public void registerSmelteryResource(Item item, SmelteryResource smelteryResource)
    {
        smelteryResourceMap.put(item, smelteryResource);
    }

    public void removeSmelteryResource(Item item)
    {
        smelteryResourceMap.remove(item);
    }

    public SmelteryResource getSmelteryResource(Item item)
    {
        return reloadSmelteryResourceMap.get(item);
    }

    public Map<TagKey<Item>,SmelteryResource> getTagSmelteryResourceMap()
    {
        return reloadTagSmelteryResourceMap;
    }

    public void addTagSmelteryResource(TagKey<Item> tag, SmelteryResource resource)
    {
        tagSmelteryResourceMap.put(tag, resource);
    }

    @Override
    public void preReload() {
        reloadTagSmelteryResourceMap.putAll(tagSmelteryResourceMap);
    }

    @Override
    public void reload() {
        reloadTagSmelteryResourceMap.putAll(tagSmelteryResourceMap);
        reloadSmelteryResourceMap.putAll(smelteryResourceMap);
    }
}
