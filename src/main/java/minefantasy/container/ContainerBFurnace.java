package minefantasy.container;

import minefantasy.api.forge.ItemHandler;
import minefantasy.block.tileentity.TileEntityBFurnace;
import minefantasy.item.ItemListMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ContainerBFurnace extends Container
{
    private TileEntityBFurnace furnace;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;
    private int type;

    public ContainerBFurnace(int meta, EntityPlayer entityplayer, TileEntityBFurnace tileentityfurnace)
    {
    	InventoryPlayer inventoryplayer = entityplayer.inventory;
    	type = meta;
        lastCookTime = 0;
        lastBurnTime = 0;
        lastItemBurnTime = 0;
        furnace = tileentityfurnace;
        if(type == 1) // INPUT
        {
	        addSlotToContainer(new Slot(tileentityfurnace, 0, 68, 17));
	        addSlotToContainer(new Slot(tileentityfurnace, 1, 93, 17));
	        addSlotToContainer(new Slot(tileentityfurnace, 2, 80, 53));
        }
        if(type == 2) // FUEL
        {
	        addSlotToContainer(new Slot(tileentityfurnace, 0, 80, 53));
        }
        if(type == 3) // OUTPUT
        {
        	addSlotToContainer(new SlotFurnace(entityplayer, tileentityfurnace, 0, 80, 35));
        	addSlotToContainer(new SlotFurnace(entityplayer, tileentityfurnace, 1, 80, 55));
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

    public void updateCraftingResults()
    {
        super.detectAndSendChanges();
        for (int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting)crafters.get(i);
            if (lastCookTime != furnace.cookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, furnace.cookTime);           }
            if (lastBurnTime != furnace.fuel)
            {
                icrafting.sendProgressBarUpdate(this, 1, furnace.fuel);
            }
            if (lastItemBurnTime != furnace.maxFuel)
            {
                icrafting.sendProgressBarUpdate(this, 2, furnace.maxFuel);
            }
        }

        lastCookTime = furnace.cookTime;
        lastBurnTime = furnace.fuel;
        lastItemBurnTime = furnace.maxFuel;
    }

    public void updateProgressBar(int i, int j)
    {
        if (i == 0)
        {
            furnace.cookTime = j;
        }
        if (i == 1)
        {
            furnace.fuel = j;
        }
        if (i == 2)
        {
            furnace.maxFuel = j;
        }
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return furnace.isUseableByPlayer(entityplayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int num)
    {
    	int invSize = 3;
    	if(type == 2)
    		invSize = 1;
    	if(type == 3)
    		invSize = 2;
    	
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
            else if(type != 3 && furnace.getItemBurnTime(itemSlot) > 0)
            {
            	if (!this.mergeItemStack(itemSlot, 0, invSize, false))
                {
                    return null;
                }

                slot.onSlotChange(itemSlot, placedItem);
            }
            //ONLY WHEN TYPE 1
            else if(type == 1 && ItemHandler.isFlux(itemSlot))
            {
            	if (!this.mergeItemStack(itemSlot, 1, invSize, false))
                {
                    return null;
                }

                slot.onSlotChange(itemSlot, placedItem);
            }
            
            else if(type == 1)
            {
            	if (!this.mergeItemStack(itemSlot, 2, invSize, false))
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
