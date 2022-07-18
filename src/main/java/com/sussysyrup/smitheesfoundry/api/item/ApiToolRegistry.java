package com.sussysyrup.smitheesfoundry.api.item;

import com.sussysyrup.smitheesfoundry.Main;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class ApiToolRegistry {

    private static List<String> tools = new ArrayList<>();

    public static List<String> getTools() {
        return tools;
    }

    private static List<String> preToolRenderedParts = new ArrayList<>();

    private static List<String> toolRenderedParts = new ArrayList<>();

    public static void registerTool(String name, Item item)
    {
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "forge_" + name), item);
        tools.add(name);
    }

    public static List<String> getPreToolRenderedParts() {
        return preToolRenderedParts;
    }

    public static List<String> getToolRenderedParts() {
        return toolRenderedParts;
    }

    public static void addPreToolRenderedPart(String id)
    {
        preToolRenderedParts.add(id);
    }

    public static void addToolRenderedParts(String id)
    {
        toolRenderedParts.add(id);
    }

    private static List<String> sweepWeapons = new ArrayList<>();

    public static List<String> getSweepWeapons()
    {
        return sweepWeapons;
    }

    public static void addSweepWeapon(String weapon)
    {
        sweepWeapons.add(weapon);
    }

    public static void removeSweepWeapon(String weapon)
    {
        sweepWeapons.remove(weapon);
    }
}
