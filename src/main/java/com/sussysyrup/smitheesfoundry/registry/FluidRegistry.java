package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.fluid.FluidProperties;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FluidRegistry {

    public static void main()
    {
        ApiMoltenFluidRegistry.registerFluid("molten_iron", new FluidProperties("molten_iron", "iron", 300));
        ApiMoltenFluidRegistry.registerFluid("molten_copper", new FluidProperties("molten_copper", "copper", 150));
        ApiMoltenFluidRegistry.registerFluid("molten_netherite", new FluidProperties("molten_netherite","netherite", 900));
        ApiMoltenFluidRegistry.registerFluid("molten_gold", new FluidProperties("molten_gold",null, 450));
        ApiMoltenFluidRegistry.registerFluid("molten_rosegold", new FluidProperties("molten_rosegold","rosegold", 300));
    }

    public static void postInit()
    {
        ApiMoltenFluidRegistry.getFluidProperties("molten_iron").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_iron")));
        ApiMoltenFluidRegistry.getFluidProperties("molten_copper").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_copper")));
        ApiMoltenFluidRegistry.getFluidProperties("molten_netherite").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_netherite")));
        ApiMoltenFluidRegistry.getFluidProperties("molten_gold").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_gold")));
        ApiMoltenFluidRegistry.getFluidProperties("molten_rosegold").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_rosegold")));
    }

}
