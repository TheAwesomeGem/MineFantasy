package minefantasy.item;

public enum EnumHammerType {
	
	STONE(0, 0.3F, 50, 0),
	TIN(0, 0.35F, 200, 4),
	COPPER(0, 0.4F, 125, 5),
	BRONZE(0, 0.5F, 250, 5),
	IRON(0, 1F, 500, 10),
	STEEL(0, 2F, 750, 12),
	MITHRIL(0, 3F, 1000, 15),
	DRAGONFORGE(0, 5.0F, 2500, 18),
	ORNATE(1, 2F, 1000, 20);
	
	private final int enchant;
	private final int level;
	private final float strength;
	private final int uses;
	
	private EnumHammerType(int lvl, float str, int use, int en)
	{
		enchant = en;
		level = lvl;
		strength = str;
		uses = use;
	}

	public int getMaxUses() {
		return uses;
	}
	
	public float getStrength()
	{
		return strength;
	}
	
	public int getLevel()
	{
		return level;
	}

	public int getEnchantability() {
		return enchant;
	}
}
