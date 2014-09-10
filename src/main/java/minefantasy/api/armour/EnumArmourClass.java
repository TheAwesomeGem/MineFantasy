package minefantasy.api.armour;


/**
 * 
 * @author AnonymousProductions
 * 
 * Armour class values
 * 
 * Light - No effect
 * Medium - 15% slower, 50% more exhaustion 10% knockback reduction
 * Heavy - 25% slower, 2x exhaustion, 50% less knockback
 * Plate - 50% slower, 3x exhaustion, can't sprint, no knockback
 */
public enum EnumArmourClass {

	UNARMOURED("Null", 1F, true, 1F, 1F, new int[]{-1, -3, -4, -2}),
	LIGHT("Light", 1F, true, 1F, 1F, new int[]{0, 0, 0, 0}),
	MEDIUM("Medium", 0.9F, true, 1.1F, 0.9F, new int[]{2, 6, 8, 4}),
	HEAVY("Heavy", 0.85F, true, 1.2F, 0.5F, new int[]{5, 15, 20, 10}),
	PLATE("Plate", 0.8F, false, 1.5F, 0, new int[]{10, 30, 40, 20});
	
	private final String className;
	private final float travelSpeed;
	private final boolean canSprintIn;
	private final float exaustionRate;
	private final float knockbackStrength;
	private int noise[];
	
	/**
	 * 
	 * @param name the name of the type
	 * @param speed the total speed you move with a full suit(calculates divisions per section)
	 *      -1.0 = normal speed, 0,5 = half etc
	 * @param canSprint if you can sprint in armour
	 *      -only applies when all armours equipped have no sprint(can mix and match)
	 * 
	 * @param exaustion the amount of exaustion you get(1.0 = normal 1.5 = 50% more.. etc...)
	 * @param knockback the amount knockback is multiplied(1.0 = normal, 0.5 = 50% etc...)
	 * @param sound the amount more or less the armour effects sneaking capabilities
	 *  positive = harder to sneak negetive = easier
	 */
	private EnumArmourClass(String name, float speed, boolean canSprint, float exaustion, float knockback, int[] sound)
	{
		className = name;
		travelSpeed = speed;
		canSprintIn = canSprint;
		exaustionRate = exaustion;
		knockbackStrength = knockback;
		noise = sound;
	}
	public float getSpeedReduction()
	{
		return 1-travelSpeed;
	}
	public String getName()
	{
		return className;
	}
	public boolean canSprintIn()
	{
		return canSprintIn;
	}
	public float getExaustion()
	{
		return exaustionRate;
	}
	public float getKnockback()
	{
		return 1-knockbackStrength;
	}
	public double getSoundMod(int id) {
		return noise[id];
	}
	
}
