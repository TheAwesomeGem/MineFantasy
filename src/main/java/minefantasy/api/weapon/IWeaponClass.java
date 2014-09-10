package minefantasy.api.weapon;

import net.minecraft.item.ItemStack;

public interface IWeaponClass {
	
	/**
	 * Determines the weapon type(used in sounds)
	 * @param item the weapon used
	 * @return the weapon class it is
	 */
	EnumWeaponType getType(ItemStack item);
}
