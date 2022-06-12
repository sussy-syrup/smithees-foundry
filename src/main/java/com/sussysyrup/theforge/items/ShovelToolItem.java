package com.sussysyrup.theforge.items;

import com.sussysyrup.theforge.api.item.ToolItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;


public class ShovelToolItem extends ToolItem {

    ShovelItem shovel;

    public ShovelToolItem(Settings settings, String toolType, TagKey<Block> effectiveBlocks) {
        super(settings, toolType, effectiveBlocks);
        shovel = (ShovelItem) Registry.ITEM.get(new Identifier("wooden_shovel"));
    }

    @Override
    public float getMiningSpeed(ItemStack stack) {
        return super.getMiningSpeed(stack);
    }

    @Override
    public double getAttackDamage(ItemStack stack) {
        return super.getAttackDamage(stack) + 1.5;
    }

    @Override
    public double getAttackSpeed(ItemStack stack) {
        return super.getAttackSpeed(stack) - 4F;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        NbtCompound nbt = context.getStack().getNbt();

        if(nbt == null)
        {
            return ActionResult.FAIL;
        }

        int dur = getDurability(context.getStack());

        ActionResult result = ActionResult.FAIL;

        if(!(dur <= 0)) {
            result = shovel.useOnBlock(context);
        }

        World world = context.getWorld();

        if(!world.isClient) {
            if (result.equals(ActionResult.CONSUME)) {

                if (dur > 0) {
                    nbt.putInt(DURABILITY_KEY, dur - 1);

                    if (dur == 1) {
                        world.playSound(null, context.getPlayer().getBlockPos(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 0.8f, 0.8f + world.random.nextFloat() * 0.4f);
                    }
                }

            }
        }

        return result;
    }
}
