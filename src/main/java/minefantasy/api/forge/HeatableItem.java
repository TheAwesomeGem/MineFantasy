package minefantasy.api.forge;

import java.util.ArrayList;
import java.util.List;

import minefantasy.MineFantasyBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This class is used to define a heatable item
 * this means if this item is used in anvil recipes IT MUST BE HEATED
 * 
 * @author AnonymousProductions
 *
 */
public class HeatableItem {

	public static boolean requiresHeating = true;
	public static List<HeatableItem>items = new ArrayList();
	/**
	 * The min heat the ingot must be to forge with
	 * mesured in celcius
	 */
	protected final int minTemperature;
	
	/**
	 * The heat when it becomes unstable
	 * mesured in celcius
	 */
	protected final int unstableTemperature;
	
	/**
	 * The max heat until the ingot is destroyed
	 * mesured in celcius
	 */
	protected final int maxTemperature;
	
	/**
	 * The item that's used
	 */
	protected ItemStack object;
	
	
	public HeatableItem(ItemStack item, int min, int unstable, int max)
	{
		object = item;
		minTemperature = min;
		unstableTemperature = unstable;
		maxTemperature = max;
	}
	
	public static void addItem(ItemStack item, int min, int unstable, int max)
	{
		items.add(new HeatableItem(item, min, unstable, max));
	}
	
	public static boolean canHeatItem(ItemStack item)
	{
		return getStats(item) != null;
	}
	
	public static boolean canWorkItem(ItemStack item, int temp)
	{
		if(!requiresHeating)return true;
		
		if(!canHeatItem(item))return true;
		
		int min = getStats(item).minTemperature;
		
		return MineFantasyBase.isDebug() || temp >= min;
	}
	
	public static boolean doesRuinItem(ItemStack item, int temp)
	{
		if(!canHeatItem(item))return false;
		
		int max = getStats(item).maxTemperature;
		return temp > max;
	}
	
	
	public static HeatableItem getStats(ItemStack item)
	{
		if(item == null)
			return null;
		
		if(items.isEmpty())
			return null;
		
		for(HeatableItem compare : items)
		{
			if(compare.object != null)
			{
				if(compare.object.getItemDamage() == OreDictionary.WILDCARD_VALUE)
				{
					if(compare.object.itemID == item.itemID)
					{
						return compare;
					}
				}
				else
				{
					if(compare.object.isItemEqual(item))
					{
						return compare;
					}
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 0 = nothing, 1 = soft, 2 = unstable
	 */
	public static byte getHeatableStage(ItemStack item, int temp)
	{
		HeatableItem stats = getStats(item);
		if(stats != null)
		{
			int work = stats.minTemperature;
			int unstable = stats.unstableTemperature;
			
			if(temp > unstable)return (byte)2;
			if(temp > work)return (byte)1;
		}
		return (byte)0;
	}
}
