package net.nextinfinity.core.arena;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

/**
 * ChunkGenerator for an empty/void world. This forms the basis for pasting schematics
 */
public class ArenaChunkGenerator extends ChunkGenerator {

	@Override
	public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
		return createChunkData(world);
	}

}
