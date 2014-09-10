package minefantasy.item.weapon;

import minefantasy.api.weapon.IStealthWeapon;
import minefantasy.api.weapon.IWeaponCustomSpeed;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;

public class ItemDagger extends ItemWeaponMF implements IWeaponCustomSpeed, IStealthWeapon
{
	public ItemDagger(int id, EnumToolMaterial material) 
	{
		super(id, material);
	}

	public ItemDagger(int id, EnumToolMaterial material, float dam, int uses) 
	{
		this(id, material);
	}

	@Override
	public float getDamageModifier() 
	{
		return 0.5F;
	}

	@Override
	public int getHitTime(ItemStack weapon, EntityLivingBase target)
	{
		return -2;
	}

	@Override
	public float getBackstabModifier()
	{
		return 4.0F;
	}

	@Override
	public float getDropModifier() 
	{
		return 3.0F;
	}

	@Override
	public boolean canDropAttack() 
	{
		return true;
	}

	@Override
	public float getDurability() 
	{
		return 0.5F;
	}

	@Override
	public int getHandsUsed()
	{
		return 1;
	}

}
