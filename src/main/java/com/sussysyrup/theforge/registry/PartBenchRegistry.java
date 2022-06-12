package com.sussysyrup.theforge.registry;

import com.sussysyrup.theforge.Main;
import com.sussysyrup.theforge.api.block.ForgePartBenchRegistry;
import net.minecraft.util.Identifier;

public class PartBenchRegistry {

    public static void init()
    {
        ForgePartBenchRegistry.registerPartBenchWood(new Identifier(Main.MODID, "oak_part_bench_block"), new Identifier("oak"));
        ForgePartBenchRegistry.registerPartBenchWood(new Identifier(Main.MODID, "spruce_part_bench_block"), new Identifier("spruce"));
        ForgePartBenchRegistry.registerPartBenchWood(new Identifier(Main.MODID, "birch_part_bench_block"), new Identifier("birch"));
        ForgePartBenchRegistry.registerPartBenchWood(new Identifier(Main.MODID, "jungle_part_bench_block"), new Identifier("jungle"));
        ForgePartBenchRegistry.registerPartBenchWood(new Identifier(Main.MODID, "birch_part_bench_block"), new Identifier("birch"));
        ForgePartBenchRegistry.registerPartBenchWood(new Identifier(Main.MODID, "dark_oak_part_bench_block"), new Identifier("dark_oak"));
    }
}
