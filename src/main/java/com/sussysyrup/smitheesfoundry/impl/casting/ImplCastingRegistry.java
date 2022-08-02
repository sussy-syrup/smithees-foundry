package com.sussysyrup.smitheesfoundry.impl.casting;

import com.sussysyrup.smitheesfoundry.api.casting.ApiCastingRegistry;
import com.sussysyrup.smitheesfoundry.api.casting.CastingResource;
import com.sussysyrup.smitheesfoundry.api.item.CastItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ImplCastingRegistry implements ApiCastingRegistry {

    private HashMap<Fluid, Item> reloadIngotFluidMap = new HashMap<>();
    private static HashMap<Fluid, Item> ingotFluidMap = new HashMap<>();

    private HashMap<Fluid, Item> reloadNuggetFluidMap = new HashMap<>();
    private static HashMap<Fluid, Item> nuggetFluidMap = new HashMap<>();

    private Map<String, CastItem> reloadTypeCastItemMap = new HashMap<>();
    private static Map<String, CastItem> typeCastItemMap = new HashMap<>();

    private Map<Item, String> reloadItemTypeMap = new HashMap<>();
    private static Map<Item, String> itemTypeMap = new HashMap<>();

    private Map<String, CastingResource> reloadTypeCastingResourceMap = new HashMap<>();
    private static Map<String, CastingResource> typeCastingResourceMap = new HashMap<>();

    @Override
    public HashMap<Fluid, Item> getPreIngotFluidMap() {
        return ingotFluidMap;
    }

    @Override
    public HashMap<Fluid, Item> getPreNuggetFluidMap() {
        return nuggetFluidMap;
    }

    @Override
    public Map<Item, String> getItemTypeMap() {
        return reloadItemTypeMap;
    }

    @Override
    public Map<String, CastingResource> getTypeCastingMap() {
        return reloadTypeCastingResourceMap;
    }

    @Override
    public void setPreIngotFluidMap(HashMap<Fluid, Item> map) {
        ingotFluidMap = map;
    }

    @Override
    public void setPreNuggetFluidMap(HashMap<Fluid, Item> map) {
        nuggetFluidMap = map;
    }

    @Override
    public void addCastItem(String type, CastItem item)
    {
        typeCastItemMap.put(type, item);
    }

    @Override
    public void removeCastItem(String type)
    {
        typeCastItemMap.remove(type);
    }

    @Override
    public void addItemToType(String type, Item item)
    {
        itemTypeMap.put(item, type);
    }

    @Override
    public void removeItemToType(Item item)
    {
        itemTypeMap.remove(item);
    }

    @Override
    public String getTypeFromItem(Item item)
    {
        return reloadItemTypeMap.get(item);
    }

    @Override
    public CastItem getCastItem(String type)
    {
        return reloadTypeCastItemMap.get(type);
    }

    @Override
    public void addCastingResource(String type, CastingResource castingResource)
    {
        typeCastingResourceMap.put(type, castingResource);
    }

    @Override
    public void removeCastingResource(String type)
    {
        typeCastingResourceMap.remove(type);
    }

    @Override
    public CastingResource getCastingResource(String type)
    {
        return reloadTypeCastingResourceMap.get(type);
    }

    @Override
    public void preReload() {
        reloadIngotFluidMap.putAll(ingotFluidMap);
        reloadNuggetFluidMap.putAll(nuggetFluidMap);

        for(Item item : reloadIngotFluidMap.values())
        {
            addItemToType("ingot", item);
        }

        for(Item item : reloadNuggetFluidMap.values())
        {
            addItemToType("nugget", item);
        }

        addCastingResource("ingot", new CastingResource(FluidConstants.INGOT, ingotFluidMap));
        addCastingResource("nugget", new CastingResource(FluidConstants.NUGGET, nuggetFluidMap));
    }

    @Override
    public void reload()
    {
        reloadItemTypeMap.putAll(itemTypeMap);
        reloadTypeCastingResourceMap.putAll(typeCastingResourceMap);
        reloadTypeCastItemMap.putAll(typeCastItemMap);
    }
    
}
