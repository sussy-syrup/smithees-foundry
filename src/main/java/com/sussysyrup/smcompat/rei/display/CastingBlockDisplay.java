package com.sussysyrup.smcompat.rei.display;

import com.sussysyrup.smcompat.rei.SmitheeFoundryReiClient;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.Collections;

public class CastingBlockDisplay extends BasicDisplay {

    public CastingBlockDisplay(Item input, Fluid fluid, Item output, long value) {
        super(new ArrayList<>(){{
            add(EntryIngredients.of(input));
            add(EntryIngredients.of(fluid, value));
        }}, Collections.singletonList(EntryIngredients.of(output)));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SmitheeFoundryReiClient.CASTING_BLOCK;
    }


}
