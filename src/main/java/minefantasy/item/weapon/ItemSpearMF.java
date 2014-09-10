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
public class ItemSpearMF extends ItemWeaponMF implements IWeaponMobility, IWeaponCustomSpeed, IExtendedReachItem
{
	public ItemSpearMF(int id, EnumToolMaterial material) 
	{
		super(id, material);
	}

	public ItemSpearMF(int id, EnumToolMaterial material, float dam, int uses) 
	{
		this(id, material);
	}

	@Override
	public float getDamageModifier() 
	{
		return 1.2F;
	}

	@Override
	public int getHitTime(ItemStack weapon, EntityLivingBase target) 
	{
		if(isPrimitive())
		{
			return 6;
		}
		return 8;
	}

	@Override
	public float getReachModifierInBlocks(ItemStack mainhand)
	{
		if(isPrimitive())
		{
			return 2.0F;
		}
		return 3.0F;
	}

	@Override
	public float getExaustion() 
	{
		if(isPrimitive())
		{
			return 0.0F;
		}
		return 0.1F;
	}

	@Override
	public float getSpeedWhenEquipped() 
	{
		if(isPrimitive())
		{
			return 1.0F;
		}
		float degrade = 0.10F;
		if(this.getMaterial() == ToolMaterialMedieval.MITHRIL)
		{
			degrade /= 2;
		}
		return 1.0F - degrade;
	}

	public int getMaxItemUseDuration(ItemStack item)
    {
        return 8;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack item)
    {
        return EnumAction.bow;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        player.setItemInUse(item, this.getMaxItemUseDuration(item));
        return item;
    }
    @Override
	public float getKnockback() 
	{
    	if(isPrimitive())
		{
			return 2.0F;
		}
		return 5.5F;
	}
    
    @Override
	public ItemStack onEaten(ItemStack item, World world, EntityPlayer player)
	{
		if(item.itemID != itemID)return item ;
        if(!world.isRemote)
        {
        	float d = (float)player.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            float var7 = 1.0F;
            
    		if(this.getMaterial() == ToolMaterialMedieval.MITHRIL)
    			var7 += 1.05F;
        	
			EntityThrownSpear spear = new EntityThrownSpear(world, player, var7).setSpear(item.copy());
        	spear.canBePickedUp = 1;
        	spear.setDamage(d);
			world.playSoundAtEntity(player, data_minefantasy.sound("spearThrow"), 1.2F, 0.5F / (itemRand.nextFloat() * 0.5F + 1F));
        	world.spawnEntityInWorld(spear);
        	spear.syncSpear();
        }
            --item.stackSize;
    	if(item.stackSize <= 0)
    		player.destroyCurrentEquippedItem();
            player.swingItem();
        return item;
	}
    
	@Override
	public boolean canJoust()
	{
		if(isPrimitive())
		{
			return false;
		}
		return true;
	}
	
	

	@Override
	public float getDurability() 
	{
		if(isPrimitive())
		{
			return 1.0F;
		}
		return 1.5F;
	}

	@Override
	public int getHandsUsed() 
	{
		return 2;
	}
	@Override
	public float getBalance()
	{
		return 0.5F;
	}
}
