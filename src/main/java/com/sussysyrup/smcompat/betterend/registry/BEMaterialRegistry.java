package com.sussysyrup.smcompat.betterend.registry;

import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import com.sussysyrup.smitheesfoundry.api.trait.TraitContainer;
import java.util.Arrays;
import java.util.List;

public class BEMaterialRegistry {

    public static void initialise()
    {
        registerTraits();

        ApiMaterialRegistry.registerMaterial("thallasium", new Material("thallasium", true, "molten_thallasium", 2, 320, 1.05F, 7, 1.5F,
                createTraitsList(), createTraitsList(), createTraitsList(), createTraitsList()));
        ApiMaterialRegistry.registerMaterial("terminite", new Material("terminite", true, "molten_terminite", 3, 1230, 0.9F, 8.5F, 3.0F,
                createTraitsList(), createTraitsList(), createTraitsList(), createTraitsList()));
        ApiMaterialRegistry.registerMaterial("aeternium", new Material("aeternium", true, "molten_terminite", 5, 2196, 0.96F, 10.0F, 4.5F,
                createTraitsList(), createTraitsList(), createTraitsList(), createTraitsList()));
        ApiMaterialRegistry.registerMaterial("eternalcrystal", new Material("eternalcrystal", false, "empty", 1, 109, 0.5F, 1.0F, 0.5F,
                createTraitsList(), createTraitsList(), createTraitsList(), createTraitsList()));
    }

    private static void registerTraits() {
    }

    private static List<TraitContainer> createTraitsList(TraitContainer... containers)
    {
        return Arrays.stream(containers).toList();
    }
}
