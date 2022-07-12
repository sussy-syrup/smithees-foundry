package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.casting.ForgeCastingRegistry;
import com.sussysyrup.theforge.api.item.CastItem;
import com.sussysyrup.theforge.api.item.ForgePartRegistry;
import com.sussysyrup.theforge.api.item.PartItem;
import com.sussysyrup.theforge.api.itemgroup.ItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PartRegistry {

    public static void init() {
        registerPart("pickaxehead", 2, "head");
        registerPart("toolhandle", 1, "handle");
        registerPart("toolbinding", 1, "binding");
        registerPart("axehead", 3, "head");
        registerPart("hoehead", 2, "head");
        registerPart("shovelhead", 2, "head");
        registerPart("swordblade", 2, "head");
        registerPart("swordguard", 1, "binding");
    }

    private static void registerPart(String name, int cost, String category)
    {
        ForgePartRegistry.addPrePart(name, category);
        ForgePartRegistry.registerPartCost(name, cost);

        Item cast = new CastItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP), name);

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, name + "_cast"), cast);
        ForgeCastingRegistry.addCastItem(name, (CastItem) cast);
    }
}
