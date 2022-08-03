package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.fluid.FluidProperties;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FluidRegistry {

    public static void main()
    {
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_iron", new FluidProperties("molten_iron", "iron", 300));
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_copper", new FluidProperties("molten_copper", "copper", 150));
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_netherite", new FluidProperties("molten_netherite","netherite", 900));
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_gold", new FluidProperties("molten_gold",null, 450));
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_rosegold", new FluidProperties("molten_rosegold","rosegold", 300));
    }

}
