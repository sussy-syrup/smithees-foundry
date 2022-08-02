package com.sussysyrup.smitheesfoundry.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.sussysyrup.smitheesfoundry.api.block.ApiPartBenchRegistry;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(LootManager.class)
public class LootManagerMixin {

    //Injecting custom loot tables here based on runtime datagen. This is done before the usual processing and hence should be overrideable
    @Inject(method = "apply", at = @At(value = "INVOKE", target = "Ljava/util/Map;remove(Ljava/lang/Object;)Ljava/lang/Object;"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void apply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo callbackInfo, ImmutableMap.Builder builderIn) {
        HashMap<Identifier, Identifier> poolIDMap = new HashMap<>();
        List<Identifier> list = ApiPartBenchRegistry.getInstance().getPartBenchWoodMap().keySet().stream().toList();
        list.forEach(identifier -> poolIDMap.put(new Identifier(identifier.getNamespace(), "blocks/" + identifier.getPath()), identifier));

        FabricLootPoolBuilder poolBuilder;

        LootTable.Builder builder;

        for (Identifier id : poolIDMap.keySet()) {
            builder = LootTable.builder();

            poolBuilder = FabricLootPoolBuilder.builder()
                    .rolls(ConstantLootNumberProvider.create(1)).with(ItemEntry.builder(Registry.ITEM.get(poolIDMap.get(id))));

            builder.pool(poolBuilder);

            LootTable lootTable = builder.build();

            builderIn.put(id, lootTable);
        }
    }
}


