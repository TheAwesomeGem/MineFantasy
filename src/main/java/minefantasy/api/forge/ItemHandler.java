package minefantasy.api.forge;

import java.util.ArrayList;
import java.util.List;

import minefantasy.api.refine.BlastFurnaceFuel;
import minefantasy.api.refine.FluxItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHandler {
	public static List<ItemStack>flux = new ArrayList();
	public static List<BlastFurnaceFuel>BlastFuel = new ArrayList();
	public static List<ForgeFuel>forgeFuel = new ArrayList();
	public static List<ItemStack>carbon = new ArrayList();
	public static int forgeMaxTemp = 0;
	public static List<FluxItem>fluxes = new ArrayList();;
	
	
	
	public static void addFluxRecipe(ItemStack in, int amount)
	{
		fluxes.add(new FluxItem(in, amount));
	}
	/**
	 * Determines if a specific Item and subId is a flux item
	 * @param item: The item by id and meta to compare
	 * @return true if the item id and meta matches a flux id
	 */
	public static boolean isFlux(ItemStack item)
	{
		if(item == null)
			return false;
		
		
		for(ItemStack Flux: flux)
		{
			if(matches(item, Flux))
				return true;
		}
		return false;
	}
	
	/**
	 * Determines if an item is flux
	 * @param id: the id to compare
	 * @return true if the items ID matches a flux(metadata doesnt count)
	 */
	public static boolean isFlux(int id)
	{
		for(ItemStack Flux: flux)
		{
			if(matches(new ItemStack(id, 1, OreDictionary.WILDCARD_VALUE), Flux))
				return true;
		}
		return false;
	}
	
	/**
	 * Gets the amount of smelts for an item
	 * @param item The item in the fuel slot
	 * @return The amount of smelts it has(will not consume if its 0)
	 */
	public static float getBlastFuel(ItemStack item)
	{
		if(item == null)
			return 0;
		
		for(BlastFurnaceFuel fuel: BlastFuel)
		{
			if(fuel != null && fuel.fuel.isItemEqual(item))
			{
				return fuel.smelts;
			}
		}
		return 0;
	}
	
	/**
	 * Gets the amount of smelts for an item
	 * @param item The item id in the fuel slot
	 * @return The amount of smelts it has(will not consume if its 0)
	 */
	public static float getBlastFuel(int id)
	{
		for(BlastFurnaceFuel fuel: BlastFuel)
		{
			if(fuel != null && fuel.fuel.itemID == id)
			{
				return fuel.smelts;
			}
		}
		return 0;
	}
	
	
	
	
	
	/**
	 * Gets the amount of smelts for an item
	 * @param item The item in the fuel slot
	 * @return The amount of smelts it has(will not consume if its 0)
	 */
	public static float getForgeFuel(ItemStack item)
	{
		if(item == null)
			return 0;
		
		for(ForgeFuel fuel: forgeFuel)
		{
			if(fuel != null)
			{
				if(fuel.fuel.itemID == item.itemID)
				{
					if(fuel.fuel.getItemDamage() == OreDictionary.WILDCARD_VALUE || fuel.fuel.getItemDamage() == item.getItemDamage())
					{
						return fuel.duration;
					}
				}
			}
		}
		return 0;
	}
	
	public static boolean willLight(ItemStack item)
	{
		if(item == null)
		{
			return false;
		}
		
		for(ForgeFuel fuel: forgeFuel)
		{
			if(fuel != null)
			{
				if(fuel.fuel.itemID == item.itemID)
				{
					if(fuel.fuel.getItemDamage() == OreDictionary.WILDCARD_VALUE || fuel.fuel.getItemDamage() == item.getItemDamage())
					{
						return fuel.doesLight;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Gets the amount of smelts for an item
	 * @param item The item id in the fuel slot
	 * @return The amount of smelts it has(will not consume if its 0)
	 */
	public static float getForgeFuelWithoutSubid(int id)
	{
		for(ForgeFuel fuel: forgeFuel)
		{
			if(fuel != null)
			{
				if(fuel.fuel.itemID == id)
				{
					return fuel.baseHeat;
				}
			}
		}
		return 0;
	}
	
	
	
	
	
	
	/**
	 * Gets the base temperature for an item
	 * @param item The item in the fuel slot
	 * @return The amount of smelts it has(will not consume if its 0)
	 */
	public static int getForgeHeat(ItemStack item)
	{
		if(item == null)
			return 0;
		
		for(ForgeFuel fuel: forgeFuel)
		{
			if(fuel != null)
			{
				if(fuel.fuel.itemID == item.itemID)
				{
					if(fuel.fuel.getItemDamage() == OreDictionary.WILDCARD_VALUE || fuel.fuel.getItemDamage() == item.getItemDamage())
					{
						return fuel.baseHeat;
					}
				}
			}
		}
		return 0;
	}
	
	/**
	 * Gets the base temperature for an item
	 * @param item The item id in the fuel slot
	 * @return The amount of smelts it has(will not consume if its 0)
	 */
	public static int getForgeHeat(int id)
	{		
		for(ForgeFuel fuel: forgeFuel)
		{
			if(fuel != null && fuel.fuel.itemID == id)
			{
				return fuel.baseHeat;
			}
		}
		return 0;
	}
	
	
	
	
	
	
	/**
	 * Determines if a specific Item and subId is a carbonator item
	 * @param item: The item by id and meta to compare
	 * @return true if the item id and meta matches a carbonator id
	 */
	public static boolean isCarbon(ItemStack item)
	{
		if(item == null)
			return false;
		
		for(ItemStack Carbon: carbon)
		{
			if(matches(item, Carbon))
				return true;
		}
		return false;
	}
	
	/**
	 * Determines if an item is carbonator
	 * @param id: the id to compare
	 * @return true if the items ID matches a carbonator(metadata doesnt count)
	 */
	public static boolean isCarbon(int id)
	{
		for(ItemStack Carbon: carbon)
		{
			if(matches(new ItemStack(id, 1, OreDictionary.WILDCARD_VALUE), Carbon))
				return true;
		}
		return false;
	}
	
	private static boolean matches(ItemStack item, ItemStack compare)
	{
		if(item == null || compare == null)
		{
			return false;
		}
		
		if(item.itemID != compare.itemID)
		{
			return false;
		}
		
		if(item.getItemDamage() != compare.getItemDamage() && compare.getItemDamage() != OreDictionary.WILDCARD_VALUE && item.getItemDamage() != OreDictionary.WILDCARD_VALUE)
		{
			return false;
		}
		return true;
	}
}
