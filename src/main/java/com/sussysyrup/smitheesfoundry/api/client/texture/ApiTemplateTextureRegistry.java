package com.sussysyrup.smitheesfoundry.api.client.texture;

import com.sussysyrup.smitheesfoundry.impl.client.registry.ClientRegistryInstances;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.awt.image.BufferedImage;

@Environment(EnvType.CLIENT)
public interface ApiTemplateTextureRegistry {

    static ApiTemplateTextureRegistry getInstance()
    {
        return ClientRegistryInstances.templateTextureRegistry;
    }
    boolean preTextureMapContains(String name);

    BufferedImage getTexture(String name);

    void registerTexture(String name, Identifier resourceID);

    void removeTexture(String name);

    void clearTextures();

    void reload();
}
