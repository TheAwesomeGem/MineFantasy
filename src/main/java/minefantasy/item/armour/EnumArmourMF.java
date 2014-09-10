package minefantasy.item.armour;

import minefantasy.item.ArmourDesign;

public enum EnumArmourMF
{
	/**EnumArmorMaterial
	 * VANILLA GUIDE:
	 * Leather_____28%    Ratio: 1.3
	 * Chain_______48%    Ratio: 1.9
	 * Iron________60%    Ratio: 2.5
	 * Gold________44%    Ratio: 1.7
	 * Diamond_____80%    Ratio: 5.0
	 */
	RAWHIDE("Rawhide",         3,   1.20F,  0,   1.00F),
	LEATHER("Leather",         5,   1.30F,  0,   1.00F),
	APRON("Apron",             6,   1.20F,  0,   1.00F),
	STEALTH("Stealth",         15,  1.50F,  16,  1.00F),
	
	BRONZE("Bronze",           20,  2.00F,  15,  1.00F),
	IRON("Iron",               20,  2.50F,  16,  1.00F),
	STEEL("Steel",		       30,  3.50F,  20,  1.00F),
	ENCRUSTED("Encrusted",     35,  5.00F,  15,  1.10F),
	DEEP_IRON("DeepIron",      40,  6.50F,  16,  1.00F),
	BLUE_STEEL("Blue Steel",   50,  7.50F,  20,  1.90F),
	MITHRIL("Mithril",         55,  9.00F,  22,  0.80F),
	MYTHIUM("Mythium",         60,  10.5F,  18,  0.65F),
	UNUSED("Unused",           80,  12.0F,  20,  1.50F),
	ADAMANTIUM("Adamantium",   100, 13.5F,  24,  1.20F),
	
	GUILDED("Guilded",         18,  2.20F,  30,  1.00F),
	DRAGONFORGE("Dragonforge", 35,  4.00F,  15,  1.00F),
	IGNOTUMITE("Ignotumite",   100, 10.0F,  24,  1.20F);
	
	public final String name;
	public final int durability;
	public final float armourRating;
	public final int enchantment;
	public final float armourWeight;
	
	private EnumArmourMF(String title, int dura, float AC, int enchant, float weight)
	{
		name = title;
		durability = dura;
		armourRating = calculateAC(AC);
		enchantment = enchant;
		armourWeight = weight;
	}
	
	public static float calculateAC(float ratio)
	{
		float percent = 100F - (100F/ratio);
		
		return percent / 60F / ArmourDesign.PLATE.protection;
	}
}
