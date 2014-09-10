package minefantasy.api.weapon;

import net.minecraft.item.ItemStack;

/**
 * Creates a weapon less effective against armour
 */
public interface IWeaponWeakArmour 
{
	/**
	 * Gets the amount of added times armour degrades the damage
	 * 
	 * 1.0F means armour does a second calculation(meaning armour will be 2x as effective at blocking this)
	 * MF axe weapons use 0.5 (+50% weakness)
	 */
	public float getArmourPower(ItemStack weapon);
}
