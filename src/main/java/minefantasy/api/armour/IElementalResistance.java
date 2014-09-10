package minefantasy.api.armour;

/**
 * 
 * @author AnonymousProductions
 * Implement this in your armours Item class
 *
 */
public interface IElementalResistance {
	
	/**
	 * Grants susceptibility or resistance to fire
	 *    -Fire damage is as you know, burns and is caused by fire, lava and other hot things
	 *    
	 *    -burns the target for a short time
	 *    
	 * @return <1 for resistance >1 for susceptibility and 1 for nothing
	 * eg. 0.5 means 50% less damage, 1.2 means 20% more, 1.0 means regular damage 
	 */
	float fireResistance();
	/**
	 * Grants susceptibility or resistance to shock
	 *    -Shock damage is caused by things like lightning
	 *    -does more damage when the target is wet(and even more in water)
	 *    
	 *    -Does larger damage than other effects
	 * 
	 * @return <1 for resistance >1 for susceptibility and 1 for nothing
	 * eg. 0.5 means 50% less damage, 1.2 means 20% more, 1.0 means regular damage 
	 */
	float shockResistance();
	/**
	 * Grants susceptibility or resistance to corrosion
	 *     -Corrosive damage is acids that eat through matter. slimes and splash potions of harming will cause this
	 *     -slime attacks are considered a way of them eating things by dissolving them.
	 *     
	 *     -decays armour
	 * 
	 * @return <1 for resistance >1 for susceptibility and 1 for nothing
	 * eg. 0.5 means 50% less damage, 1.2 means 20% more, 1.0 means regular damage 
	 */
	float corrosiveResistance();
	/**
	 * Grants susceptibility or resistance to frost
	 *    -Caused by cold things
	 *    
	 *    -slows the player down
	 * 
	 * @return <1 for resistance >1 for susceptibility and 1 for nothing
	 * eg. 0.5 means 50% less damage, 1.2 means 20% more, 1.0 means regular damage 
	 */
	float frostResistance();
	/**
	 * Grants susceptibility or resistance to arcane
	 *    -Regular non-elemental arcane damage, typically weaker but less common resistance
	 *    
	 *    -ignores armour(but resistances can still apply)
	 *    -leave resistances for things like robes and cloth. arcane damage is used against armour
	 * 
	 * @return <1 for resistance >1 for susceptibility and 1 for nothing
	 * eg. 0.5 means 50% less damage, 1.2 means 20% more, 1.0 means regular damage 
	 */
	float arcaneResistance();
	/**
	 * Determines how much damage is entirely ignored(like how plate works)
	 *    -Negates all non elemental damage when armour is hit)
	 *    
	 *    -can not be a negative
	 * 
	 * @return the amount of damage taken off hits (1 = half-heart less damage)
	 * works no matter what the durability
	 */
	int damageReduction();
}
