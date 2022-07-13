package com.sussysyrup.smitheesfoundry.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.PreLaunch;
import com.sussysyrup.smitheesfoundry.api.fluid.FluidProperties;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util {

    public static Path resourcePath;

    public static void setResourcePath(Path path)
    {
        resourcePath = path;
    }

    @Environment(EnvType.CLIENT)
    public static String createPartJsonString(String type, String itemID)
    {
        return "{\n" +
                "  \"parent\": \"minecraft:item/generated" + "\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"smitheesfoundry:item/" + type + "_" + itemID + "\"\n" +
                "  }\n" +
                "}";
    }

    @Environment(EnvType.CLIENT)
    public static String createBucketJsonString(String itemID)
    {
        return "{\n" +
                "  \"parent\": \"minecraft:item/generated" + "\",\n" +
                "  \"textures\": {\n" +
                "    \"layer0\": \"smitheesfoundry:item/" +  itemID + "\"\n" +
                "  }\n" +
                "}";
    }

    @Environment(EnvType.CLIENT)
    public static String createFluidJsonString(String name)
    {
        return "{\n" +
                "  \"textures\": {\n" +
                "    \"particle\": \"smitheesfoundry:"+ "block/moltenstill_"+name +"\"\n" +
                "  }\n" +
                "}";
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

    @Environment(EnvType.CLIENT)
    public static BufferedImage getImageFromID(Identifier identifier)
    {
        return getImageFromStream(openBufferedInputStream(identifier));
    }

    @Environment(EnvType.CLIENT)
    public static BufferedImage getImageFromStream(BufferedInputStream stream)
    {
        if(stream != null)
        {
            try {
                BufferedImage image = ImageIO.read(stream);
                stream.close();
                return image;
            } catch (Exception e)
            {
                Main.LOGGER.error(e.toString());
            }
        }
        return null;
    }

    @Environment(EnvType.CLIENT)
    public static BufferedInputStream openBufferedInputStream(Identifier id)  {
        BufferedInputStream inputStream = new BufferedInputStream(PreLaunch.classLoader.getResourceAsStream(ResourceType.CLIENT_RESOURCES.getDirectory() + "/" + id.getNamespace() + "/" + id.getPath()));
        if (inputStream != null) {
            return inputStream;
        } else {
            return null;
        }
    }

    @Environment(EnvType.CLIENT)
    public static BufferedInputStream colourise(BufferedImage image, Material material)
    {
        try
        {
            Color colour;
            Map map = material.getColourMapping();

            for(int y = 0; y < image.getHeight(); y++)
            {
                for(int x = 0; x < image.getWidth(); x++)
                {
                    int pixel = image.getRGB(x,y);
                    if( (pixel>>24) == 0x00 ) {
                        continue;
                    }
                    colour = new Color(pixel);

                    if(map.containsKey(colour)) {
                        colour = (Color) map.get(colour);
                    }

                    image.setRGB(x, y, colour.getRGB());
                }
            }

            ByteArrayOutputStream imageByteStream = new ByteArrayOutputStream();
            ImageIO.write(image,"png", imageByteStream);
            InputStream imageOutputStream = new ByteArrayInputStream(imageByteStream.toByteArray());

            return new BufferedInputStream(imageOutputStream);
        }
        catch (Exception e)
        {
            Main.LOGGER.error(e.toString());
        }
        return null;
    }

    @Environment(EnvType.CLIENT)
    public static BufferedInputStream colourise(BufferedImage image, FluidProperties properties)
    {
        try
        {
            Color colour;
            Map map = properties.getColourMapping();

            for(int y = 0; y < image.getHeight(); y++)
            {
                for(int x = 0; x < image.getWidth(); x++)
                {
                    int pixel = image.getRGB(x,y);
                    if( (pixel>>24) == 0x00 ) {
                        continue;
                    }
                    colour = new Color(pixel);

                    if(map.containsKey(colour)) {
                        colour = (Color) map.get(colour);
                    }

                    image.setRGB(x, y, colour.getRGB());
                }
            }

            ByteArrayOutputStream imageByteStream = new ByteArrayOutputStream();
            ImageIO.write(image,"png", imageByteStream);
            InputStream imageOutputStream = new ByteArrayInputStream(imageByteStream.toByteArray());

            return new BufferedInputStream(imageOutputStream);
        }
        catch (Exception e)
        {
            Main.LOGGER.error(e.toString());
        }
        return null;
    }

    @Environment(EnvType.CLIENT)
    public static InputStream createStillFluidMeta()
    {
        String toStream = "{\n" +
                "  \"animation\": {\n" +
                "    \"frametime\": 2,\n" +
                "    \"frames\": [\n" +
                "      0,\n" +
                "      1,\n" +
                "      2,\n" +
                "      3,\n" +
                "      4,\n" +
                "      5,\n" +
                "      6,\n" +
                "      7,\n" +
                "      8,\n" +
                "      9,\n" +
                "      10,\n" +
                "      11,\n" +
                "      12,\n" +
                "      13,\n" +
                "      14,\n" +
                "      15,\n" +
                "      16,\n" +
                "      17,\n" +
                "      18,\n" +
                "      19,\n" +
                "      18,\n" +
                "      17,\n" +
                "      16,\n" +
                "      15,\n" +
                "      14,\n" +
                "      13,\n" +
                "      12,\n" +
                "      11,\n" +
                "      10,\n" +
                "      9,\n" +
                "      8,\n" +
                "      7,\n" +
                "      6,\n" +
                "      5,\n" +
                "      4,\n" +
                "      3,\n" +
                "      2,\n" +
                "      1\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        return new ByteArrayInputStream(toStream.getBytes());
    }

    @Environment(EnvType.CLIENT)
    public static InputStream createFlowFluidMeta()
    {
        String toStream = "{\n" +
                "  \"animation\": {\n" +
                "    \"frametime\": 3\n" +
                "  }\n" +
                "}\n";

        return new ByteArrayInputStream(toStream.getBytes());
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

    @Environment(EnvType.CLIENT)
    public static BufferedImage copyImage(BufferedImage bi)
    {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);

        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}

