package minefantasy.container;



import minefantasy.block.tileentity.TileEntityAnvil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAnvil extends Container
{
    private TileEntityAnvil anvil;
    private boolean hasPlayer;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemBurnTime = 0;
    private int xSize;
    private int ySize;

    
    public ContainerAnvil(TileEntityAnvil tile)
    {
        this.anvil = tile;
        int yMult;
        if(tile.isBig())
    	{
    		this.ySize = 204;
    	}
        
        int startX = 8;
        int startY = 8;
        
        xSize = tile.gridSize()[0];
        ySize = tile.gridSize()[1];

        for (yMult = 0; yMult < ySize; ++yMult)
        {
            for (int xMult = 0; xMult < xSize; ++xMult)
            {
                this.addSlotToContainer(new Slot(tile,
                		xMult + yMult * xSize,
                		startX + xMult * 18,
                		startY + yMult * 18));
            }
        }
        this.addSlotToContainer(new SlotAnvil(tile.getSmith(), tile, tile.getGridSize(), 144, 35));
    }
    
    
    public ContainerAnvil(InventoryPlayer invPlayer, TileEntityAnvil tile)
    {
    	hasPlayer = invPlayer != null;
        this.anvil = tile;
        int yMult;

        int startX = tile.getPositions()[0][0];
        int startY = tile.getPositions()[0][1];
        
        xSize = tile.gridSize()[0];
        ySize = tile.gridSize()[1];

        for (yMult = 0; yMult < ySize; ++yMult)
        {
            for (int xMult = 0; xMult < xSize; ++xMult)
            {
                this.addSlotToContainer(new Slot(tile,
                		xMult + yMult * xSize,
                		startX + xMult * 18,
                		startY + yMult * 18));
            }
        }
        if(invPlayer != null)
        {
	        this.addSlotToContainer(new SlotAnvil(invPlayer.player, tile, tile.getGridSize(), tile.getPositions()[1][0], tile.getPositions()[1][1]));
	        int var3;
	
	        for (var3 = 0; var3 < 3; ++var3)
	        {
	            for (int var4 = 0; var4 < 9; ++var4)
	            {
	                this.addSlotToContainer(new Slot(invPlayer, var4 + var3 * 9 + 9, tile.getPositions()[2][0] + var4 * 18, tile.getPositions()[2][1] + var3 * 18));
	            }
	        }
	
	        for (var3 = 0; var3 < 9; ++var3)
	        {
	            this.addSlotToContainer(new Slot(invPlayer, var3, tile.getPositions()[2][0] + var3 * 18, tile.getPositions()[2][1] + 58));
	        }
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

            if (this.lastCookTime != this.anvil.forgeTime)
            {
                var2.sendProgressBarUpdate(this, 0, this.anvil.forgeTime);
            }
        }

        this.lastCookTime = this.anvil.forgeTime;
    }

    public void updateProgressBar(int slot, int set)
    {
        if (slot == 0)
        {
            this.anvil.forgeTime = set;
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.anvil.isUseableByPlayer(par1EntityPlayer);
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int num)
    {
    	int invSize = xSize * ySize + 1;
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
