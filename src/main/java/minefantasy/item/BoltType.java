package minefantasy.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.util.StatCollector;

public class BoltType 
{
	/**
	 * The material the Bolt is made out of
	 */
	public final EnumToolMaterial material;
	
	/**
	 * The subID it takes
	 */
	public final int meta;
	
	/**
	 * The texture for both items and entities
	 */
	private final String matName;
	public final int index;
	private final int durability;
	private static int nextIndex = 0;
	
	public static List<BoltType>bolts = new ArrayList();
	
	private BoltType(String tex, EnumToolMaterial mat, int sub, int i)
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
		durability = dura;
		meta = sub;
		material = mat;
	}
	private BoltType(String tex, EnumToolMaterial mat, int dura, int sub, int i)
	{
		index = i;
		matName = tex;
		durability = dura;
		meta = sub;
		material = mat;
	}
	
	public static BoltType getBolt(int subId)
	{
		if(!bolts.isEmpty())
		{
			for(int a = 0; a < bolts.size(); a ++)
			{
				BoltType Bolt = bolts.get(a);
				
				if(Bolt.meta == subId)
				{
					return Bolt;
				}
			}
		}
		return null;
	}
	
	public static void addBolt(String tex, EnumToolMaterial mat, int sub)
	{
		bolts.add(new BoltType(tex, mat, sub, nextIndex));
		nextIndex ++;
	}
	public static void addBolt(String tex, EnumToolMaterial mat, int dura, int sub)
	{
		bolts.add(new BoltType(tex, mat, dura, sub, nextIndex));
		nextIndex ++;
	}
	
	public String getDisplayName()
	{
		return StatCollector.translateToLocal("bolt."+matName.toLowerCase());
	}
	
	public String getTextureName()
	{
		return "bolt" + matName;
	}

	public int getBreakChance()
	{
		return durability;
	}
	public static double getDamage(BoltType type)
	{
		double dam = 2.5D;
		if(type.meta == 0)
		{
			return 2.5D;
		}
    	if(type != null)
    	{
    		dam = (double)type.material.getDamageVsEntity() * 3D + 0.5D;
    	}
    	return dam;
	}
}
