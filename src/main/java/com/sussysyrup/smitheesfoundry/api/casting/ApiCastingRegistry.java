package com.sussysyrup.smitheesfoundry.api.casting;

import com.sussysyrup.smitheesfoundry.api.item.CastItem;
import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public interface ApiCastingRegistry {

    static ApiCastingRegistry getInstance()
    {
        return RegistryInstances.castingRegistry;
    }

    HashMap<Fluid, Item> getPreIngotFluidMap();
    HashMap<Fluid, Item> getPreNuggetFluidMap();

    Map<Item, String> getItemTypeMap();

    Map<String, CastingResource> getTypeCastingMap();

    void setPreIngotFluidMap(HashMap<Fluid, Item> map);

    void setPreNuggetFluidMap(HashMap<Fluid, Item> map);
    void addCastItem(String type, CastItem item);

    void removeCastItem(String type);

    void addItemToType(String type, Item item);

    void removeItemToType(Item item);

    String getTypeFromItem(Item item);

    CastItem getCastItem(String type);

    void addCastingResource(String type, CastingResource castingResource);

    void removeCastingResource(String type);

    CastingResource getCastingResource(String type);

    void preReload();

    void reload();
}
