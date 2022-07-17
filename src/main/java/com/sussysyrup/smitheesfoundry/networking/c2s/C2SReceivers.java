package com.sussysyrup.smitheesfoundry.networking.c2s;

import com.sussysyrup.smitheesfoundry.api.transfer.MultiStorageView;
import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.entity.AlloySmelteryControllerBlockEntity;
import com.sussysyrup.smitheesfoundry.screen.AlloySmelteryFluidScreenHandler;
import com.sussysyrup.smitheesfoundry.screen.AlloySmelteryInvScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class C2SReceivers {

    public static void main()
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

        ServerPlayNetworking.registerGlobalReceiver(C2SConstants.AlloySmelteryOpenScreen, (server, player, handler, bufIn, sender) ->
        {
            int type = bufIn.readInt();

            if(type == 0) {
                BlockPos pos = bufIn.readBlockPos();

                double x = bufIn.readDouble();
                double y = bufIn.readDouble();

                server.execute(() ->
                {
                    AlloySmelteryControllerBlockEntity be = (AlloySmelteryControllerBlockEntity) player.getWorld().getBlockEntity(pos);

                    player.openHandledScreen(new ExtendedScreenHandlerFactory() {
                        @Override
                        public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
                            buf.writeBlockPos(pos);
                            buf.writeBlockPos(pos);
                            buf.writeBoolean(true);
                            buf.writeDouble(x);
                            buf.writeDouble(y);
                        }

                        @Override
                        public Text getDisplayName() {
                            return new TranslatableText("smitheesfoundry.container.alloysmeltery");
                        }

                        @Nullable
                        @Override
                        public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                            return new AlloySmelteryFluidScreenHandler(syncId, inv, be);
                        }
                    });
                });
            }

            if(type == 1) {
                BlockPos pos = bufIn.readBlockPos();

                double x = bufIn.readDouble();
                double y = bufIn.readDouble();

                server.execute(() ->
                {
                    AlloySmelteryControllerBlockEntity be = (AlloySmelteryControllerBlockEntity) player.getWorld().getBlockEntity(pos);

                    player.openHandledScreen(new ExtendedScreenHandlerFactory() {
                        @Override
                        public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
                            buf.writeInt(be.itemInventory.size());
                            buf.writeBlockPos(pos);
                            buf.writeBlockPos(pos);
                            buf.writeBoolean(true);
                            buf.writeDouble(x);
                            buf.writeDouble(y);
                        }

                        @Override
                        public Text getDisplayName() {
                            return new TranslatableText("smitheesfoundry.container.alloysmeltery");
                        }

                        @Nullable
                        @Override
                        public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                            return new AlloySmelteryInvScreenHandler(syncId, inv, be.itemInventory, be);
                        }
                    });
                });
            }
        });
    }

}
