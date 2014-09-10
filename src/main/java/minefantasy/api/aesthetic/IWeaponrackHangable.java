package minefantasy.api.aesthetic;

import net.minecraft.item.ItemStack;

public interface IWeaponrackHangable 
{
	/**
	 * This indicates if an item is safe to run it's "EQUIPPED" Renderer.
	 */
	public boolean canUseRenderer(ItemStack item);
}
