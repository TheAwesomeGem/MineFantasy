package minefantasy.container;

import minefantasy.block.tileentity.TileEntityWeaponRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;


/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ContainerRack extends Container
{
    private TileEntityWeaponRack rack;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;

    public ContainerRack(InventoryPlayer inventoryplayer, TileEntityWeaponRack tile)
    {
        lastCookTime = 0;
        lastBurnTime = 0;
        lastItemBurnTime = 0;
        rack = tile;
        addSlotToContainer(new SlotRack(tile, 0, 19, 36));
        addSlotToContainer(new SlotRack(tile, 1, 56, 36));
        addSlotToContainer(new SlotRack(tile, 2, 93, 36));
        addSlotToContainer(new SlotRack(tile, 3, 130, 36));
        for (int i = 0; i < 3; i++)
        {
            for (int k = 0; k < 9; k++)
            {
            	addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++)
        {
        	addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }
    }
    
    public boolean isItemValid(ItemStack item)
    {
    	return rack.canHang(item);
    }


    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return rack.isUseableByPlayer(entityplayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int num)
    {
    	int invSize = 4;
        ItemStack placedItem = null;
        Slot slot = (Slot)this.inventorySlots.get(num);

        
        
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemSlot = slot.getStack();
            placedItem = itemSlot.copy();

            //Take
            if (num < invSize)
            {
            	//System.out.println("Take from Rack");
                if (!this.mergeItemStack(itemSlot, invSize, 36 + invSize, true))
                {
                    return null;
                }

                slot.onSlotChange(itemSlot, placedItem);
            }
            //Put
            else
            {
            	if(isItemValid(itemSlot))
            	if (!this.mergeItemStack(itemSlot, 0, invSize, false))
                {
                    return null;
                }

                slot.onSlotChange(itemSlot, placedItem);
            }

            if (itemSlot.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemSlot.stackSize == placedItem.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemSlot);
        }

        return placedItem;
    }
}
