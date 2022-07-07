package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.block.ForgeAlloySmelteryRegistry;
import com.sussysyrup.theforge.api.itemgroup.ItemGroups;
import com.sussysyrup.theforge.blocks.ForgeBlock;
import com.sussysyrup.theforge.blocks.RepairAnvilBlock;
import com.sussysyrup.theforge.blocks.alloysmeltery.*;
import com.sussysyrup.theforge.blocks.alloysmeltery.entity.*;
import com.sussysyrup.theforge.blocks.entity.ForgeBlockEntity;
import com.sussysyrup.theforge.blocks.entity.RepairAnvilBlockEntity;
import com.sussysyrup.theforge.client.render.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import  net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class BlocksRegistry {

    public static Block FORGE_BLOCK = new ForgeBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool());
    public static Block REPAIR_ANVIL_BLOCK = new RepairAnvilBlock(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5.0f, 1200.0f).sounds(BlockSoundGroup.ANVIL));

    public static Block ALLOY_SMELTERY_CONTROLLER = new AlloySmelteryControllerBlock(FabricBlockSettings.of(Material.STONE).strength(4.0F));
    public static Block ALLOY_SMELTERY_DRAIN = new AlloySmelteryDrainBlock(FabricBlockSettings.of(Material.STONE).strength(4.0F));
    public static Block ALLOY_SMELTERY_FAUCET = new AlloySmelteryFaucetBlock(FabricBlockSettings.of(Material.STONE).strength(4.0F));
    public static Block TANK_BLOCK = new TankBlock(FabricBlockSettings.of(Material.STONE).strength(4.0F));
    public static Block CASTING_TABLE_BLOCK = new CastingTableBlock(FabricBlockSettings.of(Material.STONE).strength(4.0F));

    public static BlockEntityType<ForgeBlockEntity> FORGE_BLOCK_ENTITY;
    public static BlockEntityType<RepairAnvilBlockEntity> REPAIR_ANVIL_BLOCK_ENTITY;

    public static BlockEntityType<AlloySmelteryControllerBlockEntity> ALLOY_SMELTERY_CONTROLLER_BLOCK_ENTITY;
    public static BlockEntityType<AlloySmelteryDrainBlockEntity> ALLOY_SMELTERY_DRAIN_BLOCK_ENTITY;
    public static BlockEntityType<AlloySmelteryFaucetBlockEntity> ALLOY_SMELTERY_FAUCET_BLOCK_ENTITY;
    public static BlockEntityType<TankBlockEntity> TANK_BLOCK_ENTITY;
    public static BlockEntityType<CastingTableBlockEntity> CASTING_TABLE_ENTITY;

    public static Block REINFORCED_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool());

    public static void init()
    {
        register(FORGE_BLOCK, "forge_block");
        register(REPAIR_ANVIL_BLOCK, "repair_anvil_block");
        register(ALLOY_SMELTERY_CONTROLLER, "alloy_smeltery_controller");
        register(ALLOY_SMELTERY_DRAIN, "alloy_smeltery_drain");
        register(ALLOY_SMELTERY_FAUCET, "alloy_smeltery_faucet");
        register(TANK_BLOCK, "tank_block");
        register(CASTING_TABLE_BLOCK, "casting_table_block");

        FORGE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "forge_block"), FabricBlockEntityTypeBuilder.create(ForgeBlockEntity::new, FORGE_BLOCK).build());
        REPAIR_ANVIL_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "repair_anvil_block"), FabricBlockEntityTypeBuilder.create(RepairAnvilBlockEntity::new, REPAIR_ANVIL_BLOCK).build());
        ALLOY_SMELTERY_CONTROLLER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "alloy_smeltery_controller_block"), FabricBlockEntityTypeBuilder.create(AlloySmelteryControllerBlockEntity::new, ALLOY_SMELTERY_CONTROLLER).build());
        ALLOY_SMELTERY_DRAIN_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "alloy_smeltery_drain"), FabricBlockEntityTypeBuilder.create(AlloySmelteryDrainBlockEntity::new, ALLOY_SMELTERY_DRAIN).build());
        ALLOY_SMELTERY_FAUCET_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "alloy_smeltery_faucet"), FabricBlockEntityTypeBuilder.create(AlloySmelteryFaucetBlockEntity::new, ALLOY_SMELTERY_FAUCET).build());
        TANK_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "tank"), FabricBlockEntityTypeBuilder.create(TankBlockEntity::new, TANK_BLOCK).build());
        CASTING_TABLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "casting_table"), FabricBlockEntityTypeBuilder.create(CastingTableBlockEntity::new, CASTING_TABLE_BLOCK).build());

        register(REINFORCED_BRICKS, "reinforced_bricks");

        ForgeAlloySmelteryRegistry.addStructureBlock(REINFORCED_BRICKS);
        ForgeAlloySmelteryRegistry.addFunctionalBlock(ALLOY_SMELTERY_DRAIN);
        ForgeAlloySmelteryRegistry.addTankBlock(TANK_BLOCK);
        ForgeAlloySmelteryRegistry.addFuelFluid(Fluids.LAVA, 1);

        FluidStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.master.fluidStorage, ALLOY_SMELTERY_DRAIN_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.fluidStorage, TANK_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> {
            if(direction.equals(Direction.UP))
            {
                return blockEntity.fluidStorage;
            }
            return null;
        }), CASTING_TABLE_ENTITY);
    }

    private static void register(Block block, String name)
    {
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, name), block);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, name), new BlockItem(block, new FabricItemSettings().group(ItemGroups.BLOCK_GROUP)));
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        BlockEntityRendererRegistry.register(FORGE_BLOCK_ENTITY, ForgeBlockEntityRender::new);
        BlockEntityRendererRegistry.register(REPAIR_ANVIL_BLOCK_ENTITY, RepairAnvilBlockEntityRender::new);
        BlockEntityRendererRegistry.register(ALLOY_SMELTERY_CONTROLLER_BLOCK_ENTITY, AlloySmelteryBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(TANK_BLOCK_ENTITY, TankBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ALLOY_SMELTERY_FAUCET_BLOCK_ENTITY, AlloySmelteryFaucetRenderer::new);
        BlockEntityRendererRegistry.register(CASTING_TABLE_ENTITY, CastingTableBlockEntityRender::new);

        BlockRenderLayerMap.INSTANCE.putBlock(TANK_BLOCK, RenderLayer.getCutout());
    }

}
