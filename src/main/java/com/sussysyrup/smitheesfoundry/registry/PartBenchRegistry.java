package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.block.ApiPartBenchRegistry;
import net.minecraft.util.Identifier;

public class PartBenchRegistry {

    public static void main()
    {
        ApiPartBenchRegistry.registerPartBenchWood(new Identifier(Main.MODID, "oak_part_bench_block"), new Identifier("oak"));
        ApiPartBenchRegistry.registerPartBenchWood(new Identifier(Main.MODID, "spruce_part_bench_block"), new Identifier("spruce"));
        ApiPartBenchRegistry.registerPartBenchWood(new Identifier(Main.MODID, "birch_part_bench_block"), new Identifier("birch"));
        ApiPartBenchRegistry.registerPartBenchWood(new Identifier(Main.MODID, "jungle_part_bench_block"), new Identifier("jungle"));
        ApiPartBenchRegistry.registerPartBenchWood(new Identifier(Main.MODID, "birch_part_bench_block"), new Identifier("birch"));
        ApiPartBenchRegistry.registerPartBenchWood(new Identifier(Main.MODID, "dark_oak_part_bench_block"), new Identifier("dark_oak"));
    }
}
