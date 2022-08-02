package com.sussysyrup.smitheesfoundry.impl.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.impl.block.ImplAlloySmelteryRegistry;
import com.sussysyrup.smitheesfoundry.impl.block.ImplPartBenchRegistry;
import com.sussysyrup.smitheesfoundry.impl.casting.ImplBlockCastingRegistry;
import com.sussysyrup.smitheesfoundry.impl.casting.ImplCastingRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public class RegistryInstances {

    public static ImplAlloySmelteryRegistry alloySmelteryRegistry;
    public static ImplPartBenchRegistry partBenchRegistry;
    public static ImplBlockCastingRegistry blockCastingRegistry;
    public static ImplCastingRegistry castingRegistry;

    public static void flushAndCreate()
    {
        alloySmelteryRegistry = new ImplAlloySmelteryRegistry();
        partBenchRegistry = new ImplPartBenchRegistry();
        blockCastingRegistry = new ImplBlockCastingRegistry();
        castingRegistry = new ImplCastingRegistry();
    }


    public static void registerReloader()
    {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public void reload(ResourceManager manager) {
                Reloader.reload();
                reloadData();
            }

            @Override
            public Identifier getFabricId() {
                return new Identifier(Main.MODID, "data_reload");
            }
        });
    }

    /**
     * Anything that depends on vanilla instance creation goes here
     */
    public static void preReloadData()
    {
        partBenchRegistry = new ImplPartBenchRegistry();
        partBenchRegistry.reload();
        partBenchRegistry.postReload();
    }

    /**
     * Anything that can be registered outside of the vanilla environment goes here
     */
    private static void reloadData()
    {
        alloySmelteryRegistry = new ImplAlloySmelteryRegistry();
        alloySmelteryRegistry.reload();

        blockCastingRegistry = new ImplBlockCastingRegistry();
        blockCastingRegistry.reload();

        castingRegistry = new ImplCastingRegistry();
        castingRegistry.preReload();
        castingRegistry.reload();
    }
}
