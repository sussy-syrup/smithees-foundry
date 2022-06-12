package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.api.item.ForgePartRegistry;

public class PartRegistry {

    public static void init()
    {
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
    }
}
