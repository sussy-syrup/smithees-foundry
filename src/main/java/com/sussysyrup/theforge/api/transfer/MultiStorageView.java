package com.sussysyrup.theforge.api.transfer;

import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

public class MultiStorageView<T extends TransferVariant<?>> implements StorageView<T> {

    T variant;
    long amount;
    long capacity;

    public MultiStorageView(T variant, long amount, long capacity)
    {
        this.variant = variant;
        this.amount = amount;
        this.capacity = capacity;
    }

    @Override
    public long extract(T resource, long maxAmount, TransactionContext transaction) {

        setCapacity(capacity - maxAmount);

        return maxAmount;
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

    public void setCapacity(long capacityIn)
    {
        capacity = capacityIn;
    }
}
