package com.sussysyrup.smitheesfoundry.impl.block;

import com.sussysyrup.smitheesfoundry.api.block.ApiAlloySmelteryRegistry;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ImplAlloySmelteryRegistry implements ApiAlloySmelteryRegistry {

    public ImplAlloySmelteryRegistry()
    {}

    private Set<Block> reloadStructureBlocks = new HashSet<>();
    private static Set<Block> structueBlocks = new HashSet<>();

    private Set<Block> reloadFunctionalBlocks = new HashSet<>();
    private static Set<Block> functionalBlocks = new HashSet<>();

    private Set<Block> reloadTankBlocks = new HashSet<>();
    private static Set<Block> tankBlocks = new HashSet<>();

    private Map<Fluid, Integer> reloadFuelFluids = new HashMap<>();
    private static Map<Fluid, Integer> fuelFluids = new HashMap<>();

    @Override
    public Set<Block> getStructureBlocks() {
        return reloadStructureBlocks;
    }

    @Override
    public void addStructureBlock(Block block)
    {
        structueBlocks.add(block);
    }

    @Override
    public void removeStructureBlock(Block block)
    {
        structueBlocks.remove(block);
    }

    @Override
    public void clearStructureBlocks()
    {
        structueBlocks.clear();
    }

    @Override
    public Set<Block> getFunctionalBlocks() {
        return reloadFunctionalBlocks;
    }

    @Override
    public  void addFunctionalBlock(Block block)
    {
        functionalBlocks.add(block);
    }

    @Override
    public  void removeFunctionalBlock(Block block)
    {
        functionalBlocks.remove(block);
    }

    @Override
    public void clearFunctionalBlocks() {
        functionalBlocks.clear();
    }

    @Override
    public  Set<Block> getTankBlocks()
    {
        return reloadTankBlocks;
    }

    @Override
    public  void addTankBlock(Block block)
    {
        tankBlocks.add(block);
    }

    @Override
    public  void removeTankBlock(Block block)
    {
        tankBlocks.remove(block);
    }

    @Override
    public void clearTankBlocks() {
        tankBlocks.clear();
    }

    @Override
    public Map<Fluid, Integer> getFuelFluids() {
        return reloadFuelFluids;
    }

    @Override
    public  void addFuelFluid(Fluid fluid, int fuelValue)
    {
        fuelFluids.put(fluid, fuelValue);
    }

    @Override
    public  void removeFuelFluid(Fluid fluid) {
        fuelFluids.remove(fluid);
    }

    public void clearFuelFluids(Fluid fluid)
    {
        fuelFluids = new HashMap<>();
    }

    @Override
    public int getFuelValue(Fluid fluid)
    {
        return reloadFuelFluids.get(fluid);
    }

    @Override
    public void reload() {
        reloadStructureBlocks.addAll(structueBlocks);
        reloadFunctionalBlocks.addAll(functionalBlocks);
        reloadTankBlocks.addAll(tankBlocks);
        reloadFuelFluids.putAll(fuelFluids);
    }
}
