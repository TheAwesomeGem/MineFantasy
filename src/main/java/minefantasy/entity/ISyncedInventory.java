package minefantasy.entity;

import net.minecraft.item.ItemStack;

/**
 * think of this like a basic form of IInventory, only used to sync items
 * (like spears)
 */
public interface ISyncedInventory 
{
	public void setItem(ItemStack item, int slot);
}
