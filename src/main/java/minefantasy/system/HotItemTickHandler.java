package minefantasy.system;

import java.util.EnumSet;
import java.util.Random;


import minefantasy.api.MineFantasyAPI;
import minefantasy.item.ItemBloom;
import minefantasy.item.ItemHotItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class HotItemTickHandler implements ITickHandler {

	Random rand = new Random();
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (!type.contains(TickType.PLAYER)) return;
        
        EntityLivingBase user = (EntityLivingBase) tickData[0];
        
        if (cfg.burnPlayer && user != null && user.ticksExisted % 20 == 0)
        {
        	if(user instanceof EntityPlayer)
        	{
        		burnInv((EntityPlayer)user);
        	}
        	else if(isHotItem(user.getHeldItem()))
        	{
        		user.attackEntityFrom(DamageSource.inFire, 1);
        		user.playSound("random.fizz", 1.0F, 1.0F);
        	}
        		
        }
	}

	private void burnInv(EntityPlayer player) 
	{
		boolean hasBurnt = false;
		for(int a = 0; a < player.inventory.getSizeInventory(); a ++)
		{
			ItemStack item = player.inventory.getStackInSlot(a);
			if(item != null)
			{
				if(isHotItem(item))
				{
					if(!hasBurnt)
					{
						player.attackEntityFrom(DamageSource.onFire, 1);
		        		player.playSound("random.fizz", 1.0F, 1.0F);
					}
					hasBurnt = true;
					
					if(!player.worldObj.isRemote)
					{
						player.entityDropItem(item, 1.0F);
						player.inventory.setInventorySlotContents(a, null);
					}
				}
			}
		}
	}

	private boolean isHotItem(ItemStack item) 
	{
		return item != null && (item.getItem() instanceof ItemHotItem || item.getItem() instanceof ItemBloom || MineFantasyAPI.isHotToPickup(item));
	}

	@Override
    public EnumSet<TickType> ticks()
    {
            return EnumSet.of(TickType.PLAYER);
    }
    
    @Override
    public String getLabel()
    {
            return "Hot Item Tick Handler";
    }
    
  
}
