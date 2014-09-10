package minefantasy.api.weapon;

public interface IStealthWeapon 
{
	/**
	 * Gets the amount of damage added in backstab damage
	 * (1.0F = 2x damage total)
	 */
	float getBackstabModifier();
	
	/**
	 * Gets the amount of damage added in drop damage
	 * (1.0F = normal fall damage translated)
	 */
	float getDropModifier();
	
	boolean canDropAttack();
}
