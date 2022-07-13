package com.sussysyrup.smitheesfoundry.api.recipe;

import com.sussysyrup.smitheesfoundry.api.item.PartItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class ApiToolRecipe {

    private String key;
    protected Item item;

    public ApiToolRecipe(Item item)
    {
        this.item = item;
    }

    public abstract ItemStack getTool(List<ItemStack> inventory);

    protected PartItem getPartItem(ItemStack part)
    {
        return ((PartItem) part.getItem());
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
