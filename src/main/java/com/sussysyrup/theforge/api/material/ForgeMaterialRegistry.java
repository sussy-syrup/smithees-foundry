package com.sussysyrup.theforge.api.material;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForgeMaterialRegistry {

    private static Map<String, Material> registry = new HashMap<>();

    public static void registerMaterial(String id, Material material)
    {
            registry.put(id, material);
    }

    public static List<String> getKeys()
    {
        return registry.keySet().stream().toList();
    }

    public static List<Material> getMaterials()
    {
        return registry.values().stream().toList();
    }

    public static void removeMaterial(String id)
    {
        registry.remove(id.toString());
    }

    public static Material getMaterial(String id) {
        return registry.get(id);
    }

    @Environment(EnvType.CLIENT)
    public static void registerColours(String materialKey, Color first, Color second, Color third, Color fourth, Color fifth, Color sixth, Color seventh)
    {
        Material material = ForgeMaterialRegistry.getMaterial(materialKey);

        material.setColours(first, second, third, fourth, fifth, sixth, seventh);
    }

    public static HashMap<String, MaterialResource> materialResourceMap = new HashMap<>();

    public static MaterialResource getMaterialResource(String item)
    {
        return materialResourceMap.get(item);
    }

    public static void registerMaterialResource(String item, MaterialResource partRecipe)
    {
        materialResourceMap.put(item, partRecipe);
    }

    private static HashMap<TagKey<Item>, MaterialResource> tagKeyMaterialResourceMap = new HashMap<>();

    public static void registerPreMaterialResource(TagKey<Item> tagKey, MaterialResource recipe)
    {
        tagKeyMaterialResourceMap.put(tagKey, recipe);
    }

    public static void removePreMaterialResource(TagKey<Item> tagKey)
    {
        tagKeyMaterialResourceMap.remove(tagKey);
    }

    public static HashMap<TagKey<Item>, MaterialResource> getPreMaterialResourceMap()
    {
        return tagKeyMaterialResourceMap;
    }

    public static MaterialResource getPreMaterialResource(TagKey<Item> tagKey)
    {
        return tagKeyMaterialResourceMap.get(tagKey);
    }

}
