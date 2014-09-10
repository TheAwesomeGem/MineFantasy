package minefantasy.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.block.tileentity.TileEntityOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerOven extends Container
{
    private TileEntityOven oven;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;

    public ContainerOven(InventoryPlayer user, TileEntityOven tile)
    {
    	tile.openChest();
    	
        this.oven = tile;
        this.addSlotToContainer(new Slot(tile, 0, 56, 17));
        this.addSlotToContainer(new Slot(tile, 1, 56, 53));
        this.addSlotToContainer(new SlotFurnace(user.player, tile, 2, 116, 35));
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(user, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(user, i, 8 + i * 18, 142));
        }
    }
    
    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        this.oven.closeChest();
    }
    

    @Override
    public void addCraftingToCrafters(ICrafting manager)
    {
        super.addCraftingToCrafters(manager);
        manager.sendProgressBarUpdate(this, 0, this.oven.progress);
        manager.sendProgressBarUpdate(this, 1, this.oven.fuel);
        manager.sendProgressBarUpdate(this, 2, this.oven.maxFuel);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        
        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastCookTime != this.oven.progress)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.oven.progress);
            }

            if (this.lastBurnTime != this.oven.fuel)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.oven.fuel);
            }

            if (this.lastItemBurnTime != this.oven.maxFuel)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.oven.maxFuel);
            }
        }

        this.lastCookTime = this.oven.progress;
        this.lastBurnTime = this.oven.fuel;
        this.lastItemBurnTime = this.oven.maxFuel;
        
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
    	
        if (id == 0)
        {
            this.oven.progress = value;
        }

        if (id == 1)
        {
            this.oven.fuel = value;
        }

        if (id == 2)
        {
            this.oven.maxFuel = value;
        }
        
    }

    @Override
    public boolean canInteractWith(EntityPlayer user)
    {
        return this.oven.isUseableByPlayer(user);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer user, int num)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(num);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (num == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (num != 1 && num != 0)
            {
                if (oven.getResult(itemstack1) != null)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (oven.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (num >= 3 && num < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (num >= 30 && num < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(user, itemstack1);
        }

        return itemstack;
    }
}
