package minefantasy.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPackHound extends Container
{
    private IInventory lowerChestInventory;
    private int numRows;

    public ContainerPackHound(IInventory viewer, IInventory container, int rows)
    {
        this.lowerChestInventory = container;
        this.numRows = rows;
        container.openChest();
        int var3 = (this.numRows - 4) * 18;
        int var4;
        int var5;

        //Hound
        for (var4 = 0; var4 < this.numRows; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
                this.addSlotToContainer(new Slot(container, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18));
            }
        }

        //Inventory
        for (var4 = 0; var4 < 3; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
                this.addSlotToContainer(new Slot(viewer, var5 + var4 * 9 + 9, 8 + var5 * 18, 103 + var4 * 18 + var3));
            }
        }

        //Quickbar
        for (var4 = 0; var4 < 9; ++var4)
        {
            this.addSlotToContainer(new Slot(viewer, var4, 8 + var4 * 18, 161 + var3));
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer player, int num)
    {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(num);

        int max = this.numRows * 9;
        if(max > 72)max = 72;
        
        if (var4 != null && var4.getHasStack())
        {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();

            if (num < max)
            {
                if (!this.mergeItemStack(var5, max, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 0, max, false))
            {
                return null;
            }

            if (var5.stackSize == 0)
            {
                var4.putStack((ItemStack)null);
            }
            else
            {
                var4.onSlotChanged();
            }
        }

        return var3;
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    @Override
    public void onContainerClosed(EntityPlayer user)
    {
        super.onContainerClosed(user);
        this.lowerChestInventory.closeChest();
    }

    /**
     * Return this chest container's lower chest inventory.
     */
    public IInventory getLowerChestInventory()
    {
        return this.lowerChestInventory;
    }
}
