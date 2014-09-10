package minefantasy.api.refine;

import net.minecraft.item.ItemStack;

public class BlastFurnaceFuel {
	public ItemStack fuel;
	public float smelts;
	
	public BlastFurnaceFuel(ItemStack item, float uses)
	{
		fuel = item;
		smelts = uses;
	}
}
