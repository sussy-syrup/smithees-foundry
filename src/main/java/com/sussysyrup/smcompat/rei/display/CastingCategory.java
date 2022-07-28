package com.sussysyrup.smcompat.rei.display;

import com.sussysyrup.smcompat.SMCompatMain;
import com.sussysyrup.smcompat.rei.SmitheeFoundryReiClient;
import com.sussysyrup.smitheesfoundry.registry.ItemsRegistry;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.DisplayRenderer;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class CastingCategory implements DisplayCategory<CastingDisplay> {

    private static final Identifier TEXTURE = new Identifier(SMCompatMain.MODID, "textures/gui/casting_display.png");

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ItemsRegistry.INGOT_CAST);
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("rei.smitheesfoundry.casting");
    }

    @Override
    public CategoryIdentifier<? extends CastingDisplay> getCategoryIdentifier() {
        return SmitheeFoundryReiClient.CASTING;
    }

    //width 150
    //height 66

    @Override
    public List<Widget> setupDisplay(CastingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createRecipeBase(bounds));

        widgets.add(Widgets.createTexturedWidget(TEXTURE, bounds.x + 63, bounds.y + 38, 0, 0, 25, 23, 25, 23));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 71, bounds.y + 17)).entries(display.getInputEntries().get(0)));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 12, bounds.y + 17)).entries(display.getInputEntries().get(1)));

        widgets.add(Widgets.createArrow(new Point(bounds.x + 38, bounds.y + 17)).animationDurationTicks(40));

        widgets.add(Widgets.createSlot(new Point(bounds.x + 121, bounds.y + 17)).entries(display.getOutputEntries().get(0)));

        widgets.add(Widgets.createArrow(new Point(bounds.x + 93, bounds.y + 17)).animationDurationTicks(40));

        return widgets;
    }

    @Override
    public DisplayRenderer getDisplayRenderer(CastingDisplay display) {
        return DisplayCategory.super.getDisplayRenderer(display);
    }
}
