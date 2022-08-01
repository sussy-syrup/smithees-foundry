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
        ApiMoltenFluidRegistry.registerFluid("molten_thallasium", new FluidProperties("molten_thallasium", "thallasium", 315));
        ApiMoltenFluidRegistry.registerFluid("molten_terminite", new FluidProperties("molten_terminite", "terminite", 385));
        ApiMoltenFluidRegistry.registerFluid("molten_aeternium", new FluidProperties("molten_aeternium", "aeternium", 1109));
    }

    public static void postInit()
    {
        ApiMoltenFluidRegistry.getFluidProperties("molten_thallasium").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_thallasium")));
        ApiMoltenFluidRegistry.getFluidProperties("molten_terminite").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_terminite")));
        ApiMoltenFluidRegistry.getFluidProperties("molten_aeternium").setFluid(Registry.FLUID.get(new Identifier(Main.MODID, "molten_aeternium")));

        ApiMoltenFluidRegistry.addFluidToTag(Registry.FLUID.get(new Identifier(Main.MODID, "molten_thallasium")));
        ApiMoltenFluidRegistry.addFluidToTag(Registry.FLUID.get(new Identifier(Main.MODID, "flowing_molten_thallasium")));
        ApiMoltenFluidRegistry.addFluidToTag(Registry.FLUID.get(new Identifier(Main.MODID, "molten_terminite")));
        ApiMoltenFluidRegistry.addFluidToTag(Registry.FLUID.get(new Identifier(Main.MODID, "flowing_molten_terminite")));
        ApiMoltenFluidRegistry.addFluidToTag(Registry.FLUID.get(new Identifier(Main.MODID, "molten_aeternium")));
        ApiMoltenFluidRegistry.addFluidToTag(Registry.FLUID.get(new Identifier(Main.MODID, "flowing_molten_aeternium")));
    }

}
