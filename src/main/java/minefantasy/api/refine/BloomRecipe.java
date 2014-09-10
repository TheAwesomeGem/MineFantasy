package minefantasy.api.refine;

import java.util.ArrayList;
import java.util.List;

import minefantasy.item.ItemBloom;
import minefantasy.system.cfg;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class BloomRecipe {
	public final ItemStack input1;
	public final ItemStack result;
	public final int time;
	public static List<BloomRecipe> recipeList = new ArrayList();
	
	public BloomRecipe(ItemStack in1,ItemStack out)
	{
		this(in1, out, 200);
	}
	public BloomRecipe(ItemStack in1,ItemStack out, int time)
	{
		input1 = in1;
		result = out;
		this.time = time;
	}
	
	
	public static ItemStack getResult(ItemStack input)
	{
		if(input != null)
		{
			for(int a = 0; a < recipeList.size(); a ++)
			{
				BloomRecipe recipe = recipeList.get(a);
				if(recipe.input1.isItemEqual(input))
				{
					if(cfg.easyBlooms)
					{
						return recipe.result;
					}
					return ItemBloom.createBloom(recipe.result);
				}
			}
		}
		return null;
	}
	
	public static int getTime(ItemStack input)
	{
		if(input != null)
		{
			for(int a = 0; a < recipeList.size(); a ++)
			{
				BloomRecipe recipe = recipeList.get(a);
				if(recipe.input1.isItemEqual(input))
				{
					return recipe.time;
				}
			}
		}
		return 200;
	} 

	public static void add(BloomRecipe forgeRecipe) {
		if(recipeList == null)
			recipeList = new ArrayList();
		else
		{
			recipeList.add(forgeRecipe);
		}
	}
}
