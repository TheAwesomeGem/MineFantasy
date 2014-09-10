package minefantasy.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.minecraft.item.ItemStack;

public enum EnumHoundBreed 
{
	//BASE
	BLACK (0, "Black", 4, 3, 3),
	GRAY (0, "Gray", 3, 3, 4),
	WHITE (0, "White", 3, 4, 3),
	BROWN (0, "Brown", 3, 3, 4),
	GOLD (0, "Gold", 3, 4, 3),
	RED (0, "Red", 4, 3, 3),
	
	//CROSS
	MIX_BL_GR (1, "Black", "Gray", 4, 2, 4, 0.4F),
	MIX_BL_WH (1, "White", "Black", 4, 4, 2, 0.4F),
	MIX_BL_BR (1, "Brown", "Black", 4, 2, 4, 0.4F),
	MIX_BL_GO (1, "Black", "Gold", 4, 4, 2, 0.4F),
	
	MIX_GR_WH (1, "White", "Gray", 2, 4, 4, 0.4F),
	MIX_GR_GO (1, "Gray", "Gold", 2, 4, 4, 0.4F),
	MIX_GR_RE (1, "Red", "Gray", 4, 2, 4, 0.4F),
	
	MIX_WH_BR (1, "White", "Brown", 2, 4, 4, 0.4F),
	MIX_WH_RE (1, "Red", "White", 4, 4, 2, 0.4F),
	
	MIX_BR_GO (1, "Gold", "Brown", 2, 4, 4, 0.4F),
	MIX_BR_RE (1, "Red", "Brown", 4, 2, 4, 0.4F),
	
	MIX_GO_RE (1, "Gold", "Red", 4, 4, 2, 0.4F),
	
	//THOUROUGHBREAD
	THORSTR (2, "Attack", 8, 2, 2, 0.1F),
	THORDEF (2, "Defense", 2, 8, 2, 0.1F),
	THORSTA (2, "Stamina", 2, 2, 8, 0.1F),
	
	//SUPER
	WAR (3, "War", 10, 10, 5, 0.01F),
	HOLY (3, "Holy", 5, 10, 10, 0.01F),
	BLOOD (3, "Blood", 10, 5, 10, 0.01F);
	
	
	private static List<HoundBreedMix> breeding = new ArrayList();
	
	public String texture;
	public String spots;
	public int attack;
	public int defense;
	public int stamina;
	public float chanceToCreate;
	/**
	 * 0 = Pure
	 * 1 = Mix
	 * 2 = Thorough
	 * 3 = Super
	 */
	public int tierOfBreed;
	
	/**
	 * Used for basic breeds
	 * @param name The breed name (used in texture)
	 * @param att the attack bonus
	 * @param def the defense bonus
	 * @param sta the stamina bonus
	 */
	private EnumHoundBreed(int type, String name, int att, int def, int sta)
	{
		this(type, name, att, def, sta, 1.0F);
	}
	
	/**
	 * Used for Thorough breads and Supers
	 * @param name The breed name (used in texture)
	 * @param att the attack bonus
	 * @param def the defense bonus
	 * @param sta the stamina bonus
	 * @param chance the chance for this breed to be created
	 */
	private EnumHoundBreed(int type, String name, int att, int def, int sta, float chance)
	{
		this(type, name, null, att, def, sta, chance);
	}
	
	/**
	 * Used for Mixbreeds
	 * @param name The breed name (used in texture)
	 * @param spot The breed used on spots
	 * @param att the attack bonus
	 * @param def the defense bonus
	 * @param sta the stamina bonus
	 * @param chance the chance for this breed to be created
	 */
	private EnumHoundBreed(int type, String tex, String spot, int att, int def, int sta, float chance)
	{
		chanceToCreate = chance;
		texture = tex;
		spots = spot;
		attack = att;
		defense = def;
		stamina = sta;
		tierOfBreed = type;
	}
	
	public static EnumHoundBreed getBreed(int i)
	{
		if(i < 0 || i > getMaxBreeds())
		{
			return null;
		}
		return EnumHoundBreed.values()[i];
	}
	public static void addBreeding(EnumHoundBreed b1, EnumHoundBreed b2, EnumHoundBreed breed)
	{
		breeding.add(new HoundBreedMix(b1, b2, breed));
	}
	public static void addBreeding(int b1, int b2, EnumHoundBreed breed)
	{
		addBreeding(getBreed(b1), getBreed(b2), breed);
	}
	public static EnumHoundBreed getBreedFor(int b1, int b2)
	{
		return getBreedFor(getBreed(b1), getBreed(b2));
	}
	public static EnumHoundBreed getBreedFor(EnumHoundBreed b1, EnumHoundBreed b2)
	{
		Iterator results = breeding.iterator();
		
		while(results.hasNext())
		{
			HoundBreedMix result = (HoundBreedMix) results.next();
			//System.out.println("Search Breed: " + b1.ordinal() + " + " + b2.ordinal());
			if(result.matches(b1, b2))
			{
				//System.out.println("Match Found: " + b1.ordinal() + " + " + b2.ordinal() + " = " + result.getResult().ordinal());
				//System.out.println("Match Found(Names): " + b1.name() + " + " + b2.name() + " = " + result.getResult().name());
				return result.getResult();
			}
		}
		return null;
	}
	public static int getMaxBreeds()
	{
		return EnumHoundBreed.values().length - 1;
	}
	
	static
	{
		//MIX BREEDS
		addBreeding(BLACK, GRAY, MIX_BL_GR);
		addBreeding(BLACK, WHITE, MIX_BL_WH);
		addBreeding(BLACK, BROWN, MIX_BL_BR);
		addBreeding(BLACK, GOLD, MIX_BL_GO);
		
		addBreeding(GRAY, WHITE, MIX_GR_WH);
		addBreeding(GRAY, GOLD, MIX_GR_GO);
		addBreeding(GRAY, RED, MIX_GR_RE);
		
		addBreeding(WHITE, BROWN, MIX_WH_BR);
		addBreeding(WHITE, RED, MIX_WH_RE);
		
		addBreeding(BROWN, GOLD, MIX_BR_GO);
		addBreeding(BROWN, RED, MIX_BR_RE);
		
		addBreeding(GOLD, RED, MIX_GO_RE);
		
		addBreeding(BLACK, RED, THORSTR);
		addBreeding(WHITE, GOLD, THORDEF);
		addBreeding(GRAY, BROWN, THORSTA);
		
		addBreeding(THORSTR, THORDEF, WAR);
		addBreeding(THORDEF, THORSTA, HOLY);
		addBreeding(THORSTR, THORSTA, BLOOD);
	}
}
