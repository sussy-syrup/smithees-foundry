package com.sussysyrup.theforge.api.casting;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

import java.util.HashMap;

public record CastingResource(long fluidValue, HashMap<Fluid, Item> fluidItemMap) {
}
