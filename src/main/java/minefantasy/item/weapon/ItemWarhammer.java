package minefantasy.item.weapon;

import minefantasy.api.weapon.*;
import minefantasy.item.ToolMaterialMedieval;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * Warhammer:
 * Heavy weapon: more damage
 * does low damage, but with high AP
 * 2x armour damage
 */
public class ItemWarhammer extends ItemWarpick implements IWeaponPenetrateArmour, IWeaponMobility, IWeaponCustomSpeed
{
	public ItemWarhammer(int id, EnumToolMaterial material) 
	{
		super(id, material);
	}

	public ItemWarhammer(int id, EnumToolMaterial material, float dam, int uses) 
	{
		this(id, material);
	}

	@Override
	public boolean canHarvestBlock(Block block)
    {
        return block.blockID == Block.web.blockID ? false : super.canHarvestBlock(block);
    }

	@Override
	public float getExaustion() 
	{
		return 0.15F;
	}

	@Override
	public float getSpeedWhenEquipped() 
	{
		float degrade = 0.20F;
		if(this.getMaterial() == ToolMaterialMedieval.MITHRIL)
		{
			degrade /= 2;
		}
		return 1.0F - degrade;
	}

	@Override
	public float getArmourDamageBonus() 
	{
		return 2.0F;
	}

	@Override
	public boolean buffDamage() 
	{
		return true;
	}
	@Override
	public float getKnockback() 
	{
		return 5F;
	}
	
	@Override
	public float getDamageModifier() 
	{
		return super.getDamageModifier() * 1.5F;
	}

	@Override
	public int getHitTime(ItemStack weapon, EntityLivingBase target)
	{
		return super.getHitTime(null, target)*2 + 5;
	}

	@Override
	public float getDurability()
	{
		return super.getDurability() * 1.5F;
	}
	@Override
	public int getHandsUsed() 
	{
		return 2;
	}
	@Override
	public boolean sheatheOnBack(ItemStack item) 
	{
		return true;
	}
	@Override
	public float getBalance()
	{
		return 1.0F;
	}
	@Override
	public float getBlockFailureChance()
	{
		return 0.45F;
	}
	
	@Override
	public float getDebilitation()
	{
		return 0.5F;
	}
	
}
