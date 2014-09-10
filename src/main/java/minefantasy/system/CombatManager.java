package minefantasy.system;

import java.lang.annotation.Target;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;

import minefantasy.MineFantasyBase;
import minefantasy.api.armour.EnumArmourClass;
import minefantasy.api.armour.IArmourClass;
import minefantasy.api.armour.IArmourCustomSpeed;
import minefantasy.api.armour.IElementalResistance;
import minefantasy.api.weapon.*;
import minefantasy.block.BlockListMF;
import minefantasy.item.ElementalResistNone;
import minefantasy.item.I2HWeapon;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.item.weapon.ItemBroadsword;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class CombatManager 
{
	private static Random rand = new Random();
	
	public static void onAttack(LivingHurtEvent event) 
	{
		EntityLivingBase target = event.entityLiving;
		event.ammount = resistWithArmour(event.source, event.ammount, target);
		
		ItemStack defense = target.getHeldItem();
		if (defense != null) 
		{
			if(defense.getItem() instanceof IWeaponSpecialBlock)
			{
				event.ammount  = ((IWeaponSpecialBlock)defense.getItem()).blockDamage(target, event.ammount, event.source);
			}
			if(defense.getItem() instanceof IHitReaction)
			{
				((IHitReaction)defense.getItem()).onUserHit(event.source, target);
			}
		}
		
		float damage = event.ammount;
		int hurtTimeTemp = target.hurtTime;
		int hurtResistanceTimeTemp = target.hurtResistantTime;
		
		if (event.source instanceof EntityDamageSource && !(event.source instanceof EntityDamageSourceIndirect)  && !event.source.damageType.equals("battlegearExtra")) 
		{
			Entity entityHitter = ((EntityDamageSource) event.source).getEntity();
			
			if (entityHitter instanceof EntityLivingBase) 
			{
				EntityLivingBase attacker = (EntityLivingBase) entityHitter;
				ItemStack weapon = attacker.getHeldItem();
				
				if (weapon != null) 
				{
					if(weapon.getItem() instanceof IWeightedWeapon)
					{
						float power = ((IWeightedWeapon)weapon.getItem()).getBlockFailureChance();
						if(target instanceof EntityPlayer && power > 0.0F)
						{
							if(!target.worldObj.isRemote && defense != null && ((EntityPlayer)target).isBlocking())
							{
								int itemdam = (int)(Math.floor(damage * power * 2));
								defense.damageItem(itemdam, target);
								
								if(target.getRNG().nextFloat() < power)
								{
									entityDropItem(target, defense, 0.2F);
									target.setCurrentItemOrArmor(0, null);
								}
							}
						}
					}
					if(weapon.getItem() instanceof ICustomDamager)
					{
						event.ammount = damage = ((ICustomDamager)weapon.getItem()).getHitDamage(weapon);
					}
					
					if(weapon.getItem() instanceof IWeaponWeakArmour)
					{
						float multiplier = ((IWeaponWeakArmour)weapon.getItem()).getArmourPower(weapon);
						event.ammount = damage = applyArmorCalculations(multiplier, target, event.source, damage);
					}
					if(weapon.getItem() instanceof IWeaponPenetrateArmour)
					{
						
						float dam = ((IWeaponPenetrateArmour)weapon.getItem()).getAPDamage();
						event.ammount -= dam;
						
						damageWithBonus(target, DamageSourceAP.blunt, damage, dam);
						
						if(((IWeaponPenetrateArmour)weapon.getItem()).buffDamage())
						{
							float bonus = ((IWeaponPenetrateArmour)weapon.getItem()).getArmourDamageBonus()-1.0F;
							if(bonus > 0.0F)
							{
								int AD = (int)((damage + dam) * bonus);
								if(target instanceof EntityPlayer)
								{
									((EntityPlayer)target).inventory.damageArmor(AD);
								}
							}
						}
					}
					
					if(attacker.isSneaking() && TacticalManager.isDetected(target, attacker) && TacticalManager.isFlankedBy(attacker, (EntityLivingBase)target, 90))
					{
						float bonus = 1.0F;
						if(weapon.getItem() instanceof IStealthWeapon)
						{
							bonus = ((IStealthWeapon)weapon.getItem()).getBackstabModifier();
						}
						event.ammount = damage = applySneakAttack(attacker, target, damage * bonus);
					}
					if(attacker.fallDistance > 3.0F && attacker.fallDistance < 23.0F && weapon.getItem() instanceof IStealthWeapon)
					{
						if(((IStealthWeapon)weapon.getItem()).canDropAttack())
						{
							float bonus = ((IStealthWeapon)weapon.getItem()).getDropModifier();
							float dam = (attacker.fallDistance-3.0F) * bonus;
							
							event.ammount = damage = applyDropAttack(attacker, target, damage, bonus);
						}
					}

					if (weapon.getItem() instanceof IWeaponSpecial)
					{
						((IWeaponSpecial) weapon.getItem()).onHit(damage, weapon, target, attacker);
					}
					
					if (weapon.getItem() instanceof IWeaponCustomSpeed) 
					{
						hurtTimeTemp += ((IWeaponCustomSpeed)weapon.getItem()).getHitTime(weapon, target);
						hurtResistanceTimeTemp += ((IWeaponCustomSpeed)weapon.getItem()).getHitTime(weapon, target);
					}
				}
				EventManagerMF.makeHitSound(weapon, target);
			}
		}
		target.hurtResistantTime = hurtResistanceTimeTemp;
		target.hurtTime = hurtTimeTemp;
		
	}
	
	public static float applySneakAttack(Entity attacker, Entity target, float dam)
	{
		target.playSound("minefantasy:Weapon.crit", dam/2F, 1.0F);
		if(attacker instanceof EntityPlayer)
		{
			((EntityPlayer)attacker).onCriticalHit(target);
		}
		return dam;
	}
	
	public static void attackSpecial(Entity attacker, Entity target, float dam)
	{
		target.attackEntityFrom(new EntityDamageSource("battlegearSpecial", attacker), dam);
	}
	
	public static float applyDropAttack(EntityLivingBase attacker, Entity target, float dam, float bonus)
	{
		target.playSound("minefantasy:Weapon.crit", dam*2F, 1.0F);
		if(attacker instanceof EntityPlayer)
		{
			((EntityPlayer)attacker).onCriticalHit(target);
		}
		
		attacker.getHeldItem().damageItem((int)(bonus/2), attacker);
		attacker.setPositionAndUpdate(target.posX, target.posY, target.posZ);
		attacker.motionY = 0;
		attacker.fallDistance = 0;
		
		return dam*bonus;
	}
	
	
	
	protected static float applyArmorCalculations(float power, EntityLivingBase target, DamageSource source, float damage)
    {
        if (!source.isUnblockable())
        {
            int i = 25 - (int)(target.getTotalArmorValue() * power);
            float f1 = damage * (float)i;
            damage = f1 / 25.0F;
        }

        return damage;
    }
	
	
	
	
	
	
	
	
	
	
	
	

	public static float getMovementSpeedModifier(EntityLivingBase entity)
	{
		return getMovementSpeedModifier(entity, true);
	}
	public static float getMovementSpeedModifier(EntityLivingBase entity, boolean includeWep)
	{
		float r = 1F;

			float AC0 = CombatManager.getMoveSpeedFor(entity, 0);
			r -= (float)CombatManager.getArmourFractionOfTotal(AC0, 4);
			
			float AC1 = CombatManager.getMoveSpeedFor(entity, 1);
			r -= (float)CombatManager.getArmourFractionOfTotal(AC1, 7);
			
			float AC2 = CombatManager.getMoveSpeedFor(entity, 2);
			r -= (float)CombatManager.getArmourFractionOfTotal(AC2, 8);
			
			float AC3 = CombatManager.getMoveSpeedFor(entity, 3);
			r -= (float)CombatManager.getArmourFractionOfTotal(AC3, 5);
			
			if(includeWep)
			{
				r *= slowdownHeavyWeapon(entity);
			}
		return r;
	}
	
	public static int getAntisprintArmours(EntityLivingBase entity)
	{
		int r = 0;
		EnumArmourClass AC0 = CombatManager.getClassInSlot(entity, 0);
		r += AC0.canSprintIn() ? 0 : 1;
		
		EnumArmourClass AC1 = CombatManager.getClassInSlot(entity, 1);
		r += AC1.canSprintIn() ? 0 : 1;
		
		EnumArmourClass AC2 = CombatManager.getClassInSlot(entity, 2);
		r += AC2.canSprintIn() ? 0 : 1;
		
		EnumArmourClass AC3 = CombatManager.getClassInSlot(entity, 3);
		r += AC3.canSprintIn() ? 0 : 1;
		return r;
	}
	
	public static float getArmourExaustModifier(EntityLivingBase player)
	{
		float r = 0F;

			EnumArmourClass AC0 = CombatManager.getClassInSlot(player, 0);
			r += (float)CombatManager.getArmourFractionOfTotal(AC0.getExaustion(), 4);
			
			EnumArmourClass AC1 = CombatManager.getClassInSlot(player, 1);
			r += (float)CombatManager.getArmourFractionOfTotal(AC1.getExaustion(), 7);
			
			EnumArmourClass AC2 = CombatManager.getClassInSlot(player, 2);
			r += (float)CombatManager.getArmourFractionOfTotal(AC2.getExaustion(), 8);
			
			EnumArmourClass AC3 = CombatManager.getClassInSlot(player, 3);
			r += (float)CombatManager.getArmourFractionOfTotal(AC3.getExaustion(), 5);
		return r;
	}
	
	private static float getMoveSpeedFor(EntityLivingBase entity, int i) 
	{
		EnumArmourClass AC = CombatManager.getClassInSlot(entity, i);
		float r = AC.getSpeedReduction();
		
		ItemStack armour = armourInSlot(entity, i);
		
		if(armour != null && armour.getItem() != null)
		{
			if(armour.getItem() instanceof IArmourCustomSpeed)
			{
				r = 1F - ((IArmourCustomSpeed)armour.getItem()).getMoveSpeed(armour);
			}
		}
		return r;
	}
	
	public static double getArmourFractionOfTotal(double total, int armour)
	{
		return (total/24)*armour;
	}
	public static ItemStack armourInSlot(EntityLivingBase ent, int i) {
		return ent.getCurrentItemOrArmor(i+1);
	}
	public static EnumArmourClass getClassInSlot(EntityLivingBase entity, int i) {
		return TacticalManager.getClassFor(armourInSlot(entity, i));
	}
	private static float slowdownHeavyWeapon(EntityLivingBase entity) {
		float wp = 1F;
		
		ItemStack held = entity.getHeldItem();
		if(held != null && held.getItem() instanceof IWeaponMobility)
		{
			float minus = ((IWeaponMobility)held.getItem()).getSpeedWhenEquipped();
			if(wp > minus)wp = minus;
		}
		
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			
			for(int a = 0; a < 9; a ++)
			{
				ItemStack slot = player.inventory.mainInventory[a];
				if( slot != null && slot.getItem() instanceof IWeaponMobility)
				{
					float minus = ((IWeaponMobility)slot.getItem()).getSpeedWhenEquipped();
					if(wp > minus)wp = minus;
				}
			}
		}
		else
		{
			ItemStack s = entity.getHeldItem();
			if( s != null && s.getItem() instanceof IWeaponMobility)
			{
				float minus = ((IWeaponMobility)s.getItem()).getSpeedWhenEquipped();
				if(wp > minus)wp = minus;
			}
		}
		if(wp < 0.1) return 0.1F;
		
		if(cfg.disableWeight && wp < 1.0F)
		{
			return 1.0F;
		}
		return wp;
	}
	
	/**
	 * Damages an entity but will not register as a kill (meaning enemies will not die twice)
	 */
	public static void damageWithBonus(EntityLivingBase target, DamageSource source, float damage, float bonus)
	{
		if(bonus < target.getHealth())
		{
			target.attackEntityFrom(DamageSourceAP.blunt, bonus+damage);
			if(!target.worldObj.isRemote)
			{
				System.out.println("Damage");
			}
		}
		else
		{
			if(!target.worldObj.isRemote)
			{
				target.setHealth(-1F); // Over here it sets health to <0 killing the entity, but not registering as it's own kill
			}
		}
	}
	
	public static void entityDropItem(Entity entity, ItemStack drop, float range)
    {
        if (drop.stackSize == 0)
        {
            return;
        }
        else
        {
            EntityItem entityitem = new EntityItem(entity.worldObj, entity.posX, entity.posY + (double)range, entity.posZ, drop);
            entityitem.delayBeforeCanPickup = 50;
            if (entity.captureDrops)
            {
            	entity.capturedDrops.add(entityitem);
            }
            else
            {
            	entity.worldObj.spawnEntityInWorld(entityitem);
            }
        }
    }
	
	private static float resistWithArmour(DamageSource source, float dam, EntityLivingBase target)
	{
		for(int a = 1; a < 5; a ++)
		{
			ItemStack piece = target.getCurrentItemOrArmor(a);
			if(piece != null && piece.getItem() instanceof IElementalResistance)
			{
				IElementalResistance resist = (IElementalResistance)piece.getItem();
				
				if(source.isFireDamage())
				{
					dam *= resist.fireResistance();
				}
			}
		}
		return dam;
	}
}
