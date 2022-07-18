package com.sussysyrup.smitheesfoundry.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static Path resourcePath;

    public static void setResourcePath(Path path)
    {
        resourcePath = path;
    }

    public static String materialTranslationKey(Material material)
    {
        return materialTranslationkey(material.getName());
    }

    public static String materialTranslationkey(String materialName)
    {
        return "material." + Main.MODID + "." + materialName;
    }

    public static String materialAdjTranslationkey(String material) {
        return "material." + Main.MODID + "." + material + ".adj";
    }

    public static String woodTranslationKey(String wood) {
        return "wood." + Main.MODID + "." + wood;
    }

    public static JsonObject createShapedRecipeJson(ArrayList<Character> keys, ArrayList<Identifier> items, ArrayList<String> type, ArrayList<String> pattern, Identifier output) {
        JsonObject json = new JsonObject();

        json.addProperty("type", "minecraft:crafting_shaped");

        JsonArray jsonArray = new JsonArray();
        jsonArray.add(pattern.get(0));
        jsonArray.add(pattern.get(1));
        jsonArray.add(pattern.get(2));
        json.add("pattern", jsonArray);

        JsonObject individualKey;
        JsonObject keyList = new JsonObject();

        for (int i = 0; i < keys.size(); ++i) {
            individualKey = new JsonObject();
            individualKey.addProperty(type.get(i), items.get(i).toString());
            keyList.add(keys.get(i) + "", individualKey);
        }

        json.add("key", keyList);

        JsonObject result = new JsonObject();
        result.addProperty("item", output.toString());
        result.addProperty("count", 1);
        json.add("result", result);

        return json;
    }

    public static NbtList encodeStringList(List<String> list)
    {
        NbtList nbtList = new NbtList();

        for(String s: list)
        {
            nbtList.add(NbtString.of(s));
        }

        return nbtList;
    }

    /**
     * Make sure to fetch list by type 8
     * @param list
     * @return
     */
    public static List<String> decodeStringList(NbtList list)
    {
        List<String> stringList = new ArrayList<>();

        for(int i = 0; i < list.size(); i++)
        {

            stringList.add(list.getString(i));
        }

        return stringList;
    }

}

