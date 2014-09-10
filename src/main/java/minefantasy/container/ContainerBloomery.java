package minefantasy.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.api.refine.BloomRecipe;
import minefantasy.block.tileentity.TileEntitySmelter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;


/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ContainerBloomery extends Container
{
    private TileEntitySmelter bloomery;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;

    public ContainerBloomery(InventoryPlayer inventoryplayer, TileEntitySmelter tileentityfurnace)
    {
        lastCookTime = 0;
        lastBurnTime = 0;
        lastItemBurnTime = 0;
        bloomery = tileentityfurnace;
        addSlotToContainer(new Slot(tileentityfurnace, 0, 56, 46));//FUEL
        addSlotToContainer(new Slot(tileentityfurnace, 1, 79, 17));//INPUT
        addSlotToContainer(new Slot(tileentityfurnace, 2, 102, 46));//COAL
        addSlotToContainer(new SlotFurnace(inventoryplayer.player, //OUTPUT
        		tileentityfurnace, bloomery.getOutSlot(), 79, 53));
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

    @Override
    public void addCraftingToCrafters(ICrafting icrafting)
    {
        super.addCraftingToCrafters(icrafting);
        icrafting.sendProgressBarUpdate(this, 0, bloomery.progress);
        icrafting.sendProgressBarUpdate(this, 1, bloomery.fuel);
        icrafting.sendProgressBarUpdate(this, 2, bloomery.maxFuel);
    }
//ContainerFurnace
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastCookTime != this.bloomery.progress)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.bloomery.progress);
            }

            if (this.lastBurnTime != this.bloomery.fuel)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.bloomery.fuel);
            }

            if (this.lastItemBurnTime != this.bloomery.maxFuel)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.bloomery.maxFuel);
            }
        }

        this.lastCookTime = this.bloomery.progress;
        this.lastBurnTime = this.bloomery.fuel;
        this.lastItemBurnTime = this.bloomery.maxFuel;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
        if (id == 0)
        {
            this.bloomery.progress = value;
        }

        if (id == 1)
        {
            this.bloomery.fuel = value;
        }

        if (id == 2)
        {
            this.bloomery.maxFuel = value;
        }
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return bloomery.isUseableByPlayer(entityplayer);
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
            	//System.out.println("Take from Grid");
                if (!this.mergeItemStack(itemSlot, invSize, 36 + invSize, true))
                {
                    return null;
                }

                slot.onSlotChange(itemSlot, placedItem);
            }
            //Put
            else
            {
            	if(bloomery.goesInCarbon(itemSlot))
            	{
            		if (!this.mergeItemStack(itemSlot, 2, 3, false))
                    {
                        return null;
                    }
            	}
            	else if(bloomery.goesInFuel(itemSlot))
            	{
            		if (!this.mergeItemStack(itemSlot, 0, 1, false))
                    {
                        return null;
                    }
            	}
            	else
            	{
            		if (!this.mergeItemStack(itemSlot, 1, 2, true))
                    {
                        return null;
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
}
