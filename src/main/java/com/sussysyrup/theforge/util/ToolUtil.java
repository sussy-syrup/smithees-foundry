package com.sussysyrup.theforge.util;

import com.sussysyrup.theforge.api.item.ForgePartRegistry;
import com.sussysyrup.theforge.api.item.ToolItem;
import com.sussysyrup.theforge.api.material.ForgeMaterialRegistry;
import com.sussysyrup.theforge.api.material.Material;
import com.sussysyrup.theforge.api.recipe.ForgeToolRecipe;
import com.sussysyrup.theforge.api.recipe.ForgeToolRecipeRegistry;
import com.sussysyrup.theforge.api.trait.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToolUtil {

    public static List<TraitContainer> getStackTraits(ItemStack stack)
    {
        List<TraitContainer> listOut = new ArrayList<>();

        ToolItem toolItem = ((ToolItem) stack.getItem());

        NbtCompound nbt = stack.getNbt();

        if(nbt == null)
        {
            return listOut;
        }

        ForgeToolRecipe recipe = ForgeToolRecipeRegistry.getRecipeByType(toolItem.getToolType());

        List<String> parts = Arrays.stream(recipe.getKey().split(",")).toList();

        List<String> partCategories = new ArrayList<>();

        for(String part : parts)
        {
            if(part.equals("empty"))
            {
                continue;
            }

            partCategories.add(ForgePartRegistry.getPrePartCategory(part));
        }

        List<String> materialKeys = new ArrayList<>();
        materialKeys.add(nbt.getString(ToolItem.HANDLE_KEY));
        materialKeys.add(nbt.getString(ToolItem.BINDING_KEY));
        materialKeys.add(nbt.getString(ToolItem.HEAD_KEY));
        materialKeys.add(nbt.getString(ToolItem.EXTRA1_KEY));
        materialKeys.add(nbt.getString(ToolItem.EXTRA2_KEY));

        String materialKey;
        Material material;

        int counter = 0;
        List<TraitContainer> processing;

        for(int i = 0; i < materialKeys.size(); i++)
        {
            materialKey = materialKeys.get(i);
            if(materialKey.equals("empty"))
            {
                continue;
            }

            material = ForgeMaterialRegistry.getMaterial(materialKey);

            processing = material.getTraits(partCategories.get(counter));

            for (TraitContainer trait : processing)
            {
                if(!listOut.contains(trait))
                listOut.add(trait);
            }

            counter++;
        }

        return listOut;
    }

    public static List<TraitContainer> getActiveToolTraits(ItemStack stack)
    {

        List<TraitContainer> traits = new ArrayList<>();

        for(TraitContainer trait : getStackTraits(stack))
        {
            if(trait instanceof IActiveTrait)
            {
                traits.add(trait);
            }
        }

        return traits;
    }

    public static List<TraitContainer> getStatsTraits(ItemStack stack)
    {
        List<TraitContainer> traits = new ArrayList<>();

        for(TraitContainer trait : getStackTraits(stack))
        {
            if(trait instanceof IStatTrait)
            {
                traits.add(trait);
            }
        }

        return traits;
    }

    public static List<TraitContainer> getRepairTraits(ItemStack stack)
    {
        List<TraitContainer> traits = new ArrayList<>();

        for(TraitContainer trait : getStackTraits(stack))
        {
            if(trait instanceof IRepairTrait)
            {
                traits.add(trait);
            }
        }

        return traits;
    }

    public static ItemStack getDurabilityCorrectStack(ItemStack stack)
    {
        ToolItem tool = ((ToolItem) stack.getItem());

        int dur = tool.getMaxDurability(stack);

        stack.getNbt().putInt(ToolItem.DURABILITY_KEY, dur);

        return stack;
    }

    public static int getDurabilityCorrection(ItemStack stack)
    {
        ToolItem tool = ((ToolItem) stack.getItem());

        int dur = tool.getMaxDurability(stack);

        return dur;
    }
}
