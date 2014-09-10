package minefantasy.api.weapon;

public interface IWeaponMobility {
	/**
	 * Gets the additional Exaustion per swing
	 * @return the additional exastion added
	 * 0 = no effect(this adds to the 0.3 for every swin; returning 0.3 would double the exaustion)
	 */
	float getExaustion();
	
	/**
	 * Gets the speed the player moves when this is equipped
	 * @return The total speed when holding the item
	 * Eg. 0.8 means the plaer moves 20% slower
	 */
	float getSpeedWhenEquipped();
}
