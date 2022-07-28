package com.sussysyrup.smcompat.rei;

import com.sussysyrup.smcompat.rei.display.CastingCategory;
import com.sussysyrup.smcompat.rei.display.CastingDisplay;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.casting.ApiCastingRegistry;
import com.sussysyrup.smitheesfoundry.api.casting.CastingResource;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import com.sussysyrup.smitheesfoundry.registry.ItemsRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SmitheeFoundryReiClient implements REIClientPlugin {

    public final static CategoryIdentifier<CastingDisplay> CASTING = CategoryIdentifier.of(
            Main.MODID,
            "casting"
    );

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registerCasting(registry);
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CastingCategory());

        registry.addWorkstations(CASTING, EntryStacks.of(BlocksRegistry.CASTING_TABLE_BLOCK.asItem()));
    }

    private void registerCasting(DisplayRegistry registry)
    {
        for(CastingResource resource : ApiCastingRegistry.typeCastingResourceMap.values())
        {
            long fluidValue = resource.fluidValue();

            CastingDisplay castingDisplay;
            Item item;

            for(Fluid fluid : resource.fluidItemMap().keySet())
            {
                item = resource.fluidItemMap().get(fluid);
                castingDisplay = new CastingDisplay(ApiCastingRegistry.getCastItem(ApiCastingRegistry.getTypeFromItem(item)), fluid, item, fluidValue);

                registry.add(castingDisplay);
            }
        }

        Fluid goldFluid = Registry.FLUID.get(new Identifier(Main.MODID, "molten_gold"));

        for(Item item : ApiCastingRegistry.itemTypeMap.keySet())
        {
            registry.add(new CastingDisplay(item, goldFluid, ApiCastingRegistry.getCastItem(ApiCastingRegistry.getTypeFromItem(item)), FluidConstants.INGOT * 2));
        }

        registry.add(new CastingDisplay(Items.AIR, goldFluid, ItemsRegistry.BLANK_CAST, FluidConstants.INGOT * 2));
    }
}
