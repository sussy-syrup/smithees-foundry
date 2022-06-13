package com.sussysyrup.theforge.api.client.texture;

import com.sussysyrup.theforge.util.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.awt.image.BufferedImage;
import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class ForgeGrayTextureRegistry {

    private static HashMap<String, BufferedImage> itemTextureMap = new HashMap<>();

    public static boolean itemTextureMapContains(String name)
    {
        return itemTextureMap.containsKey(name);
    }

    public static BufferedImage getItemTexture(String name)
    {
        return Util.copyImage(itemTextureMap.get(name));
    }

    public static void registerItemTexture(String name, Identifier resourceID)
    {
        itemTextureMap.put(name, Util.getImageFromID(resourceID));
    }

    public static void removeItemTexture(String name)
    {
        itemTextureMap.remove(name);
    }
}
