package com.sussysyrup.smitheesfoundry.client.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.client.model.ApiToolTypeModelRegistry;
import com.sussysyrup.smitheesfoundry.client.model.toolmodels.*;
import com.sussysyrup.smitheesfoundry.client.render.TankItemRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class ItemsRegistryClient {
    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        ApiToolTypeModelRegistry.getInstance().addToolTypeModel("pickaxe", new PickaxeToolTypeModel());
        ApiToolTypeModelRegistry.getInstance().addToolTypeModel("axe", new AxeToolTypeModel());
        ApiToolTypeModelRegistry.getInstance().addToolTypeModel("hoe", new HoeToolTypeModel());
        ApiToolTypeModelRegistry.getInstance().addToolTypeModel("shovel", new ShovelToolTypeModel());
        ApiToolTypeModelRegistry.getInstance().addToolTypeModel("sword", new SwordToolTypeModel());

        BuiltinItemRendererRegistry.INSTANCE.register(Registry.ITEM.get(new Identifier(Main.MODID, "tank_block")), new TankItemRenderer());
    }
}
