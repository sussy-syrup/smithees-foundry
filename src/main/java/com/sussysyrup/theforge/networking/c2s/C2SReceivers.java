package com.sussysyrup.theforge.networking.c2s;

import com.sussysyrup.theforge.api.transfer.MultiStorageView;
import com.sussysyrup.theforge.blocks.alloysmeltery.entity.AlloySmelteryControllerBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.util.math.BlockPos;

public class C2SReceivers {

    public static void init()
    {
        ServerPlayNetworking.registerGlobalReceiver(C2SConstants.AlloySmelteryFluidClick, (server, player, handler, buf, sender) ->
        {
            BlockPos bePos = buf.readBlockPos();

            int fluidID = buf.readInt();

            server.execute(() ->
            {
                if(player.getWorld().getBlockEntity(bePos) instanceof AlloySmelteryControllerBlockEntity be)
                {
                    MultiStorageView<FluidVariant> view = (MultiStorageView<FluidVariant>) be.fluidStorage.views.get(fluidID);

                    be.fluidStorage.views.remove(fluidID);
                    be.fluidStorage.views.add(0, view);
                }
            });
        });
    }

}
