package com.sussysyrup.theforge.trait.active;

import com.sussysyrup.theforge.api.trait.IActiveTrait;
import com.sussysyrup.theforge.api.trait.TraitContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class MagneticTrait extends TraitContainer implements IActiveTrait {

    private final float attraction;

    public MagneticTrait(String name, Formatting formatting, float attraction) {
        super(name, formatting);
        this.attraction = attraction;
    }

    @Override
    public void activeInventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(selected && !world.isClient)
        {
            Vec3d pullVec;
            Vec3d holderPos = entity.getPos();
            Vec3d itemPos;
            Box box = Box.of(entity.getPos(), attraction, attraction, attraction);

            List<ItemEntity> list = world.getEntitiesByType(TypeFilter.instanceOf(ItemEntity.class), box, Entity::isAlive);

            double length;

            int scalingFactor = 10;

            for(ItemEntity itemEntity : list)
            {
                itemPos = itemEntity.getPos();

                pullVec = new Vec3d(holderPos.x - itemPos.x, holderPos.y - itemPos.y, holderPos.z - itemPos.z);

                length = pullVec.length();

                itemEntity.addVelocity((pullVec.x / length) / scalingFactor, (pullVec.y / length) / scalingFactor, (pullVec.z / length) / scalingFactor);
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
