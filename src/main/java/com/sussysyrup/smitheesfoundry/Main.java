package com.sussysyrup.smitheesfoundry;

import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonFluidPost;
import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonMaterialPost;
import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonPost;
import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonSetup;
import com.sussysyrup.smitheesfoundry.util.Cache;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.entrypoint.EntrypointUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("smitheesfoundry");
	public static final String MODID = "smitheesfoundry";
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

		EntrypointUtils.invoke("FoundrySetup", CommonSetup.class, CommonSetup::init);
	}

	private static void processing()
	{
		//Creates Fluids and other things that only depend on materials
		EntrypointUtils.invoke("FoundrySetupPostMaterials", CommonMaterialPost.class, CommonMaterialPost::init);

		//Depends on Fluids and the bits you defined before on existing
		EntrypointUtils.invoke("FoundrySetupPostFluids", CommonFluidPost.class, CommonFluidPost::init);
	}

	private static void post()
	{
		EntrypointUtils.invoke("FoundrySetupPost", CommonPost.class, CommonPost::init);
	}

}
