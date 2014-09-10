package minefantasy.item.weapon;

import minefantasy.api.weapon.*;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.CombatManager;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;

public class ItemBattleaxe extends ItemWaraxe implements IWeaponMobility, IWeaponCustomSpeed
{
	public ItemBattleaxe(int id, EnumToolMaterial material) 
	{
		super(id, material);
	}

	public ItemBattleaxe(int id, EnumToolMaterial material, float dam, int uses) 
	{
		this(id, material);
	}

	@Override
	public float getExaustion() 
	{
		return 0.3F;
	}

	@Override
	public float getSpeedWhenEquipped() 
	{
		float degrade = 0.16F;
		if(this.getMaterial() == ToolMaterialMedieval.MITHRIL)
		{
			degrade /= 2;
		}
		return 1.0F - degrade;
	}
	
	@Override
	/**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block block, ItemStack stack)
    {
        return ForgeHooks.isToolEffective(stack, block, material.getHarvestLevel());
    }

	@Override
	public float getKnockback() 
	{
		return 4F;
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
		return 0.95F;
	}
	
	@Override
	public float getBlockFailureChance()
	{
		return 0.15F;
	}
	
	//SPECIAL EFFECT: On hit: "Rage": Combat benefits low health, increased with projectiles and creepers. only in light armour.
	@Override
	public void applyHeavyDefenseBonus(DamageSource source, EntityLivingBase user)
	{
		//ONLY LIGHT ARMOUR/UNARMOURED
		if(CombatManager.getMovementSpeedModifier(user, false) < 1.0F)
		{
			return;
		}
		boolean creeper = source.isExplosion() && source.getEntity() != null && source.getEntity() instanceof EntityCreeper;
		
		//IF health falls below 35%
		if(user.getHealth() < (user.getMaxHealth()*0.35F) || creeper)
		{
			user.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 200, 0));
			user.addPotionEffect(new PotionEffect(Potion.resistance.id, 200, 1));
			if(source.isProjectile())
			{
				user.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 100, 1));
			}
			
			//if health falls below 20%
			if(user.getHealth() < (user.getMaxHealth()*0.20F))
			{
				user.addPotionEffect(new PotionEffect(Potion.regeneration.id, 60, 0));
				if(source.isProjectile())
				{
					user.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 100, 2));
				}
				else
				{
					user.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 100, 1));
				}
			}
		}
	}
}
