package com.sussysyrup.theforge.api.item;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.trait.TraitContainer;
import com.sussysyrup.theforge.api.item.ForgePartRegistry;
import com.sussysyrup.theforge.api.material.ForgeMaterialRegistry;
import com.sussysyrup.theforge.api.material.Material;
import com.sussysyrup.theforge.util.Util;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PartItem extends Item {

    private String partName;
    private String materialId;

    public PartItem(Settings settings, String partName, String materialId) {
        super(settings);

        this.partName = partName;
        this.materialId = materialId;
    }

    public String getPartName() {
        return partName;
    }

    public String getMaterialId() {
        return materialId;
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText("item." + Main.MODID + "." + partName, new TranslatableText(Util.materialAdjTranslationkey(materialId)));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Material material = ForgeMaterialRegistry.getMaterial(materialId);

        String category = ForgePartRegistry.getPrePartCategory(partName);

        List<TraitContainer> list = material.getTraits(category);

        if(list == null)
        {
            super.appendTooltip(stack, world, tooltip, context);
            return;
        }

        for(TraitContainer trait : list)
        {
            tooltip.add(trait.getTraitTranslation().formatted(trait.getFormatting()));
        }

        tooltip.add(new TranslatableText("part.theforge.cost", ForgePartRegistry.getPartCost(partName)));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
