package minefantasy.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.util.StatCollector;

public class ArrowType 
{
	/**
	 * The material the arrow is made out of
	 */
	public final EnumToolMaterial material;
	/**
	 * The arrow type
	 * normal=0.....
	 * bodkin=1.....
	 * broad =2......
	 */
	public final int arrowType;
	
	/**
	 * The subID it takes
	 */
	public final int meta;
	
	/**
	 * The texture for both items and entities
	 */
	private final String matName;
	private final int durability;
	public final int index;
	private static int nextIndex = 0;
	
	public static List<ArrowType>arrows = new ArrayList();
	
	private ArrowType(String tex, EnumToolMaterial mat, int type, int sub, int i)
	{
		int dura = 1;
		if(mat.getMaxUses() < 0)
		{
			dura = -1;
		}
		else
		{
			dura = mat.getMaxUses() / 100;
		}
		
		index = i;
		matName = tex;
		meta = sub;
		material = mat;
		durability = dura;
		arrowType = type;
	}
	private ArrowType(String tex, EnumToolMaterial mat, int dura, int type, int sub, int i)
	{
		index = i;
		matName = tex;
		meta = sub;
		material = mat;
		durability = dura;
		arrowType = type;
	}
	
	public static ArrowType getArrow(int subId)
	{
		if(!arrows.isEmpty())
		{
			for(int a = 0; a < arrows.size(); a ++)
			{
				ArrowType arrow = arrows.get(a);
				
				if(arrow.meta == subId)
				{
					return arrow;
				}
			}
		}
		return null;
	}
	
	public static void addArrow(String tex, EnumToolMaterial mat, int type, int sub)
	{
		arrows.add(new ArrowType(tex, mat, type, sub, nextIndex));
		nextIndex ++;
	}
	
	public String getUnlocalisedDisplayName()
	{
		String name = getTypeTex().length() > 0 ? getTypeTex(): "arrow";
		
		return name.toLowerCase()+"."+matName.toLowerCase();
	}
	public String getTextureName()
	{
		return "arrow" + matName + getTypeTex();
	}

	private String getTypeTex() 
	{
		if(arrowType == 2)
		{
			return "Broad";
		}
		if(arrowType == 1)
		{
			return "Bodkin";
		}
		return "";
	}
	
	public int getBreakChance()
	{
		return durability;
	}
	public static double getDamage(ArrowType type)
	{
		double dam = 2.0D;
    	
    	if(type != null)
    	{
    		if(type.meta == 0)
        	{
        		return 1.5D;
        	}
    		
    		dam = (double)type.material.getDamageVsEntity() * 2.5D + 0.5D;
    		
    		if(type.arrowType == 0)
    		{
    			dam *= 0.5D;
    		}
    		if(type.arrowType == 1)
    		{
    			dam *= 0.35D;
    		}
    		if(type.arrowType == 2)
    		{
    			dam *= 0.65D;
    		}
    	}
    	return dam;
	}
}
