package minefantasy.item;

import minefantasy.api.armour.EnumArmourClass;

public enum ArmourDesign {

	//AC PROT, BLUNT EXP PROJ FIRE DURA SPEED
	/**
	 * Vanilla Crafting Leather
	 * 
	 * Cost: 24 Leather
	 * *       Ac,    Prot Blunt Exp   Proj  Fire Dura Speed
	 */
	LEATHER("Light", 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F),
	/**
	 * Upgraded Leather
	 * 20% More Projectile resistance
	 * *       Ac,    Prot Blunt Exp   Proj  Fire Dura Speed
	 */
	STUDDED("Light", 1.0F, 0.0F, 1.0F, 1.2F, 1.0F, 1.0F, 1.0F),
	/**
	 * Light Metal
	 * 30% Less armour than metal
	 * 
	 * COST: 8 Metal, 24 Leather
	 * *    Ac,    Prot Blunt   Exp  Proj  Fire Dura Speed
	 */
	SCALE("Light", 0.75F, 0.0F, 0.7F, 0.7F, 0.7F, 0.5F*1.5F, 1.0F),
	
	/**
	 * Projectile Resistant
	 * 25% Less armour than metal
	 * 100% More Projectile resistance
	 * 
	 * COST: 16 Metal
	 * *     Ac,    Prot Blunt    Exp   Proj  Fire Dura Speed
	 */
	CHAIN("Medium", 0.8F, 0.0F, 0.6F, 1.5F, 0.7F, 1.0F*1.5F, 0.9F),
	/**
	 * Durable Armour
	 * 10% Less armour than metal
	 * 20% More Durability
	 * 
	 * COST: 16 Metal, 24 Leather
	* *       Ac,    Prot   Blunt   Exp  Proj  Fire Dura Speed
	 */
	SPLINT("Medium", 0.85F, 0.05F,  0.9F, 0.85F, 0.85F*1.5F, 0.8F, 0.9F),

	/**
	 * 20% More armour than metal
	 * 50% More Projectile resistance
	 * 100% More Durability
	 * 
	 * Cost: 24 Metal
   	 * *      Ac,    Prot   Blunt Exp   Proj  Fire Dura Speed
	 */
	HVYCHAIN("Heavy", 0.9F, 0.0F, 0.5F, 2.5F, 0.8F, 1.2F*1.5F, 0.85F),
	
	/**
	 * Vanilla Crafting (Inferior to forged armours)
	 * 
	 * Cost: 24 Metal
	 * *    Ac,    Prot Blunt  Exp   Proj  Fire Dura Speed
	 */
	SOLID("Heavy", 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.85F),
	
	/**
	 * Heavy Duty
	 * 30% More armour than metal
	 * 3x More Durability
	 * 100% more Projectile Resistance
	 * 
	 * COST: 24 Metal, 10 Iron, 18 Lether, 6 Cloth
	 * *    Ac,    Prot   Blunt   Exp   Proj  Fire   Dura Speed
	 */
	PLATE("Plate", 1.0F, 0.5F, 1.0F,  1.0F, 1.0F, 3.5F*1.5F, 0.8F);
	
	public final String AC;
	public final float protection;
	public final float bluntResist;
	public final float fallResist;
	public final float expResist;
	public final float projResist;
	public final float fireResist;
	public final float dura;
	public final float moveSpeed;
	
	private ArmourDesign(String Ac, float Pro, float blunt, float Exp, float Proj, float Fire, float fall, float Dur, float speed)
	{
		moveSpeed = speed;
		AC = Ac;
		protection = Pro;
		bluntResist = blunt;
		expResist = Exp;
		projResist = Proj;
		fireResist = Fire;
		dura = Dur;
		fallResist = fall;
	}
	private ArmourDesign(String Ac, float Pro, float blunt, float Exp, float Proj, float Fire, float Dur, float speed)
	{
		this(Ac, Pro, blunt, Exp, Proj, Fire, 0.0F, Dur, speed);
	}
	
	public static EnumArmourClass getAC(String s)
	{
		if(s.equalsIgnoreCase("Light"))
		{
			return EnumArmourClass.LIGHT;
		}
		if(s.equalsIgnoreCase("Medium"))
		{
			return EnumArmourClass.MEDIUM;
		}
		if(s.equalsIgnoreCase("Heavy"))
		{
			return EnumArmourClass.HEAVY;
		}
		if(s.equalsIgnoreCase("Plate"))
		{
			return EnumArmourClass.PLATE;
		}
		return EnumArmourClass.HEAVY;
	}
}
