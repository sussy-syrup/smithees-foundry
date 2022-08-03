package com.sussysyrup.smitheesfoundry.impl.client.block;

import com.sussysyrup.smitheesfoundry.client.model.provider.PartBenchVariantProvider;
import com.sussysyrup.smitheesfoundry.client.render.PartBenchEntityRender;
import com.sussysyrup.smitheesfoundry.impl.block.ImplPartBenchRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ImplPartBenchRegistryClient {
    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> new PartBenchVariantProvider());

        BlockEntityRendererRegistry.INSTANCE.register(ImplPartBenchRegistry.PART_BENCH_BLOCK_ENTITY, PartBenchEntityRender::new);
    }
}
