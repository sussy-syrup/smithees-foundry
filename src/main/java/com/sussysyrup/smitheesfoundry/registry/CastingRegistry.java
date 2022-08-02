package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.casting.ApiBlockCastingRegistry;
import com.sussysyrup.smitheesfoundry.api.casting.ApiCastingRegistry;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class CastingRegistry {

    public static void preInit()
    {
        HashMap<Fluid, Item> ingotFluidMap = ApiCastingRegistry.getInstance().getPreIngotFluidMap();
        HashMap<Fluid, Item> nuggetFluidMap = ApiCastingRegistry.getInstance().getPreNuggetFluidMap();
        HashMap<Fluid, Item> blockFluidMap = ApiBlockCastingRegistry.getInstance().preBlockFluidMap();

        ingotFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_iron")), Items.IRON_INGOT);
        ingotFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_copper")), Items.COPPER_INGOT);
        ingotFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_netherite")), Items.NETHERITE_INGOT);
        ingotFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_gold")), Items.GOLD_INGOT);
        ingotFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_rosegold")), ItemsRegistry.ROSEGOLD_INGOT);

        ApiCastingRegistry.getInstance().setPreIngotFluidMap(ingotFluidMap);

        nuggetFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_iron")), Items.IRON_NUGGET);
        nuggetFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_gold")), Items.GOLD_NUGGET);
        nuggetFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_rosegold")), ItemsRegistry.ROSEGOLD_NUGGET);
        nuggetFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_copper")), ItemsRegistry.COPPER_NUGGET);
        nuggetFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_netherite")), ItemsRegistry.NETHERITE_NUGGET);

        ApiCastingRegistry.getInstance().setPreNuggetFluidMap(nuggetFluidMap);

        blockFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_iron")), Items.IRON_BLOCK);
        blockFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_gold")), Items.GOLD_BLOCK);
        blockFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_rosegold")), Registry.ITEM.get(new Identifier(Main.MODID, "rosegold_block")));
        blockFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_copper")), Items.COPPER_BLOCK);
        blockFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_netherite")), Items.NETHERITE_BLOCK);
        blockFluidMap.put(Registry.FLUID.get(new Identifier(Main.MODID, "molten_gold")), Items.GOLD_BLOCK);

        ApiBlockCastingRegistry.getInstance().setPreBlockFluidMap(blockFluidMap);
    }

}
