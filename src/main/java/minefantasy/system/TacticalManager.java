package minefantasy.system;

import minefantasy.api.armour.EnumArmourClass;
import minefantasy.api.armour.IArmourClass;
import minefantasy.api.tactic.ISpecialSenses;
import minefantasy.api.tactic.IStealthArmour;
import minefantasy.api.weapon.IHiddenItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class TacticalManager {
	public static boolean isFlankedBy(Entity attacker, EntityLivingBase defender, float angle)
	{
		float att = getAttackAngle(attacker, defender);
		return att > 270 - (angle/2) && att < 270 + (angle/2);
	}
	public static float getAttackAngle(Entity attacker, EntityLivingBase defender) {
		float attackedAtYaw = 90.0F;

		if (attacker != null) {

			double d = attacker.posX - defender.posX;
			double d1;

			for (d1 = attacker.posZ - defender.posZ; d * d + d1 * d1 < 0.0001D; d1 = (Math
					.random() - Math.random()) * 0.01D) {
				d = (Math.random() - Math.random()) * 0.01D;
			}

			attackedAtYaw = (float) ((Math.atan2(d1, d) * 180D) / Math.PI)
					- defender.rotationYaw;
			while (attackedAtYaw > 360)
				attackedAtYaw = attackedAtYaw - 360;

			while (attackedAtYaw < 0)
				attackedAtYaw = attackedAtYaw + 360;
		}
		return attackedAtYaw;
	}
	/**
	 * @param target The fellow trying to hide
	 * @param observer who is looking
	 * @return true if the observer can see the target
	 */
	public static boolean isDetected(Entity target, Entity observer)
	{
		/*
		if(targeter != null && target != null)
		{
			if(targeter instanceof EntityLiving)
			{
				if(((EntityLiving)targeter).getAttackTarget() != null && ((EntityLiving)targeter).getAttackTarget() == target)
				return true;
				
			}
		}
		*/
		if(observer instanceof EntityLiving)
		{
			if(((EntityLiving)observer).hurtResistantTime > 0)
			{
				return true;
			}
		}
		boolean sound = getAudioSound(target, observer) > getHearing(observer);
		boolean seen = getVisibility(target, observer) > getSight(observer);
		return sound || seen;
	}
	public static int getVisibility(Entity target, Entity targeter)
	{
		int r = 100;
		//BEHIND
		if(isFlankedBy(target, (EntityLivingBase)targeter, 360-(getArc(targeter)*2)))
		{
			return 0;
		}
		//Direct viewing
		else if(!isFlankedBy(target, (EntityLivingBase)targeter, 360-getArc(targeter)))
		{
			r += 10;
		}
		//DISTANCE
			int range = (int)target.getDistanceToEntity(targeter);
			
			if(range > 10)
			r -= (range - 10);
			if(range < 0.25F)
			{
				return 100;
			}
			
			//DARKNESS
			
			float light = target.worldObj.getBlockLightValue((int)target.posX, (int)target.posY, (int)target.posZ);
			if(light> 24) light = 24;
			
			float multi = 4F;
			if(target instanceof EntityLiving)
			{
				multi *= modLight((EntityLiving)target);
			}
			r -= (24-light)*multi;
		
			if(isInvisible(target)) r = 0;
		return r;
	}
	
	private static boolean isInvisible(Entity target) {
		if(target instanceof EntityLiving)
		{
			EntityLiving live = (EntityLiving)target;
			if(live.getActivePotionEffect(Potion.invisibility) != null)
			{
				for(int a = 0; a < 5; a ++)
				{
					if(live.getCurrentItemOrArmor(a) != null)
					{
						if(live.getCurrentItemOrArmor(a).getItem() instanceof IHiddenItem)
						{
							if(!((IHiddenItem)live.getCurrentItemOrArmor(a).getItem()).doesHide())
							{
								return false;
							}
						}
						else if(live.getCurrentItemOrArmor(a).getItem() instanceof IStealthArmour)
						{
							if(!((IStealthArmour)live.getCurrentItemOrArmor(a).getItem()).canTurnInvisible())
							{
								return false;
							}
						}
						else
							return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	public static int getAudioSound(Entity target, Entity targeter)
	{
		int r = 0;
			//SNEAK
				if(!isQuietSteps(target) && getSpeed(target) > 0)
				{
					r += 50;
				}
				
				//Sprint
				if(target.isSprinting())
				{
					r += 50;
				}
				//ARMOUR EFFECTS
				//ARMOUR
				int spd = r;
				if(target instanceof EntityPlayer)
					spd = (int)getAudioMod((EntityPlayer)target, r);
	
				if(spd < r) r = spd;
				if(getSpeed(target) > 0)
				{
					if(spd > r)r = spd;
				//WATER
				if(target.isInWater())
				{
					r += 10;
				}
				}
				
				//DISTANCE
				int range = (int)target.getDistanceToEntity(targeter);
				if(range > 2)
				r /= (range/2);
				
				if(target instanceof EntityLiving)
				{
					r = modSound(r, (EntityLiving)target);
				}
				return r;
				
	}
	private static boolean isQuietSteps(Entity target) {
		if(target instanceof EntityLivingBase)
		{
			EntityLivingBase live = (EntityLivingBase)target;
			if(live.getCurrentItemOrArmor(4) != null)
			{
				if(live.getCurrentItemOrArmor(4).getItem() instanceof IStealthArmour)
				{
					if(((IStealthArmour)live.getCurrentItemOrArmor(4).getItem()).quietRun())
					{
						return !target.isSprinting();
					}
				}
			}
		}
		return target.isSneaking();
	}
	private static double getSpeed(Entity target) {
		double x = target.posX - target.lastTickPosX;
		double y = target.posZ - target.lastTickPosZ;
		
		if(y < 0) y = -y;
		if(x < 0) x = -x;
		
		return Math.hypot(x, y);
	}
	public static double getAudioMod(EntityPlayer player, int r)
	{
			int r1 = r;
			EnumArmourClass AC0 = getClassInSlot(player, 0);
			r += (double)AC0.getSoundMod(0);
			EnumArmourClass AC1 = getClassInSlot(player, 1);
			r += (double)AC1.getSoundMod(1);
			EnumArmourClass AC2 = getClassInSlot(player, 2);
			r += (double)AC2.getSoundMod(2);
			EnumArmourClass AC3 = getClassInSlot(player, 3);
			r += (double)AC3.getSoundMod(3);
			return (double)r;
	}
	public static EnumArmourClass getClassInSlot(EntityPlayer player, int i) {
		return getClassFor(armourInSlot(player, i));
	}
	public static EnumArmourClass getClassFor(ItemStack armour)
	{
		if(armour == null)
		{
			return EnumArmourClass.UNARMOURED;
		}
		EnumArmourClass AC = EnumArmourClass.HEAVY;
		
		if(armour.itemID == Item.helmetLeather.itemID || armour.itemID == Item.plateLeather.itemID || armour.itemID == Item.legsLeather.itemID || armour.itemID == Item.bootsLeather.itemID)
		{
			AC = EnumArmourClass.LIGHT;
		}
		else if(armour.getItem().getClass().getName().endsWith("MoCItemArmor"))
		{
			AC = EnumArmourClass.LIGHT;
		}
		else if(armour.itemID == Item.helmetChain.itemID || armour.itemID == Item.plateChain.itemID || armour.itemID == Item.legsChain.itemID || armour.itemID == Item.bootsChain.itemID)
		{
			AC = EnumArmourClass.MEDIUM;
		}
		else if(armour.getItem() instanceof IArmourClass)
		{
			AC = ((IArmourClass)armour.getItem()).getArmourClass();
		}
		else
		{
			if(armour.getItem() != null && armour.getItem() instanceof ItemArmor)
			{
				EnumArmorMaterial material = ((ItemArmor)armour.getItem()).getArmorMaterial();
				AC = getClassOf(material, AC);
			}
		}
		AC = cfg.getClassFor(armour, AC);
		
		return AC;
	}
	
	private static EnumArmourClass getClassOf(EnumArmorMaterial material, EnumArmourClass AC) {
		return AC;
	}
	public static int getHearing(Entity entity)
	{
		if(entity instanceof ISpecialSenses)
		{
			return((ISpecialSenses)entity).getSight();
		}
		if(entity instanceof EntityMob)
		{
			if(((EntityMob)entity).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
			{
				return 10;
			}
		}
		
		return 5;
	}
	
	private static ItemStack armourInSlot(EntityPlayer player, int i) {
		return player.inventory.armorItemInSlot(i);
	}
	
	public static int getSight(Entity entity)
	{
		if(entity instanceof ISpecialSenses)
		{
			return((ISpecialSenses)entity).getSight();
		}
		return 20;
	}
	
	private static int getArc(Entity entity) {
		if(entity instanceof ISpecialSenses)
		{
			return((ISpecialSenses)entity).getViewingArc();
		}
		return 90;
	}
	
	public static float getSoundModForItem(Item item)
	{
		if(item == null)
			return 1.0F;
		
		if(item instanceof IStealthArmour)
		{
			return ((IStealthArmour)item).noiseReduction();
		}
		return 1.0F;
	}
	public static float getSightModForItem(Item item)
	{
		if(item == null)
			return 1.0F;
		
		if(item instanceof IStealthArmour)
		{
			return ((IStealthArmour)item).darknessAmplifier();
		}
		return 1.0F;
	}
	public static float modLight(EntityLiving live)
	{
		float tot = 0.0F;
		for(int a = 0; a < 4; a ++)
		{
			ItemStack armour = live.getCurrentItemOrArmor(a);
			if(armour == null)
			{
				tot += 1.0F;
			}
			else
			{
				tot += getSightModForItem(armour.getItem());
			}
		}
		return tot/4;
	}
	
	public static int modSound(int sound, EntityLiving live)
	{
		float tot = 0.0F;
		for(int a = 0; a < 4; a ++)
		{
			ItemStack armour = live.getCurrentItemOrArmor(a);
			if(armour == null)
			{
				tot += 1.0F;
			}
			else
			{
				tot += getSoundModForItem(armour.getItem());
			}
		}
		return (int)(tot/4);
	}
	public static boolean canBackstab(EntityLivingBase attacker, EntityLivingBase target)
	{
		return isFlankedBy(target, (EntityLivingBase)attacker, 90);
	}
}
