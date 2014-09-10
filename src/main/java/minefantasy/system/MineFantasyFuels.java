package minefantasy.system;

import minefantasy.item.ItemListMF;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class MineFantasyFuels implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if(fuel.itemID == ItemListMF.misc.itemID)
		{
			int m = fuel.getItemDamage();
			
			if(m == ItemListMF.coke)
			{
				return 960;
			}
			if(m == ItemListMF.longCoal)
			{
				return 3200;
			}
			if(m == ItemListMF.infernoCoal)
			{
				return 6400;
			}
			if(m == ItemListMF.HellCoal)
			{
				return 12800;
			}
			if(m == ItemListMF.plankIronbark)
			{
				return 200;
			}
		}
		if(fuel.itemID == ItemListMF.plank.itemID)
		{
			return 150;
		}
		return 0;
	}

}
