package com.sussysyrup.smitheesfoundry;

import com.sussysyrup.smitheesfoundry.util.Util;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

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
