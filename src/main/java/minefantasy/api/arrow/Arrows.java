package minefantasy.api.arrow;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Arrows 
{
	/**
	 * List of fireable arrows
	 */
	public static List<ItemStack> arrows = new ArrayList<ItemStack>();
	/**
	 * List of handlers
	 */
	public static List<IArrowHandler> handlers = new ArrayList<IArrowHandler>();
	
	/**
	 * Adds an arrow that can be fired
	 */
	public static void addArrow(ItemStack item)
	{
		arrows.add(item);
	}
	
	/**
	 * Adds an arrow that can be fired not considering sub Ids
	 */
	public static void addArrow(Item item)
	{
		addArrow(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE));
	}
	
	/**
	 * Adds a handler
	 */
	public static void addHandler(IArrowHandler handler)
	{
		handlers.add(handler);
	}
	
	/**
	 * Gets the arrow loaded on the bow used for rendering and firing
	 */
	public static ItemStack getLoadedArrow(ItemStack bow)
	{
		if(bow != null && bow.hasTagCompound())
		{
			if(bow.getTagCompound().hasKey("loadedArrow"))
			{
				return ItemStack.loadItemStackFromNBT(bow.getTagCompound().getCompoundTag("loadedArrow"));
			}
		}
		return null;
	}
	
}
