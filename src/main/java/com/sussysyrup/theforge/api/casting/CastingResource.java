package com.sussysyrup.theforge.api.casting;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

import java.util.Map;

public record CastingResource(long fluidValue, Map<Fluid, Item> fluidItemMap) {
}
