package minefantasy.api.tailor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/*
 * FurnaceRecipes
 */
public class StringList 
{
	private static HashMap<List<Integer>, Integer> stringList = new HashMap<List<Integer>, Integer>();
	private static HashMap<Integer, Integer>  stringID = new HashMap<Integer, Integer>();
	
	
	/**
	 * Adds a string item ignoring subIDs
	 * @param string The string itemID
	 * @param tier The tier it equals
	 */
	public static void addString(int string, int tier)
	{
		stringID.put(string, tier);
	}
	/**
	 * Adds a string item ignoring subIDs
	 * @param string The string Item
	 * @param tier The tier it equals
	 */
	public static void addString(Item string, int tier)
	{
		stringID.put(string.itemID, tier);
	}
	
	/**
	 * Adds a string item dependant on ID and SubID
	 * @param string The string itemstack
	 * @param tier The tier it equals
	 */
	public static void addString(ItemStack string, int tier)
	{
		stringList.put(Arrays.asList(string.itemID, string.getItemDamage()), tier);
	}
	/**
	 * Gets if the string used is great enough for the tier
	 * @param string the item checked
	 * @param tier the tier required
	 */
	public static boolean doesMatchTier(ItemStack string, int tier)
	{
		if(string == null)
		{
			return false;
		}
		int stringTier = 0;
		
		if(!stringList.isEmpty() && stringList.containsKey(Arrays.asList(string.itemID, string.getItemDamage())))
		{
			stringTier = (int)stringList.get(Arrays.asList(string.itemID, string.getItemDamage()));
		}
		else if(!stringID.isEmpty() && stringID.containsKey(string.itemID))
		{
			stringTier = (int)stringID.get(string.itemID);
		}
		
		return stringTier >= tier;
	}
	
	public static boolean isString(ItemStack string)
	{
		if(string == null)
		{
			return false;
		}
		
		if(!stringList.isEmpty() && stringList.containsKey(Arrays.asList(string.itemID, string.getItemDamage())))
		{
			return true;
		}
		else if(!stringID.isEmpty() && stringID.containsKey(string.itemID))
		{
			return true;
		}
		
		return false;
	}
}
