package minefantasy.item.weapon;

import minefantasy.api.weapon.IExtendedReachItem;
import minefantasy.api.weapon.IWeaponCustomSpeed;
import minefantasy.item.ToolMaterialMedieval;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Halbeard:
 * Heavier spear
 */
public class ItemHalbeard extends ItemSpearMF
{
	public ItemHalbeard(int id, EnumToolMaterial material) 
	{
		super(id, material);
	}

	public ItemHalbeard(int id, EnumToolMaterial material, float dam, int uses) 
	{
		this(id, material);
	}

	@Override
	public float getDamageModifier() 
	{
		return 1.8F;
	}

	@Override
	public int getHitTime(ItemStack weapon, EntityLivingBase target) 
	{
		return 10;
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
	public float getKnockback() 
	{
		return 6F;
	}
	@Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {
        return item;
    }
	@Override
	public float getBalance()
	{
		return 0.75F;
	}
}
