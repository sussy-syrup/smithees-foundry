package com.sussysyrup.smitheesfoundry.api.trait;

import net.minecraft.item.ItemStack;

public interface IRepairTrait {

    void modifyStack(ItemStack stack);

    ItemStack modifyStackCorrection(ItemStack newStack, ItemStack originalStack);
}
