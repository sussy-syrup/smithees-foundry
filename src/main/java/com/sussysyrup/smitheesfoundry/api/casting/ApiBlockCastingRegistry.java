package com.sussysyrup.smitheesfoundry.api.casting;


import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

import java.util.HashMap;

public interface ApiBlockCastingRegistry {

    static ApiBlockCastingRegistry getInstance() {
        return RegistryInstances.blockCastingRegistry;
    }

    HashMap<Item, BlockCastingResource> getCastingResourceHashmap();

    HashMap<Fluid, Item> preBlockFluidMap();

    void setPreBlockFluidMap(HashMap<Fluid, Item> map);

    void addCastingResource(Item item, BlockCastingResource castingResource);

    BlockCastingResource getCastingResource(Item item);

    void reload();
}
