package minefantasy.api.weapon;

import net.minecraft.item.ItemStack;

/**
 * This is used if you want a simple item do damage based off subIDs(like MF sharp rocks)
 * As you know .6 got rid of the ability to do this
 */
public interface ICustomDamager 
{
	public float getHitDamage(ItemStack item);
}
