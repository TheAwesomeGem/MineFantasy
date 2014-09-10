package minefantasy.item.weapon;

import minefantasy.api.weapon.*;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;

/**
 * Waraxe:
 * does high damage
 */
public class ItemWaraxe extends ItemWeaponMF implements IWeaponCustomSpeed, IWeaponWeakArmour
{
	public ItemWaraxe(int id, EnumToolMaterial material) 
	{
		super(id, material);
		MinecraftForge.setToolClass(this,  "axe", material.getHarvestLevel());
	}

	public ItemWaraxe(int id, EnumToolMaterial material, float dam, int uses) 
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
		return 1;
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, Block block)
    {
        return ForgeHooks.isToolEffective(stack, block, material.getHarvestLevel()) ? material.getEfficiencyOnProperMaterial()*0.4F : 2.0F;
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
	public float getDurability()
	{
		return 1.25F;
	}

	@Override
	public float getArmourPower(ItemStack weapon) 
	{
		return 0.5F;
	}

	@Override
	public int getHandsUsed() 
	{
		return 1;
	}
}
