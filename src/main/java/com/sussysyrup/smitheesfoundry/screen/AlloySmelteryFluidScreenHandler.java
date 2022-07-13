package com.sussysyrup.smitheesfoundry.screen;

import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.entity.AlloySmelteryControllerBlockEntity;
import com.sussysyrup.smitheesfoundry.registry.ModScreenHandlerRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.Nullable;

public class AlloySmelteryFluidScreenHandler extends ScreenHandler implements ExtendedScreenHandlerFactory {

    public AlloySmelteryControllerBlockEntity be;

    public AlloySmelteryFluidScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, (AlloySmelteryControllerBlockEntity) playerInventory.player.world.getBlockEntity(buf.readBlockPos()));

        be = (AlloySmelteryControllerBlockEntity) playerInventory.player.world.getBlockEntity(buf.readBlockPos());
    }

    public AlloySmelteryFluidScreenHandler(int syncId, PlayerInventory playerInventory, AlloySmelteryControllerBlockEntity be) {
        super(ModScreenHandlerRegistry.ALLOY_SMELTERYFLUID_SCREEN_HANDLER, syncId);

         this.be = be;

        int m;
        int l;

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {

        if (id == 0) {
            player.openHandledScreen(this);
        }

        if(id == 1)
        {
             be.activeFuel = FluidVariant.of(be.currentFuels.get(0));
        }
        if(id == 2)
        {
            be.activeFuel = FluidVariant.of(be.currentFuels.get(1));
        }
        if(id == 3)
        {
            be.activeFuel = FluidVariant.of(be.currentFuels.get(2));
        }

        return super.onButtonClick(player, id);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(be.itemInventory.size());
        buf.writeBlockPos(be.getPos());
        buf.writeBlockPos(be.getPos());
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
}
