package com.sussysyrup.theforge;

import com.sussysyrup.theforge.api.block.ForgePartBenchRegistry;
import com.sussysyrup.theforge.api.fluid.ForgeMoltenFluidRegistry;
import com.sussysyrup.theforge.registry.*;
import com.sussysyrup.theforge.api.item.ForgePartRegistry;
import com.sussysyrup.theforge.util.Cache;
import net.fabricmc.api.ModInitializer;
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
		independent();
	}

	private static void setup()
	{
		cache = new Cache();

		//Config should be loaded before anything else here
		Config.config();

		//First to exist other than config
		MaterialRegistry.init();

		//Creates Default Parts
		PartRegistry.init();

		//Makes all the necessary calls for ForgePartBenchRegistry. multiple calls like this can exist as long as its before ForgePartBenchRegistry
		PartBenchRegistry.init();

		//Fluids
		FluidRegistry.init();
	}

	private static void processing()
	{

		//Depends on Materials Existing
		ForgePartRegistry.init();

		//Depends on Materials Existing
		ForgeMoltenFluidRegistry.init();

		//Depends on ForgePartRegistry to define part benches first
		ForgePartBenchRegistry.init();
	}

	private static void independent()
	{
		BlocksRegistry.init();
		ItemsRegistry.init();
		EventRegistry.init();
		ModScreenHandlerRegistry.init();

	}

}
