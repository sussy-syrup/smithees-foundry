package com.sussysyrup.theforge.networking.s2c;

import com.sussysyrup.theforge.client.gui.screen.AlloySmelteryInvScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class S2CReceivers {

    public static void  init()
    {
        ClientPlayNetworking.registerGlobalReceiver(S2CConstants.AlloySmelteryInvSync, (client, handler, buf, packetSender) ->
        {
            client.execute(() ->
            {
                ((AlloySmelteryInvScreen) client.currentScreen).getScreenHandler().updateClient();
            });
        });
    }

}
