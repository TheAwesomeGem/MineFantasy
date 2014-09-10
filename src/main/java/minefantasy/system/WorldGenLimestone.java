package minefantasy.system;

import java.util.Random;

import minefantasy.block.BlockListMF;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;


public class WorldGenLimestone extends WorldGenerator
{
    /** The block ID of the ore to be placed using this generator. */
    private int minableBlockMeta = 0;

    /** The number of blocks to generate. */
    private int limestoneSize;
    private int submergeDepth;
    private int height;
    private boolean sandOnly;

    public WorldGenLimestone(int under, int over, int size)
    {
        this.limestoneSize = size;
        submergeDepth = under;
        height = over;
    }

    public boolean generate(World world, Random random, int i, int j, int k)
    {
        boolean flag = true;
        if(j < 1 || j + height + 1 > 256)
        {
            return false;
        }
        for(int i1 = j; i1 <= j + 1 + height; i1++)
        {
            byte byte0 = 1;
            if(i1 == j)
            {
                byte0 = 0;
            }
            if(i1 >= (j + 1 + height) - 2)
            {
                byte0 = 2;
            }
            for(int i2 = i - byte0; i2 <= i + byte0 && flag; i2++)
            {
                for(int l2 = k - byte0; l2 <= k + byte0 && flag; l2++)
                {
                    if(i1 >= 0 && i1 < 256)
                    {
                        int j3 = world.getBlockId(i2, i1, l2);
                        if(j3 != 0 && j3 != BlockListMF.limestone.blockID)  /////Leaf/////
                        {
                            flag = false;
                        }
                    } else
                    {
                        flag = false;
                    }
                }

            }

        }

        if(!flag)
        {
            return false;
        }
        int j1 = world.getBlockId(i, j - 1, k);
        if(j1 != Block.sand.blockID && j1 != Block.grass.blockID && j1 != Block.dirt.blockID || j >= 256 - height - 1)
        {
            return false;
        }
        if(sandOnly && (j1 == Block.grass.blockID && j1 == Block.dirt.blockID))
        {
        	return false;
        }
        generateLimestone(world, random, i, j - 1, k); 
        return true;
    }
    public WorldGenLimestone sandOnly()
    {
    	sandOnly = true;
    	return this;
    }
    
    private void generateLimestone(World world, Random random, int x, int y,
			int z) {
    	for(int t = -submergeDepth; t < height; t ++)
    	new WorldGenLimestoneRock(limestoneSize).generate(world, random, x, y-t, z);
	}

	private void initGeneration(World world, Random rand, int x, int y, int z) {
		world.setBlock(x, y, z, BlockListMF.limestone.blockID);
		
	}

	public boolean canBuildOff(World world, int x, int y, int z)
    {
    	return world.getBlockId(x, y, z) == Block.sand.blockID
    		   || world.getBlockId(x, y, z) == Block.grass.blockID;
    }
}
