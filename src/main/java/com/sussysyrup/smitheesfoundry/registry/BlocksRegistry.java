package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.block.ApiAlloySmelteryRegistry;
import com.sussysyrup.smitheesfoundry.api.itemgroup.ItemGroups;
import com.sussysyrup.smitheesfoundry.blocks.ForgeBlock;
import com.sussysyrup.smitheesfoundry.blocks.RepairAnvilBlock;
import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.*;
import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.entity.*;
import com.sussysyrup.smitheesfoundry.blocks.entity.ForgeBlockEntity;
import com.sussysyrup.smitheesfoundry.blocks.entity.RepairAnvilBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.function.ToIntFunction;

public class BlocksRegistry {

    public static Block FORGE_BLOCK = new ForgeBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool());
    public static Block REPAIR_ANVIL_BLOCK = new RepairAnvilBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5.0f, 1200.0f).sounds(BlockSoundGroup.ANVIL));

    public static Block ALLOY_SMELTERY_CONTROLLER = new AlloySmelteryControllerBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F).luminance(createLightLevelFromLitBlockState(13)));
    public static Block ALLOY_SMELTERY_DRAIN = new AlloySmelteryDrainBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F));
    public static Block ALLOY_SMELTERY_FAUCET = new AlloySmelteryFaucetBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F));
    public static Block TANK_BLOCK = new TankBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F));
    public static Block CASTING_TABLE_BLOCK = new CastingTableBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F));
    public static Block CASTING_BASIN_BLOCK = new CastingBasinBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F));

    public static BlockEntityType<ForgeBlockEntity> FORGE_BLOCK_ENTITY;
    public static BlockEntityType<RepairAnvilBlockEntity> REPAIR_ANVIL_BLOCK_ENTITY;

    public static BlockEntityType<AlloySmelteryControllerBlockEntity> ALLOY_SMELTERY_CONTROLLER_BLOCK_ENTITY;
    public static BlockEntityType<AlloySmelteryDrainBlockEntity> ALLOY_SMELTERY_DRAIN_BLOCK_ENTITY;
    public static BlockEntityType<AlloySmelteryFaucetBlockEntity> ALLOY_SMELTERY_FAUCET_BLOCK_ENTITY;
    public static BlockEntityType<TankBlockEntity> TANK_BLOCK_ENTITY;
    public static BlockEntityType<CastingTableBlockEntity> CASTING_TABLE_ENTITY;
    public static BlockEntityType<CastingBasinBlockEntity> CASTING_BASIN_ENTITY;

    public static Block REINFORCED_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool());
    public static Block ROSEGOLD_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5.0F));

    public static void main()
    {
        register(FORGE_BLOCK, "forge_block");
        register(REPAIR_ANVIL_BLOCK, "repair_anvil_block");
        register(ALLOY_SMELTERY_CONTROLLER, "alloy_smeltery_controller");
        register(ALLOY_SMELTERY_DRAIN, "alloy_smeltery_drain");
        register(ALLOY_SMELTERY_FAUCET, "alloy_smeltery_faucet");
        register(TANK_BLOCK, "tank_block");
        register(CASTING_TABLE_BLOCK, "casting_table_block");
        register(CASTING_BASIN_BLOCK, "casting_basin_block");

        FORGE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "forge_block"), FabricBlockEntityTypeBuilder.create(ForgeBlockEntity::new, FORGE_BLOCK).build());
        REPAIR_ANVIL_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "repair_anvil_block"), FabricBlockEntityTypeBuilder.create(RepairAnvilBlockEntity::new, REPAIR_ANVIL_BLOCK).build());
        ALLOY_SMELTERY_CONTROLLER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "alloy_smeltery_controller_block"), FabricBlockEntityTypeBuilder.create(AlloySmelteryControllerBlockEntity::new, ALLOY_SMELTERY_CONTROLLER).build());
        ALLOY_SMELTERY_DRAIN_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "alloy_smeltery_drain"), FabricBlockEntityTypeBuilder.create(AlloySmelteryDrainBlockEntity::new, ALLOY_SMELTERY_DRAIN).build());
        ALLOY_SMELTERY_FAUCET_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "alloy_smeltery_faucet"), FabricBlockEntityTypeBuilder.create(AlloySmelteryFaucetBlockEntity::new, ALLOY_SMELTERY_FAUCET).build());
        TANK_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "tank"), FabricBlockEntityTypeBuilder.create(TankBlockEntity::new, TANK_BLOCK).build());
        CASTING_TABLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "casting_table"), FabricBlockEntityTypeBuilder.create(CastingTableBlockEntity::new, CASTING_TABLE_BLOCK).build());
        CASTING_BASIN_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "casting_basin"), FabricBlockEntityTypeBuilder.create(CastingBasinBlockEntity::new, CASTING_BASIN_BLOCK).build());

        register(REINFORCED_BRICKS, "reinforced_bricks");
        register(ROSEGOLD_BLOCK, "rosegold_block");

        ApiAlloySmelteryRegistry.addStructureBlock(REINFORCED_BRICKS);
        ApiAlloySmelteryRegistry.addStructureBlock(ALLOY_SMELTERY_CONTROLLER);
        ApiAlloySmelteryRegistry.addFunctionalBlock(ALLOY_SMELTERY_DRAIN);
        ApiAlloySmelteryRegistry.addTankBlock(TANK_BLOCK);
        ApiAlloySmelteryRegistry.addFuelFluid(Fluids.LAVA, 1);

        FluidStorage.SIDED.registerForBlockEntity((entity, direction) -> {
            if(entity.master != null) {
                return entity.master.fluidStorage;
            }
            return null;
        }, ALLOY_SMELTERY_DRAIN_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.fluidStorage, TANK_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> {
            if(direction.equals(Direction.UP))
            {
                return blockEntity.fluidStorage;
            }
            return null;
        }), CASTING_TABLE_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> {
            if(direction.equals(Direction.UP))
            {
                return blockEntity.fluidStorage;
            }
            return null;
        }), CASTING_BASIN_ENTITY);
    }

    private static void register(Block block, String name)
    {
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, name), block);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, name), new BlockItem(block, new FabricItemSettings().group(ItemGroups.BLOCK_GROUP)));
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) != false ? litLevel : 0;
    }
}
