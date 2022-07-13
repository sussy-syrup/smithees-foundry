package com.sussysyrup.smitheesfoundry.api.fluid;

import net.minecraft.fluid.Fluid;

import java.awt.*;
import java.util.Map;

public class FluidProperties {

    private final String name;
    private final String materialID;
    private final int cookTime;
    private Fluid fluid;

    private Map<Color, Color> colourMap = Map.of(new Color(0, 0, 0), new Color(0, 0, 0),
            new Color(43, 43, 43), new Color(43, 43, 43),
            new Color(85, 85 ,75), new Color(85, 85 ,75),
            new Color(128, 128, 128), new Color(128, 128, 128),
            new Color(170, 170, 170), new Color(170, 170, 170),
            new Color(213, 213, 213), new Color(213, 213, 213),
            new Color(255, 255, 255), new Color(255, 255, 255));

    public FluidProperties(String name, String materialID, int cookTime)
    {
        this.name = name;
        this.materialID = materialID;
        this.cookTime = cookTime;
    }

    public Map<Color, Color> getColourMapping() {
        return colourMap;
    }

    public void setColours(Color first, Color second, Color third, Color fourth, Color fifth, Color sixth, Color seventh)
    {
        colourMap = Map.of(new Color(0, 0, 0), first,
                new Color(43, 43, 43), second,
                new Color(85, 85 ,85), third,
                new Color(128, 128, 128), fourth,
                new Color(170, 170, 170), fifth,
                new Color(213, 213, 213), sixth,
                new Color(255, 255, 255), seventh);
    }

    public String getMaterialID()
    {
        return materialID;
    }

    public int getCookTime()
    {
        return cookTime;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public void setFluid(Fluid fluid)
    {
        this.fluid = fluid;
    }
}
