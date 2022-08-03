package com.sussysyrup.smitheesfoundry.impl.client.model;

import com.sussysyrup.smitheesfoundry.api.client.model.ApiToolTypeModelRegistry;
import com.sussysyrup.smitheesfoundry.client.model.toolmodels.IToolTypeModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ImplToolTypeModelRegistry implements ApiToolTypeModelRegistry {

    private HashMap<String, IToolTypeModel> reloadToolTypeMap = new HashMap<>();
    private static HashMap<String, IToolTypeModel> toolTypeMap = new HashMap<>();

    @Override
    public void addToolTypeModel(String key, IToolTypeModel model)
    {
        toolTypeMap.put(key, model);
    }

    @Override
    public void removeToolTypeModel(String key)
    {
        toolTypeMap.remove(key);
    }

    @Override
    public void clearToolTypeModels() {
        toolTypeMap.clear();
    }

    @Override
    public IToolTypeModel getToolTypeModel(String key)
    {
        return reloadToolTypeMap.get(key);
    }

    @Override
    public void reload() {
        reloadToolTypeMap.putAll(toolTypeMap);
    }
}
