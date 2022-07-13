package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.casting.CastingResource;
import com.sussysyrup.smitheesfoundry.api.casting.ApiCastingRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class CastingRegistry {

    public static HashMap<Fluid, Item> ingotFluidMap = new HashMap<>();

    public static void preInit()
    {
        ingotFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_iron")), Items.IRON_INGOT);
        ingotFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_copper")), Items.COPPER_INGOT);
        ingotFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_netherite")), Items.NETHERITE_INGOT);
        ingotFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_gold")), Items.GOLD_INGOT);
    }

    public static void init()
    {
        ApiCastingRegistry.addCastingResource("ingot", new CastingResource(FluidConstants.INGOT, ingotFluidMap));

        for(Item item : ingotFluidMap.values())
        {
            ApiCastingRegistry.addItemToType("ingot", item);
        }
    }

}
