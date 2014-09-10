package minefantasy.item.weapon;

import cpw.mods.fml.relauncher.Side;
import minefantasy.api.weapon.IWeaponSpecialBlock;
import minefantasy.item.mabShield.ItemShield;
import mods.battlegear2.api.PlayerEventChild.OffhandAttackEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class ItemBroadsword extends ItemWeaponMF implements IWeaponSpecialBlock
{
	public ItemBroadsword(int id, EnumToolMaterial material) 
	{
		super(id, material);
	}

	public ItemBroadsword(int id, EnumToolMaterial material, float dam, int uses) 
	{
		this(id, material);
	}

	@Override
	public float getDamageModifier() 
	{
		return 0.9F;
	}
	public boolean canBlock()
	{
		return true;
	}

	@Override
	public float getDurability() 
	{
		return 1.1F;
	}
	
	@Override
	public float blockDamage(EntityLivingBase entity, float damage, DamageSource source) 
	{
		if(!source.isUnblockable())
		{
			if(entity instanceof EntityPlayer)
			{
				if(((EntityPlayer)entity).isBlocking())
				{
					damage *= 0.5F;
				}
				else
				{
					damage *= 0.75F;
				}
			}
			else
			{
				damage *= 0.75F;
			}
		}
		return damage;
	}

	@Override
	public int getHandsUsed() 
	{
		return 1;
	}
}
