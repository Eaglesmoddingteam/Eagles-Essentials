package btf.util.handlers;


import btf.init.BlockInit;
import btf.main.Main;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class OresHandler implements IWorldGenerator {
	//World Generators
	private WorldGenerator cobaltOre;
	private WorldGenerator copperOre;
	private WorldGenerator leadOre;
	private WorldGenerator platinumOre;
	private WorldGenerator tinOre;
	private WorldGenerator zincOre;

	public OresHandler() {
		cobaltOre = new WorldGenMinable(BlockInit.cobaltOre.getDefaultState(), 3);
		copperOre = new WorldGenMinable(BlockInit.copperOre.getDefaultState(), 8);
		leadOre = new WorldGenMinable(BlockInit.leadOre.getDefaultState(), 8);
		platinumOre = new WorldGenMinable(BlockInit.platinumOre.getDefaultState(), 3);
		tinOre = new WorldGenMinable(BlockInit.tinOre.getDefaultState(), 8);
		zincOre = new WorldGenMinable(BlockInit.zincOre.getDefaultState(), 8);
	}

	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunkX, int chunkZ,
	                          int chancesToSpawn, int minHeight, int maxHeight) {
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight) {
			Main.LOGGER.info("Received Illegal Height Arguments For Ore Generation.");
			return;
		}

		int heightDiff = maxHeight - minHeight + 1;
		for (int i = 0; i < chancesToSpawn; i++) {
			int x = chunkX * 16 + rand.nextInt(16);
			int y = maxHeight + rand.nextInt(heightDiff);
			int z = chunkZ * 16 + rand.nextInt(16);
			generator.generate(world, rand, new BlockPos(x, y, z));
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
			case 0: //Overworld
				runGenerator(cobaltOre, world, random, chunkX, chunkZ, 2, 5, 18);
				runGenerator(copperOre, world, random, chunkX, chunkZ, 4, 25, 68);
				runGenerator(leadOre, world, random, chunkX, chunkZ, 3, 13, 32);
				runGenerator(platinumOre, world, random, chunkX, chunkZ, 2, 5, 18);
				runGenerator(tinOre, world, random, chunkX, chunkZ, 5, 20, 68);
				runGenerator(zincOre, world, random, chunkX, chunkZ, 3, 13, 32);
		}
	}
}
