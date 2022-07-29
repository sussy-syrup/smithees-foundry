package com.sussysyrup.smcompat.rei;

import com.google.common.collect.ArrayListMultimap;
import com.sussysyrup.smcompat.rei.display.AlloyingCategory;
import com.sussysyrup.smcompat.rei.display.AlloyingDisplay;
import com.sussysyrup.smcompat.rei.display.CastingCategory;
import com.sussysyrup.smcompat.rei.display.CastingDisplay;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.casting.ApiCastingRegistry;
import com.sussysyrup.smitheesfoundry.api.casting.CastingResource;
import com.sussysyrup.smitheesfoundry.api.fluid.AlloyResource;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiAlloyRegistry;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import com.sussysyrup.smitheesfoundry.registry.ItemsRegistry;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class SmitheeFoundryReiClient implements REIClientPlugin {

    public final static CategoryIdentifier<CastingDisplay> CASTING = CategoryIdentifier.of(
            Main.MODID,
            "casting"
    );

    public final static CategoryIdentifier<AlloyingDisplay> ALLOYING = CategoryIdentifier.of(
            Main.MODID,
            "alloying"
    );

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registerCasting(registry);
        registerAlloying(registry);
    }

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new CastingCategory());
        registry.add(new AlloyingCategory());

        registry.addWorkstations(CASTING, EntryStacks.of(BlocksRegistry.CASTING_TABLE_BLOCK.asItem()));
        registry.addWorkstations(ALLOYING, EntryStacks.of(BlocksRegistry.ALLOY_SMELTERY_CONTROLLER.asItem()));
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

    private void registerAlloying(DisplayRegistry registry)
    {
        ArrayListMultimap<Fluid, AlloyResource> alloyMap = ApiAlloyRegistry.multimap;

        for(Fluid fluid : alloyMap.keySet())
        {
            for(AlloyResource resource : alloyMap.get(fluid))
            {
                List<EntryIngredient> input = new ArrayList<>();
                List<EntryIngredient> output = new ArrayList<>();

                AlloyResource next = resource.alloyResourceOut();
                AlloyResource next2 = resource.alloyResourceOut();

                input.add(EntryIngredients.of(resource.keyFluid(), resource.keyFluidAmount()));

                while(true)
                {
                    next = next.alloyResourceOut();

                    if(next == null)
                    {
                        output.add(EntryIngredients.of(next2.fluidOut(), next2.fluidOutAmount()));
                        input.add(EntryIngredients.of(next2.keyFluid(), next2.keyFluidAmount()));
                        break;
                    }
                    if(next != null)
                    {
                        input.add(EntryIngredients.of(next.keyFluid(), next.keyFluidAmount()));
                    }
                    next2 = next;
                }

                registry.add(new AlloyingDisplay(input, output));
            }
        }
    }
}
