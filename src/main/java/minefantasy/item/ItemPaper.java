package minefantasy.item;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ItemPaper extends Item {
    
    public ItemPaper(int i) {
        super(i);
        setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
    	Random rand = new Random();
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null)
        {
            return item;
        }
        else
        {
            if (!world.isRemote && movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
            {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!world.canMineBlock(player, i, j, k))
                {
                    return item;
                }

                if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item))
                {
                    return item;
                }

                if (isSaltWaterSource(world, i, j, k))
                {
                	world.playSoundAtEntity(player, "random.splash", 0.125F + rand.nextFloat()/4, 0.5F + rand.nextFloat());
                    item.stackSize --;
                    EntityItem result = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ItemListMF.misc, 1, ItemListMF.saltPaper));
                    world.spawnEntityInWorld(result);
                }
            }
        }
        return item;
    }
    
    
    private boolean isSaltWaterSource(World world, int i, int j, int k) 
    {
    	for(int x = -1; x <= 1; x ++)
    	{
			for(int z = -1; z <= 1; z ++)
	    	{
				if(!isProperSource(world, i+x, j, k+z))
				{
					return false;
				}
	    	}	
    	}
		return true;
	}

	private boolean isProperSource(World world, int x, int y, int z) 
	{
		if(world.getBlockMaterial(x, y, z) != Material.water)
		{
			return false;
		}
		return isSandBedded(world, x, y, z);
	}

	private boolean isSandBedded(World world, int x, int y, int z) 
	{
		for(int y1 = y; y1 >= 0; y1 --)
		{
			if(world.getBlockMaterial(x, y1, z) == Material.sand)
			{
				if(!world.isRemote)
				{
					System.out.println("Sand " + x + " " + y + " " + z);
				}
				return true;
			}
			else if(world.getBlockMaterial(x, y1, z) != Material.water)
			{
				return false;
			}
		}
		return false;
	}
}
