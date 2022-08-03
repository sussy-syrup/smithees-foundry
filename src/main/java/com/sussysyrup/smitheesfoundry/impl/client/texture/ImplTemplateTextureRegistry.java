package com.sussysyrup.smitheesfoundry.impl.client.texture;

import com.sussysyrup.smitheesfoundry.api.client.texture.ApiTemplateTextureRegistry;
import com.sussysyrup.smitheesfoundry.util.ClientUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.awt.image.BufferedImage;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ImplTemplateTextureRegistry implements ApiTemplateTextureRegistry {

    private HashMap<String, BufferedImage> reloadTextureMap = new HashMap<>();
    private static HashMap<String, BufferedImage> textureMap = new HashMap<>();

    public boolean preTextureMapContains(String name)
    {
        return textureMap.containsKey(name);
    }

    public BufferedImage getTexture(String name)
    {
        return ClientUtil.copyImage(reloadTextureMap.get(name));
    }

    public void registerTexture(String name, Identifier resourceID)
    {
        textureMap.put(name, ClientUtil.getImageFromID(resourceID));
    }

    public void removeTexture(String name)
    {
        textureMap.remove(name);
    }

    @Override
    public void clearTextures() {
        textureMap.clear();
    }

    @Override
    public void reload() {
        reloadTextureMap.putAll(textureMap);
    }
}
