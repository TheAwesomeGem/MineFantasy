package minefantasy.api.tailor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class StringRecipes 
{
	private static List<StringRecipes> recipes = new ArrayList();
	
	public final ItemStack input;
	public final ItemStack output;
	public final int timeRequired;
	
	public StringRecipes(ItemStack in, ItemStack out, int time)
	{
		input = in;
		output = out;
		timeRequired = time;
	}
	
	public static void addRecipe(ItemStack in, ItemStack out, int time)
	{
		recipes.add(new StringRecipes(in, out, time));
	}
	
	public static StringRecipes getRecipe(ItemStack input)
	{
		if(recipes.isEmpty())
		{
			return null;
		}
		
		for(int a = 0; a < recipes.size(); a ++)
		{
			StringRecipes recipe = recipes.get(a);
			if(recipe != null && matchItem(recipe.input, input))
			{
				return recipe;
			}
		}
		return null;
	}

	private static boolean matchItem(ItemStack recipe, ItemStack item) 
	{
		if(recipe == null || item == null)
		{
			return false;
		}
		if(recipe.itemID == item.itemID)
		{
			if(recipe.getItemDamage() == OreDictionary.WILDCARD_VALUE)
			{
				return true;
			}
			return recipe.getItemDamage() == item.getItemDamage();
		}
		return false;
	}
}
