package com.sussysyrup.smitheesfoundry.api.client.model;

import com.sussysyrup.smitheesfoundry.client.model.toolmodels.IToolTypeModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ApiToolTypeModelRegistry {

    private static HashMap<String, IToolTypeModel> toolTypeMap = new HashMap<>();

    public static void addToolTypeModel(String key, IToolTypeModel model)
    {
        toolTypeMap.put(key, model);
    }

    public static void removeToolTypeModel(String key)
    {
        toolTypeMap.remove(key);
    }

    public static IToolTypeModel getToolTypeModel(String key)
    {
        return toolTypeMap.get(key);
    }
}
