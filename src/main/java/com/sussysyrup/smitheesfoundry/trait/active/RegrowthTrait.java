package com.sussysyrup.smitheesfoundry.trait.active;

import com.sussysyrup.smitheesfoundry.api.trait.IActiveTrait;
import com.sussysyrup.smitheesfoundry.api.trait.TraitContainer;
import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RegrowthTrait extends TraitContainer implements IActiveTrait {


    public RegrowthTrait(String name,Formatting formatting) {
        super(name, formatting);
    }

    @Override
    public void activeInventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)  {

        if(!world.isClient) {

            NbtCompound nbt = stack.getNbt();

            if (14 == world.getRandom().nextInt(1250)) {

                int dur = nbt.getInt(ToolItem.DURABILITY_KEY);

                if (dur < nbt.getInt(ToolItem.MAX_DURABILITY_KEY)) {
                    nbt.putInt(ToolItem.DURABILITY_KEY, dur + 1);
                }
            }
        }
    }

    @Override
    public void activePostHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

    }

    @Override
    public void activePostMine(ItemStack stack, World world, ItemStack stack1, BlockPos pos, LivingEntity miner) {
    }
}
