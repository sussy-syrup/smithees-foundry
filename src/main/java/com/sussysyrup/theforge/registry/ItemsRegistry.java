package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.item.CastItem;
import com.sussysyrup.theforge.api.casting.ForgeCastingRegistry;
import com.sussysyrup.theforge.api.item.ForgeToolRegistry;
import com.sussysyrup.theforge.api.itemgroup.ItemGroups;
import com.sussysyrup.theforge.api.client.model.ForgeToolTypeModelRegistry;
import com.sussysyrup.theforge.api.recipe.ForgeToolRecipeRegistry;
import com.sussysyrup.theforge.client.model.toolmodels.*;
import com.sussysyrup.theforge.client.render.TankItemRenderer;
import com.sussysyrup.theforge.items.*;
import com.sussysyrup.theforge.recipe.ThreePartToolRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemsRegistry {

    public static final TagKey<Block> SWORD_MINEABLE = TagKey.of(Registry.BLOCK_KEY, new Identifier("c", "mineable/sword"));

    public static Item FORGE_PICKAXE = new PickaxeToolItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP).maxCount(1), "pickaxe", BlockTags.PICKAXE_MINEABLE);
    public static Item FORGE_AXE = new AxeToolItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP).maxCount(1), "axe", BlockTags.AXE_MINEABLE);
    public static Item FORGE_HOE = new HoeToolItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP).maxCount(1), "hoe", BlockTags.HOE_MINEABLE);
    public static Item FORGE_SHOVEL = new ShovelToolItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP).maxCount(1), "shovel", BlockTags.SHOVEL_MINEABLE);
    public static Item FORGE_SWORD = new SwordToolItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP).maxCount(1), "sword", SWORD_MINEABLE);

    public static Item CRUDE_CHISEL = new Item(new FabricItemSettings().group(ItemGroups.ITEM_GROUP).maxCount(1).maxDamage(128));

    public static Item COMPOUND_CLAY = new Item(new FabricItemSettings().group(ItemGroups.ITEM_GROUP));
    public static Item REINFORCED_BRICK = new Item(new FabricItemSettings().group(ItemGroups.ITEM_GROUP));

    public static CastItem BLANK_CAST = new CastItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP), "blank");
    public static CastItem INGOT_CAST = new CastItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP), "ingot");

    public static void init()
    {
        register("crude_chisel", CRUDE_CHISEL);

        register("compound_clay", COMPOUND_CLAY);
        register("reinforced_brick", REINFORCED_BRICK);

        ForgeToolRegistry.registerTool("pickaxe", FORGE_PICKAXE);
        ForgeToolRegistry.addPreToolRenderedPart("pickaxe_head");
        ForgeToolRegistry.addPreToolRenderedPart("pickaxe_binding");
        ForgeToolRegistry.addPreToolRenderedPart("pickaxe_handle");
        ForgeToolRecipeRegistry.register("pickaxe", ForgeToolRecipeRegistry.createKey("toolhandle", "toolbinding", "pickaxehead", "empty", "empty"), new ThreePartToolRecipe(FORGE_PICKAXE));

        ForgeToolRegistry.registerTool("axe", FORGE_AXE);
        ForgeToolRegistry.addPreToolRenderedPart("axe_head");
        ForgeToolRegistry.addPreToolRenderedPart("axe_binding");
        ForgeToolRegistry.addPreToolRenderedPart("axe_handle");
        ForgeToolRecipeRegistry.register("axe", ForgeToolRecipeRegistry.createKey("toolhandle", "toolbinding", "axehead", "empty", "empty"), new ThreePartToolRecipe(FORGE_AXE));

        ForgeToolRegistry.registerTool("hoe", FORGE_HOE);
        ForgeToolRegistry.addPreToolRenderedPart("hoe_head");
        ForgeToolRegistry.addPreToolRenderedPart("hoe_binding");
        ForgeToolRegistry.addPreToolRenderedPart("hoe_handle");
        ForgeToolRecipeRegistry.register("hoe", ForgeToolRecipeRegistry.createKey("toolhandle", "toolbinding", "hoehead", "empty", "empty"), new ThreePartToolRecipe(FORGE_HOE));

        ForgeToolRegistry.registerTool("shovel", FORGE_SHOVEL);
        ForgeToolRegistry.addPreToolRenderedPart("shovel_head");
        ForgeToolRegistry.addPreToolRenderedPart("shovel_binding");
        ForgeToolRegistry.addPreToolRenderedPart("shovel_handle");
        ForgeToolRecipeRegistry.register("shovel", ForgeToolRecipeRegistry.createKey("toolhandle", "toolbinding", "shovelhead", "empty", "empty"), new ThreePartToolRecipe(FORGE_SHOVEL));

        ForgeToolRegistry.registerTool("sword", FORGE_SWORD);
        ForgeToolRegistry.addPreToolRenderedPart("sword_blade");
        ForgeToolRegistry.addPreToolRenderedPart("sword_guard");
        ForgeToolRegistry.addPreToolRenderedPart("sword_handle");
        ForgeToolRecipeRegistry.register("sword", ForgeToolRecipeRegistry.createKey("toolhandle", "swordguard", "swordblade", "empty", "empty"), new ThreePartToolRecipe(FORGE_SWORD));
        ForgeToolRegistry.addSweepWeapon("sword");

        register("blank_cast", BLANK_CAST);
        ForgeCastingRegistry.addCastItem("blank", BLANK_CAST);

        register("ingot_cast", INGOT_CAST);
        ForgeCastingRegistry.addCastItem("ingot", INGOT_CAST);
    }

    private static void register(String name, Item item)
    {
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, name), item);
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        ForgeToolTypeModelRegistry.addToolTypeModel("pickaxe", new PickaxeToolTypeModel());
        ForgeToolTypeModelRegistry.addToolTypeModel("axe", new AxeToolTypeModel());
        ForgeToolTypeModelRegistry.addToolTypeModel("hoe", new HoeToolTypeModel());
        ForgeToolTypeModelRegistry.addToolTypeModel("shovel", new ShovelToolTypeModel());
        ForgeToolTypeModelRegistry.addToolTypeModel("sword", new SwordToolTypeModel());

        BuiltinItemRendererRegistry.INSTANCE.register(Registry.ITEM.get(new Identifier(Main.MODID, "tank_block")), new TankItemRenderer());
    }
}
