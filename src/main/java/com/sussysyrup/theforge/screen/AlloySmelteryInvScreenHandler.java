package com.sussysyrup.theforge.screen;

import com.sussysyrup.theforge.blocks.alloysmeltery.entity.AlloySmelteryControllerBlockEntity;
import com.sussysyrup.theforge.networking.s2c.S2CConstants;
import com.sussysyrup.theforge.registry.ModScreenHandlerRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

public class AlloySmelteryInvScreenHandler extends ScreenHandler implements ExtendedScreenHandlerFactory {

    private Inventory inventory;
    private final PlayerInventory playerInventory;

    public int pageShift = 0;

    private AlloySmelteryControllerBlockEntity be;

    public AlloySmelteryInvScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, new SimpleInventory(buf.readInt()), (AlloySmelteryControllerBlockEntity) playerInventory.player.world.getBlockEntity(buf.readBlockPos()));

        be = (AlloySmelteryControllerBlockEntity) playerInventory.player.world.getBlockEntity(buf.readBlockPos());
    }

    public AlloySmelteryInvScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, AlloySmelteryControllerBlockEntity be)
    {
        super(ModScreenHandlerRegistry.ALLOY_SMELTERY_SCREEN_HANDLER, syncId);
        AlloySmelteryInvScreenHandler.checkSize(inventory, 0);

        this.be = be;
        this.pageShift = be.itemPageShift;

        this.inventory = inventory;

        inventory.onOpen(playerInventory.player);

        this.playerInventory = playerInventory;
        
        calculateSlots();
    }

    @Override
    public void sendContentUpdates() {
        super.sendContentUpdates();

        if (!be.itemInventory.equals(inventory) && !be.getWorld().isClient) {
            inventory = be.itemInventory;
            calculateSlots();
            ServerPlayNetworking.send((ServerPlayerEntity) playerInventory.player, S2CConstants.AlloySmelteryInvSync, PacketByteBufs.create());
        }
    }

    public void updateClient()
    {
        inventory = be.itemInventory;
        calculateSlots();
    }

    public void calculateSlots()
    {
        slots = DefaultedList.of();

        be.itemPageShift = pageShift;


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

        int index = 0 + (pageShift * 21);

        for(int h = 0; h < 3; h++)
        {
            for(int w = 0; w < 7; w++) {
                if(index >= inventory.size())
                {
                    this.addSlot(new BlockedSlot(14 + (w * 20), 17 + (h * 18)));
                }
                else {
                    this.addSlot(new SingleSlot(inventory, index, 14 + (w * 20), 17 + (h * 18)));
                }
                index++;
            }
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {

        return this.inventory.canPlayerUse(player);
    }

    @Override
    protected boolean insertItem(ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
        ItemStack itemStack;
        Slot slot;
        boolean bl = false;
        int i = startIndex;
        if (fromLast) {
            i = endIndex - 1;
        }
        if (stack.isStackable()) {
            while (!stack.isEmpty() && (fromLast ? i >= startIndex : i < endIndex)) {
                slot = this.slots.get(i);
                itemStack = slot.getStack();
                if (false) {
                    int j = itemStack.getCount() + stack.getCount();
                    if (j <= stack.getMaxCount()) {
                        stack.setCount(0);
                        itemStack.setCount(j);
                        slot.markDirty();
                        bl = true;
                    } else if (itemStack.getCount() < stack.getMaxCount()) {
                        stack.decrement(stack.getMaxCount() - itemStack.getCount());
                        itemStack.setCount(stack.getMaxCount());
                        slot.markDirty();
                        bl = true;
                    }
                }
                if (fromLast) {
                    --i;
                    continue;
                }
                ++i;
            }
        }
        if (!stack.isEmpty()) {
            i = fromLast ? endIndex - 1 : startIndex;
            while (fromLast ? i >= startIndex : i < endIndex) {
                slot = this.slots.get(i);
                itemStack = slot.getStack();
                if (itemStack.isEmpty() && slot.canInsert(stack)) {
                    if (stack.getCount() > slot.getMaxItemCount()) {
                        slot.setStack(stack.split(slot.getMaxItemCount()));
                    } else {
                        slot.setStack(stack.split(stack.getCount()));
                    }
                    slot.markDirty();
                    bl = true;
                    break;
                }
                if (fromLast) {
                    --i;
                    continue;
                }
                ++i;
            }
        }
        return bl;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (invSlot < 36 ? !this.insertItem(itemStack2, 36, this.slots.size(), false) : !this.insertItem(itemStack2, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {

        super.onSlotClick(slotIndex, button, actionType, player);
        onContentChanged(inventory);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {

        if(id == 0)
        {
            if(!(pageShift == 0))
            {
                pageShift -= 1;
            }
            calculateSlots();
        }
        if(id == 1)
        {
           int shiftMinimalIndex = 20 + (21 * pageShift);

           if(shiftMinimalIndex < inventory.size() - 2)
           {
               pageShift +=1;
           }
            calculateSlots();
        }
        if(id == 2)
        {
            player.openHandledScreen(this);
        }

        return super.onButtonClick(player, id);
    }

    public AlloySmelteryControllerBlockEntity getBlockEntity() {
        return be;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(be.getPos());
        buf.writeBlockPos(be.getPos());
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("theforge.container.alloysmeltery");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new AlloySmelteryFluidScreenHandler(syncId, inv, be);
    }


    public class SingleSlot extends Slot
    {
        public SingleSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }
        @Override
        public int getMaxItemCount() {
            return 1;
        }

        @Override
        public int getMaxItemCount(ItemStack stack) {
            return 1;
        }
    }

    public class BlockedSlot extends Slot
    {
        public BlockedSlot(int x, int y) {
            super(new SimpleInventory(1), 0, x, y);
        }

        @Override
        public boolean canTakeItems(PlayerEntity playerEntity) {
            return false;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }

        @Override
        public ItemStack getStack() {
            return Items.BARRIER.getDefaultStack();
        }
    }
}
