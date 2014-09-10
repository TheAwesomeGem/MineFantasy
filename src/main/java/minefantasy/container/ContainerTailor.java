package minefantasy.container;



import minefantasy.block.tileentity.TileEntityTailor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTailor extends Container
{
    private TileEntityTailor bench;
    private int lastProgress = 0;
    private int xSize;
    private int ySize;

    
    public ContainerTailor(TileEntityTailor tile)
    {
    	this.bench = tile;
        int yMult;
        
        int startX = 34;
        int startY = 22;
        
        xSize = 4;
        ySize = 4;

        for (yMult = 0; yMult < ySize; ++yMult)
        {
        	this.addSlotToContainer(new Slot(tile, yMult, 8, startY + yMult * 18));
        }
        for (yMult = 0; yMult < ySize; ++yMult)
        {
            for (int xMult = 0; xMult < xSize; ++xMult)
            {
                this.addSlotToContainer(new Slot(tile,
                		xMult + yMult * xSize + 4,
                		startX + xMult * 18,
                		startY + yMult * 18));
            }
        }
        this.addSlotToContainer(new Slot(tile, tile.getSizeInventory()-1, 148, 49));
    }
    
    
    public ContainerTailor(InventoryPlayer invPlayer, TileEntityTailor tile)
    {
    	this.bench = tile;
        int yMult;
        
        int startX = 34;
        int startY = 22;
        
        xSize = 4;
        ySize = 4;

        for (yMult = 0; yMult < ySize; ++yMult)
        {
        	this.addSlotToContainer(new Slot(tile, yMult, 8, startY + yMult * 18));
        }
        for (yMult = 0; yMult < ySize; ++yMult)
        {
            for (int xMult = 0; xMult < xSize; ++xMult)
            {
                this.addSlotToContainer(new Slot(tile,
                		xMult + yMult * xSize + 4,
                		startX + xMult * 18,
                		startY + yMult * 18));
            }
        }
        this.addSlotToContainer(new Slot(tile, tile.getSizeInventory()-1, 148, 49));
        
        
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(invPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 98 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(invPlayer, var3, 8 + var3 * 18, 98 + 58));
        }
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void updateCraftingResults()
    {
        super.detectAndSendChanges();

        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);

            if (this.lastProgress != this.bench.progress)
            {
                var2.sendProgressBarUpdate(this, 0, this.bench.progress);
            }
        }

        this.lastProgress = this.bench.progress;
    }

    public void updateProgressBar(int slot, int set)
    {
        if (slot == 0)
        {
            this.bench.progress = set;
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.bench.isUseableByPlayer(par1EntityPlayer);
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int num)
    {
    	int invSize = xSize * ySize + 5;
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
                if (!this.mergeItemStack(itemSlot, 0, invSize-1, false))
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
