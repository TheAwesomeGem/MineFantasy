package minefantasy.system;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS;

import java.util.ConcurrentModificationException;
import java.util.Random;

import minefantasy.block.BlockListMF;
import minefantasy.block.special.WorldGenIronbarkTree;
import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.event.terraingen.TerrainGen;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenMF implements IWorldGenerator{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		
		generateMisc(random, chunkX, chunkZ, world);
		generateOres(random, chunkX, chunkZ, world, false);
		
	}
	public void generateOres(Random random, int chunkX, int chunkZ, World world, boolean HC) 
	{
		try
		{
			/**BiomeDecorator
			 * Vanilla Veins: size, abundance, minY, maxY
			 * Coal: 16, 20, 0, 128
			 * Iron: 8, 20, 0, 64
			 * Gold: 8, 2, 0, 32
			 * Lapis: 6, 1, 16, 16
			 * Diamond: 7, 1, 0, 16
			 * Red: 7, 8, 0, 16
			 */
			if(random.nextInt(250) == 0 && cfg.spawnIgnot)
			{
				addOre(random, chunkX, chunkZ, world,
				BlockListMF.oreIgnotumite.blockID, 0, 8, 1, 0, 16);
			}
			
			if (cfg.spawnSilver) 
			{
				addOre(random, chunkX, chunkZ, world,
				BlockListMF.oreUtil.blockID, 0, 8, 2, 0, 32);
			}
			if (cfg.spawnNitre) 
			{
				addOreWithNeighbour(random, chunkX, chunkZ, world,
				BlockListMF.oreUtil.blockID, 1, 8, 4, 0, 64, Block.stone.blockID, 0);
			}
			if (cfg.spawnSulfur) 
			{
				addOre(random, chunkX, chunkZ, world,
				BlockListMF.oreUtil.blockID, 2, 6, 3, 0, 16);
			}

			if (cfg.spawnCopper)
			{
				addOre(random, chunkX, chunkZ, world,
				BlockListMF.oreCopper.blockID, 0, 8, 10, 0, 96);
			}

			if (cfg.spawnTin)
			{
				addOre(random, chunkX, chunkZ, world,
				BlockListMF.oreTin.blockID, 0, 8, 7, 0, 96);
			}
			
			if (cfg.spawnMithril) 
			{
				addRangedOre(random, chunkX, chunkZ, world,
				BlockListMF.oreMythic.blockID, 0, 6, 2, 0, 16, Block.stone.blockID, cfg.mithrilDistance);
			}
			if (cfg.spawnDeepIron) 
			{
				addOre(random, chunkX, chunkZ, world,
				BlockListMF.oreMythic.blockID, 1, 8, 3, 0, 32);
				
				addOre(random, chunkX, chunkZ, world,
						BlockListMF.oreMythic.blockID, 2, 8, 5, 0, 128, Block.netherrack.blockID);
			}
			addOre(random, chunkX, chunkZ, world,
					BlockListMF.oreInferno.blockID, 0, 12, 10, 0, 128, Block.netherrack.blockID);
			addOre(random, chunkX, chunkZ, world,
					BlockListMF.oreInferno.blockID, 1, 10, 6, 0, 64, Block.netherrack.blockID);
			
		} catch(ConcurrentModificationException e)
		{
			System.err.println("MineFantasy: WorldGen Failed");
		}

	}
	
	private void addOre(Random rand, int chunkX, int chunkZ, World world,
			int id, int meta, int size, int abundance, int min, int max)
	{
		addOre(rand, chunkX, chunkZ, world, id, meta, size, abundance, min, max, Block.stone.blockID);
	}
	private void addOre(Random rand, int chunkX, int chunkZ, World world,
			int id, int meta, int size, int abundance, int min, int max, int inside)
	{
		for(int a = 0; a < abundance; a ++)
		{
			int x = chunkX*16 + rand.nextInt(16);
			int y = min + rand.nextInt(max-min+1);
			int z = chunkZ*16 + rand.nextInt(16);
			(new WorldGenMinable(id, meta, size, inside)).generate(world, rand, x, y, z);
		}
	}
	
	private void addRangedOre(Random rand, int chunkX, int chunkZ, World world,
			int id, int meta, int size, int abundance, int min, int max, int inside, double range)
	{
		for(int a = 0; a < abundance; a ++)
		{
			int x = chunkX*16 + rand.nextInt(16);
			int y = min + rand.nextInt(max-min+1);
			int z = chunkZ*16 + rand.nextInt(16);
			if(getDistance(world, x, z) > range)
			{
				(new WorldGenMinable(id, meta, size, inside)).generate(world, rand, x, y, z);
			}
		}
	}
	
	private double getDistance(World world, int x, int z) 
	{
		ChunkCoordinates spawn = world.getSpawnPoint();
		
		int xd = x - spawn.posX;
		int zd = z - spawn.posZ;
		
		if(xd < 0)xd = -xd;
		if(zd < 0)zd = -zd;
		
		double dist = Math.hypot((double)xd, (double)zd);
		
		return dist;
	}
	private void addOreWithNeighbour(Random rand, int chunkX, int chunkZ, World world, int id, int meta, int size, int abundance, int min, int max, int inside, int neighbour)
	{
		for(int a = 0; a < abundance; a ++)
		{
			int x = chunkX*16 + rand.nextInt(16);
			int y = min + rand.nextInt(max-min+1);
			int z = chunkZ*16 + rand.nextInt(16);
			
			if(isNeibourNear(world, x, y, z, neighbour))
			{
				if(neighbour == Block.lavaStill.blockID)
				{
					System.out.println("Gen By Lava: " + x + " " + y + " " + z);
				}
				(new WorldGenMinable(id, meta, size, inside)).generate(world, rand, x, y, z);
			}
		}
	}
	
	private boolean isNeibourNear(World world, int x, int y, int z, int neighbour) 
	{
		return world.getBlockId(x-1, y, z) == neighbour
			|| world.getBlockId(+1, y, z) == neighbour
			|| world.getBlockId(x, y-1, z) == neighbour
			|| world.getBlockId(x, y+1, z) == neighbour
			|| world.getBlockId(x, y, z-1) == neighbour
			|| world.getBlockId(x, y, z+1) == neighbour;
	}
	public void generateMisc(Random random, int chunkX, int chunkZ, World world) {
		try
		{
			if (random.nextInt(100) == 0) {
				for (int k = 0; k < 1; k++) {
					int k1 = chunkX*16 + random.nextInt(16);
					int k2 = random.nextInt(32);
					int k3 = chunkZ*16 + random.nextInt(16);
					(new WorldGenMinable(BlockListMF.granite.blockID, 128))
							.generate(world, random, k1, k2, k3);
				}
			}
			
			if (cfg.generateSlate && random.nextInt(cfg.slateSpawnRate) == 0) {
				for (int k = 0; k < 1; k++) {
					int k1 = chunkX*16 + random.nextInt(16);
					int k2 = random.nextInt(64);
					int k3 = chunkZ*16 + random.nextInt(16);
					(new WorldGenMinable(BlockListMF.slate.blockID, 0, 64, Block.stone.blockID))
							.generate(world, random, k1, k2, k3);
				}
			}
			
			BiomeGenBase b = world.getBiomeGenForCoords(chunkX*16, chunkZ*16);
			
			if (BiomeDictionary.isBiomeOfType(b, Type.MOUNTAIN)) {
				for (int k = 0; k < 1; k++) {
					int k1 = chunkX*16 + random.nextInt(16);
					int k2 = random.nextInt(128);
					int k3 = chunkZ*16 + random.nextInt(16);
					(new WorldGenMinable(BlockListMF.granite.blockID, 64))
							.generate(world, random, k1, k2, k3);
				}
			}
			

			if(cfg.spawnIBark)
            if(BiomeDictionary.isBiomeOfType(b, Type.JUNGLE)) {
            	if(random.nextInt(100) < 15)
            	for (int x = 0; x < 3+2; x++) {
					int Xcoord = chunkX*16 + random.nextInt(16);
					int Zcoord = chunkZ*16 + random.nextInt(16);
					int i2 = world.getHeightValue(Xcoord, Zcoord);
					new WorldGenIronbarkTree().generate(world, random, Xcoord, i2, Zcoord);
					//System.out.println("Gen Tree: " + b.biomeName);
				}
            }
			if(cfg.spawnIBark)
            if(BiomeDictionary.isBiomeOfType(b, Type.FOREST))
            {
            	if(random.nextInt(100) < 5)
            	for (int x = 0; x < 3; x++) {
					int Xcoord = chunkX*16 + random.nextInt(16);
					int Zcoord = chunkZ*16 + random.nextInt(16);
					int i2 = world.getHeightValue(Xcoord, Zcoord);
					new WorldGenIronbarkTree().generate(world, random, Xcoord, i2, Zcoord);
					//System.out.println("Gen Tree: " + b.biomeName + " Gen " + Xcoord + " " +  Zcoord + " Chunk " + chunkX + " " +  chunkZ);
				}
            }
            
			if(cfg.spawnEbony)
            if(BiomeDictionary.isBiomeOfType(b, Type.JUNGLE)) 
            {
            	if(random.nextInt(100) < 1)
            	{
	            	for (int x = 0; x < 1; x++) 
	            	{
						int Xcoord = chunkX*16 + random.nextInt(16);
						int Zcoord = chunkZ*16 + random.nextInt(16);
						int i2 = world.getHeightValue(Xcoord, Zcoord);
						new WorldGenEbony(false).generate(world, random, Xcoord, i2, Zcoord);
						//System.out.println("Gen Tree: " + b.biomeName);
					}
            	}
            }
			
			if(cfg.spawnEbony)
            if(BiomeDictionary.isBiomeOfType(b, Type.FOREST)) 
            {
            	if(random.nextInt(250) < 1)
            	{
	            	for (int x = 0; x < 1; x++) 
	            	{
						int Xcoord = chunkX*16 + random.nextInt(16);
						int Zcoord = chunkZ*16 + random.nextInt(16);
						int i2 = world.getHeightValue(Xcoord, Zcoord);
						new WorldGenEbony(false).generate(world, random, Xcoord, i2, Zcoord);
						//System.out.println("Gen Tree: " + b.biomeName + " Gen " + Xcoord + " " +  Zcoord + " Chunk " + chunkX + " " +  chunkZ);
					}
            	}
            }
            
            if(BiomeDictionary.isBiomeOfType(b, Type.WATER)) {
            	if(random.nextInt(100) < 10)
            	for (int x = 0; x < 1; x++) {
					int Xcoord = chunkX*16 + random.nextInt(16);
					int Zcoord = chunkZ*16 + random.nextInt(16);
					int i2 = world.getHeightValue(Xcoord, Zcoord);
					new WorldGenLimestone(4, 8, 12).generate(world, random, Xcoord, i2, Zcoord);
					//System.out.println("Gen Tree: " + b.biomeName + " Gen " + Xcoord + " " +  Zcoord + " Chunk " + chunkX + " " +  chunkZ);
				}
            }
            
            if(BiomeDictionary.isBiomeOfType(b, Type.SWAMP)) {
            	if(random.nextInt(100) < 8)
            	for (int x = 0; x < 1; x++) {
					int Xcoord = chunkX*16 + random.nextInt(16);
					int Zcoord = chunkZ*16 + random.nextInt(16);
					int i2 = world.getHeightValue(Xcoord, Zcoord);
					new WorldGenLimestone(4, 5, 10).generate(world, random, Xcoord, i2, Zcoord);
					//System.out.println("Gen Tree: " + b.biomeName + " Gen " + Xcoord + " " +  Zcoord + " Chunk " + chunkX + " " +  chunkZ);
				}
            }
            
            if(BiomeDictionary.isBiomeOfType(b, Type.BEACH)) {
            	if(random.nextInt(100) < 5)
            	for (int x = 0; x < 1; x++) {
					int Xcoord = chunkX*16 + random.nextInt(16);
					int Zcoord = chunkZ*16 + random.nextInt(16);
					int i2 = world.getHeightValue(Xcoord, Zcoord);
					new WorldGenLimestone(4, 4, 8).generate(world, random, Xcoord, i2, Zcoord);
					//System.out.println("Gen Tree: " + b.biomeName + " Gen " + Xcoord + " " +  Zcoord + " Chunk " + chunkX + " " +  chunkZ);
				}
            }

            if (cfg.limeCavern && random.nextInt(cfg.limestoneSpawnRate) == 0) {
				for (int k = 0; k < 1; k++) {
					int k1 = chunkX*16 + random.nextInt(16);
					int k2 = 32+random.nextInt(64);
					int k3 = chunkZ*16 + random.nextInt(16);
					(new WorldGenHole(48))
					.generate(world, random, k1, k2+8, k3);
					
					(new WorldGenHole(64))
					.generate(world, random, k1, k2, k3);
					
					(new WorldGenMinable(BlockListMF.limestone.blockID, 128))
							.generate(world, random, k1, k2, k3);
					
				}
			}
		} catch(ConcurrentModificationException e)
		{
			System.err.println("MineFantasy: WorldGen Failed");
		}

	}
	
}
