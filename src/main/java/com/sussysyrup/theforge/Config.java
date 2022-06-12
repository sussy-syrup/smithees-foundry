package com.sussysyrup.theforge;

import com.sussysyrup.sussyconfig.ConfigManager;

public class Config {

    public static void config()
    {
        ConfigManager manager = new ConfigManager(Main.MODID, Main.LOGGER);
        manager.writeComment("The Forge Configuration");
    }

}
