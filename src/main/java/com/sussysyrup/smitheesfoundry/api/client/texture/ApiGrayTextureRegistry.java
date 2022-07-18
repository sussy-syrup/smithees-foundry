package com.sussysyrup.smitheesfoundry.api.client.texture;

import com.sussysyrup.smitheesfoundry.util.ClientUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.awt.image.BufferedImage;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ApiGrayTextureRegistry {

    private static HashMap<String, BufferedImage> textureMap = new HashMap<>();

    public static boolean textureMapContains(String name)
    {
        return textureMap.containsKey(name);
    }

    public static BufferedImage getTexture(String name)
    {
        return ClientUtil.copyImage(textureMap.get(name));
    }

    public static void registerTexture(String name, Identifier resourceID)
    {
        textureMap.put(name, ClientUtil.getImageFromID(resourceID));
    }

    public static void removeTexture(String name)
    {
        textureMap.remove(name);
    }
}
