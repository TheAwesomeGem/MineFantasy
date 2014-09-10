package minefantasy.item.weapon;

import java.util.List;

import minefantasy.api.weapon.*;
import minefantasy.entity.EntityThrownSpear;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.data_minefantasy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

/**
 * Spear:
 * long, slow, damaging, can joust
 */
public class ItemLance extends ItemWeaponMF implements IWeaponMobility, IWeaponCustomSpeed, IExtendedReachItem
{
	public ItemLance(int id, EnumToolMaterial material) 
	{
		super(id, material);
	}

	public ItemLance(int id, EnumToolMaterial material, float dam, int uses) 
	{
		this(id, material);
	}

	@Override
	public float getDamageModifier() 
	{
		return 0.25F;
	}

	@Override
	public int getHitTime(ItemStack weapon, EntityLivingBase target) 
	{
		return 15;
	}

	@Override
	public float getReachModifierInBlocks(ItemStack mainhand)
	{
		return 3.5F;
	}

	@Override
	public float getExaustion() 
	{
		return 0.2F;
	}

	@Override
	public float getSpeedWhenEquipped() 
	{
		float degrade = 0.25F;
		if(this.getMaterial() == ToolMaterialMedieval.MITHRIL)
		{
			degrade /= 2;
		}
		return 1.0F - degrade;
	}

    public EnumAction getItemUseAction(ItemStack item)
    {
        return EnumAction.none;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        return item;
    }
    @Override
	public float getKnockback() 
	{
		return 8F;
	}
    
	@Override
	public boolean canJoust()
	{
		return true;
	}

	@Override
	public float getDurability() 
	{
		return 2.0F;
	}
	@Override
	public float getJoustModifierDamage() 
	{
		return 7.5F;
	}

	@Override
	public int getHandsUsed() 
	{
		return 2;
	}
	@Override
	public float getBalance()
	{
		return 1.0F;
	}
}
