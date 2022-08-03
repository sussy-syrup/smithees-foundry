package com.sussysyrup.smitheesfoundry.impl.client.fluid;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import com.sussysyrup.smitheesfoundry.client.model.provider.FluidVariantProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class ImplMoltenFluidRegistryClient {

    public static void regClient(String fluidName)
    {
        FluidRenderHandlerRegistry.INSTANCE.register(Registry.FLUID.get(new Identifier(Main.MODID, fluidName)), Registry.FLUID.get(new Identifier(Main.MODID, "flowing_"+fluidName)
        ), new SimpleFluidRenderHandler(
                new Identifier(Main.MODID, "block/moltenstill_" + fluidName),
                new Identifier(Main.MODID, "block/moltenflow_" + fluidName)
        ));
    }

    public static void clientInit()
    {
        for(String s : ApiMoltenFluidRegistry.getFluidRegistry().keySet()) {
            regClient(s);
        }

        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> new FluidVariantProvider());
    }
}
