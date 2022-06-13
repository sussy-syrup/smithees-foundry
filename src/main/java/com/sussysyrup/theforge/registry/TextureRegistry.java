package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.client.texture.ForgeGrayTextureRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TextureRegistry {

    public static void init()
    {
        registerPart("pickaxehead");
        registerPart("toolhandle");
        registerPart("toolbinding");
        registerPart("axehead");
        registerPart("hoehead");
        registerPart("shovelhead");
        registerPart("swordblade");
        registerPart("swordguard");

        registerRenderPart("head", "pickaxe");
        registerRenderPart("handle", "pickaxe");
        registerRenderPart("binding", "pickaxe");

        registerRenderPart("head", "axe");
        registerRenderPart("handle", "axe");
        registerRenderPart("binding", "axe");

        registerRenderPart("head", "hoe");
        registerRenderPart("handle", "hoe");
        registerRenderPart("binding", "hoe");

        registerRenderPart("head", "shovel");
        registerRenderPart("handle", "shovel");
        registerRenderPart("binding", "shovel");

        registerRenderPart("blade", "sword");
        registerRenderPart("handle", "sword");
        registerRenderPart("guard", "sword");
    }

    private static void registerPart(String name)
    {
        ForgeGrayTextureRegistry.registerItemTexture(name, new Identifier(Main.MODID, "textures/item/" + name + ".png"));
    }

    private static void registerRenderPart(String name, String tool)
    {
        ForgeGrayTextureRegistry.registerItemTexture(tool + "_" + name, new Identifier(Main.MODID, "textures/tool/" + tool + "/" + name + ".png"));
    }
}
