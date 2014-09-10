package minefantasy.api.refine;

import java.util.ArrayList;
import java.util.List;

import minefantasy.api.MineFantasyAPI;
import minefantasy.item.ItemListMF;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CrushRecipes {
	private static List<CrushRecipe>recipeList = new ArrayList();
	private static List<ICustomCrushRecipe>customs = new ArrayList();
	
	public static void addRecipe(ICustomCrushRecipe recipe)
	{
		customs.add(recipe);
	}
	public static void addRecipe(ItemStack input, ItemStack output)
	{
		recipeList.add(new CrushRecipe(input, output));
	}
	
	public static ItemStack getResult(ItemStack in)
	{
		if(recipeList == null || recipeList.isEmpty())
			return null;
		
		if(in != null)
		{
			
			for(int a = 0; a < customs.size() ; a ++)
			{
				ICustomCrushRecipe recipe = customs.get(a);
				if(recipe != null)
				{
					if(recipe.getOutput(in) != null)
					{
						return recipe.getOutput(in);
					}
				}
			}
			
			
			for(int a = 0; a < recipeList.size() ; a ++)
			{
				CrushRecipe recipe = recipeList.get(a);
				if(recipe.input != null)
				{
					if(recipe.input.getItemDamage() == OreDictionary.WILDCARD_VALUE)
					{
						if(recipe.input.itemID == in.itemID)
							return recipe.output;
					}
					if(recipe.input.isItemEqual(in))
					{
						return recipe.output;
					}
				}
			}
		}
		return null;
	}
}
