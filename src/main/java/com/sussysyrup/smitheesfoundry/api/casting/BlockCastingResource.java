package com.sussysyrup.smitheesfoundry.api.casting;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

import java.util.HashMap;

public record BlockCastingResource(HashMap<Fluid, Item> fluidItemMap) {
}
