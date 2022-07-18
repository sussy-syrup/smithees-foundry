package com.sussysyrup.smitheesfoundry.util;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.PreLaunch;
import com.sussysyrup.smitheesfoundry.api.fluid.FluidProperties;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ClientUtil {
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

    @Environment(EnvType.CLIENT)
    public static BufferedImage copyImage(BufferedImage bi)
    {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);

        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
