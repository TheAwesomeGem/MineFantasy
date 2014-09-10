package minefantasy.container;

import minefantasy.block.tileentity.TileEntityRoast;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

public class SlotRoast extends Slot
{
	private TileEntityRoast roast;
    public SlotRoast(TileEntityRoast tile, int slotNum, int x, int y)
    {
        super(tile, slotNum, x, y);
        roast = tile;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack item)
    {
    	return roast.isValid(item);
    }

}
