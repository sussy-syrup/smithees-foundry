package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.casting.ApiCastingRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ApiPartRegistry;
import com.sussysyrup.smitheesfoundry.api.item.CastItem;
import com.sussysyrup.smitheesfoundry.api.itemgroup.ItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PartRegistry {

    public static void main() {
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
        ApiPartRegistry.addPrePart(name, category);
        ApiPartRegistry.registerPartCost(name, cost);

        Item cast = new CastItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP), name);

        Registry.register(Registry.ITEM, new Identifier(Main.MODID, name + "_cast"), cast);
        ApiCastingRegistry.addCastItem(name, (CastItem) cast);
    }
}
