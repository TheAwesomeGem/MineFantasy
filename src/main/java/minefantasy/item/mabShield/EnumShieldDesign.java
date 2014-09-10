package minefantasy.item.mabShield;

public enum EnumShieldDesign 
{
	BUCKLER("buckler", 10.0F, 0.35F, 1.0F, 15F, 2),
	ROUND("round", 7.5F, 0.8F, 1.0F, 60F, 7),
	KITE("kite", 6.5F, 0.7F, 1.0F, 90F, 10),
	TOWER("tower", 5.0F, 1.1F, 1.25F, 120F, 15);
	
	private final String name;
	private final float carryTime;
	private final float threshold;
	private final float scale;
	private final float blockArc;
	private final int bashTime;
	
	private EnumShieldDesign(String label, float carry, float dt, float sc, float arc, int bash)
	{
		name = label;
		carryTime = carry;
		threshold = dt;
		scale = sc;
		blockArc = arc;
		bashTime = bash;
	}
	
	public String getTitle()
	{
		return name;
	}
	
	public float getCarryTime()
	{
		return carryTime;
	}
	public float getThreshold()
	{
		return threshold;
	}
	public float getScale()
	{
		return scale;
	}
	public float getArc()
	{
		return blockArc;
	}
	public int getBashTime()
	{
		return bashTime;
	}
}
