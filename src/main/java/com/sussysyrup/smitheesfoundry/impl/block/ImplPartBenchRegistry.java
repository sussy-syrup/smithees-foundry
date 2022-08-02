package com.sussysyrup.smitheesfoundry.impl.block;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.block.ApiPartBenchRegistry;
import com.sussysyrup.smitheesfoundry.api.itemgroup.ItemGroups;
import com.sussysyrup.smitheesfoundry.blocks.PartBenchBlock;
import com.sussysyrup.smitheesfoundry.blocks.entity.PartBenchBlockEntity;
import com.sussysyrup.smitheesfoundry.items.PartBenchBlockItem;
import com.sussysyrup.smitheesfoundry.util.Util;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class ImplPartBenchRegistry implements ApiPartBenchRegistry {

    private HashMap<Identifier, JsonObject> reloadRecipes = new HashMap<>();
    private static HashMap<Identifier, JsonObject> recipes = new HashMap<>();

    private HashMap<Identifier,Identifier> reloadPartBenchWoodMap = new HashMap<>();
    private static HashMap<Identifier,Identifier> partBenchWoodMap = new HashMap<>();

    private Set<Block> reloadPartBenchBlocks = new HashSet<>();
    private static Set<Block> partBenchBlocks = new HashSet<>();

    public static BlockEntityType<PartBenchBlockEntity> PART_BENCH_BLOCK_ENTITY;

    @Override
    public HashMap<Identifier, Identifier> getPartBenchWoodMap()
    {
        return reloadPartBenchWoodMap;
    }

    @Override
    public void registerPartBenchWood(Identifier id, Identifier wood)
    {
        partBenchWoodMap.put(id, wood);
    }

    @Override
    public void removePartBenchWood(Identifier id)
    {
        partBenchWoodMap.remove(id);
    }

    @Override
    public void clearPartBenchWoods()
    {
        partBenchWoodMap.clear();
    }

    @Override
    public Identifier getPartBenchWood(Identifier id)
    {
        return reloadPartBenchWoodMap.get(id);
    }

    @Override
    public boolean containsPartBenchWood(Identifier id)
    {
        return partBenchWoodMap.containsKey(id);
    }

    @Override
    public boolean containsReloadPartBenchWood(Identifier id)
    {
        return reloadPartBenchWoodMap.containsKey(id);
    }

    @Override
    public HashMap<Identifier, JsonObject> getRecipes()
    {
        return reloadRecipes;
    }

    @Override
    public JsonObject getRecipe(Identifier id)
    {
        return reloadRecipes.get(id);
    }

    @Override
    public Set<Block> getPartBenchWoodBlocks() {
        return reloadPartBenchBlocks;
    }

    @Override
    public void reload()
    {
        Block block;
        for(Identifier id :partBenchWoodMap.keySet())
        {
            Identifier wood = partBenchWoodMap.get(id);

            block = new PartBenchBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F), wood.getPath());
            Registry.register(Registry.BLOCK, id, block);
            Registry.register(Registry.ITEM, id, new PartBenchBlockItem(block, new FabricItemSettings().group(ItemGroups.BLOCK_GROUP), wood.getPath()));
            partBenchBlocks.add(block);

            recipes.put(id, Util.createShapedRecipeJson(
                    Lists.newArrayList(
                            'L', 'I'), Lists.newArrayList(new Identifier(wood.getNamespace(), wood.getPath() + "_log"), new Identifier(wood.getNamespace(), wood.getPath() + "_planks")),

                    Lists.newArrayList("item", "item"),
                    Lists.newArrayList("LLL",
                            "I I",
                            "I I"
                    ),
                    id
            ));
        }

        reloadPartBenchWoodMap.putAll(partBenchWoodMap);
        reloadPartBenchBlocks.addAll(partBenchBlocks);
        reloadRecipes.putAll(recipes);
    }

    @Override
    public void postReload()
    {
        PART_BENCH_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "part_bench"), FabricBlockEntityTypeBuilder.create(PartBenchBlockEntity::new, reloadPartBenchBlocks.stream().toArray(Block[]::new)).build());
    }

}
