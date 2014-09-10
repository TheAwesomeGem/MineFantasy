package minefantasy.item;

public enum EnumCrossbowType {
	
	HAND_CROSSBOW("crossbowHand", 1, 10, 3.5F, 300, 0.5F, 12),
	LIGHT_CROSSBOW("crossbowLight", 1, 30, 1.5F, 800, 1.0F, 10),
	REPEATER_CROSSBOW("crossbowRepeat", 6, 20, 3.0F, 1000, 0.9F, 8),
	HEAVY_CROSSBOW("crossbowHeavy", 1, 45, 2.0F, 800, 1.0F, 10);
	
	private int ammo;
	private int durability;
	private int time;
	private float accuracy;
	private float damage;
	private int enchant;
	private String name;
	
	private EnumCrossbowType(String n, int a, int t, float a1, int u, float d, int e)
	{
		name = n;
		enchant = e;
		durability = u;
		damage = d;
		ammo = a;
		time = t;
		accuracy = a1;
	}
	
	public int getMaxAmmo()
	{
		return ammo;
	}
	
	public int getReload()
	{
		return time;
	}
	
	public float getAccuracy()
	{
		return accuracy;
	}

	public int getDurability() {
		return durability;
	}

	public float getDamage() {
		return damage;
	}

	public int getEnchantment() {
		return enchant;
	}

	public String getName() {
		return name;
	}
}
