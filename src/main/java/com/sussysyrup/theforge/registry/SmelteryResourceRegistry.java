package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.fluid.ForgeSmelteryResourceRegistry;
import com.sussysyrup.theforge.api.fluid.SmelteryResource;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

//TODO DO UP THE METALS
public class SmelteryResourceRegistry {

    private static final SmelteryResource COPPER_2 = new SmelteryResource("molten_copper", 2 * FluidConstants.INGOT);
    private static final TagKey<Item> COPPER_2_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/copper_2_smelting"));

    private static final SmelteryResource COPPER_1 = new SmelteryResource("molten_copper", 1 * FluidConstants.INGOT);
    private static final TagKey<Item> COPPER_1_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/copper_1_smelting"));

    private static final SmelteryResource COPPER_9 = new SmelteryResource("molten_copper", 9 * FluidConstants.INGOT);
    private static final TagKey<Item> COPPER_9_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/copper_9_smelting"));

    public static void init()
    {
        ForgeSmelteryResourceRegistry.addPreSmelteryResource(COPPER_2_SMELTING, COPPER_2);
        ForgeSmelteryResourceRegistry.addPreSmelteryResource(COPPER_1_SMELTING, COPPER_1);
        ForgeSmelteryResourceRegistry.addPreSmelteryResource(COPPER_9_SMELTING, COPPER_9);
    }
}
