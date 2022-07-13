package com.sussysyrup.smitheesfoundry.api.trait;

import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public abstract class TraitContainer {

    private final Formatting formatting;
    private final String name;

    public TraitContainer(String name, Formatting formatting)
    {
        this.name = name;
        this.formatting = formatting;
    }

    public String getName()
    {
        return name;
    }

    public TranslatableText getTraitTranslation()
    {
        return new TranslatableText("smitheesfoundry.trait." + name);
    }
    public TranslatableText getTraitDescription()
    {
        return new TranslatableText("smitheesfoundry.trait." + name + ".desc");
    }

    public Formatting getFormatting() {
        return formatting;
    }
}
