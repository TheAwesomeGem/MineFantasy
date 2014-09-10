package minefantasy.system;

import java.util.Random;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import minefantasy.api.weapon.ICrossbowHandler;
import minefantasy.entity.EntityArrowMF;
import minefantasy.entity.EntityBoltMF;
import minefantasy.item.ItemListMF;

public class CrossbowAmmoMF implements ICrossbowHandler {

	@Override
	public boolean shoot(ItemStack item, World world, EntityPlayer shooter, float accuracy, float damage, ItemStack ammo) 
	{
		boolean infinite = shooter.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
		float power = 2.0F;
		
		
		//FLINT
		if(ammo.itemID == Item.arrow.itemID)
		{
			EntityArrow shot = new EntityArrow(world, shooter,
					power);
			int enc_Power = EnchantmentHelper.getEnchantmentLevel(
					Enchantment.power.effectId, item);
			if (enc_Power > 0) 
			{
				shot.setDamage(damage + (double) enc_Power * 0.5D + 0.5D);
			}
	
			int enc_Punch = EnchantmentHelper.getEnchantmentLevel(
					Enchantment.punch.effectId, item);
	
			if (enc_Punch > 0) {
				shot.setKnockbackStrength(enc_Punch);
			}
	
			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId,
					item) > 0) {
				shot.setFire(100);
			}
	
			if (infinite) {
				shot.canBePickedUp = 2;
			}
	
			Random rand = new Random();
			if (!world.isRemote) {
				world.spawnEntityInWorld(shot);
			}
			return true;
		}
		
		
		//MF ARROW
		if(ammo.itemID == ItemListMF.arrowMF.itemID)
		{
	        if(shootSpecificArrow(item, world, shooter, power, ammo))
	        {
	        	return true;
	        }
		}
		
		//MF BOLT
		if(ammo.itemID == ItemListMF.boltMF.itemID)
		{
	        if(shootSpecificBolt(item, world, shooter, power, ammo, damage))
	        {
	        	return true;
	        }
		}
		
		return false;
	}
	
	
	
	public boolean shootSpecificArrow(ItemStack item, World world, EntityPlayer player, float power, ItemStack ammo)
    {
		boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
		Random itemRand = new Random();
        EntityArrowMF arrow = new EntityArrowMF(world, player, power, ammo.getItemDamage());

        int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, item);

        if (var9 > 0)
        {
            arrow.setDamage(arrow.getDamage() + (double)var9 * 0.5D + 0.5D);
        }

        int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, item);

        if (var10 > 0)
        {
            arrow.setKnockbackStrength(var10);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, item) > 0)
        {
            arrow.setFire(100);
        }

        if(infinite)
        {
        	arrow.canBePickedUp = 2;
        }

        if (!world.isRemote)
        {
            world.spawnEntityInWorld(arrow);
        }
        return true;
    }
	
	
	
	public boolean shootSpecificBolt(ItemStack item, World world, EntityPlayer player, float power, ItemStack ammo, float damage)
    {
		boolean infinite = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, item) > 0;
		Random itemRand = new Random();
		EntityBoltMF arrow = new EntityBoltMF(world, player, power, ammo.getItemDamage());

		arrow.setDamage(damage);
        int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, item);

        if (var9 > 0)
        {
            arrow.setDamage(arrow.getDamage() + (double)var9 * 0.5D + 0.5D);
        }

        int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, item);

        if (var10 > 0)
        {
            arrow.setKnockbackStrength(var10);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, item) > 0)
        {
            arrow.setFire(100);
        }

        if(infinite)
        {
        	arrow.canBePickedUp = 2;
        }

        if (!world.isRemote)
        {
            world.spawnEntityInWorld(arrow);
        }
        return true;
    }
}
