package minefantasy.system;

import cpw.mods.fml.common.network.PacketDispatcher;
import minefantasy.MineFantasyBase;
import minefantasy.api.arrow.Arrows;
import minefantasy.item.weapon.ItemBowMF;
import minefantasy.system.network.PacketManagerMF;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ArrowHandlerMF 
{
	@ForgeSubscribe
	/**
	 * When the arrow is pulled back it initiates
	 */
	public void readyBow(ArrowNockEvent event)
	{
		if(MineFantasyBase.isBGLoaded())
		{
			return;
		}
		
		if(Arrows.arrows == null || Arrows.arrows.size() <= 0)
		{
			return;
		}
		
		EntityPlayer user = event.entityPlayer;
		ItemStack bow = event.result;
		
		/*Checks over registered arrows and finds one to load
		* The Quiver can be used to determine this
		*/
		for(int a = 0; a < Arrows.arrows.size(); a ++)
		{
			ItemStack arrow = Arrows.arrows.get(a);
			if(user.inventory.hasItemStack(arrow))
			{
				user.setItemInUse(bow, bow.getMaxItemUseDuration());//Starts pullback
				loadArrow(user, bow, arrow);//adds the arrow to NBT for rendering and later use
				event.setCanceled(true);
				return;
			}
		}
	}
	
	/**
	 * This method adds arrows to the bow
	 */
	public static void loadArrow(EntityPlayer player, ItemStack bow, ItemStack arrow)
	{
		NBTTagCompound nbt = getOrApplyNBT(bow);
		
		if(arrow == null)
		{
			nbt.removeTag("loadedArrow");
		}
		else
		{
			NBTTagCompound loaded = new NBTTagCompound();
			arrow.writeToNBT(loaded);
			nbt.setTag("loadedArrow", loaded);
		}
	}
	
	/**
	 * Gets the NBT, if there is none, it creates it
	 */
	private static NBTTagCompound getOrApplyNBT(ItemStack bow) 
	{
		if(!bow.hasTagCompound())
		{
			bow.setTagCompound(new NBTTagCompound());
		}
		return bow.getTagCompound();
	}
	
	@ForgeSubscribe
	public void fireArrow(ArrowLooseEvent event)
	{
		if(MineFantasyBase.isBGLoaded())
		{
			return;
		}
		
		
		ItemStack bow = event.bow;
		float power = event.charge;
		EntityPlayer user = event.entityPlayer;
		World world = event.entity.worldObj;
		boolean infinite = user.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, bow) > 0;
		
		float charge = (float)power / 20.0F;
        charge = (charge * charge + charge * 2.0F) / 3.0F;

        if ((double)charge < 0.1D)
        {
            return;
        }

        if (charge > 1.0F)
        {
            charge = 1.0F;
        }
        
        
		//Default is flint arrow
		ItemStack arrow = new ItemStack(Item.arrow);
		if(Arrows.getLoadedArrow(bow) != null)
		{
			//if an arrow is on the bow, it uses that
			arrow = Arrows.getLoadedArrow(bow);
		}
		
		if(Arrows.handlers != null && Arrows.handlers.size() > 0)
		{
			for(int a = 0; a < Arrows.handlers.size(); a ++)
			{
				//If the Arrow handler succeeds at firing an arrow
				if(Arrows.handlers.get(a).onFireArrow(world, arrow, bow, user, charge, infinite))
				{
					if(!user.capabilities.isCreativeMode)
		            {
		            	bow.damageItem(1, user);
		            }
					if(!infinite)
					{
						consumePlayerItem(user, arrow);
					}
					world.playSoundAtEntity(user, "random.bow", 0.5F, 1.0F / (world.rand.nextFloat() * 0.4F + 1.2F) + charge * 0.5F);
					loadArrow(user, bow, null);
					event.setCanceled(true);
					break;
				}
			}
		}
	}
	//Used to take an item/subId from the inventory
	private boolean consumePlayerItem(EntityPlayer player, ItemStack item) 
    {
    	for(int a = 0; a < player.inventory.getSizeInventory(); a ++)
    	{
    		ItemStack i = player.inventory.getStackInSlot(a);
    		if(i != null && i.isItemEqual(item))
    		{
    			player.inventory.decrStackSize(a, 1);
    			return true;
    		}
    	}
    	return false;
	}
}
