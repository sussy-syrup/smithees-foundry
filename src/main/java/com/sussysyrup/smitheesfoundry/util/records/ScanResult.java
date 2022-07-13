package com.sussysyrup.smitheesfoundry.util.records;

import net.minecraft.util.math.BlockPos;

public record ScanResult(int length, BlockPos endPos, BlockPos nextPos) {
}
