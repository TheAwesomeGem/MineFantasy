package minefantasy.system;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class ArrowsMF {
	public static List<ItemStack> useableArrows = new ArrayList();
	
	public static void addArrow(ItemStack arrow)
	{
		useableArrows.add(arrow);
	}
	
	public static boolean canShootArrow(ItemStack arrow)
	{
		for(ItemStack Comparearrow: useableArrows)
		{
			if(Comparearrow.isItemEqual(arrow))
				return true;
		}
		return false;
	}
}
