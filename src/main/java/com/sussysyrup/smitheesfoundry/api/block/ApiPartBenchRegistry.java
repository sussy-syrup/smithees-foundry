package com.sussysyrup.smitheesfoundry.api.block;

import com.google.gson.JsonObject;
import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Set;

public interface ApiPartBenchRegistry {

    /**
     * returns currently active instance
     * @return
     */
    static ApiPartBenchRegistry getInstance() {
        return RegistryInstances.partBenchRegistry;
    }

    HashMap<Identifier, Identifier> getPartBenchWoodMap();

    /**
     * Really the only method you will every need to use here
     * @param id
     * @param wood
     */
    void registerPartBenchWood(Identifier id, Identifier wood);

    void removePartBenchWood(Identifier id);

    void clearPartBenchWoods();

    Identifier getPartBenchWood(Identifier id);

    boolean containsPartBenchWood(Identifier id);

    boolean containsReloadPartBenchWood(Identifier id);

    HashMap<Identifier, JsonObject> getRecipes();

    JsonObject getRecipe(Identifier id);

    Set<Block> getPartBenchWoodBlocks();

    void reload();

    void postReload();
}
