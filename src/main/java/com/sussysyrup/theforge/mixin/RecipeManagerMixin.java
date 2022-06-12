package com.sussysyrup.theforge.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.block.ForgePartBenchRegistry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Inject(method = "apply", at = @At("HEAD"))
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        for(Identifier id : ForgePartBenchRegistry.getRecipes().keySet())
        {
            map.put(id, ForgePartBenchRegistry.getRecipe(id));
        }
    }
}
