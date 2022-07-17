package com.sussysyrup.smitheesfoundry.api.block;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.itemgroup.ItemGroups;
import com.sussysyrup.smitheesfoundry.blocks.PartBenchBlock;
import com.sussysyrup.smitheesfoundry.blocks.entity.PartBenchBlockEntity;
import com.sussysyrup.smitheesfoundry.client.model.provider.PartBenchVariantProvider;
import com.sussysyrup.smitheesfoundry.client.render.PartBenchEntityRender;
import com.sussysyrup.smitheesfoundry.items.PartBenchBlockItem;
import com.sussysyrup.smitheesfoundry.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApiPartBenchRegistry {

    private static HashMap<Identifier,JsonObject> recipes = new HashMap<>();

    public static BlockEntityType<PartBenchBlockEntity> PART_BENCH_BLOCK_ENTITY;

    private static HashMap<Identifier,Identifier> partBenchWoodMap = new HashMap<>();

    private static List<Block> partBenchBlocks = new ArrayList<>();

    public static void main()
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

        PART_BENCH_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "part_bench"), FabricBlockEntityTypeBuilder.create(PartBenchBlockEntity::new, partBenchBlocks.stream().toArray(Block[]::new)).build());
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> new PartBenchVariantProvider());

        BlockEntityRendererRegistry.INSTANCE.register(PART_BENCH_BLOCK_ENTITY, PartBenchEntityRender::new);
    }

    public static HashMap<Identifier, Identifier> getPartBenchWoodMap()
    {
        return partBenchWoodMap;
    }

    public static void registerPartBenchWood(Identifier id, Identifier wood)
    {
        partBenchWoodMap.put(id, wood);
    }

    public static void removePartBenchWood(Identifier id)
    {
        partBenchWoodMap.remove(id);
    }

    public static Identifier getPartBenchWood(Identifier id)
    {
        return partBenchWoodMap.get(id);
    }

    public static boolean containsPartBenchWood(Identifier id)
    {
        return partBenchWoodMap.containsKey(id);
    }

    public static HashMap<Identifier, JsonObject> getRecipes()
    {
        return recipes;
    }

    public static JsonObject getRecipe(Identifier id)
    {
        return recipes.get(id);
    }
}
