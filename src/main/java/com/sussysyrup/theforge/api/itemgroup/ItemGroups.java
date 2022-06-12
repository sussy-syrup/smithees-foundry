package com.sussysyrup.theforge.api.itemgroup;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.registry.BlocksRegistry;
import com.sussysyrup.theforge.registry.ItemsRegistry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemGroups {

    public static final ItemGroup PART_GROUP = FabricItemGroupBuilder.build(
            new Identifier(Main.MODID, "parts"),
            () -> new ItemStack(Registry.ITEM.get(new Identifier(Main.MODID, "wood_pickaxehead"))));

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(Main.MODID, "items"),
            () -> new ItemStack(ItemsRegistry.CRUDE_CHISEL));

    public static final ItemGroup BLOCK_GROUP = FabricItemGroupBuilder.build(
            new Identifier(Main.MODID, "blocks"),
            () -> new ItemStack(BlocksRegistry.FORGE_BLOCK));

}
