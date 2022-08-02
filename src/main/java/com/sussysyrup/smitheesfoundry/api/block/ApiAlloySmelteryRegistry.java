package com.sussysyrup.smitheesfoundry.api.block;

import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;

import java.util.Map;
import java.util.Set;

public interface ApiAlloySmelteryRegistry {

    static ApiAlloySmelteryRegistry getInstance() {
        return RegistryInstances.alloySmelteryRegistry;
    }

    /**
     * Returns structure blocks which are subject to reloads
     * @return
     */
    Set<Block> getStructureBlocks();

    void addStructureBlock(Block block);

    void removeStructureBlock(Block block);

    void clearStructureBlocks();

    Set<Block> getFunctionalBlocks();

    void addFunctionalBlock(Block block);

    void removeFunctionalBlock(Block block);

    void clearFunctionalBlocks();

    Set<Block> getTankBlocks();

    void addTankBlock(Block block);

    void removeTankBlock(Block block);

    void clearTankBlocks();

    Map<Fluid, Integer> getFuelFluids();

    void addFuelFluid(Fluid fluid, int fuelValue);

    void removeFuelFluid(Fluid fluid);

    void clearFuelFluids(Fluid fluid);

    int getFuelValue(Fluid fluid);

    void reload();
}
