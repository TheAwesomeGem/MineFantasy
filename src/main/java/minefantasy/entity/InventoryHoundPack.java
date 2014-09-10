package minefantasy.entity;

import java.util.List;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;

public class InventoryHoundPack implements IInventory
{

	/**
     * Set true whenever the inventory changes. Nothing sets it false so you will have to write your own code to check
     * it and reset the value.
     */
    public boolean inventoryChanged = false;
    
	private String inventoryTitle;
    private int slotsCount;
    private ItemStack[] inventoryContents;
    private List field_70480_d;
    private boolean field_94051_e;

    public InventoryHoundPack(String title, boolean local, int slots)
    {
        this.inventoryTitle = title;
        this.field_94051_e = local;
        this.slotsCount = slots;
        this.inventoryContents = new ItemStack[slots];
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int slot)
    {
        return this.inventoryContents[slot];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int slot, int num)
    {
        if (this.inventoryContents[slot] != null)
        {
            ItemStack itemstack;

            if (this.inventoryContents[slot].stackSize <= num)
            {
                itemstack = this.inventoryContents[slot];
                this.inventoryContents[slot] = null;
                this.onInventoryChanged();
                return itemstack;
            }
            else
            {
                itemstack = this.inventoryContents[slot].splitStack(num);

                if (this.inventoryContents[slot].stackSize == 0)
                {
                    this.inventoryContents[slot] = null;
                }

                this.onInventoryChanged();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.inventoryContents[slot] != null)
        {
            ItemStack itemstack = this.inventoryContents[slot];
            this.inventoryContents[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        this.inventoryContents[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.slotsCount;
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return this.inventoryTitle;
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's
     * language. Otherwise it will be used directly.
     */
    public boolean isInvNameLocalized()
    {
        return this.field_94051_e;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        this.inventoryChanged = true;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return true;
    }

    public void openChest() {}

    public void closeChest() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return true;
    }
	
	
    public boolean addItemStackToInventory(ItemStack item)
    {
        if (item == null)
        {
            return false;
        }
        else
        {
            try
            {
                int i;

                if (item.isItemDamaged())
                {
                    i = this.getFirstEmptyStack();

                    if (i >= 0)
                    {
                        this.inventoryContents[i] = ItemStack.copyItemStack(item);
                        this.inventoryContents[i].animationsToGo = 5;
                        item.stackSize = 0;
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    do
                    {
                        i = item.stackSize;
                        item.stackSize = this.storePartialItemStack(item);
                    }
                    while (item.stackSize > 0 && item.stackSize < i);

                    return item.stackSize < i;
                }
            }
            catch (Throwable throwable)
            {
            	return false;
            }
        }
    }
	
	
	/**
     * Returns the first item stack that is empty.
     */
    public int getFirstEmptyStack()
    {
        for (int i = 0; i < this.inventoryContents.length; ++i)
        {
            if (this.inventoryContents[i] == null)
            {
                return i;
            }
        }

        return -1;
    }
    
    
    private int storePartialItemStack(ItemStack par1ItemStack)
    {
        int i = par1ItemStack.itemID;
        int j = par1ItemStack.stackSize;
        int k;

        if (par1ItemStack.getMaxStackSize() == 1)
        {
            k = this.getFirstEmptyStack();

            if (k < 0)
            {
                return j;
            }
            else
            {
                if (this.inventoryContents[k] == null)
                {
                    this.inventoryContents[k] = ItemStack.copyItemStack(par1ItemStack);
                }

                return 0;
            }
        }
        else
        {
            k = this.storeItemStack(par1ItemStack);

            if (k < 0)
            {
                k = this.getFirstEmptyStack();
            }

            if (k < 0)
            {
                return j;
            }
            else
            {
                if (this.inventoryContents[k] == null)
                {
                    this.inventoryContents[k] = new ItemStack(i, 0, par1ItemStack.getItemDamage());

                    if (par1ItemStack.hasTagCompound())
                    {
                        this.inventoryContents[k].setTagCompound((NBTTagCompound)par1ItemStack.getTagCompound().copy());
                    }
                }

                int l = j;

                if (j > this.inventoryContents[k].getMaxStackSize() - this.inventoryContents[k].stackSize)
                {
                    l = this.inventoryContents[k].getMaxStackSize() - this.inventoryContents[k].stackSize;
                }

                if (l > this.getInventoryStackLimit() - this.inventoryContents[k].stackSize)
                {
                    l = this.getInventoryStackLimit() - this.inventoryContents[k].stackSize;
                }

                if (l == 0)
                {
                    return j;
                }
                else
                {
                    j -= l;
                    this.inventoryContents[k].stackSize += l;
                    this.inventoryContents[k].animationsToGo = 5;
                    return j;
                }
            }
        }
    }
    
    /**
     * stores an itemstack in the users inventory
     */
    private int storeItemStack(ItemStack par1ItemStack)
    {
        for (int i = 0; i < this.inventoryContents.length; ++i)
        {
            if (this.inventoryContents[i] != null && this.inventoryContents[i].itemID == par1ItemStack.itemID && this.inventoryContents[i].isStackable() && this.inventoryContents[i].stackSize < this.inventoryContents[i].getMaxStackSize() && this.inventoryContents[i].stackSize < this.getInventoryStackLimit() && (!this.inventoryContents[i].getHasSubtypes() || this.inventoryContents[i].getItemDamage() == par1ItemStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(this.inventoryContents[i], par1ItemStack))
            {
                return i;
            }
        }

        return -1;
    }
}
