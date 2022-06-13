package com.sussysyrup.theforge.api.material;

import com.sussysyrup.theforge.api.trait.TraitContainer;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Material {

    private boolean metal;
    private float miningSpeed;
    private int miningLevel;
    private int durability;
    private float durabilityMultiplier;
    private float damage;

    private String name;

    private Map<Color, Color> colourMap = Map.of(new Color(0, 0, 0), new Color(0, 0, 0),
            new Color(43, 43, 43), new Color(43, 43, 43),
            new Color(85, 85 ,75), new Color(85, 85 ,75),
            new Color(128, 128, 128), new Color(128, 128, 128),
            new Color(170, 170, 170), new Color(170, 170, 170),
            new Color(213, 213, 213), new Color(213, 213, 213),
            new Color(255, 255, 255), new Color(255, 255, 255));

    private HashMap<String, List<TraitContainer>> partTraitsMap = new HashMap<>();

    public Material(String name, boolean metal, int miningLevel, int durability, float durabilityMultiplier, float miningSpeed, float damage,
                    List<TraitContainer> headTraits, List<TraitContainer> bindingTraits, List<TraitContainer> handleTraits, List<TraitContainer> extraTraits)
    {
        this.name = name;
        this.miningSpeed = miningSpeed;
        this.miningLevel = miningLevel;
        this.durability = durability;
        this.durabilityMultiplier = durabilityMultiplier;
        this.damage = damage;
        this.metal = metal;

        partTraitsMap.put("head", headTraits);
        partTraitsMap.put("binding", bindingTraits);
        partTraitsMap.put("handle", handleTraits);
        partTraitsMap.put("extra", extraTraits);
    }

    public String getName() {
        return name;
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

    public float getMiningSpeed()
    {
        return miningSpeed;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public int getDurability()
    {
        return durability;
    }

    public float getDurabilityMultiplier()
    {
        return durabilityMultiplier;
    }

    public float getDamage() {
        return damage;
    }

    public List<TraitContainer> getTraits(String partCategory)
    {
        return partTraitsMap.get(partCategory);
    }

    public boolean isMetal() {
        return metal;
    }
}
