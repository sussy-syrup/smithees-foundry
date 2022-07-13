package com.sussysyrup.smitheesfoundry.items;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class FluidBucketItem extends BucketItem {

    private final String fluidName;

    public FluidBucketItem(Fluid fluid, Settings settings, String fluidName) {
        super(fluid, settings);
        this.fluidName = fluidName;
    }

    @Override
    public Text getName(ItemStack stack) {

        return new TranslatableText("item.smitheesfoundry.fluidbucket", new TranslatableText("block.smitheesfoundry." + fluidName));
    }
}
