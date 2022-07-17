package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.client.texture.ApiGrayTextureRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TextureRegistry {

    public static void clientInit()
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

        ApiGrayTextureRegistry.registerTexture("molten_metal_flow", new Identifier(Main.MODID, "textures/block/molten_metal_flow.png"));
        ApiGrayTextureRegistry.registerTexture("molten_metal_still", new Identifier(Main.MODID, "textures/block/molten_metal_still.png"));
        ApiGrayTextureRegistry.registerTexture("fluidbucket", new Identifier(Main.MODID, "textures/item/fluidbucket.png"));
    }

    private static void registerPart(String name)
    {
        ApiGrayTextureRegistry.registerTexture(name, new Identifier(Main.MODID, "textures/item/" + name + ".png"));
    }

    private static void registerRenderPart(String name, String tool)
    {
        ApiGrayTextureRegistry.registerTexture(tool + "_" + name, new Identifier(Main.MODID, "textures/tool/" + tool + "/" + name + ".png"));
    }
}
