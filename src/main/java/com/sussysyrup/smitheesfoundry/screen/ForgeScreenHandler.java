package com.sussysyrup.smitheesfoundry.screen;

import com.sussysyrup.smitheesfoundry.api.item.ApiPartRegistry;
import com.sussysyrup.smitheesfoundry.api.recipe.ApiToolRecipeRegistry;
import com.sussysyrup.smitheesfoundry.api.item.PartItem;
import com.sussysyrup.smitheesfoundry.registry.ModScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

import java.util.ArrayList;
import java.util.List;

public class ForgeScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public ForgeScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6));
    }

    public ForgeScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory)
    {
        super(ModScreenHandlerRegistry.FORGE_SCREEN_HANDLER, syncId);
        ForgeScreenHandler.checkSize(inventory, 6);

        this.inventory = inventory;

        inventory.onOpen(playerInventory.player);

        int m;
        int l;

        //HANDLE SLOT
        this.addSlot(new PartSlot(inventory, 0, 44, 54, "handle"));

        //BINDING SLOT
        this.addSlot(new PartSlot(inventory, 1, 44, 35, "binding"));

        //HEAD SLOT
        this.addSlot(new PartSlot(inventory, 2, 44, 16, "head"));

        //EXTRA SLOT 1
        this.addSlot(new PartSlot(inventory, 3, 20, 35, "extra"));

        //EXTRA SLOT 2
        this.addSlot(new PartSlot(inventory, 4, 68, 35, "extra"));

        //OUTPUT SLOT
        this.addSlot(new ResultSlot(inventory, 5, 127, 35));

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

        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.onQuickTransfer(newStack, originalStack);
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);
        onContentChanged(inventory);
    }

    @Override
    public void onContentChanged(Inventory inventory) {

        List<ItemStack> stacks = new ArrayList<>();
        stacks.add(inventory.getStack(0));
        stacks.add(inventory.getStack(1));
        stacks.add(inventory.getStack(2));
        stacks.add(inventory.getStack(3));
        stacks.add(inventory.getStack(4));

        ItemStack stack = ApiToolRecipeRegistry.lookup(stacks);

        if(stack != null)
        {
            inventory.setStack(5, stack);
        }
        else
        {
            inventory.setStack(5, ItemStack.EMPTY);
        }

        super.onContentChanged(inventory);
    }

    private static class PartSlot extends Slot
    {

        private final String partCategory;

        public PartSlot(Inventory inventory, int index, int x, int y, String partCategory) {
            super(inventory, index, x, y);
            this.partCategory = partCategory;
        }

        @Override
        public boolean canInsert(ItemStack stack) {

            if(stack.getItem() instanceof PartItem item) {

                if(ApiPartRegistry.getPrePartCategory(item.getPartName()).equals(partCategory))
                return true;
            }

            return false;
        }

        @Override
        public void markDirty() {
            super.markDirty();
        }
    }

    private static class ResultSlot extends Slot
    {

        public ResultSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }

        @Override
        public void onTakeItem(PlayerEntity player, ItemStack stack) {

            ItemStack stack1 = inventory.getStack(0);
            stack1.split(1);

            stack1 = inventory.getStack(1);
            stack1.split(1);

            stack1 = inventory.getStack(2);
            stack1.split(1);

            stack1 = inventory.getStack(3);
            stack1.split(1);

            stack1 = inventory.getStack(4);
            stack1.split(1);

            super.onTakeItem(player, stack);
        }

        @Override
        public void onQuickTransfer(ItemStack newItem, ItemStack original) {

            ItemStack stack1 = inventory.getStack(0);
            stack1.split(1);

            stack1 = inventory.getStack(1);
            stack1.split(1);

            stack1 = inventory.getStack(2);
            stack1.split(1);

            stack1 = inventory.getStack(3);
            stack1.split(1);

            stack1 = inventory.getStack(4);
            stack1.split(1);

            super.onQuickTransfer(newItem, original);
        }
    }
}
