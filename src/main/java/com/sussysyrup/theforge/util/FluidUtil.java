package com.sussysyrup.theforge.util;

import com.sussysyrup.theforge.api.transfer.MultiStorageView;
import com.sussysyrup.theforge.api.transfer.MultiVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;

/**
 * Only applies to multivariant storage
 */
public class FluidUtil {

    public static NbtCompound writeNbt(NbtCompound compound, MultiVariantStorage<FluidVariant> storage)
    {
        NbtList nbtList = new NbtList();

        NbtCompound nbtCompound;

        for(StorageView<FluidVariant> storageView : storage.views)
        {
             nbtCompound = new NbtCompound();

             nbtCompound.put("fluidvariant", storageView.getResource().toNbt());
             nbtCompound.putLong("fluidamount", storageView.getAmount());

             nbtList.add(nbtCompound);
        }

        compound.put("fluidvariants", nbtList);
        compound.putLong("maxamount", storage.maxCapacity);

        return compound;
    }

    public static void readNbt(NbtCompound compound, MultiVariantStorage<FluidVariant> storage)
    {
        NbtList nbtList = compound.getList("fluidvariants", 10);

        MultiStorageView<FluidVariant> storageView;

        storage.views = new ArrayList<>();

        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);

            storageView = new MultiStorageView<>(FluidVariant.fromNbt(nbtCompound.getCompound("fluidvariant")), nbtCompound.getLong("fluidamount"), 1000000000000000L, storage);

            storage.views.add(storageView);
        }
        storage.maxCapacity = compound.getLong("maxamount");
    }

    public static NbtCompound writeVariantOnly(NbtCompound compound, List<Fluid> fluids)
    {
        NbtList nbtList = new NbtList();

        NbtCompound nbt;

        for(Fluid fluidVariant : fluids)
        {
            nbt = new NbtCompound();
            nbt.put("fluidvariant", FluidVariant.of(fluidVariant).toNbt());

            nbtList.add(nbt);
        }

        compound.put("oFluidVariants", nbtList);

        return compound;
    }
    public static List<Fluid> readVariantOnly(NbtCompound compound)
    {
        NbtList nbtList = compound.getList("oFluidVariants", 10);

        List<Fluid> fluids = new ArrayList<>();

        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);

            fluids.add(FluidVariant.fromNbt(nbtCompound.getCompound("fluidvariant")).getFluid());
        }

        return fluids;
    }

}
