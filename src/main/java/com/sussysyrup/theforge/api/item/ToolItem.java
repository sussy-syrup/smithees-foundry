package com.sussysyrup.theforge.api.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.itemgroup.ItemGroups;
import com.sussysyrup.theforge.api.material.ForgeMaterialRegistry;
import com.sussysyrup.theforge.api.material.Material;
import com.sussysyrup.theforge.api.recipe.ForgeToolRecipeRegistry;
import com.sussysyrup.theforge.api.recipe.ForgeToolRecipe;
import com.sussysyrup.theforge.api.trait.IActiveTrait;
import com.sussysyrup.theforge.api.trait.IStatTrait;
import com.sussysyrup.theforge.api.trait.TraitContainer;
import com.sussysyrup.theforge.util.ToolUtil;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ToolItem extends Item {

    public static String HEAD_KEY = "HEAD";
    public static String HANDLE_KEY = "HANDLE";
    public static String BINDING_KEY = "BINDING";
    public static String EXTRA1_KEY = "EXTRA1";
    public static String EXTRA2_KEY = "EXTRA2";
    public static String DURABILITY_KEY = "DURABILITY";
    public static String MAX_DURABILITY_KEY = "MAXDURABILITY";
    public static String ATTACK_DAMAGE_KEY = "ATTACKDAMAGE";
    public static String SWING_SPEED_KEY = "SWINGSPEED";
    public static String MINING_LEVEL_KEY = "MININGLEVEL";
    public static String MINING_SPEED_KEY = "MININGSPEED";

    private String toolType;

    protected TagKey<Block> effectiveBlocks;

    public ToolItem(Settings settings, String  toolType, TagKey<Block> effectiveBlocks) {
        super(settings);

        this.toolType = toolType;
        this.effectiveBlocks = effectiveBlocks;
    }

    @Override
    public boolean isSuitableFor(ItemStack stack, BlockState state) {

        NbtCompound nbt = stack.getNbt();

        if(nbt== null)
        {
            return false;
        }

        if(nbt.getInt(DURABILITY_KEY) <= 0)
        {
            return false;
        }

        int i = nbt.getInt(MINING_LEVEL_KEY);

        if (i < MiningLevels.DIAMOND && state.isIn(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        }
        if (i < MiningLevels.IRON && state.isIn(BlockTags.NEEDS_IRON_TOOL)) {
            return false;
        }
        if (i < MiningLevels.STONE && state.isIn(BlockTags.NEEDS_STONE_TOOL)) {
            return false;
        }
        return state.isIn(effectiveBlocks);
    }

    public String getToolType() {
        return toolType;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {

        NbtCompound nbt = stack.getNbt();

        if(nbt== null)
        {
            return 0;
        }

        int durability = getDurability(stack);

        if(durability > 0 ) {
            return state.isIn(this.effectiveBlocks) ? getMiningSpeed(stack) : 1F;
        }
        return 0.2F;
    }

    @Override
    public ItemStack getDefaultStack() {
        ForgeToolRecipe recipe = ForgeToolRecipeRegistry.getRecipeByType(toolType);
        List<String> partSet = Arrays.stream(recipe.getKey().split(",")).toList();

        String materialString;
        ItemStack part;
        List<ItemStack> inventoryStacks;

        materialString = "wood";
        inventoryStacks = new ArrayList<>();

        for (int i = 0; i < partSet.size(); i++) {
            part = new ItemStack(Registry.ITEM.get(new Identifier(Main.MODID, materialString + "_" + partSet.get(i))));
            inventoryStacks.add(part);
        }

        return recipe.getTool(inventoryStacks);
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if(ItemGroups.ITEM_GROUP.equals(group)) {

            ForgeToolRecipe recipe = ForgeToolRecipeRegistry.getRecipeByType(toolType);
            List<Material> materialsList = ForgeMaterialRegistry.getMaterials();
            List<String> partSet = Arrays.stream(recipe.getKey().split(",")).toList();

            String materialString;
            ItemStack part;
            List<ItemStack> inventoryStacks;
            ItemStack outStack;

            for(Material material : materialsList)
            {
                materialString = material.getName();
                inventoryStacks = new ArrayList<>();

                for(int i = 0; i < partSet.size(); i++) {
                    part = new ItemStack(Registry.ITEM.get(new Identifier(Main.MODID, materialString + "_" + partSet.get(i))));
                    inventoryStacks.add(part);
                }

                outStack = recipe.getTool(inventoryStacks);

                stacks.add(outStack);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {;

        if(stack.getNbt()== null)
        {
            tooltip.add(new LiteralText("MALFORMED TOOL").formatted(Formatting.BOLD).formatted(Formatting.RED));
            return;
        }

        List<TraitContainer> active = ToolUtil.getActiveToolTraits(stack);
        List<TraitContainer> stats = ToolUtil.getStatsTraits(stack);
        List<TraitContainer> repair = ToolUtil.getRepairTraits(stack);

        for(TraitContainer trait : active)
        {
            tooltip.add(trait.getTraitTranslation().formatted(trait.getFormatting()));
        }
        for(TraitContainer trait : stats)
        {
            tooltip.add(trait.getTraitTranslation().formatted(trait.getFormatting()));
        }
        for(TraitContainer trait : repair)
        {
            tooltip.add(trait.getTraitTranslation().formatted(trait.getFormatting()));
        }

        tooltip.add(new LiteralText(""));
        tooltip.add(new TranslatableText("tool.theforge.mininglevel", getMiningLevel(stack)).formatted(Formatting.DARK_GRAY));
        tooltip.add(new TranslatableText("tool.theforge.miningspeed", "+" + getMiningSpeed(stack)).formatted(Formatting.DARK_GRAY));
        tooltip.add(new TranslatableText("tool.theforge.durability", getDurability(stack) + "/" + getMaxDurability(stack)).formatted(Formatting.DARK_GRAY));
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if(stack.getNbt()== null)
        {
            return super.getItemBarColor(stack);
        }
        Color color = ForgeMaterialRegistry.getMaterial(stack.getOrCreateNbt().getString(HEAD_KEY)).getColourMapping().get(new Color(128, 128, 128));
        return color.getRGB();
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        if(stack.getNbt()== null)
        {
            return super.getItemBarStep(stack);
        }
        return Math.round((((float) getDurability(stack)) / ((float) getMaxDurability(stack))) * 13);
    }

    @Override
    public boolean isFireproof() {
        return true;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if(slot == EquipmentSlot.MAINHAND) {

            if(stack.getNbt()== null)
            {
                return super.getAttributeModifiers(stack, slot);
            }

            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", getDurability(stack) <= 0 ? 0 : getAttackDamage(stack), EntityAttributeModifier.Operation.ADDITION));
            builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", getAttackSpeed(stack), EntityAttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        else
        {
            return super.getAttributeModifiers(stack, slot);
        }
    }


    public float getMiningSpeed(ItemStack stack)
    {
        float miningSpeed = stack.getNbt().getFloat(ToolItem.MINING_SPEED_KEY);

        IStatTrait trait1;
        for(TraitContainer trait : ToolUtil.getStatsTraits(stack))
        {
            trait1 = ((IStatTrait) trait);
            miningSpeed += trait1.miningSpeedAdd();
        }
        for(TraitContainer trait : ToolUtil.getStatsTraits(stack))
        {
            trait1 = ((IStatTrait) trait);
            miningSpeed *= trait1.miningSpeedMultiply();
        }

        return miningSpeed;
    }

    public double getAttackDamage(ItemStack stack) {

        float attackDamage = stack.getNbt().getFloat(ToolItem.ATTACK_DAMAGE_KEY);

        IStatTrait trait1;
        for(TraitContainer trait : ToolUtil.getStatsTraits(stack)) {
            trait1 = ((IStatTrait) trait);
            attackDamage += trait1.damageAdd();
        }
        for(TraitContainer trait : ToolUtil.getStatsTraits(stack)) {
            trait1 = ((IStatTrait) trait);
            attackDamage *= trait1.damageMultiply();
        }

        return attackDamage;
    }

    public double getAttackSpeed(ItemStack stack)
    {
        float attackSpeed = stack.getNbt().getFloat(ToolItem.SWING_SPEED_KEY);

        IStatTrait trait1;
        for(TraitContainer trait : ToolUtil.getStatsTraits(stack)) {
            trait1 = ((IStatTrait) trait);
            attackSpeed += trait1.swingSpeedAdd();
        }
        for(TraitContainer trait : ToolUtil.getStatsTraits(stack)) {
            trait1 = ((IStatTrait) trait);
            attackSpeed *= trait1.swingSpeedMultiply();
        }

        return attackSpeed;
    }

    public int getDurability(ItemStack stack)
    {
        //SPECIAL CASE UNTOUCHED
        return stack.getNbt().getInt(DURABILITY_KEY);
    }

    public void setDurability(ItemStack stack, int durability)
    {
        stack.getNbt().putInt(DURABILITY_KEY, durability);
    }

    public int getMaxDurability(ItemStack stack)
    {
        int maxDurability = stack.getNbt().getInt(MAX_DURABILITY_KEY);

        IStatTrait trait1;
        for(TraitContainer trait : ToolUtil.getStatsTraits(stack)) {
            trait1 = ((IStatTrait) trait);
            maxDurability += trait1.durabilityAdd();
        }
        for(TraitContainer trait : ToolUtil.getStatsTraits(stack)) {
            trait1 = ((IStatTrait) trait);
            maxDurability *= trait1.durabilityMultiply();
        }

        return maxDurability;
    }

    public int getMiningLevel(ItemStack stack) {
        return stack.getNbt().getInt(MINING_LEVEL_KEY);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if(stack.getNbt()== null)
        {
            return;
        }

        List<TraitContainer> traits = ToolUtil.getActiveToolTraits(stack);

        IActiveTrait trait1;

        for (TraitContainer trait : traits)
        {
            trait1 = (IActiveTrait) trait;
            trait1.activeInventoryTick(stack, world, entity, slot, selected);
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getWorld();
        NbtCompound nbt = stack.getNbt();

        if(nbt== null)
        {
            return false;
        }

        List<TraitContainer> traits = ToolUtil.getActiveToolTraits(stack);

        IActiveTrait trait1;

        for (TraitContainer trait : traits)
        {
            trait1 = (IActiveTrait) trait;
            trait1.activePostHit(stack, target, attacker);
        }

        if(!world.isClient ) {

            if(attacker instanceof PlayerEntity pe)
            {
                if(pe.isCreative())
                {
                    return super.postHit(stack, target, attacker);
                }
            }

            int dur = getDurability(stack);
            if (dur > 0) {
                nbt.putInt(DURABILITY_KEY, dur - 1);

                if (dur == 1) {
                    world.playSound(null, attacker.getBlockPos(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 0.8f, 0.8f + world.random.nextFloat() * 0.4f);
                }
            }
        }

        return super.postHit(stack, target, attacker);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if(stack.getNbt()== null)
        {
            return false;
        }

        if(!world.isClient) {
            NbtCompound nbt = stack.getNbt();
            int dur = getDurability(stack);
            if (dur > 0) {
                nbt.putInt(DURABILITY_KEY, dur - 1);

                if (dur == 1) {
                    world.playSound(null, miner.getBlockPos(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 0.8f, 0.8f + world.random.nextFloat() * 0.4f);
                }
            }
        }

        List<TraitContainer> traits = ToolUtil.getActiveToolTraits(stack);

        IActiveTrait trait1;
        for (TraitContainer trait : traits)
        {
            trait1 = (IActiveTrait) trait;
            trait1.activePostMine(stack, world, stack, pos, miner);
        }

        return super.postMine(stack, world, state, pos, miner);
    }
}
