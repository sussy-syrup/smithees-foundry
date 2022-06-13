package com.sussysyrup.theforge.mixin.client;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.client.texture.ForgeGrayTextureRegistry;
import com.sussysyrup.theforge.api.material.ForgeMaterialRegistry;
import com.sussysyrup.theforge.util.Util;
import net.minecraft.resource.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;

@Mixin(NamespaceResourceManager.class)
public abstract class NamespaceResourceManagerMixin {

    @Shadow
    protected abstract void shadow$validate(Identifier id);

    @Shadow
    static @Nullable Identifier shadow$getMetadataPath(Identifier id) {
        return null;
    }

    @Shadow
    protected abstract InputStream shadow$open(Identifier id, ResourcePack pack);

    @Final
    @Shadow
    private ResourceType type;

    @Shadow
    List<ResourcePack> packList;

    @Inject(method = "getResource", at = @At("HEAD"), cancellable = true)
     private void getResource(Identifier identifier, CallbackInfoReturnable<Resource> cir)
    {

        if(!identifier.getNamespace().equals(Main.MODID)) return;

        /**
         * This ugly block of vanilla code ensures that parts and items with a texture are loaded normally before moving to texture-gen
         * to use existing textures used in datagen you can just pop them into forge:textures/item and name them something like partrender or partitem followed by _material_ 
         * and finally the part type
         */
        shadow$validate(identifier);
        ResourcePack resourcePack = null;
        Identifier identifier2 = this.shadow$getMetadataPath(identifier);
        for (int i = this.packList.size() - 1; i >= 0; --i) {
            ResourcePack resourcePack2 = this.packList.get(i);
            if (resourcePack == null && resourcePack2.contains(this.type, identifier2)) {
                resourcePack = resourcePack2;
            }
            if (!resourcePack2.contains(this.type, identifier)) continue;
            InputStream inputStream = null;
            if (resourcePack != null) {
                inputStream = this.shadow$open(identifier2, resourcePack);
            }
            cir.setReturnValue(new ResourceImpl(resourcePack2.getName(), identifier, this.shadow$open(identifier, resourcePack2), inputStream));
            return;
        }

        String[] string_parts = identifier.getPath().split("/");

        if(!string_parts[0].equals("textures")) return;
        if(!string_parts[1].equals("item")) return;

        String[] parsing = string_parts[2].split("_");

        if(parsing[0].equals("partitem"))
        {
            String material = parsing[1];
            String toolPart = parsing[2];

            Identifier correctResourceLocation = new Identifier(Main.MODID, string_parts[0] + "/" + string_parts[1] + "/" + toolPart);

            BufferedImage gray = ForgeGrayTextureRegistry.getItemTexture(toolPart.replaceAll(".png", ""));

            cir.setReturnValue(new ResourceImpl(Main.MODID, correctResourceLocation, Util.colourise(gray, ForgeMaterialRegistry.getMaterial(material)),null));
            return;
        }

        if(parsing[0].equals("partrender"))
        {
            String material = parsing[1];
            String toolType = parsing[2];
            String toolPart = parsing[3];

            Identifier correctResourceLocation = new Identifier(Main.MODID, string_parts[0] + "/" + "tool" + "/" + toolType + "/" + toolPart);

            BufferedImage gray = ForgeGrayTextureRegistry.getItemTexture(toolType + "_" + toolPart.replaceAll(".png", ""));

            BufferedInputStream outputStream = Util.colourise(gray, ForgeMaterialRegistry.getMaterial(material));

            cir.setReturnValue(new ResourceImpl(Main.MODID, correctResourceLocation, outputStream,null));
            return;
        }

        return;
    }

}
