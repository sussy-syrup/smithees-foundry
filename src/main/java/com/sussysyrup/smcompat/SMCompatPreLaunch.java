package com.sussysyrup.smcompat;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

import java.util.ArrayList;
import java.util.List;

public class SMCompatPreLaunch implements PreLaunchEntrypoint {

    public static List<String> modidList = new ArrayList<>();

    //betterEnd = betterend


    public static boolean betterEnd = false;
    @Override
    public void onPreLaunch() {
        FabricLoader.getInstance().getAllMods().forEach(modContainer -> modidList.add(modContainer.getMetadata().getId()));

        if(SMCompatPreLaunch.modidList.contains("betterend"))
        {
            betterEnd = true;
        }
    }
}
