package minefantasy.item.weapon;

import minefantasy.item.ItemListMF;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;

/**
 * Sword:
 * Just a sword.
 */
public class ItemSwordMF extends ItemWeaponMF 
{
	public ItemSwordMF(int id, EnumToolMaterial material) 
	{
		super(id, material);
	}

	public ItemSwordMF(int id, EnumToolMaterial material, float dam, int uses) 
	{
		this(id, material);
	}

	@Override
	public float getDamageModifier() 
	{
		return 1.0F;
	}

	public boolean canBlock()
	{
		return true;
	}

	@Override
	public float getDurability() 
	{
		return 1.0F;
	}

	public int getHitTime(ItemStack weapon, EntityLivingBase target)
	{
		return 0;
	}

	@Override
	public int getHandsUsed()
	{
		return 1;
	}
}
