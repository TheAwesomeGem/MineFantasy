package minefantasy.api.refine;

import net.minecraft.item.ItemStack;

public class CrushRecipe {
	public ItemStack input;
	public ItemStack output;
	
	public CrushRecipe(ItemStack in, ItemStack out)
	{
		input = in;
		output = out;
	}
}
