package com.sussysyrup.smitheesfoundry.api.item;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.util.Util;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CastItem extends Item {

    public String castType;

    public CastItem(Settings settings, String castType) {
        super(settings);
        this.castType = castType;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        Float value = ApiPartRegistry.getPartCost(castType);

        if(value == null)
        {
            value = Float.valueOf(0);
            if(castType.equals("ingot"))
            {
                value = Float.valueOf(1);
            }
        }

        tooltip.add(new TranslatableText("part.smitheesfoundry.cost", value));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText("item." + Main.MODID + ".cast", new TranslatableText("item." + Main.MODID + "." + castType, new TranslatableText(Util.materialAdjTranslationkey("gold"))));
    }
}
