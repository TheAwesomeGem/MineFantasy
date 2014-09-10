package minefantasy.system;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import minefantasy.api.arrow.IArrowHandler;
import minefantasy.api.arrow.ISpecialBow;

/**
 * This class is an example used to fire custom arrows. 
 */
public class ArrowFireFlint implements IArrowHandler
{

	@Override
	public boolean onFireArrow(World world, ItemStack arrow, ItemStack bow, EntityPlayer user, float charge, boolean infinite) 
	{
		if(arrow.itemID != Item.arrow.itemID)
		{
			return false;
		}
        EntityArrow entArrow = new EntityArrow(world, user, charge * 2.0F);

        int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, bow);

        if (var9 > 0)
        {
            entArrow.setDamage(entArrow.getDamage() + (double)var9 * 0.5D + 0.5D);
        }

        int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, bow);

        if (var10 > 0)
        {
            entArrow.setKnockbackStrength(var10);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, bow) > 0)
        {
            entArrow.setFire(100);
        }

        if(infinite)
        {
        	entArrow.canBePickedUp = 2;
        }
        
        if(bow != null && bow.getItem() != null && bow.getItem() instanceof ISpecialBow)
        {
        	entArrow = (EntityArrow) ((ISpecialBow)bow.getItem()).modifyArrow(entArrow);
        }
        if (!world.isRemote)
        {
            world.spawnEntityInWorld(entArrow);
        }
        
		return true;
	}

}
