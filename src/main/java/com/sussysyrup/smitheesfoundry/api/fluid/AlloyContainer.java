package com.sussysyrup.smitheesfoundry.api.fluid;

import net.minecraft.fluid.Fluid;

import java.util.List;

public record AlloyContainer(List<Fluid> reduceFluids, List<Long> reduceAmounts, Fluid addFluid, long addAmount) {
}
