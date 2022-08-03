package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiSmelteryResourceRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.SmelteryResource;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

//TODO DO UP THE METALS
public class SmelteryResourceRegistry {

    private static final SmelteryResource COPPER_2 = new SmelteryResource("molten_copper", 2 * FluidConstants.INGOT);
    private static final TagKey<Item> COPPER_2_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/copper_2"));
    private static final SmelteryResource COPPER_1 = new SmelteryResource("molten_copper", 1 * FluidConstants.INGOT);
    private static final TagKey<Item> COPPER_1_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/copper_1"));
    private static final SmelteryResource COPPER_9 = new SmelteryResource("molten_copper", 9 * FluidConstants.INGOT);
    private static final TagKey<Item> COPPER_9_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/copper_9"));

    private static final SmelteryResource GOLD_2 = new SmelteryResource("molten_gold", 2 * FluidConstants.INGOT);
    private static final TagKey<Item> GOLD_2_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/gold_2"));
    private static final SmelteryResource GOLD_1 = new SmelteryResource("molten_gold", 1 * FluidConstants.INGOT);
    private static final TagKey<Item> GOLD_1_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/gold_1"));
    private static final SmelteryResource GOLD_9 = new SmelteryResource("molten_gold", 9 * FluidConstants.INGOT);
    private static final TagKey<Item> GOLD_9_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/gold_9"));
    private static final SmelteryResource GOLD_NUG = new SmelteryResource("molten_gold", FluidConstants.NUGGET);
    private static final TagKey<Item> GOLD_NUG_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/gold_nug"));

    private static final SmelteryResource IRON_2 = new SmelteryResource("molten_iron", 2 * FluidConstants.INGOT);
    private static final TagKey<Item> IRON_2_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/iron_2"));
    private static final SmelteryResource IRON_1 = new SmelteryResource("molten_iron", 1 * FluidConstants.INGOT);
    private static final TagKey<Item> IRON_1_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/iron_1"));
    private static final SmelteryResource IRON_9 = new SmelteryResource("molten_iron", 9 * FluidConstants.INGOT);
    private static final TagKey<Item> IRON_9_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/iron_9"));

    private static final SmelteryResource NETHERITE_1 = new SmelteryResource("molten_netherite", 1 * FluidConstants.INGOT);
    private static final TagKey<Item> NETHERITE_1_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/netherite_1"));
    private static final SmelteryResource NETHERITE_9 = new SmelteryResource("molten_netherite", 9 * FluidConstants.INGOT);
    private static final TagKey<Item> NETHERITE_9_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/netherite_9"));

    private static final SmelteryResource ROSEGOLD_1 = new SmelteryResource("molten_rosegold", 1 * FluidConstants.INGOT);
    private static final TagKey<Item> ROSEGOLD_1_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/rosegold_1"));
    private static final SmelteryResource ROSEGOLD_9 = new SmelteryResource("molten_rosegold", 9 * FluidConstants.INGOT);
    private static final TagKey<Item> ROSEGOLD_9_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/rosegold_9"));
    private static final SmelteryResource ROSEGOLD_NUG = new SmelteryResource("molten_rosegold", FluidConstants.NUGGET);
    private static final TagKey<Item> ROSEGOLD_NUG_SMELTING = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "smelting/rosegold_nug"));
    public static void main()
    {
        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(COPPER_1_SMELTING, COPPER_1);
        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(COPPER_2_SMELTING, COPPER_2);
        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(COPPER_9_SMELTING, COPPER_9);

        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(GOLD_1_SMELTING, GOLD_1);
        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(GOLD_2_SMELTING, GOLD_2);
        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(GOLD_9_SMELTING, GOLD_9);
        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(GOLD_NUG_SMELTING, GOLD_NUG);

        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(IRON_1_SMELTING, IRON_1);
        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(IRON_2_SMELTING, IRON_2);
        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(IRON_9_SMELTING, IRON_9);

        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(NETHERITE_1_SMELTING, NETHERITE_1);
        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(NETHERITE_9_SMELTING, NETHERITE_9);

        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(ROSEGOLD_1_SMELTING, ROSEGOLD_1);
        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(ROSEGOLD_9_SMELTING, ROSEGOLD_9);
        ApiSmelteryResourceRegistry.getInstance().addTagSmelteryResource(ROSEGOLD_NUG_SMELTING, ROSEGOLD_NUG);
    }
}
