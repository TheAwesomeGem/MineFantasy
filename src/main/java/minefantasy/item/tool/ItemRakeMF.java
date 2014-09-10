package minefantasy.item.tool;
import java.util.ArrayList;
import java.util.List;

import minefantasy.api.weapon.IWeaponSpecial;
import minefantasy.block.BlockListMF;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.system.IPublicMaterialItem;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;
/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ItemRakeMF extends ItemToolMF implements IPublicMaterialItem, IWeaponSpecial
{
    private EnumToolMaterial toolMaterial;
	public ItemRakeMF(int id, EnumToolMaterial material)
    {
        super(id);
        toolMaterial = material;
        setMaxDamage(material.getMaxUses()*10);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
	@Override
	public EnumRarity getRarity(ItemStack itemStack)
	{
		if (getMaterial() == ToolMaterialMedieval.DRAGONFORGE)
		{
			return rarity(itemStack, 1);
		}
		if (getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
		{
			return rarity(itemStack, 2);
		}
		return super.getRarity(itemStack);
	}
	
	private EnumRarity rarity(ItemStack item, int lvl)
	{
		EnumRarity[] rarity = new EnumRarity[]{EnumRarity.common, EnumRarity.uncommon, EnumRarity.rare, EnumRarity.epic};
		if(item.isItemEnchanted())
		{
			if(lvl == 0)
			{
				lvl++;
			}
			lvl ++;
		}
		if(lvl >= rarity.length)
		{
			lvl = rarity.length-1;
		}
		return rarity[lvl];
	}
    
    @Override
    public boolean onItemUse(ItemStack rake, EntityPlayer player, World world, int x, int y, int z, int facing, float pitch, float yaw, float pan)
    {
        double dist = 8;
        AxisAlignedBB search = player.boundingBox.expand(dist, dist, dist);
        List<EntityItem>items = player.worldObj.getEntitiesWithinAABB(EntityItem.class, search);
        if(!items.isEmpty())
        {
        	for(Object obj:  items.toArray())
        	{
        		EntityItem item = (EntityItem)obj;
        		moveItem(item, player, dist);
        	}
        }
        rake.damageItem(1, player);
        return true;
    }
    private void moveItem(EntityItem item, EntityPlayer closestPlayer, double range) {
    	if (closestPlayer != null)
        {
            double d1 = (closestPlayer.posX - item.posX) / range;
            double d2 = (closestPlayer.posY + (double)closestPlayer.getEyeHeight() - item.posY) / range;
            double d3 = (closestPlayer.posZ - item.posZ) / range;
            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            double d5 = 1.0D - d4;

            double speed = 1.5D;
            if (d5 > 0.0D)
            {
                d5 *= d5;
                item.motionX += d1 / d4 * d5 * speed;
                item.motionY += d2 / d4 * d5 * speed;
                item.motionZ += d3 / d4 * d5 * speed;
            }
        }
	}

	public EnumToolMaterial getMaterial()
    {
        return this.toolMaterial;
    }
	@Override
	public void onHit(float damage, ItemStack weapon, EntityLivingBase target, EntityLivingBase attacker) 
	{
		if (getMaterial() == ToolMaterialMedieval.DRAGONFORGE) 
		{
			target.setFire(20);
		}
		
		if (getMaterial() == ToolMaterialMedieval.IGNOTUMITE)
		{
			attacker.heal(1F);
		}
	}
}
