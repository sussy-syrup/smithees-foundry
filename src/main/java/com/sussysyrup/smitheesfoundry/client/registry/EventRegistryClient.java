package com.sussysyrup.smitheesfoundry.client.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ApiToolRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EventRegistryClient {
    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        ClientSpriteRegistryCallback.event(new Identifier("textures/atlas/blocks.png")).register((spriteAtlasTexture, registry) ->
        {
            for(String s : ApiToolRegistry.getToolRenderedParts()) {
                registry.register(new Identifier(Main.MODID, "item/partrender_" + s));
            }
            for(String s : ApiMoltenFluidRegistry.getInstance().getFluidPropertiesRegistry().keySet())
            {
                registry.register(new Identifier(Main.MODID, "block/moltenflow_" + s));
            }
        });
    }
}
