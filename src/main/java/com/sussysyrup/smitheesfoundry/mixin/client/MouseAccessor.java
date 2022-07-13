package com.sussysyrup.smitheesfoundry.mixin.client;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Mouse.class)
public interface MouseAccessor {

    @Accessor("x")
    public void setX(double x);

    @Accessor("y")
    public void setY(double y);
}
