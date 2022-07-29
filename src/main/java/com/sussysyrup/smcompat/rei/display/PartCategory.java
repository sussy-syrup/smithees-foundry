package com.sussysyrup.smcompat.rei.display;

import com.sussysyrup.smcompat.rei.SmitheeFoundryReiClient;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import com.sussysyrup.smitheesfoundry.api.item.PartItem;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import com.sussysyrup.smitheesfoundry.registry.ItemsRegistry;
import com.sussysyrup.smitheesfoundry.util.Util;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.DisplayRenderer;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

public class PartCategory implements DisplayCategory<PartDisplay> {

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ItemsRegistry.CRUDE_CHISEL);
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("rei.smitheesfoundry.part");
    }

    @Override
    public CategoryIdentifier<? extends PartDisplay> getCategoryIdentifier() {
        return SmitheeFoundryReiClient.PART;
    }

    //width 150
    //height 66

    @Override
    public List<Widget> setupDisplay(PartDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 4, bounds.y + 4)).entry(display.getInputEntries().get(0).get(0)));

        PartItem partItem = (PartItem) ((ItemStack) display.getInputEntries().get(0).get(0).getValue()).getItem();

        widgets.add(Widgets.createLabel(new Point(bounds.x + 75, bounds.y + 4), new TranslatableText(Util.materialAdjTranslationkey(partItem.getMaterialId()))));

        Material material = ApiMaterialRegistry.getMaterial(partItem.getMaterialId());

        widgets.add(Widgets.createLabel(new Point(bounds.x + 75, bounds.y + 16), new TranslatableText("tool.smitheesfoundry.durability", material.getDurability())));
        widgets.add(Widgets.createLabel(new Point(bounds.x + 75, bounds.y + 28), new TranslatableText("tool.smitheesfoundry.durability_mult", material.getDurabilityMultiplier())));
        widgets.add(Widgets.createLabel(new Point(bounds.x + 75, bounds.y + 40), new TranslatableText("tool.smitheesfoundry.miningspeed", material.getMiningSpeed())));
        widgets.add(Widgets.createLabel(new Point(bounds.x + 75, bounds.y + 52), new TranslatableText("tool.smitheesfoundry.mininglevel", material.getMiningLevel())));

        if(ApiMoltenFluidRegistry.getFluidRegistry().containsKey(material.getFluidID()))
        {
            Fluid fluid = ApiMoltenFluidRegistry.getFluidProperties(material.getFluidID()).getFluid();

            widgets.add(Widgets.createSlot(new Point(bounds.x +130, bounds.y + 4)).entry(EntryStacks.of(fluid)));
        }

        return widgets;
    }

    @Override
    public DisplayRenderer getDisplayRenderer(PartDisplay display) {
        return DisplayCategory.super.getDisplayRenderer(display);
    }
}
