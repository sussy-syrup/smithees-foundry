package com.sussysyrup.theforge.api.item;

import net.minecraft.item.Item;

public class CastItem extends Item {

    public String castType;

    public CastItem(Settings settings, String castType) {
        super(settings);
        this.castType = castType;
    }
}
