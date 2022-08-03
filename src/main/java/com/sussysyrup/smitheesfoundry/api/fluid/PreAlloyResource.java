package com.sussysyrup.smitheesfoundry.api.fluid;

import net.minecraft.util.Identifier;

import java.util.List;

public record PreAlloyResource(List<Identifier> identifiers, List<Long> amounts) {
}
