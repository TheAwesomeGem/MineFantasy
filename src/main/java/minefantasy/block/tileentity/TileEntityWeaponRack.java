package minefantasy.block.tileentity;

import java.io.IOException;

import minefantasy.block.special.BlockClickHelper;
import minefantasy.block.special.BlockWeaponRack;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityWeaponRack extends TileEntity
    implements IInventory, PacketUserMF
{
    private ItemStack inv[];
    public int direction;
	private int ticksExisted;

    public TileEntityWeaponRack()
    {
        inv = new ItemStack[4];
    }

    public int getSizeInventory()
    {
        return inv.length;
    }

    public ItemStack getStackInSlot(int i)
    {
        return inv[i];
    }

    public ItemStack decrStackSize(int i, int j)
    {
        if (inv[i] != null)
        {
            if (inv[i].stackSize <= j)
            {
                ItemStack itemstack = inv[i];
                inv[i] = null;
                syncItems();
                return itemstack;
            }
            ItemStack itemstack1 = inv[i].splitStack(j);
            if (inv[i].stackSize == 0)
            {
                inv[i] = null;
            }
            syncItems();
            return itemstack1;
        }
        else
        {
        	syncItems();
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        inv[i] = itemstack;
        syncItems();
    }

    public String getInvName()
    {
        return "Rack";
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        
        inv = new ItemStack[getSizeInventory()];
        
        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if (byte0 >= 0 && byte0 < inv.length)
            {
                inv[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        direction = nbttagcompound.getInteger("Dir");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("Dir", direction);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < inv.length; i++)
        {
            if (inv[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                inv[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    public void updateEntity()
    {
    	++ticksExisted;
    	if(ticksExisted % 20 == 0)
    	{
    		syncItems();
    	}
    	sendPacketToClients();
    }


    public void syncItems() 
    {
    	if(!worldObj.isRemote)
    	{
    		try
    		{
    			for(int a = 0; a < this.getSizeInventory(); a ++)
    			{
    				Packet packet = PacketManagerMF.getPacketItemStackArray(this, a, getStackInSlot(a));
    				PacketDispatcher.sendPacketToAllPlayers(packet);
    			}
    						
    		}catch(Exception e){}
    	}
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }
        return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }

    public void openChest()
    {
    }

    public void closeChest()
    {
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }
    private void sendPacketToClients() {
		if(!worldObj.isRemote)
		{
			try
			{	
				Packet packet = PacketManagerMF.getPacketIntegerArray(this, new int[]{0, direction});
				FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
			} 
			catch(NullPointerException e)
			{
				System.out.println("MineFantasy: Client connections lost");
			}
		}
	}

	private int getEnchantment(int i) {
		ItemStack is = this.getStackInSlot(i);
		if(is == null)return 0;
		
		if(is.isItemEnchanted())return 1;
		
		return 0;
	}

	@Override
	public void recievePacket(ByteArrayDataInput data) {
		try
		{
			int id = data.readInt();
			
			if(id == 0)
			{
				direction = data.readInt();
			}
			if(id == 1)
			{
				int p = data.readInt();
				int i = data.readInt();
				int slot = data.readInt();
				
				Entity e = worldObj.getEntityByID(p);
				
				if(e != null && e instanceof EntityPlayer)
				{
					BlockWeaponRack.tryPlaceItem(slot, worldObj, this, (EntityPlayer)e);
				}
				return;
			}
		}catch(Exception e)
		{
			System.err.println("MineFantasy: Weapon rack packet failed");
		}
    		
    }

	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	public int getSlotFor(float x, float y) 
	{
		ForgeDirection FD = BlockClickHelper.FD[direction];
		
		float offset = 1F/16F;
		
		float x1 = 0.0F + offset;
		float x2 = 1.0F - offset;
		float y1 = 0.0F;
		float y2 = 1.0F;
		if(FD == ForgeDirection.EAST || FD == ForgeDirection.WEST)
		{
			x1 = 0.0F;
			x2 = 1.0F;
			y1 = 0.0F + offset;
			y2 = 1.0F - offset;
		}
		int[] coord = BlockClickHelper.getCoordsFor(x, y, x1, x2, y1, y2, 4, 4, direction);

		if(coord == null)
		{
			return -1;
		}
		return coord[0];
		
	}

	public static boolean canHang(ItemStack item) 
	{
		if(item == null || item.getItem() == null)
		{
			return false;
		}
		if(item.getItem() instanceof ItemBlock)
		{
			return false;
		}
		return true;
	}

}
