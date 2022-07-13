package com.sussysyrup.smitheesfoundry.api.transfer;

import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

public class MultiStorageView<T extends TransferVariant<?>> implements StorageView<T> {

    private final MultiVariantStorage storage;
    T variant;
    long amount;
    long capacity;

    public MultiStorageView(T variant, long amount, long capacity, MultiVariantStorage storage)
    {
        this.variant = variant;
        this.amount = amount;
        this.capacity = capacity;
        this.storage = storage;
    }

    @Override
    public long extract(T resource, long maxAmount, TransactionContext transaction) {

        return storage.extract(resource, maxAmount, transaction);
    }

    public void removeAmount(long amount)
    {
        this.amount = this.amount - amount;
    }

    @Override
    public boolean isResourceBlank() {
        return variant.isBlank()? true:false;
    }

    @Override
    public T getResource() {
        return variant;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getCapacity() {
        return capacity;
    }

    public void setAmount(long l) {
        amount = l;
    }
}
