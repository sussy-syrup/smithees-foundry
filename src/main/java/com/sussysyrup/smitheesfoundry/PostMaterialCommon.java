package com.sussysyrup.smitheesfoundry;

import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonMaterialPost;
import com.sussysyrup.smitheesfoundry.api.item.ApiPartRegistry;

public class PostMaterialCommon implements CommonMaterialPost {
    @Override
    public void init() {
        ApiPartRegistry.main();
    }
}
