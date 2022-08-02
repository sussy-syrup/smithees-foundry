package com.sussysyrup.smitheesfoundry.api.block;

import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;

import java.util.Map;
import java.util.Set;

public interface ApiAlloySmelteryRegistry {

    /**
     * returns the currently active instance. Persistent entries are shared between all instances of this type
     * @return
     */
    static ApiAlloySmelteryRegistry getInstance() {
        return RegistryInstances.alloySmelteryRegistry;
    }

    /**
     * returns structure blocks - refreshed with reload
     * @return
     */
    Set<Block> getStructureBlocks();

    /**
     * adds structure block - persistent
     * @param block
     */
    void addStructureBlock(Block block);

    /**
     * removes structure block - persistent
     * @param block
     */
    void removeStructureBlock(Block block);

    /**
     * removes all structure blocks - persistent
     */
    void clearStructureBlocks();

    /**
     * returns functional blocks - refreshed with reload
     * @return
     */
    Set<Block> getFunctionalBlocks();

    /**
     * adds a functional block - persistent
     * @param block
     */
    void addFunctionalBlock(Block block);

    /**
     * removes a functional block - persistent
     * @param block
     */
    void removeFunctionalBlock(Block block);

    /**
     * removes all functional blocks - persistent
     */
    void clearFunctionalBlocks();

    /**
     * returns tank blocks - refreshed with reload
     * @return
     */
    Set<Block> getTankBlocks();

    /**
     * adds a tank block - persistent
     * @param block
     */
    void addTankBlock(Block block);

    /**
     * removes a tank block - persistent
     * @param block
     */
    void removeTankBlock(Block block);

    /**
     * removes all tank blocks - persistent
     */
    void clearTankBlocks();

    /**
     * returns the fuel hashmap - refreshed with reload
     * @return
     */
    Map<Fluid, Integer> getFuelFluids();

    /**
     * adds a fuel - persistent
     * @param fluid
     * @param fuelValue
     */
    void addFuelFluid(Fluid fluid, int fuelValue);

    /**
     * removes a fuel - persistent
     * @param fluid
     */
    void removeFuelFluid(Fluid fluid);

    /**
     * removes all fuels - persistent
     * @param fluid
     */
    void clearFuelFluids(Fluid fluid);

    /**
     * returns a fuel value from fluid - refreshed with reload
     * @param fluid
     * @return
     */
    int getFuelValue(Fluid fluid);

    /**
     * the reload function. This does not depend on anything so it can be called at any stage after the persistent registry is complete to build the refreshable registry
     */
    void reload();
}
