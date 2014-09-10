package minefantasy.container;

import minefantasy.api.hound.IHoundEquipment;
import minefantasy.entity.EntityHound;
import minefantasy.item.ItemHoundArmourMF;
import minefantasy.item.ItemHoundPackMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ContainerHoundArmour extends Container {

	public EntityHound hound;
	
	public ContainerHoundArmour(EntityPlayer player, EntityHound dog)
	{
		InventoryPlayer inventoryplayer = player.inventory;
		hound = dog;
		
		if(dog.isTamed() && player != null && dog.getOwnerName().equals(player.username))
		{
			addSlotToContainer(new SlotHoundArmour(this, dog, 0, 120, 39, 0));
			addSlotToContainer(new SlotHoundArmour(this, dog, 1, 144, 39, 1));
		}
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
	
	public ContainerHoundArmour addPlayer(InventoryPlayer inventoryplayer)
	{
		if(inventoryplayer != null)
		
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
        
        return this;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return hound.isUseableByPlayer(var1);
	}

	@Override
    public ItemStack transferStackInSlot(EntityPlayer player, int num)
    {
		if(!hound.isTamed() || player == null || !hound.getOwnerName().equals(player.username))
		{
			return null;
		}
		
		try
		{
    	int invSize = hound.getSizeInventory();
        ItemStack placedItem = null;
        Slot slot = (Slot)this.inventorySlots.get(num);

        
        
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemSlot = slot.getStack();
            placedItem = itemSlot.copy();

            //Take
            if (num < invSize)
            {
            	//System.out.println("Take from Grid");
                if (!this.mergeItemStack(itemSlot, invSize, 36 + invSize, true))
                {
                    return null;
                }

                slot.onSlotChange(itemSlot, placedItem);
            }
            //Put
            else if (placedItem.getItem() instanceof IHoundEquipment && !((Slot)this.inventorySlots.get(((IHoundEquipment)placedItem.getItem()).getPiece())).getHasStack() && ((SlotHoundArmour)this.inventorySlots.get(((IHoundEquipment)placedItem.getItem()).getPiece())).isItemValid(placedItem))
            {
                int var6 = ((IHoundEquipment)placedItem.getItem()).getPiece();

                if (!this.mergeItemStack(itemSlot, var6, var6 + 1, false))
                {
                    return null;
                }
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
		}catch(Exception e)
		{
			return null;
		}
    }
}
