package minefantasy.api.weapon;

import java.util.ArrayList;
import java.util.List;

import minefantasy.api.Components;
import minefantasy.api.MineFantasyAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CrossbowAmmo {
	private static List<ItemStack>bolts = new ArrayList();
	private static List<ItemStack>arrows = new ArrayList();
	private static List<ICrossbowHandler> handlers = new ArrayList();
	
	
	public static void addCrossbowAmmo(int id, EnumAmmo type)
	{
		addCrossbowAmmo(new ItemStack(id, 1, 0), type);
	}
	/**
	 * Creates an Ammo item
	 * @param id the item
	 * @param type Weather it's classed as a bolt or arrow
	 * 
	 * Make sure to add an arrow instance for every arrow used(since crossbows work
	 * differently; they need a seperate arrow registration)
	 */
	public static void addCrossbowAmmo(ItemStack arrow, EnumAmmo type)
	{
		if(type == EnumAmmo.ARROW)
		{
			arrows.add(arrow);
		}
		else
		{
			bolts.add(arrow);
		}
	}
	
	/**
	 * Creates an arrow Ammo item
	 * @param id the item ID(sub ids don't work with the system)
	 * 
	 * * Make sure to add an arrow instance for every arrow used(since crossbows work
	 * differently; they need a seperate arrow registration)
	 */
	public static void addArrow(int id)
	{
		addCrossbowAmmo(id, EnumAmmo.ARROW);
	}
	public static void addArrow(ItemStack id)
	{
		addCrossbowAmmo(id, EnumAmmo.ARROW);
	}
	/**
	 * Creates a bolt Ammo item
	 * @param id the item ID(sub ids don't work with the system)
	 */
	public static void addBolt(int id)
	{
		addCrossbowAmmo(id, EnumAmmo.BOLT);
	}
	public static void addBolt(ItemStack id)
	{
		addCrossbowAmmo(id, EnumAmmo.BOLT);
	}
	
	public static boolean canLoadArrow(EntityPlayer player, ItemStack arrow, boolean infinite)
	{
		if(infinite)
		{
			return true;
		}
		
		if(arrows.contains(arrow))
		{
			boolean hasInfiniteItem = player.inventory.hasItemStack(arrow) && player.capabilities.isCreativeMode;
			
			if(hasInfiniteItem || consumePlayerItem(player, arrow))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean canLoadBolt(EntityPlayer player, ItemStack bolt, boolean infinite)
	{
		if(infinite)
			return true;
		if(bolts.contains(bolt))
		{
			boolean hasInfiniteItem = player.inventory.hasItemStack(bolt) && player.capabilities.isCreativeMode;
			if(hasInfiniteItem || consumePlayerItem(player, bolt))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks to load an arrow
	 * @param player the player that is loading
	 * @return the item that has been loaded, -1 if failed
	 */
	public static ItemStack tryLoadArrow(EntityPlayer player, boolean infinite)
	{
		if(arrows.isEmpty())return null;
		
		for(ItemStack arrow: arrows)
		{
			if(canLoadArrow(player, arrow, infinite))
			{
				return arrow;
			}
		}
		
		if(player.capabilities.isCreativeMode)
		{
			return new ItemStack(Item.arrow);
		}
		
		if(infinite)
		{
			return new ItemStack(Item.arrow);
		}
		return null;
	}
	
	/**
	 * Checks to load a bolt
	 * @param player the player that is loading
	 * @return the itemID that has been loaded, -1 if failed
	 */
	public static ItemStack tryLoadBolt(EntityPlayer player, boolean infinite)
	{
		if(bolts.isEmpty())return null;
		
		for(ItemStack bolt: bolts)
		{
			if(canLoadBolt(player, bolt, infinite))
			{
				return bolt;
			}
		}
		
		if(infinite || player.capabilities.isCreativeMode)
		{
			if(MineFantasyAPI.isModLoaded())
			{
				return Components.getItem("boltMF", 2);
			}else
			return new ItemStack(Item.arrow);
		}
		return null;
	}

	/**
	 * @return true if the player has a loadable arrow
	 */
	public static boolean hasArrow(EntityPlayer player) 
	{
		if(arrows.isEmpty())return false;
		
		for(ItemStack arrow: arrows)
		{
			if(player.inventory.hasItemStack(arrow))
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * @return true if the player has a loadable arrow
	 */
	public static boolean hasBolt(EntityPlayer player) 
	{
		if(bolts.isEmpty())return false;
		
		for(ItemStack bolt: bolts)
		{
			if(player.inventory.hasItemStack(bolt))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a crossbow handler
	 * @param handler the handler used
	 */
	public static void addCrossbowHandler(ICrossbowHandler handler)
	{
		handlers.add(handler);
	}

	public static boolean shoot(ItemStack item, World world, EntityPlayer player, float accuracy, float power, ItemStack load) {
		if(load == null)
		{
			return false;
		}
		for(ICrossbowHandler handler : handlers)
		{
			if(handler.shoot(item, world, player, accuracy, power, load))
				return true;
		}
		return false;
	}
	private static boolean consumePlayerItem(EntityPlayer player, ItemStack item) 
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
