package minefantasy.container;

import java.util.Iterator;

import minefantasy.api.forge.HeatableItem;
import minefantasy.block.tileentity.TileEntityForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerForge extends Container
{
    private TileEntityForge furnace;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;

    public ContainerForge(InventoryPlayer invPlayer, TileEntityForge forge)
    {
        this.furnace = forge;
        this.addSlotToContainer(new Slot(forge, 0, 26, 44));
        for(int x = 0; x < 3; x ++)
        {
        	for(int y = 0; y < 3; y ++)
            {
        		this.addSlotToContainer(new Slot(forge, (y*3)+x + 1, 58 + (x * 22), 13 + (y * 22)));
            }
        }
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(invPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(invPlayer, var3, 8 + var3 * 18, 142));
        }
    }

    public void addCraftingToCrafters(ICrafting craft)
    {
        super.addCraftingToCrafters(craft);
        craft.sendProgressBarUpdate(this, 1, this.furnace.fuel);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void updateCraftingResults()
    {
        super.detectAndSendChanges();
        Iterator var1 = this.crafters.iterator();

        while (var1.hasNext())
        {
            ICrafting var2 = (ICrafting)var1.next();

            if (this.lastBurnTime != this.furnace.fuel)
            {
                var2.sendProgressBarUpdate(this, 1, this.furnace.fuel);
            }
        }

        this.lastBurnTime = this.furnace.fuel;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int amount)
    {
        if (id == 1)
        {
            this.furnace.fuel = amount;
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int num)
    {
    	int invSize = 3 * 3 + 1;
        ItemStack placedItem = null;
        Slot slot = (Slot)this.inventorySlots.get(num);

        
        
        
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemSlot = slot.getStack();
            placedItem = itemSlot.copy();

            //Take
            if (num < invSize)
            {
                if (!this.mergeItemStack(itemSlot, invSize, 36 + invSize, true))
                {
                    return null;
                }

                slot.onSlotChange(itemSlot, placedItem);
            }
            //Put
            else
            {
            	if(furnace.isItemFuel(itemSlot))
            	{
            		if (((Slot)this.inventorySlots.get(0)).getHasStack())
                    {
                        return null;
                    }

                    if (itemSlot.hasTagCompound() && itemSlot.stackSize == 1)
                    {
                        ((Slot)this.inventorySlots.get(0)).putStack(itemSlot.copy());
                        itemSlot.stackSize = 0;
                    }
                    else if (itemSlot.stackSize >= 1)
                    {
                        ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemSlot.itemID, 1, itemSlot.getItemDamage()));
                        --itemSlot.stackSize;
                    }
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

	public boolean canInteractWith(EntityPlayer user)
    {
        return this.furnace.isUseableByPlayer(user);
    }
}
