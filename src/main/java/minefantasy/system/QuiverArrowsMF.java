package minefantasy.system;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import minefantasy.api.arrow.Arrows;
import minefantasy.entity.EntityArrowMF;
import minefantasy.item.ItemListMF;
import mods.battlegear2.api.quiver.IArrowFireHandler;
import mods.battlegear2.api.quiver.IQuiverSelection;

public class QuiverArrowsMF implements IArrowFireHandler
{

	@Override
	public boolean canFireArrow(ItemStack arrow, World world, EntityPlayer player, float charge) 
	{
		if(arrow == null)return false;
		
		return arrow.itemID == ItemListMF.arrowMF.itemID;
	}

	@Override
	public EntityArrow getFiredArrow(ItemStack arrow, World world, EntityPlayer player, float charge)
	{
		EntityArrow entArrow = new EntityArrowMF(world, player, charge, arrow.getItemDamage());
		
		boolean infinite = false;
		ItemStack held = player.getHeldItem();
		if(held != null)
		{
			infinite = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, held) > 0;
		}
		if(player.capabilities.isCreativeMode)
		{
			entArrow.canBePickedUp = 0;
		}
		if(infinite)
        {
        	entArrow.canBePickedUp = 2;
        }
		
		return entArrow;
	}
}
