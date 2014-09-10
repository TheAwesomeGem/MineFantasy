package minefantasy.api.anvil;

import minefantasy.api.forge.TongsHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Simply implement this to tell blocks(like anvils and forges) to use these as tongs
 * You will need to add your own water cooling method
 */
public interface ITongs 
{
	/**
	 * Here is an example for Item using
	 * 
	 * 
	 * 
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

        if (movingobjectposition == null)
        {
            return item;
        }
        else
        {
            if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE)
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

                if (isWaterSource(world, i, j, k) && TongsHelper.getHeldItem(item) != null) (isWaterSource() just checks if it is water or cauldron)
                {
                	ItemStack drop = TongsHelper.getHeldItem(item).copy();
                	if(drop.getItem() instanceof IHotItem)
                	{
                		drop = TongsHelper.getHotItem(drop);
                		
                		player.playSound("random.splash", 1.0F, 1.0F);
                    	player.playSound("random.fizz", 2.0F, 0.5F);
                    	
                    	for(int a = 0; a < 5 ; a ++)
            	        {
            	        	world.spawnParticle("largesmoke", i+0.5F, j+1, k+0.5F, 0, 0.065F, 0);
            	        }
                	}
                	drop.stackSize = item.stackSize;
                	if(drop != null)
                	{
                		player.dropPlayerItem(drop);
                	}
                	
                	return TongsHelper.clearHeldItem(item, player);
                }
            }

            return item;
        }
    }
	 */
}
