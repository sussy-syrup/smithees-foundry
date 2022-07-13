package com.sussysyrup.theforge;

import com.sussysyrup.theforge.api.entrypoints.CommonMaterialPost;
import com.sussysyrup.theforge.api.fluid.ForgeMoltenFluidRegistry;
import com.sussysyrup.theforge.api.item.ForgePartRegistry;

public class PostMaterialCommon implements CommonMaterialPost {
    @Override
    public void init() {
        ForgePartRegistry.init();
        ForgeMoltenFluidRegistry.init();
    }
}
