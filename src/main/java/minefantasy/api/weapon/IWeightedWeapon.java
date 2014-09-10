package minefantasy.api.weapon;

public interface IWeightedWeapon 
{
	/**
	 * The value on how much swinging a weapon throws you off balance. making it harder to hit small enemies
	 * Warhammers have 1.0
	 */
	public float getBalance();
	
	/**
	 * Heavier weapons are harder to block, this increases the chance to disarm enemies when they try to block
	 * 1.0 means any attempted parry is disarmed
	 * defending weapons will take more damage from this
	 */
	float getBlockFailureChance();
}
