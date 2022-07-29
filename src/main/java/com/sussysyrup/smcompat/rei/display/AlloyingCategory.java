package com.sussysyrup.smcompat.rei.display;

import com.sussysyrup.smcompat.SMCompatMain;
import com.sussysyrup.smcompat.rei.SmitheeFoundryReiClient;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.DisplayRenderer;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class AlloyingCategory implements DisplayCategory<AlloyingDisplay> {

    private static final Identifier TEXTURE = new Identifier(SMCompatMain.MODID, "textures/gui/alloying_display.png");

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(BlocksRegistry.ALLOY_SMELTERY_CONTROLLER);
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("rei.smitheesfoundry.alloying");
    }

    @Override
    public CategoryIdentifier<? extends AlloyingDisplay> getCategoryIdentifier() {
        return SmitheeFoundryReiClient.ALLOYING;
    }

    //width 150
    //height 66

    @Override
    public List<Widget> setupDisplay(AlloyingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createTexturedWidget(TEXTURE, bounds.x, bounds.y, 0, 0, 150, 66, 150, 66));

        List<EntryIngredient> inputEntries = display.getInputEntries();

        widgets.add(Widgets.createSlot(new Point(bounds.x + 26, bounds.y + 30)).entry(inputEntries.get(0).get(0)).disableBackground());
        widgets.add(Widgets.createSlot(new Point(bounds.x + 42, bounds.y + 30)).entry(inputEntries.get(1).get(0)).disableBackground());

        if(inputEntries.size() > 2)
        {
            widgets.add(Widgets.createSlot(new Point(bounds.x + 58, bounds.y + 30)).entry(inputEntries.get(2).get(0)).disableBackground());
        }
        if(inputEntries.size() > 3)
        {
            widgets.add(Widgets.createSlot(new Point(bounds.x + 26, bounds.y + 14)).entry(inputEntries.get(3).get(0)).disableBackground());
        }
        if(inputEntries.size() > 4)
        {
            widgets.add(Widgets.createSlot(new Point(bounds.x + 42, bounds.y + 14)).entry(inputEntries.get(4).get(0)).disableBackground());
        }
        if(inputEntries.size() > 5)
        {
            widgets.add(Widgets.createSlot(new Point(bounds.x + 58, bounds.y + 14)).entry(inputEntries.get(5).get(0)).disableBackground());
        }


        widgets.add(Widgets.createArrow(new Point(bounds.x + 95, bounds.y + 33)).animationDurationTicks(120F));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 124, bounds.y + 33)).entry(display.getOutputEntries().get(0).get(0)));

        return widgets;
    }

    @Override
    public DisplayRenderer getDisplayRenderer(AlloyingDisplay display) {
        return DisplayCategory.super.getDisplayRenderer(display);
    }
}
