package com.sussysyrup.theforge.api.block;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ForgeAlloySmelteryRegistry {

    private static List<Block> structureBlocks = new ArrayList<>();

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

    private static List<Block> functionalBlocks = new ArrayList<>();

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

}
