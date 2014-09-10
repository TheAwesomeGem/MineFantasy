package minefantasy.api.tactic;

public interface IStealthArmour {
	/**
	 * @return The effectivness of darkness (1.0F = no bonus, 2.0F = twice as effective)
	 * This is a sensetive number (Shrouded armour uses 1.5F), having upto 1.2F makes you nearly invisible at night
	 */
	float darknessAmplifier();
	
	/**
	 * @return The noise made from moving(0.5F = half, 1.0F = normal)
	 */
	float noiseReduction();

	/**
	 * @return true if moving is as effective as sneaking
	 * This only applies to boots
	 */
	boolean quietRun();
	
	/**
	 * @return true if the armour is undetectable when invisible
	 */
	boolean canTurnInvisible();
}
