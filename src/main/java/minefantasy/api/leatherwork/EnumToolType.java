package minefantasy.api.leatherwork;

public enum EnumToolType {
	KNIFE(1),
	CUTTER(2);
	
	private final int id;
	
	private EnumToolType(int i)
	{
		id = i;
	}
	
	public int getID()
	{
		return id;
	}
}
