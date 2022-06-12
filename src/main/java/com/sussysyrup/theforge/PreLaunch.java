package com.sussysyrup.theforge;

import com.sussysyrup.theforge.util.Util;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.client.MinecraftClient;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PreLaunch implements PreLaunchEntrypoint {

    public static ClassLoader classLoader;

    @Override
    public void onPreLaunch() {

        classLoader = PreLaunch.class.getClassLoader();

        Path path = null;
        try {
            path = Paths.get(PreLaunch.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Util.setResourcePath(path);
    }
}
