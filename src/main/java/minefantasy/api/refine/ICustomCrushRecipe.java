package minefantasy.api.refine;

import net.minecraft.item.ItemStack;

public interface ICustomCrushRecipe {
	ItemStack getOutput(ItemStack input);
}
