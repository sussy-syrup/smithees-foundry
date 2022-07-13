package com.sussysyrup.smitheesfoundry.api.transfer;

import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;

import java.util.List;

public record MultiResource<T>(List<StorageView<T>> views) {
}
