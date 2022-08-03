package com.sussysyrup.smitheesfoundry.impl.client.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.impl.client.block.ImplPartBenchRegistryClient;
import com.sussysyrup.smitheesfoundry.impl.client.fluid.ImplMoltenFluidRegistryClient;
import com.sussysyrup.smitheesfoundry.impl.client.item.ImplPartItemClient;
import com.sussysyrup.smitheesfoundry.impl.client.item.ImplToolRegistryClient;
import com.sussysyrup.smitheesfoundry.impl.client.model.ImplToolTypeModelRegistry;
import com.sussysyrup.smitheesfoundry.impl.client.texture.ImplTemplateTextureRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ClientRegistryInstances {

    public static ImplToolTypeModelRegistry toolTypeModelRegistry;
    public static ImplTemplateTextureRegistry templateTextureRegistry;

    public static void registerReloader()
    {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public void reload(ResourceManager manager) {
                reloadResources();
            }

            @Override
            public Identifier getFabricId() {
                return new Identifier(Main.MODID, "resource_reload");
            }
        });
    }

    public static void flushAndCreate()
    {
        toolTypeModelRegistry = new ImplToolTypeModelRegistry();
        templateTextureRegistry = new ImplTemplateTextureRegistry();
    }

    public static void preReload()
    {
        ImplPartBenchRegistryClient.clientInit();
        ImplMoltenFluidRegistryClient.clientInit();
        ImplPartItemClient.clientInit();
        ImplToolRegistryClient.itemRenderingInit();

        templateTextureRegistry = new ImplTemplateTextureRegistry();
        templateTextureRegistry.reload();
    }

    public static void reloadResources()
    {
        toolTypeModelRegistry = new ImplToolTypeModelRegistry();
        toolTypeModelRegistry.reload();
    }
}
