package com.sussysyrup.smitheesfoundry.impl.fluid;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.fluid.AbstractMoltenMetalFluid;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.FluidProperties;
import com.sussysyrup.smitheesfoundry.api.itemgroup.ItemGroups;
import com.sussysyrup.smitheesfoundry.items.FluidBucketItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImplMoltenFluidRegistry implements ApiMoltenFluidRegistry {

    private static HashMap<String, FluidProperties> createFluidPropertiesRegistry = new HashMap<>();

    private HashMap<String, FluidProperties> reloadFluidPropertiesRegistry = new HashMap<>();
    private static HashMap<String, FluidProperties> fluidPropertiesRegistry = new HashMap<>();
    private static HashMap<String, FluidProperties> externalFluidPropertiesRegistry = new HashMap<>();

    private Set<Identifier> reloadBucketIDs = new HashSet<>();
    private static Set<Identifier> bucketIDs = new HashSet<>();

    private Set<RegistryEntry<Fluid>> reloadCreateFluidSet = new HashSet<>();
    private static Set<RegistryEntry<Fluid>> createFluidSet = new HashSet<>();

    @Override
    public void registerCreateFluid(String fluidName, FluidProperties fluidProperties)
    {
        createFluidPropertiesRegistry.put(fluidName, fluidProperties);
    }

    @Override
    public void removeCreateFluid(String fluidName)
    {
        createFluidPropertiesRegistry.remove(fluidName);
    }

    @Override
    public void clearCreateFluids() {
        createFluidPropertiesRegistry.clear();
    }

    @Override
    public void registerExternalFluidProperties(String fluidName, FluidProperties fluidProperties) {
        externalFluidPropertiesRegistry.put(fluidName, fluidProperties);
    }

    @Override
    public void removeExternalFluidProperties(String fluidName) {
        externalFluidPropertiesRegistry.remove(fluidName);
    }

    @Override
    public void clearExternalFluidProperties() {
        externalFluidPropertiesRegistry.clear();
    }

    @Override
    public HashMap<String, FluidProperties> getCreateFluidRegistry()
    {
        return createFluidPropertiesRegistry;
    }

    @Override
    public HashMap<String, FluidProperties> getFluidPropertiesRegistry()
    {
        return reloadFluidPropertiesRegistry;
    }

    @Override
    public FluidProperties getFluidProperties(String fluidName)
    {
        return reloadFluidPropertiesRegistry.get(fluidName);
    }

    @Override
    public List<Identifier> getBucketIDs()
    {
        return reloadBucketIDs.stream().toList();
    }

    @Override
    public Set<RegistryEntry<Fluid>> getCreateFluidEntries() {
        return reloadCreateFluidSet;
    }

    @Override
    public void registerColours(String fluidKey, Color first, Color second, Color third, Color fourth, Color fifth, Color sixth, Color seventh)
    {
        FluidProperties fluidProperties = reloadFluidPropertiesRegistry.get(fluidKey);

        if(fluidProperties == null)
        {
            return;
        }

        fluidProperties.setColours(first, second, third, fourth, fifth, sixth, seventh);
    }

    @Override
    public void preReload()
    {
        for(String s : createFluidPropertiesRegistry.keySet()) {
            reg(s);
        }

        fluidPropertiesRegistry.putAll(createFluidPropertiesRegistry);
        reloadFluidPropertiesRegistry.putAll(fluidPropertiesRegistry);
        reloadBucketIDs.addAll(bucketIDs);
        reloadCreateFluidSet.addAll(createFluidSet);
    }

    @Override
    public void reload() {
        reloadFluidPropertiesRegistry.putAll(fluidPropertiesRegistry);
        reloadFluidPropertiesRegistry.putAll(externalFluidPropertiesRegistry);
        reloadBucketIDs.addAll(bucketIDs);
        reloadCreateFluidSet.addAll(createFluidSet);
    }

    private static void addFluidToTag(Fluid fluid)
    {
        createFluidSet.add(Registry.FLUID.getEntry(Registry.FLUID.getKey(fluid).get()).get());
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

        Item FLUID_BUCKET = Registry.register(Registry.ITEM, id,
                new FluidBucketItem(STILL_FLUID, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ItemGroups.ITEM_GROUP), fluidName));

        bucketIDs.add(id);

        ((AbstractMoltenMetalFluid) STILL_FLUID).setBucketItem(FLUID_BUCKET);
        ((AbstractMoltenMetalFluid) FLOWING_FLUID).setBucketItem(FLUID_BUCKET);

        Block FLUID_BLOCK = Registry.register(Registry.BLOCK, new Identifier(Main.MODID, fluidName), new FluidBlock(STILL_FLUID, FabricBlockSettings.copy(Blocks.LAVA)){});

        ((AbstractMoltenMetalFluid) STILL_FLUID).setFluidBlock(FLUID_BLOCK);
        ((AbstractMoltenMetalFluid) FLOWING_FLUID).setFluidBlock(FLUID_BLOCK);

        addFluidToTag(STILL_FLUID);
        addFluidToTag(FLOWING_FLUID);

        createFluidPropertiesRegistry.get(fluidName).setFluid(STILL_FLUID);
    }
}
