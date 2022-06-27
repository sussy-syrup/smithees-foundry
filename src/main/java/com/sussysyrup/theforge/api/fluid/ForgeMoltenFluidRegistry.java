package com.sussysyrup.theforge.api.fluid;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.itemgroup.ItemGroups;
import com.sussysyrup.theforge.client.model.provider.FluidVariantProvider;
import com.sussysyrup.theforge.items.FluidBucketItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.util.HashMap;

public class ForgeMoltenFluidRegistry {

    private static HashMap<String, FluidProperties> fluidRegistry = new HashMap<>();
    private static HashMap<String, FluidProperties> fluidRegistryPost = new HashMap<>();

    private static List<Identifier> bucketIDs = new ArrayList<>();

    public static void init()
    {
        for(String s : fluidRegistry.keySet()) {
            reg(s);
        }

        for(String s : fluidRegistry.keySet())
        {
            fluidRegistryPost.put(s, fluidRegistry.get(s));
        }
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        for(String s : fluidRegistry.keySet()) {
            regClient(s);
        }

        ModelLoadingRegistry.INSTANCE.registerVariantProvider(resourceManager -> new FluidVariantProvider());
    }

    private static void reg(String fluidName)
    {
        FlowableFluid STILL_FLUID = Registry.register(Registry.FLUID, new Identifier(Main.MODID, fluidName), new AbstractMoltenMetalFluid.Still(fluidName));
        FlowableFluid FLOWING_FLUID = Registry.register(Registry.FLUID, new Identifier(Main.MODID, "flowing_"+fluidName), new AbstractMoltenMetalFluid.Flowing(fluidName));

        ((AbstractMoltenMetalFluid) STILL_FLUID).setStill(STILL_FLUID);
        ((AbstractMoltenMetalFluid) STILL_FLUID).setFlowing(FLOWING_FLUID);

        ((AbstractMoltenMetalFluid) FLOWING_FLUID).setStill(STILL_FLUID);
        ((AbstractMoltenMetalFluid) FLOWING_FLUID).setFlowing(FLOWING_FLUID);

        Identifier id = new Identifier(Main.MODID, "fluidbucket_" + fluidName);

        bucketIDs.add(id);

        Item FLUID_BUCKET = Registry.register(Registry.ITEM, id,
                new FluidBucketItem(STILL_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ItemGroups.ITEM_GROUP), fluidName));

        ((AbstractMoltenMetalFluid) STILL_FLUID).setBucketItem(FLUID_BUCKET);
        ((AbstractMoltenMetalFluid) FLOWING_FLUID).setBucketItem(FLUID_BUCKET);

        Block FLUID_BLOCK = Registry.register(Registry.BLOCK, new Identifier(Main.MODID, fluidName), new FluidBlock(STILL_FLUID, FabricBlockSettings.copy(Blocks.LAVA)){});

        ((AbstractMoltenMetalFluid) STILL_FLUID).setFluidBlock(FLUID_BLOCK);
        ((AbstractMoltenMetalFluid) FLOWING_FLUID).setFluidBlock(FLUID_BLOCK);
    }
    @Environment(EnvType.CLIENT)
    private static void regClient(String fluidName)
    {
        FluidRenderHandlerRegistry.INSTANCE.register(Registry.FLUID.get(new Identifier(Main.MODID, fluidName)), Registry.FLUID.get(new Identifier(Main.MODID, "flowing_"+fluidName)
        ), new SimpleFluidRenderHandler(
                new Identifier(Main.MODID, "block/moltenstill_" + fluidName),
                new Identifier(Main.MODID, "block/moltenflow_" + fluidName)
        ));
    }

    public static void registerFluid(String fluidName, FluidProperties fluidProperties)
    {
        fluidRegistry.put(fluidName, fluidProperties);
    }

    public static void removeFluid(String fluidName)
    {
        fluidRegistry.remove(fluidName);
    }

    public static FluidProperties getFluidProperties(String fluidName)
    {
        return fluidRegistryPost.get(fluidName);
    }

    public static HashMap<String, FluidProperties> getPreFluidRegistry()
    {
        return fluidRegistry;
    }

    public static HashMap<String, FluidProperties> getFluidRegistry()
    {
        return fluidRegistryPost;
    }

    @Environment(EnvType.CLIENT)
    public static void registerColours(String fluidKey, Color first, Color second, Color third, Color fourth, Color fifth, Color sixth, Color seventh)
    {
        FluidProperties fluidProperties = fluidRegistryPost.get(fluidKey);

        fluidProperties.setColours(first, second, third, fourth, fifth, sixth, seventh);
    }

    /**
     * Post Fluid registry is for assigning fluidProperties to existing fluids which already exist.
     * @param fluidName
     * @param fluidProperties
     */
    public static void registerPostFluid(String fluidName, FluidProperties fluidProperties)
    {
        fluidRegistryPost.put(fluidName, fluidProperties);
    }

    public static void removePostFluid(String fluidName)
    {
        fluidRegistryPost.remove(fluidName);
    }

    public static List<Identifier> getBucketIDs()
    {
        return bucketIDs;
    }
}
