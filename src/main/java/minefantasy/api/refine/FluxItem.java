package minefantasy.api.refine;

import net.minecraft.item.ItemStack;

/**
 * 
 * @author AnonymousProductions
 * This class is used to easially make flux from an item
 */
public class FluxItem {
	/**
	 * The amount of flux the recipe creates
	 */
	public int fluxOut;
	/**
	 * The item input
	 */
	public ItemStack fluxItem;
	
	public FluxItem(ItemStack item, int out)
	{
		fluxItem = item;
		fluxOut = Math.min(out, 64);
	}
}
