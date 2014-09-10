package minefantasy.api.cooking;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class UtensilManager {
	
	public static String getTypeOfTool(ItemStack tool)
	{
		if(tool != null && tool.getItem() != null)
		{
			if(tool.getItem() instanceof IUtensil)
			{
				return ((IUtensil)tool.getItem()).getType(tool);
			}
		}
		
		return "Null";
	}
	
	public static boolean isToolValid(ItemStack tool, String type)
	{
		return getTypeOfTool(tool).equalsIgnoreCase(type);
	}
}
