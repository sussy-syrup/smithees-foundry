package com.sussysyrup.theforge.mixin.client;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.material.ForgeMaterialRegistry;
import com.sussysyrup.theforge.util.Util;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceImpl;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.BufferedInputStream;
import java.io.File;
import java.nio.file.StandardCopyOption;

@Mixin(NamespaceResourceManager.class)
public class NamespaceResourceManagerMixin {

    @Inject(method = "getResource", at = @At("HEAD"), cancellable = true)
     private void getResource(Identifier identifier, CallbackInfoReturnable<Resource> cir)
    {

        if(!identifier.getNamespace().equals(Main.MODID)) return;

        String[] string_parts = identifier.getPath().split("/");

        if(!string_parts[0].equals("textures")) return;
        if(!string_parts[1].equals("item")) return;

        String[] parsing = string_parts[2].split("_");

        if(parsing[0].equals("partitem"))
        {
            String material = parsing[1];
            String toolPart = parsing[2];

            Identifier correctResourceLocation = new Identifier(Main.MODID, string_parts[0] + "/" + string_parts[1] + "/" + toolPart);

            BufferedInputStream gray = Util.openImageStream(correctResourceLocation);

            cir.setReturnValue(new ResourceImpl(Main.MODID, correctResourceLocation, Util.colourise(gray, ForgeMaterialRegistry.getMaterial(material)),null));
            return;
        }

        if(parsing[0].equals("partrender"))
        {
            String material = parsing[1];
            String toolType = parsing[2];
            String toolPart = parsing[3];

            Identifier correctResourceLocation = new Identifier(Main.MODID, string_parts[0] + "/" + "tool" + "/" + toolType + "/" + toolPart);

            BufferedInputStream gray = Util.openImageStream(correctResourceLocation);

            BufferedInputStream outputStream = Util.colourise(gray, ForgeMaterialRegistry.getMaterial(material));

            cir.setReturnValue(new ResourceImpl(Main.MODID, correctResourceLocation, outputStream,null));
            return;
        }

        return;
    }

}
