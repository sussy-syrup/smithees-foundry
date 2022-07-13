package com.sussysyrup.smitheesfoundry.util;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public class InventoryUtil {

    public static NbtCompound writeNbt(NbtCompound nbt, Inventory stacks, boolean setIfEmpty) {
        NbtList nbtList = new NbtList();
        for (int i = 0; i < stacks.size(); ++i) {
            ItemStack itemStack = stacks.getStack(i);
            if (itemStack.isEmpty()) continue;
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)i);
            itemStack.writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        if (!nbtList.isEmpty() || setIfEmpty) {
            nbt.put("Items", nbtList);
        }
        return nbt;
    }

    public static void readNbt(NbtCompound nbt, Inventory stacks) {
        NbtList nbtList = nbt.getList("Items", 10);
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 0xFF;
            if (j < 0 || j >= stacks.size()) continue;
            stacks.setStack(j, ItemStack.fromNbt(nbtCompound));
        }
    }
}
