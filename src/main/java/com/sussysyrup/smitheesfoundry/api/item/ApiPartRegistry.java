package com.sussysyrup.smitheesfoundry.api.item;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.itemgroup.ItemGroups;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApiPartRegistry {

    public static void main()
    {
        generateParts();
    }

    private static void register(String name, Item item)
    {
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, name), item);
    }

    private static List<String> prePartNames = new ArrayList<>();
    private static List<String> prePartCategory = new ArrayList<>();

    public static void addPrePart(String partName, String category)
    {
        prePartNames.add(partName);
        prePartCategory.add(category);
    }

    public static void removePrePart(String partName)
    {
        prePartCategory.remove(prePartNames.indexOf(partName));
        prePartNames.remove(partName);
    }

    public static String getPrePartCategory(String prePartName)
    {
        return prePartCategory.get(prePartNames.indexOf(prePartName));
    }

    public static List<String> getPrePartNames()
    {
        return prePartNames;
    }

    private static List<String> customResourcesIdentifiers = new ArrayList<>();

    private static List<String> partNames = new ArrayList<>();
    private static List<String> partCategory = new ArrayList<>();

    public static List<String> getCustomResourcesIdentifiers()
    {
        return customResourcesIdentifiers;
    }

    public static List<String> getPartNames()
    {
        return partNames;
    }

    public static String getPartCategory(String partName)
    {
        return partCategory.get(partNames.indexOf(partName));
    }

    private static void generateParts()
    {
        Material material;

        for(String materialId : ApiMaterialRegistry.getKeys().stream().toList())
        {
            material = ApiMaterialRegistry.getMaterial(materialId);

            for(String partName : prePartNames)
            {
                String id = material.getName() + "_" + partName;

                Item item = new PartItem(new FabricItemSettings().maxCount(1).group(ItemGroups.PART_GROUP), partName, materialId);

                register(id, item);

                partNames.add(id);
                partCategory.add(prePartCategory.get(prePartNames.indexOf(partName)));
            }
        }
    }

    private static HashMap<String, Float> partCostMap = new HashMap<>();

    public static void registerPartCost(String part, float cost)
    {
        partCostMap.put(part, cost);
    }

    public static void removePartCost(String part)
    {
        partCostMap.remove(part);
    }

    public static Float getPartCost(String part)
    {
        return partCostMap.get(part);
    }
}
