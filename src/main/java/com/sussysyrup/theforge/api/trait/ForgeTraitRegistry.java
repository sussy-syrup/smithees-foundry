package com.sussysyrup.theforge.api.trait;

import java.util.HashMap;

public class ForgeTraitRegistry {

    private static HashMap<String, TraitContainer> nameTraitMap = new HashMap<>();

    public static void registerTrait(String name, TraitContainer trait)
    {

        nameTraitMap.put(name, trait);
    }

    public static TraitContainer getTrait(String name)
    {
        return nameTraitMap.get(name);
    }

}
