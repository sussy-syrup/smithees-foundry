package com.sussysyrup.theforge.trait.repair;

import com.sussysyrup.theforge.api.item.ToolItem;
import com.sussysyrup.theforge.api.trait.IRepairTrait;
import com.sussysyrup.theforge.api.trait.TraitContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Formatting;

public class GrowthTrait extends TraitContainer implements IRepairTrait {

    public GrowthTrait(String name, Formatting formatting) {
        super(name, formatting);
    }

    @Override
    public void modifyStack(ItemStack stack) {
        ToolItem toolItem = ((ToolItem) stack.getItem());
        NbtCompound nbt = stack.getNbt();

        int maxDurPrev = toolItem.getMaxDurability(stack);

        nbt.putInt(ToolItem.MAX_DURABILITY_KEY, nbt.getInt(ToolItem.MAX_DURABILITY_KEY) + 4);

        toolItem.setDurability(stack, toolItem.getDurability(stack) + ( toolItem.getMaxDurability(stack) - maxDurPrev));
    }

    @Override
    public ItemStack modifyStackCorrection(ItemStack newStack, ItemStack originalStack) {
        ItemStack stackNew1 = newStack.copy();
        ItemStack stackOld1 = originalStack.copy();
        ToolItem toolItem = ((ToolItem) stackNew1.getItem());

        int maxDurPrev = toolItem.getMaxDurability(stackOld1);

        stackOld1.getNbt().putInt(ToolItem.MAX_DURABILITY_KEY, stackOld1.getNbt().getInt(ToolItem.MAX_DURABILITY_KEY) + 4);

        toolItem.setDurability(stackNew1, toolItem.getDurability(stackNew1) - ( toolItem.getMaxDurability(stackOld1) - maxDurPrev));

        return stackNew1;
    }
}
