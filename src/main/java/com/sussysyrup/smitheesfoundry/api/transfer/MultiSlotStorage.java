package com.sussysyrup.smitheesfoundry.api.transfer;

import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

import java.util.Iterator;

public interface MultiSlotStorage <T> extends Storage<T> {

    @Override
    Iterator<StorageView<T>> iterator(TransactionContext transaction);

}
