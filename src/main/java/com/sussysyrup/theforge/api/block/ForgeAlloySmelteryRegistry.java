package com.sussysyrup.theforge.api.block;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForgeAlloySmelteryRegistry {

    private final static List<Block> structureBlocks = new ArrayList<>();

    public static List<Block> getStructureBlocks() {
        return structureBlocks;
    }

    public static void addStructureBlock(Block block)
    {
        structureBlocks.add(block);
    }

    public static void removeStructureBlock(Block block)
    {
        structureBlocks.remove(block);
    }

    private final static List<Block> functionalBlocks = new ArrayList<>();

    public static List<Block> getFunctionalBlocks() {
        return functionalBlocks;
    }

    public static void addFunctionalBlock(Block block)
    {
        functionalBlocks.add(block);
    }

    public static void removeFunctionalBlock(Block block)
    {
        functionalBlocks.remove(block);
    }

    private final static List<Block> tankBlocks = new ArrayList<>();

    public static List<Block> getTankBlocks()
    {
        return tankBlocks;
    }

    public static void addTankBlock(Block block)
    {
        tankBlocks.add(block);
    }

    public static void removeTankBlock(Block block)
    {
        tankBlocks.remove(block);
    }

    private final static Map<Fluid, Float> fuelFluids = new HashMap<>();

    public static Map<Fluid, Float> getFuelFluids()
    {
        return fuelFluids;
    }

    public static void addFuelFluid(Fluid fluid, float fuelValue)
    {
        fuelFluids.put(fluid, fuelValue);
    }

    public static void removeFuelFluid(Fluid fluid)
    {
        fuelFluids.remove(fluid);
    }
}
