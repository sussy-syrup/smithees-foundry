package com.sussysyrup.theforge;

import com.sussysyrup.theforge.api.entrypoints.CommonFluidPost;
import com.sussysyrup.theforge.api.entrypoints.CommonMaterialPost;
import com.sussysyrup.theforge.api.entrypoints.CommonPost;
import com.sussysyrup.theforge.api.entrypoints.CommonSetup;
import com.sussysyrup.theforge.api.fluid.ForgeMoltenFluidRegistry;
import com.sussysyrup.theforge.registry.*;
import com.sussysyrup.theforge.api.item.ForgePartRegistry;
import com.sussysyrup.theforge.util.Cache;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.entrypoint.EntrypointUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("theforge");
	public static final String MODID = "theforge";
	public static Cache cache;

	@Override
	public void onInitialize() {
		setup();
		processing();
		post();
	}

	private static void setup()
	{
		cache = new Cache();

		//Config should be loaded before anything else here
		Config.config();

		EntrypointUtils.invoke("ForgeSetup", CommonSetup.class, CommonSetup::init);
	}

	private static void processing()
	{
		//Creates Fluids and other things that only depend on materials
		EntrypointUtils.invoke("ForgeSetupPostMaterials", CommonMaterialPost.class, CommonMaterialPost::init);

		//Depends on Fluids and the bits you defined before on existing
		EntrypointUtils.invoke("ForgeSetupPostFluids", CommonFluidPost.class, CommonFluidPost::init);
	}

	private static void post()
	{
		EntrypointUtils.invoke("ForgeSetupPost", CommonPost.class, CommonPost::init);
	}

}
