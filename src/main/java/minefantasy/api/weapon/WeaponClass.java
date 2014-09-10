package minefantasy.api.weapon;

import net.minecraft.item.ItemStack;

public class WeaponClass {
	
	/**
	 * Gets the weapon class for an item
	 * @param item the weapon
	 * @return the custom class(or default based on name
	 */
	public static EnumWeaponType getClassFor(ItemStack item)
	{
		if(item != null && item.getItem() != null)
		{
			if(item.getItem() instanceof IWeaponClass)
			{
				return ((IWeaponClass)item.getItem()).getType(item);
			}
			else
			{
				return getDefaultOn(item.getItem().getUnlocalizedName(item));
			}
		}
		return null;
	}

	/**
	 * Finds a default for an item, based on it's name
	 * @param unlocalizedName the name of the item(not display)
	 * @return a class based on what the item is called, by guessing, DEFAULT: medium blade
	 */
	private static EnumWeaponType getDefaultOn(String name) 
	{
		if(guessClass(name, new String[]
		{
			"waraxe",
		}))
		{
			return EnumWeaponType.AXE;
		}
		
		
		if(guessClass(name, new String[]
				{
					"battleaxe",
					"beardedaxe",
					"greataxe",
				}))
				{
					return EnumWeaponType.BIGAXE;
				}
		
		
		
		if(guessClass(name, new String[]
				{
					"bastard",
					"bastardsword",
					"claymore",
					"greatsword",
				}))
				{
					return EnumWeaponType.BIGBLADE;
				}
		
		
		
		if(guessClass(name, new String[]
				{
					"warhammer",
					"morningstar",
					"maul",
				}))
				{
					return EnumWeaponType.BIGBLUNT;
				}
		
		
		if(guessClass(name, new String[]
				{
					"halbeard",
					"trident",
				}))
				{
					return EnumWeaponType.BIGPOLEARM;
				}
		
		
		
		if(guessClass(name, new String[]
				{
					"longsword",
					"broadsword",
					"broad",
					"katana",
					"rapier",
					"sabre",
					"scimitar",
					"cutlass",
				}))
				{
					return EnumWeaponType.LONGBLADE;
				}
		
		
		
		if(guessClass(name, new String[]
				{
					"spear",
					"javelin",
					"pike",
				}))
				{
					return EnumWeaponType.POLEARM;
				}
		
		
		if(guessClass(name, new String[]
				{
					"axe",
					"tommahawk",
					"hatchet",
				}))
				{
					return EnumWeaponType.SMLAXE;
				}
		
		
		if(guessClass(name, new String[]
				{
					"knife",
					"dagger",
					"dirk",
					"stiletto",
				}))
				{
					return EnumWeaponType.SMLBLADE;
				}
		
		
		
		if(guessClass(name, new String[]
				{
					"mace",
					"club",
					"blackjack",
				}))
				{
					return EnumWeaponType.SMLBLUNT;
				}
		
		
		
		
		
		if(guessClass(name, new String[]
				{
					"staff",
					"quaterstaff",
				}))
				{
					return EnumWeaponType.STAFF;
				}
		
		
		
		return EnumWeaponType.MEDBLADE;
	}
	
	private static boolean guessClass(String name, String[] keys)
	{
		for(String key : keys)
		{
			if(name.contains(key))
			{
				return true;
			}
		}
		return false;
	}
}
