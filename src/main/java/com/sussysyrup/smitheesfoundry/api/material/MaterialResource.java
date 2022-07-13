package com.sussysyrup.smitheesfoundry.api.material;

public record MaterialResource(String materialId, float materialValue) {

    public MaterialResource
    {
        java.util.Objects.requireNonNull(materialId);
        java.util.Objects.requireNonNull(materialValue);
    }

}
