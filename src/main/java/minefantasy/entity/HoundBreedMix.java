package minefantasy.entity;

public class HoundBreedMix 
{
	private EnumHoundBreed breed1;
	private EnumHoundBreed breed2;
	private EnumHoundBreed result;
	
	public HoundBreedMix(EnumHoundBreed b1, EnumHoundBreed b2, EnumHoundBreed r)
	{
		breed1 = b1;
		breed2 = b2;
		result = r;
	}
	
	public boolean matches(EnumHoundBreed b1, EnumHoundBreed b2)
	{
		//System.out.println("Search: " + breed1.ordinal() + " + " + breed2.ordinal());
		return (breed1 == b1 && breed2 == b2) || (breed1 == b2 && breed2 == b1);
	}
	
	public EnumHoundBreed getResult()
	{
		return result;
	}
}
