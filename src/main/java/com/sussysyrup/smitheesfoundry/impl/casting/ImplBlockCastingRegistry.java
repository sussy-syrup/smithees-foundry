package com.sussysyrup.smitheesfoundry.impl.casting;

import com.sussysyrup.smitheesfoundry.api.casting.ApiBlockCastingRegistry;
import com.sussysyrup.smitheesfoundry.api.casting.BlockCastingResource;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;

public class ImplBlockCastingRegistry implements ApiBlockCastingRegistry {

    private HashMap<Fluid, Item> reloadBlockFluidMap = new HashMap<>();
    private static HashMap<Fluid, Item> blockFluidMap = new HashMap<>();

    private HashMap<Item, BlockCastingResource> reloadCastingResourceHashMap = new HashMap<>();
    private static HashMap<Item, BlockCastingResource> castingResourceHashMap = new HashMap<>();

    @Override
    public HashMap<Item, BlockCastingResource> getCastingResourceHashmap() {
        return reloadCastingResourceHashMap;
    }

    @Override
    public HashMap<Fluid, Item> preBlockFluidMap() {
        return blockFluidMap;
    }

    @Override
    public void setPreBlockFluidMap(HashMap<Fluid, Item> map) {
        blockFluidMap = map;
    }

    @Override
    public void addCastingResource(Item item, BlockCastingResource castingResource)
    {
        castingResourceHashMap.put(item, castingResource);
    }

    @Override
    public BlockCastingResource getCastingResource(Item item)
    {
        return reloadCastingResourceHashMap.get(item);
    }

    @Override
    public void reload()
    {
        reloadBlockFluidMap.putAll(blockFluidMap);

        addCastingResource(Items.AIR, new BlockCastingResource(reloadBlockFluidMap));

        reloadCastingResourceHashMap.putAll(castingResourceHashMap);
    }

}
