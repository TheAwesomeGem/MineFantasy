package minefantasy.api.refine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SpecialFurnaceRecipes {
	private static List<Alloy>alloys = new ArrayList();
	private static HashMap<Integer, SpecialSmelt> specials = new HashMap<Integer, SpecialSmelt>();
	private static HashMap<List<Integer>, SpecialSmelt> specialsMeta = new HashMap<List<Integer>, SpecialSmelt>();
	
	public static void addAlloy(ItemStack out, int level, List in)
	{
		alloys.add(new Alloy(out, level, in));
	}
	public static void addAlloy(ItemStack out, List in)
	{
		addAlloy(out, 0, in);
	}
	public static void addAlloy(Alloy alloy)
	{
		alloys.add(alloy);
	}
	
	public static Alloy getResult(ItemStack[] inv)
	{
		for(Alloy alloy : alloys)
		{
			if(alloy.matches(inv))
			{
				return alloy;
			}
		}
		return null;
	}
	
	public static void addSmelting(int itemID, int metadata, ItemStack itemstack, int level)
    {
        specialsMeta.put(Arrays.asList(itemID, metadata), new SpecialSmelt(level, itemstack));
    }
	public static void addSmelting(int itemID, ItemStack itemstack, int level)
    {
        specials.put(itemID, new SpecialSmelt(level, itemstack));
    }
	
	public static ItemStack getSmeltingResult(ItemStack item, int lvl) 
    {
        if (item == null)
        {
            return null;
        }
        SpecialSmelt ret = (SpecialSmelt)specialsMeta.get(Arrays.asList(item.itemID, item.getItemDamage()));
        if (ret != null && ret.lvl <= lvl) 
        {
            return ret.result;
        }
        ret = (SpecialSmelt)specials.get(item.itemID);
        if (ret != null && ret.lvl <= lvl) 
        {
            return ret.result;
        }
        return null;
    }
	
	
	/**
	 * Adds an alloy, and duplicates it so the ratio can be copied
	 * eg a recipe with 2 'x' ore and 1 'y' ore can be made with 4 'x' ore and 2 'y' ore...
	 * @param the amount of times the ratio can be added
	 */
	public static void addRatioRecipe(ItemStack out, int level, List in, int levels)
	{
		for(int a = 1; a <= levels; a ++)
		{
			List list2 = createDupeList(in, a);
			ItemStack out2 = out.copy();
			int ss = Math.min(out2.getMaxStackSize(), out2.stackSize * a);
			out2.stackSize = ss;
			addAlloy(out2, level, list2);
		}
	}
	public static List createDupeList(List list)
	{
		return createDupeList(list, 2);
	}
	public static List createDupeList(List list, int dupe)
	{
		if(dupe == 0)
		{
			dupe = 1;
		}
		List list2 = new ArrayList();
		for(int a = 0; a < dupe; a ++)
		{
			for(int b = 0; b < list.size(); b ++)
			{
				list2.add(list.get(b));
			}
		}
		return list2;
	}
	private static class SpecialSmelt
	{
		public int lvl;
		public ItemStack result;
		private SpecialSmelt(int level, ItemStack out)
		{
			lvl = level;
			result = out;
		}
	}
}