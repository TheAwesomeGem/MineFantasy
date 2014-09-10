package minefantasy.item.weapon;

import minefantasy.api.weapon.*;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.CombatManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemGreatsword extends ItemSwordMF implements IWeaponMobility, IWeaponCustomSpeed
{
	public ItemGreatsword(int id, EnumToolMaterial material) 
	{
		super(id, material);
	}

	public ItemGreatsword(int id, EnumToolMaterial material, float dam, int uses) 
	{
		this(id, material);
	}

	@Override
	public float getExaustion() 
	{
		return 0.15F;
	}

	@Override
	public float getSpeedWhenEquipped() 
	{
		float degrade = 0.10F;
		if(this.getMaterial() == ToolMaterialMedieval.MITHRIL)
		{
			degrade /= 2;
		}
		return 1.0F - degrade;
	}

	public boolean canBlock()
	{
		return true;
	}

	@Override
	public float getKnockback() 
	{
		return 3F;
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
	public float getBalance()
	{
		return 0.8F;
	}
	@Override
	public float getBlockFailureChance()
	{
		return 0.20F;
	}
	//SPECIAL EFFECT: On hit: "Push forward": chance to gain resistance, heal and speed only when in full plate.
	@Override
	protected void applyHeavyAttackBonus(EntityLivingBase attacker, EntityLivingBase target)
	{
		if(CombatManager.getAntisprintArmours(attacker) < 4)
		{
			return;
		}
		
		if(rand.nextInt(5) == 0)
		{
			attacker.addPotionEffect(new PotionEffect(Potion.resistance.id, 100, 0));
			attacker.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 100, 0));
			attacker.heal(1);
		}
	}
}
