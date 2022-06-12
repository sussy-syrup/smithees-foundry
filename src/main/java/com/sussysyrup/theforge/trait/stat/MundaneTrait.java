package com.sussysyrup.theforge.trait.stat;

import com.sussysyrup.theforge.api.trait.IStatTrait;
import com.sussysyrup.theforge.api.trait.TraitContainer;
import net.minecraft.util.Formatting;

public class MundaneTrait extends TraitContainer implements IStatTrait {

    public MundaneTrait(String name, Formatting formatting) {
        super(name, formatting);
    }

    @Override
    public int durabilityAdd() {
        return -10;
    }

    @Override
    public float durabilityMultiply() {
        return 0.6F;
    }

    @Override
    public float damageAdd() {
        return 0.6F;
    }

    @Override
    public float damageMultiply() {
        return 0.6F;
    }

    @Override
    public float swingSpeedAdd() {
        return 0;
    }

    @Override
    public float swingSpeedMultiply() {
        return 0.6F;
    }

    @Override
    public float miningSpeedAdd() {
        return 0;
    }

    @Override
    public float miningSpeedMultiply() {
        return 0.6F;
    }

    @Override
    public float miningLevelAdd() {
        return 0;
    }
}
