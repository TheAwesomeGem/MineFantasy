package minefantasy.item.weapon;

import java.text.DecimalFormat;

import minefantasy.api.weapon.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;

/**
 * Mace:
 * Same dam as sword, can not block, armour penetrate
 */
public class ItemMaceMF extends ItemWeaponMF implements IWeaponPenetrateArmour, IWeaponCustomSpeed
{
	private float APdamage;
	public ItemMaceMF(int id, EnumToolMaterial material) 
	{
		super(id, material);
		APdamage = baseDamage*getAPPercent();
	}

	public ItemMaceMF(int id, EnumToolMaterial material, float dam, int uses) 
	{
		this(id, material);
	}

	@Override
	public float getDamageModifier() 
	{
		return 1.0F;
	}

	@Override
	public int getHitTime(ItemStack weapon, EntityLivingBase target)
	{
		return 2;
	}

	@Override
	public float getAPDamage() 
	{
		return APdamage;
	}
	
	@Override
	public boolean canHarvestBlock(Block block)
    {
        return block.blockID == Block.web.blockID ? false : super.canHarvestBlock(block);
    }
	@Override
	public float getStrVsBlock(ItemStack stack, Block block)
    {
		return 2.5F;
    }

	@Override
	public float getDurability() 
	{
		return 1.5F;
	}

	@Override
	public float getArmourDamageBonus() 
	{
		return 0;
	}

	@Override
	public boolean buffDamage() 
	{
		return false;
	}

	@Override
	public int getHandsUsed() 
	{
		return 1;
	}
	@Override
	protected float getAPPercent()
	{
		return 0.25F;
	}
}
