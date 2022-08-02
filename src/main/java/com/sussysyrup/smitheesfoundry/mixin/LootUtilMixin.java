package com.sussysyrup.smitheesfoundry.mixin;

import com.sussysyrup.smitheesfoundry.api.block.ApiPartBenchRegistry;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.fabricmc.fabric.impl.loot.LootUtil;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//This mod depends on fabric api so this compat is necessary
@Mixin(LootUtil.class)
public class LootUtilMixin {

    @Inject(method = "determineSource", at = @At("HEAD"), cancellable = true)
    private static void determineSource(Identifier packSource, ResourceManager resource, CallbackInfoReturnable<LootTableSource> cir)
    {
        if(!packSource.getNamespace().equals("smitheesfoundry"))
        {
            return;
        }
        if(ApiPartBenchRegistry.getInstance().getPartBenchWoodMap().keySet().contains(new Identifier(packSource.getNamespace(), packSource.getPath().replaceAll("blocks/", ""))))
        {
            cir.setReturnValue(LootTableSource.MOD);
        }
    }
}
