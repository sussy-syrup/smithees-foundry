package com.sussysyrup.smitheesfoundry.api.transfer;

import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public final class MultiViewIterator<T> implements Iterator<StorageView<T>>, Transaction.CloseCallback {

    public static <T> Iterator<StorageView<T>> create(List<StorageView<T>> views, TransactionContext transaction) {
        MultiViewIterator<T> it = new MultiViewIterator<>(views);
        transaction.addCloseCallback(it);
        return it;
    }

    private boolean open = true;
    private boolean hasNext = true;
    private final List<StorageView<T>> views;
    private int counter;

    private MultiViewIterator(List<StorageView<T>> views) {
        this.views = views;
        this.counter = 0;
    }

    @Override
    public boolean hasNext() {
        return open && hasNext;
    }

    @Override
    public StorageView<T> next() {
        if (!open) {
            throw new NoSuchElementException("The transaction for this iterator was closed.");
        }

        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        int maxIndex = views.size() - 1;

        StorageView<T> view;

        if(!views.isEmpty()) {
            view = views.get(counter);
        }
        else
        {
            view = new StorageView<T>() {
                @Override
                public long extract(T resource, long maxAmount, TransactionContext transaction) {
                    return 0;
                }

                @Override
                public boolean isResourceBlank() {
                    return true;
                }

                @Override
                public T getResource() {
                    return null;
                }

                @Override
                public long getAmount() {
                    return 0;
                }

                @Override
                public long getCapacity() {
                    return 0;
                }
            };
            hasNext = false;
            return view;
        }

        if(counter == maxIndex)
        {
            hasNext = false;
        }

        counter++;

        return view;
    }

    @Override
    public void onClose(TransactionContext transaction, Transaction.Result result) {
        open = false;
    }
}
