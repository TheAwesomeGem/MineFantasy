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
public class ContainerCrucible extends Container
{
    private TileEntitySmelter smelter;
    private int lastCookTime;
    private int shownIngot;

    public ContainerCrucible(InventoryPlayer inventoryplayer, TileEntitySmelter tileentityfurnace)
    {
        lastCookTime = 0;
        shownIngot = 0;
        int outOffset = 0;
        smelter = tileentityfurnace;
        int s = 3;
        int xp = 37;
        int yp = 17;
        if(smelter.getTier() == 2)
        {
        	s = 4;
        	xp = 30;
        	yp = 7;
        	outOffset = 9;
        }
        for(int x = 0; x < s; x ++)
        {
        	for(int y = 0; y < s; y ++)
        	{
        		addSlotToContainer(new Slot(tileentityfurnace, 1 + (y*s)+x, xp + x*18, yp + y * 18));
        	}
        }
        addSlotToContainer(new SlotFurnace(inventoryplayer.player, tileentityfurnace, smelter.getOutSlot(), 135+outOffset, 35));//OUT
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
        icrafting.sendProgressBarUpdate(this, 0, smelter.progress);
        icrafting.sendProgressBarUpdate(this, 1, smelter.showIngot ? 1 : 0);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastCookTime != this.smelter.progress)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.smelter.progress);
            }
            
            if (this.shownIngot != (this.smelter.showIngot ? 1 : 0))
            {
                icrafting.sendProgressBarUpdate(this, 1, smelter.showIngot ? 1 : 0);
            }
        }

        this.lastCookTime = this.smelter.progress;
        this.shownIngot = this.smelter.showIngot ? 1 : 0;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
        if (id == 0)
        {
            this.smelter.progress = value;
        }
        if (id == 1)
        {
            this.smelter.showIngot = value == 1;
        }
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return smelter.isUseableByPlayer(entityplayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int num)
    {
    	int invSize = 10;
    	if(smelter.getTier() == 2)
    	{
    		invSize = 17;
    	}
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
        		if (!this.mergeItemStack(itemSlot, 0, 9, false))
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
