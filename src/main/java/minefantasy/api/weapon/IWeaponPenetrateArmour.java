package minefantasy.api.weapon;

public interface IWeaponPenetrateArmour 
{
	/**
	 * Gets the amount of additional damage in AP damage
	 */
	float getAPDamage();
	
	/**
	 * Gets the multiplier health of armour is damaged
	 */
	float getArmourDamageBonus();
	boolean buffDamage();
}
