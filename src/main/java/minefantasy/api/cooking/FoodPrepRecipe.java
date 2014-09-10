package minefantasy.api.cooking;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class FoodPrepRecipe {
	public static List<FoodPrepRecipe> recipes = new ArrayList();
	
	public float time;
	public ItemStack input;
	public ItemStack output;
	public String utensil;
	public String prepSound;
	
	
	
	/**
	 * Adds a food preperation recipe on a board, while ignoring item metadata
	 * @param in the item ID for the input food
	 * @param out the result of the food
	 * @param time the amount of time used to craft
	 * @param utensil the utensil type (eg. "knife") used
	 * @param sound the sound made(same string as making a sound yourself)
	 */
	public static void addFoodPrepRecipe(int in, ItemStack out, float time, String utensil, String sound)
	{
		recipes.add(new FoodPrepRecipe(in, out, time, utensil, sound));
	}
	
	/**
	 * Adds a food preperation recipe on a board
	 * @param in the food that goes in
	 * @param out the result of the food
	 * @param time the amount of time used to craft
	 * @param utensil the utensil type (eg. "knife") used
	 * @param sound the sound made(same string as making a sound yourself)
	 */
	public static void addFoodPrepRecipe(ItemStack in, ItemStack out, float time, String utensil, String sound)
	{
		recipes.add(new FoodPrepRecipe(in, out, time, utensil, sound));
	}
	
	
	
	public FoodPrepRecipe(int in, ItemStack out, float time, String utensil, String sound)
	{
		this(new ItemStack(in, 1, OreDictionary.WILDCARD_VALUE), out, time, utensil, sound);
	}
	
	public FoodPrepRecipe(ItemStack in, ItemStack out, float time, String utensil, String sound)
	{
		this.time = time;
		this.input = in;
		this.output = out;
		this.utensil = utensil;
		this.prepSound = sound;
	}
	
	/**
	 * Gets a recipe depending on the type of tool used, and on what food
	 * @param in the food being used
	 * @param utensil the item that's being used on the food
	 * @return the recipe matching the process
	 */
	public static FoodPrepRecipe getRecipeFor(ItemStack in, ItemStack utensil)
	{
		if(in == null)
		{
			return null;
		}
		
		for(int a = 0; a < recipes.size(); a ++)
		{
			FoodPrepRecipe recipe = recipes.get(a);
			
			if(in.itemID == recipe.input.itemID)
			{
				if(recipe.input.getItemDamage() == OreDictionary.WILDCARD_VALUE || recipe.input.getItemDamage() == in.getItemDamage())
				{
					if(UtensilManager.isToolValid(utensil, recipe.utensil))
					{
						return recipe;
					}
				}
			}
		}
		return null;
	}
}
