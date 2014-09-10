package minefantasy.api.refine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class BlastRecipes {
	public final ItemStack input;
	public final ItemStack result;
	public static List<BlastRecipes> recipeList = new ArrayList();
	
	public BlastRecipes(ItemStack in, ItemStack out)
	{
		input = in;
		result = out;
	}
	
	
	public static ItemStack getResult(ItemStack in)
	{
		if(in == null)
		return null;
		
		for(int a = 0; a < recipeList.size(); a ++)
		{
			BlastRecipes recipe = recipeList.get(a);
			if(recipe.input.isItemEqual(in))
			{
				return recipe.result;
			}
		}
		return null;
	}

	public static void add(BlastRecipes forgeRecipe) {
		if(recipeList == null)
			recipeList = new ArrayList();
		else
		{
			recipeList.add(forgeRecipe);
		}
	}
}
