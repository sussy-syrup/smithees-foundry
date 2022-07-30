package com.sussysyrup.smitheesfoundry.api.casting;


import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;

public class ApiBlockCastingRegistry {

    public static HashMap<Fluid, Item> blockFluidMap = new HashMap<>();

    public static HashMap<Item,BlockCastingResource> castingResourceHashMap = new HashMap<>();

    public static void addCastingResource(Item item, BlockCastingResource castingResource)
    {
       castingResourceHashMap.put(item, castingResource);
    }

    public static BlockCastingResource getCastingResource(Item item)
    {
        return castingResourceHashMap.get(item);
    }

    public static void init()
    {
        addCastingResource(Items.AIR, new BlockCastingResource(blockFluidMap));

    }
}
