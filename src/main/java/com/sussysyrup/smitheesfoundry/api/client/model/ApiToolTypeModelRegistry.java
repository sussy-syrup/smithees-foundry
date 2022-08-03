package com.sussysyrup.smitheesfoundry.api.client.model;

import com.sussysyrup.smitheesfoundry.client.model.toolmodels.IToolTypeModel;
import com.sussysyrup.smitheesfoundry.impl.client.registryInstances.ClientRegistryInstances;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public interface ApiToolTypeModelRegistry {

    static ApiToolTypeModelRegistry getInstance()
    {
        return ClientRegistryInstances.toolTypeModelRegistry;
    }

    void addToolTypeModel(String key, IToolTypeModel model);

    void removeToolTypeModel(String key);

    void clearToolTypeModels();

    IToolTypeModel getToolTypeModel(String key);

    void reload();
}
