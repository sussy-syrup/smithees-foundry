package com.sussysyrup.theforge.api.transfer;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is untested
 * @param <T>
 */
@ApiStatus.Experimental
public abstract class MultiVariantStorage<T extends TransferVariant<?>> extends SnapshotParticipant<MultiResource<T>> implements MultiSlotStorage<T> {

    public long maxCapacity = 0;

    public List<StorageView<T>> views = new ArrayList<>();

    @Override
    public long insert(T resource, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount);

        int index = -1;

        for(StorageView view : views)
        {
            if(resource.equals((T) view.getResource()))
            {
                index = views.indexOf(view);
                break;
            }
        }

        long insertedAmount = Math.min(maxAmount, maxCapacity - getCurrentCapacity());

        MultiStorageView view;

        updateSnapshots(transaction);

        if(index == -1)
        {
            view = new MultiStorageView(resource, insertedAmount, 1000000000000000L);

            views.add(view);

            return insertedAmount;
        }
        else
        {
            view = (MultiStorageView) views.get(index);

            view.setAmount(view.amount + insertedAmount);

            return insertedAmount;
        }
    }

    @Override
    public long extract(T resource, long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notBlankNotNegative(resource, maxAmount);

        int index = -1;

        for(StorageView view : views)
        {
            if(resource.equals((T) view.getResource()))
            {
                index = views.indexOf(view);
                break;
            }
        }

        updateSnapshots(transaction);

        if(index == -1)
        {
            return 0;
        }
        else
        {
            MultiStorageView view = (MultiStorageView) views.get(index);
            long extractedAmount = Math.min(maxAmount, view.amount);

            view.extract(resource, extractedAmount, transaction);

            if(view.getAmount() == 0)
            {
                views.remove(index);
            }

            return extractedAmount;
        }
    }

    @Override
    public Iterator<StorageView<T>> iterator(TransactionContext transaction) {
        return MultiViewIterator.create(views, transaction);
    }

    @Override
    protected MultiResource<T> createSnapshot() {
        return new MultiResource(views);
    }

    @Override
    protected void readSnapshot(MultiResource<T> snapshot) {
        views = snapshot.views();
    }

    @Override
    public abstract void onFinalCommit();

    public long getCurrentCapacity() {
        long currentCapacity = 0;

        for(StorageView<T> view : views)
        {
            currentCapacity += view.getAmount();
        }
        return currentCapacity;
    }
}
