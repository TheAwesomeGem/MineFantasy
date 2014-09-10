package minefantasy.system;

import net.minecraft.item.ItemStack;
import minefantasy.api.refine.ICustomCrushRecipe;
import minefantasy.item.ItemBloom;
import minefantasy.item.ItemListMF;

public class RecipeBloom implements ICustomCrushRecipe {

	@Override
	public ItemStack getOutput(ItemStack input) {
		if(input == null)
		{
			return null;
		}
		if(input.itemID == ItemListMF.bloom.itemID)
		{
			return ItemBloom.getItem(input);
		}
		return null;
	}

}
