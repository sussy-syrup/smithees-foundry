package com.sussysyrup.smcompat.betterend.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.FluidProperties;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BEFluidRegistry {

    //TODO hack minecraft tags so that these are injected dynamically

    public static void main()
    {
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_thallasium", new FluidProperties("molten_thallasium", "thallasium", 315));
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_terminite", new FluidProperties("molten_terminite", "terminite", 385));
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_aeternium", new FluidProperties("molten_aeternium", "aeternium", 1109));
    }

}
