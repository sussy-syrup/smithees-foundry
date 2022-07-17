package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.item.CastItem;
import com.sussysyrup.smitheesfoundry.api.casting.ApiCastingRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ApiToolRegistry;
import com.sussysyrup.smitheesfoundry.api.itemgroup.ItemGroups;
import com.sussysyrup.smitheesfoundry.api.client.model.ApiToolTypeModelRegistry;
import com.sussysyrup.smitheesfoundry.api.recipe.ApiToolRecipeRegistry;
import com.sussysyrup.smitheesfoundry.client.model.toolmodels.*;
import com.sussysyrup.smitheesfoundry.client.render.TankItemRenderer;
import com.sussysyrup.smitheesfoundry.items.*;
import com.sussysyrup.smitheesfoundry.recipe.ThreePartToolRecipe;
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
    public static Item GUIDE_BOOK_ITEM = new GuideBookItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP).maxCount(1));

    public static Item COMPOUND_CLAY = new Item(new FabricItemSettings().group(ItemGroups.ITEM_GROUP));
    public static Item REINFORCED_BRICK = new Item(new FabricItemSettings().group(ItemGroups.ITEM_GROUP));

    public static Item ROSEGOLD_INGOT = new Item((new FabricItemSettings().group(ItemGroups.ITEM_GROUP)));
    public static Item ROSEGOLD_NUGGET = new Item((new FabricItemSettings().group(ItemGroups.ITEM_GROUP)));


    public static CastItem BLANK_CAST = new CastItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP), "blank");
    public static CastItem INGOT_CAST = new CastItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP), "ingot");
    public static CastItem NUGGET_CAST = new CastItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP), "nugget");

    public static void main()
    {
        register("smithee_guide", GUIDE_BOOK_ITEM);

        register("crude_chisel", CRUDE_CHISEL);

        register("compound_clay", COMPOUND_CLAY);
        register("reinforced_brick", REINFORCED_BRICK);

        register("rosegold_ingot", ROSEGOLD_INGOT);
        register("rosegold_nugget", ROSEGOLD_NUGGET);

        ApiToolRegistry.registerTool("pickaxe", FORGE_PICKAXE);
        ApiToolRegistry.addPreToolRenderedPart("pickaxe_head");
        ApiToolRegistry.addPreToolRenderedPart("pickaxe_binding");
        ApiToolRegistry.addPreToolRenderedPart("pickaxe_handle");
        ApiToolRecipeRegistry.register("pickaxe", ApiToolRecipeRegistry.createKey("toolhandle", "toolbinding", "pickaxehead", "empty", "empty"), new ThreePartToolRecipe(FORGE_PICKAXE));

        ApiToolRegistry.registerTool("axe", FORGE_AXE);
        ApiToolRegistry.addPreToolRenderedPart("axe_head");
        ApiToolRegistry.addPreToolRenderedPart("axe_binding");
        ApiToolRegistry.addPreToolRenderedPart("axe_handle");
        ApiToolRecipeRegistry.register("axe", ApiToolRecipeRegistry.createKey("toolhandle", "toolbinding", "axehead", "empty", "empty"), new ThreePartToolRecipe(FORGE_AXE));

        ApiToolRegistry.registerTool("hoe", FORGE_HOE);
        ApiToolRegistry.addPreToolRenderedPart("hoe_head");
        ApiToolRegistry.addPreToolRenderedPart("hoe_binding");
        ApiToolRegistry.addPreToolRenderedPart("hoe_handle");
        ApiToolRecipeRegistry.register("hoe", ApiToolRecipeRegistry.createKey("toolhandle", "toolbinding", "hoehead", "empty", "empty"), new ThreePartToolRecipe(FORGE_HOE));

        ApiToolRegistry.registerTool("shovel", FORGE_SHOVEL);
        ApiToolRegistry.addPreToolRenderedPart("shovel_head");
        ApiToolRegistry.addPreToolRenderedPart("shovel_binding");
        ApiToolRegistry.addPreToolRenderedPart("shovel_handle");
        ApiToolRecipeRegistry.register("shovel", ApiToolRecipeRegistry.createKey("toolhandle", "toolbinding", "shovelhead", "empty", "empty"), new ThreePartToolRecipe(FORGE_SHOVEL));

        ApiToolRegistry.registerTool("sword", FORGE_SWORD);
        ApiToolRegistry.addPreToolRenderedPart("sword_blade");
        ApiToolRegistry.addPreToolRenderedPart("sword_guard");
        ApiToolRegistry.addPreToolRenderedPart("sword_handle");
        ApiToolRecipeRegistry.register("sword", ApiToolRecipeRegistry.createKey("toolhandle", "swordguard", "swordblade", "empty", "empty"), new ThreePartToolRecipe(FORGE_SWORD));
        ApiToolRegistry.addSweepWeapon("sword");

        register("blank_cast", BLANK_CAST);
        ApiCastingRegistry.addCastItem("blank", BLANK_CAST);

        register("ingot_cast", INGOT_CAST);
        ApiCastingRegistry.addCastItem("ingot", INGOT_CAST);

        register("nugget_cast", NUGGET_CAST);
        ApiCastingRegistry.addCastItem("nugget", NUGGET_CAST);
    }

    private static void register(String name, Item item)
    {
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, name), item);
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        ApiToolTypeModelRegistry.addToolTypeModel("pickaxe", new PickaxeToolTypeModel());
        ApiToolTypeModelRegistry.addToolTypeModel("axe", new AxeToolTypeModel());
        ApiToolTypeModelRegistry.addToolTypeModel("hoe", new HoeToolTypeModel());
        ApiToolTypeModelRegistry.addToolTypeModel("shovel", new ShovelToolTypeModel());
        ApiToolTypeModelRegistry.addToolTypeModel("sword", new SwordToolTypeModel());

        BuiltinItemRendererRegistry.INSTANCE.register(Registry.ITEM.get(new Identifier(Main.MODID, "tank_block")), new TankItemRenderer());
    }
}
