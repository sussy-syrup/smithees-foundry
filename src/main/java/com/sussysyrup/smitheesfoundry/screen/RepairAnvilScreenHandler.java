package com.sussysyrup.smitheesfoundry.screen;

import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.MaterialResource;
import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import com.sussysyrup.smitheesfoundry.api.trait.IRepairTrait;
import com.sussysyrup.smitheesfoundry.api.trait.TraitContainer;
import com.sussysyrup.smitheesfoundry.registry.ModScreenHandlerRegistry;
import com.sussysyrup.smitheesfoundry.util.ToolUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.registry.Registry;

import java.util.Collections;
import java.util.List;

public class RepairAnvilScreenHandler extends ScreenHandler {


    private final Inventory inventory;

    public RepairAnvilScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3));
    }

    public RepairAnvilScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlerRegistry.REPAIR_ANVIL_SCREEN_HANDLER, syncId);
        ForgeScreenHandler.checkSize(inventory, 3);

        inventory.onOpen(playerInventory.player);

        this.inventory = inventory;

        //TOOL
        this.addSlot(new ToolSlot(inventory, 0, 27, 35));

        //REPAIR
        this.addSlot(new RepairSlot(inventory, 1, 76, 35));

        //OUTPUT
        this.addSlot(new ResultSlot(inventory, 2, 134, 35));

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
        if(!inventory.getStack(0).isEmpty() && !inventory.getStack(1).isEmpty())
        {
            ItemStack toolStack = inventory.getStack(0);
            ToolItem toolItem = ((ToolItem) toolStack.getItem());
            NbtCompound nbt = toolStack.getNbt();

            String material = nbt.getString(ToolItem.HEAD_KEY);

            MaterialResource materialResource = ApiMaterialRegistry.getMaterialResource(Registry.ITEM.getId(inventory.getStack(1).getItem()).toString());

            if(materialResource == null)
            {
                inventory.setStack(2, ItemStack.EMPTY);
                return;
            }

            float materialValue = materialResource.materialValue() * inventory.getStack(1).getCount();

            if(!material.equals(materialResource.materialId()))
            {
                inventory.setStack(2, ItemStack.EMPTY);
                return;
            }

            int durability = toolItem.getDurability(toolStack);
            int maxDurability = toolItem.getMaxDurability(toolStack);

            if(durability == maxDurability && materialValue >= 1)
            {
                inventory.setStack(2, ItemStack.EMPTY);
                return;
            }

            toolStack = toolStack.copy();

            if(maxDurability - durability <= 500)
            {
                toolItem.setDurability(toolStack, maxDurability);
            }
            if(maxDurability - durability > 500)
            {
                boolean enoughMaterial = true;
                while (enoughMaterial) {
                    if(maxDurability - durability <= 500)
                    {
                        toolItem.setDurability(toolStack, maxDurability);
                        enoughMaterial = false;
                        break;
                    }
                    durability +=500;
                    toolItem.setDurability(toolStack, durability);

                    materialValue -=1;

                    if(materialValue < 1)
                    {
                        enoughMaterial = false;
                        break;
                    }
                }
            }

            List<TraitContainer> traits = ToolUtil.getRepairTraits(toolStack);

            IRepairTrait trait1;

            for(TraitContainer trait : traits)
            {
                trait1 = ((IRepairTrait) trait);
                trait1.modifyStack(toolStack);
            }

            inventory.setStack(2, toolStack);
        }
        else
        {
            inventory.setStack(2, ItemStack.EMPTY);
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

            ItemStack toolStackOriginal = inventory.getStack(0);
            ToolItem toolItem = ((ToolItem) toolStackOriginal.getItem());

            ItemStack stackNew = stack.copy();

            List<TraitContainer> traits = ToolUtil.getRepairTraits(stackNew);
            Collections.reverse(traits);

            IRepairTrait trait1;

            for(TraitContainer trait : traits)
            {
                trait1 = (IRepairTrait) trait;
                stackNew = trait1.modifyStackCorrection(stackNew, toolStackOriginal);
            }

            int durabilityDif = toolItem.getDurability(stackNew) - toolItem.getDurability(stack);

            int durabilityFac = ((int) Math.ceil(((float) durabilityDif) / 500F));

            MaterialResource materialResource = ApiMaterialRegistry.getMaterialResource(Registry.ITEM.getId(inventory.getStack(1).getItem()).toString());

            float materialValue = materialResource.materialValue() * inventory.getStack(1).getCount();

            float materialFac = 1F / materialValue;

            int materialStackReduction = ((int) Math.ceil(durabilityFac * materialFac));

            toolStackOriginal.split(1);

            inventory.getStack(1).split(materialStackReduction);

            super.onTakeItem(player, stack);
        }

        @Override
        public void onQuickTransfer(ItemStack newItem, ItemStack unused) {

            ItemStack toolStackOriginal = inventory.getStack(0);
            ToolItem toolItem = ((ToolItem) toolStackOriginal.getItem());

            ItemStack stackNew = newItem.copy();

            List<TraitContainer> traits = ToolUtil.getRepairTraits(stackNew);
            Collections.reverse(traits);

            IRepairTrait trait1;

            for(TraitContainer trait : traits)
            {
                trait1 = (IRepairTrait) trait;
                stackNew = trait1.modifyStackCorrection(stackNew, toolStackOriginal);
            }

            int durabilityDif = toolItem.getDurability(stackNew) - toolItem.getDurability(toolStackOriginal);

            int durabilityFac = ((int) Math.ceil(((float) durabilityDif) / 500F));

            MaterialResource materialResource = ApiMaterialRegistry.getMaterialResource(Registry.ITEM.getId(inventory.getStack(1).getItem()).toString());

            float materialValue = materialResource.materialValue() * inventory.getStack(1).getCount();

            float materialFac = 1F / materialValue;

            int materialStackReduction = ((int) Math.ceil(durabilityFac * materialFac));

            toolStackOriginal.split(1);

            inventory.getStack(1).split(materialStackReduction);

            super.onQuickTransfer(newItem, unused);
        }
    }

    private static class ToolSlot extends Slot
    {

        public ToolSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            if(stack.getItem() instanceof ToolItem)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    private static class RepairSlot extends Slot
    {

        public RepairSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            if(!(stack.getItem() instanceof ToolItem))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
