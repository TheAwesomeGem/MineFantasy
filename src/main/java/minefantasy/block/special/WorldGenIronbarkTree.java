package minefantasy.block.special;

import java.util.Random;

import minefantasy.block.BlockListMF;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;


public class WorldGenIronbarkTree extends WorldGenerator
{
	public WorldGenIronbarkTree(boolean notify)
	{		
		super(notify);
	}
	public WorldGenIronbarkTree()
	{		
		super(false);
	}
	
	public boolean generate(World world, Random random, int i, int j, int k)
    {
        int height = random.nextInt(5) + 8;
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
                        if(j3 != 0 && j3 != BlockListMF.leaves.blockID)  /////Leaf/////
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
        if(j1 != Block.grass.blockID && j1 != Block.dirt.blockID || j >= 256 - height - 1)
        	//sand = which block CAN the tree spawn on
        {
            return false;
        }
        world.setBlock(i, j - 1, k, Block.dirt.blockID);   //dirt = block created underneath the tree
        for(int k1 = (j - 3) + height; k1 <= j + height; k1++)
        {
            int j2 = k1 - (j + height);
            int i3 = 1 - j2 / 2;
            for(int k3 = i - i3; k3 <= i + i3; k3++)
            {
                int l3 = k3 - i;
                for(int i4 = k - i3; i4 <= k + i3; i4++)
                {
                    int j4 = i4 - k;
                    if((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && !Block.opaqueCubeLookup[world.getBlockId(k3, k1, i4)])
                    {
                    	 setBlockAndMetadata(world, k3, k1, i4, BlockListMF.leaves.blockID, 0);  //Leaf////
                    }
                }

            }

        }

        for(int l1 = 0; l1 < height; l1++)
        {
            int k2 = world.getBlockId(i, j + l1, k);
            if(k2 == 0 || k2 == BlockListMF.leaves.blockID) ////Leaf/////
            {
            	 setBlockAndMetadata(world, i, j + l1, k, BlockListMF.log.blockID, 0);   //////Log////
            }
        }

        return true;
    }
}
