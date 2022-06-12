package com.sussysyrup.theforge.items;

import com.sussysyrup.theforge.api.item.ToolItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.TagKey;


public class AxeToolItem extends ToolItem {

    public AxeToolItem(Settings settings, String toolType, TagKey<Block> effectiveBlocks) {
        super(settings, toolType, effectiveBlocks);
    }

    @Override
    public float getMiningSpeed(ItemStack stack) {
        return super.getMiningSpeed(stack);
    }

    @Override
    public double getAttackDamage(ItemStack stack) {
        return super.getAttackDamage(stack) + 6;
    }

    @Override
    public double getAttackSpeed(ItemStack stack) {
        return super.getAttackSpeed(stack) -4.2F;
    }
}
