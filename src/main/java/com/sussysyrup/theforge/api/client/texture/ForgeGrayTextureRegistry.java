package com.sussysyrup.theforge.api.client.texture;

import com.sussysyrup.theforge.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.awt.image.BufferedImage;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ForgeGrayTextureRegistry {

    private static HashMap<String, BufferedImage> textureMap = new HashMap<>();

    public static boolean textureMapContains(String name)
    {
        return textureMap.containsKey(name);
    }

    public static BufferedImage getTexture(String name)
    {
        return Util.copyImage(textureMap.get(name));
    }

    public static void registerTexture(String name, Identifier resourceID)
    {
        textureMap.put(name, Util.getImageFromID(resourceID));
    }

    public static void removeTexture(String name)
    {
        textureMap.remove(name);
    }
}
