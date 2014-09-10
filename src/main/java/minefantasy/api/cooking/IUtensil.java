package minefantasy.api.cooking;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;

public interface IUtensil
{

	/**
	 * Gets the tool type for the item
	 * @param tool the tool item itself
	 * @return the item used Example:
	 * "knife" is used for a knife
	 */
	String getType(ItemStack tool);
	
	/**
	 * Determines how fast the tool finishes recipes
	 * @param tool the item used
	 * @return the amount of efficiency
	 */
	float getEfficiency(ItemStack tool);
}
