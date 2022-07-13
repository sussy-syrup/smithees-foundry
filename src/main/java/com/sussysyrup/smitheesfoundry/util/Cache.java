package com.sussysyrup.smitheesfoundry.util;

import com.sussysyrup.smitheesfoundry.Main;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;

public class Cache {

    Path path;

    public Cache()
    {
        this.path = FabricLoader.getInstance().getConfigDir().getParent().resolve("cache").resolve("smitheesfoundry");

        try {
            Files.createDirectories(path);
        } catch (Exception e)
        {
            Main.LOGGER.error("Failed to create cache directory: " + e);
        }
    }

    public Path getPath()
    {
        return path;
    }
}
