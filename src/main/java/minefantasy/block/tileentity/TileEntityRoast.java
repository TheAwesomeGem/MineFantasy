package minefantasy.block.tileentity;

import java.io.IOException;
import java.util.Random;

import minefantasy.api.cooking.IHeatSource;
import minefantasy.api.forge.HeatableItem;
import minefantasy.block.special.BlockForge;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityRoast extends TileEntity
    implements IInventory, PacketUserMF
{
    private ItemStack inv[];
    public int direction;
    public int[] enchantment = new int[5];
    public int[] cooking = new int[5];
    private static int maxCookTime = 60000;

    public TileEntityRoast()
    {
        inv = new ItemStack[5];
    }

    public int getSizeInventory()
    {
        return inv.length;
    }

    public ItemStack getStackInSlot(int i)
    {
    	ItemStack item = inv[i];
    	if(item == null || item.getItem() == null)
    	{
    		return null;
    	}
        return item;
    }

    public ItemStack decrStackSize(int i, int j)
    {
        if (inv[i] != null)
        {
            if (inv[i].stackSize <= j)
            {
                ItemStack itemstack = inv[i];
                inv[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = inv[i].splitStack(j);
            if (inv[i].stackSize == 0)
            {
                inv[i] = null;
            }
            return itemstack1;
        }
        else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        inv[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    public String getInvName()
    {
        return "Roast";
    }
    
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList nbttaglist = nbt.getTagList("Items");
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

        direction = nbt.getInteger("Dir");
        
        int[] cook = nbt.getIntArray("Cooking");
        if(cook != null && cook.length == cooking.length)
        {
        	cooking = nbt.getIntArray("Cooking");
        }
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("Dir", direction);
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
        nbt.setTag("Items", nbttaglist);
        nbt.setIntArray("Cooking", cooking);
    }

    public int getInventoryStackLimit()
    {
        return 1;
    }

    public void updateEntity()
    {
    	if(!worldObj.isRemote)
    	{
    		for(int a = 0; a < 5; a ++)
    		{
    			if(this.getResultFor(a) != null)
    			{
    				cooking[a] += getCookSpeed();
    				if(cooking[a] > this.maxCookTime)
    				{
    					cook(a);
    				}
    			}
    			else
    			{
    				cooking[a] = 0;
    			}
    		}
    		sendPacketToClients();
    	}
    }


    private void cook(int a) {
    	if(inv[a] != null)
    	{
    		ItemStack result = getResultFor(a);
    		if(result != null)
    		{
    			this.setInventorySlotContents(a, result.copy());
    			cooking[a] = 0;
    		}
    	}
	}

    private int getCookSpeed()
    {
    	if(getHeat() <= 0)return 0;
    	
    	Random rand = new Random();
    	if(rand.nextInt(2) == 0)return 0;
    	
    	return rand.nextInt(getHeat()+1);
    }
	private int getHeat() {
    	if(worldObj == null)
		{
			return 0;
		}
    	if(worldObj.getBlockMaterial(xCoord, yCoord-1, zCoord) != null && worldObj.getBlockMaterial(xCoord, yCoord-1, zCoord) == Material.fire)
    	{
    		return 200;
    	}
    	TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
		
		if(tile == null)
		{
			return 0;
		}
		if(tile instanceof IHeatSource)
		{
			return ((IHeatSource)tile).getHeat();
		}
		if(tile instanceof TileEntityForge)
		{
			return (int) ((TileEntityForge)tile).heat*2;
		}
		return 0;
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
    	int e0 = getEnchantment(0);
    	int e1 = getEnchantment(1);
    	int e2 = getEnchantment(2);
    	int e3 = getEnchantment(3);
    	int e4 = getEnchantment(4);
    	
    	int c0 = getCook(0);
    	int c1 = getCook(1);
    	int c2 = getCook(2);
    	int c3 = getCook(3);
    	int c4 = getCook(4);
    	
		if(!worldObj.isRemote || FMLCommonHandler.instance().getSide().isServer())
		{
			try
			{	
				Packet packet = PacketManagerMF.getPacketIntegerArray(this, new int[]{direction, getItemID(0), getItemID(1), getItemID(2), getItemID(3), getItemID(4),
																								 getItemMeta(0), getItemMeta(1), getItemMeta(2), getItemMeta(3), getItemMeta(4), e0, e1, e2, e3, e4, c0, c1, c2, c3, c4});
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
	
	private int getCook(int i) {
		return cooking[i];
	}

	public void sendPacketToServer() {
		
		if(worldObj.isRemote)
		{
			try
			{
				Packet packet = PacketManagerMF.getPacketIntegerArray(this, new int[]{direction, getItemID(0), getItemID(1), getItemID(2), getItemID(3), getItemID(4)});
				PacketDispatcher.sendPacketToServer(packet);
			} catch(NullPointerException e)
			{
				e.printStackTrace();
			}
		}
	}

    private int getItemID(int i) {
		ItemStack item = inv[i];
		if(item == null)return 0;
		return item.itemID;
	}
    
    private int getItemMeta(int i) {
		ItemStack item = inv[i];
		if(item == null)return 0;
		return item.getItemDamage();
	}

	@Override
	public void recievePacket(ByteArrayDataInput data) {
		try
		{
    		direction = data.readInt();
    		int i1 = data.readInt();
    		int i2 = data.readInt();
    		int i3 = data.readInt();
    		int i4 = data.readInt();
    		int i5 = data.readInt();
    		
    		int m1 = data.readInt();
    		int m2 = data.readInt();
    		int m3 = data.readInt();
    		int m4 = data.readInt();
    		int m5 = data.readInt();
    		if(worldObj.isRemote)
    		{
    			inv[0] = new ItemStack(i1, 1, m1);
    			inv[1] = new ItemStack(i2, 1, m2);
    			inv[2] = new ItemStack(i3, 1, m3);
    			inv[3] = new ItemStack(i4, 1, m4);
    			inv[4] = new ItemStack(i5, 1, m5);
    			
    			for(int a = 0; a < 5; a ++)
    			enchantment[a] = data.readInt();
    			
    			for(int a = 0; a < 5; a ++)
        			cooking[a] = data.readInt();
    		}
		}catch(Exception e)
		{
			System.err.println("MineFantasy: Spit Roast packet failed");
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

	public boolean isEnchanted(int slot) {
		return enchantment[slot] == 1;
	}

	public boolean willShowBase() {
		if(worldObj == null)
		{
			return false;
		}
		
		if(worldObj.getBlockMaterial(xCoord, yCoord-1, zCoord) != null && worldObj.getBlockMaterial(xCoord, yCoord-1, zCoord) == Material.fire)
    	{
    		return true;
    	}
		
		TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
		
		if(tile == null)
		{
			return false;
		}
		if(tile instanceof IHeatSource)
		{
			return ((IHeatSource)tile).canPlaceAbove();
		}
		if(tile instanceof TileEntityForge)
		{
			return true;
		}
		return false;
	}

	public boolean isValid(ItemStack item) {
		return getResultFor(item) != null;
	}
	
	public ItemStack getResultFor(int slot)
	{
		return getResultFor(inv[slot]);
	}
	public ItemStack getResultFor(ItemStack item)
	{
		ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(item);
		if(result != null && result.getItem() != null)
		{
			if(result.getItem() instanceof ItemFood)
			{
				return result;
			}
		}
		return null;
	}
	
	public int getCookProgressScaled(int height, int slot) {
        return cooking[slot] * height / maxCookTime;
    }
	
	public boolean tryAddItem(ItemStack item)
	{
		if(item == null)
		{
			return false;
		}
		for(int a = 0 ; a < getSizeInventory() ; a ++)
		{
			ItemStack slot = inv[a];
			if(slot == null && getResultFor(item) != null)
			{
				ItemStack copy = item.copy();
				copy.stackSize = 1;
				setInventorySlotContents(a, copy);
				return true;
			}
		}
		return false;
	}
	
	
	public boolean tryTakeItem(EntityPlayer player)
	{
		if(player == null)
		{
			return false;
		}
		for(int a = 0 ; a < getSizeInventory() ; a ++)
		{
			ItemStack slot = inv[a];
			if(slot != null && getResultFor(slot) == null)
			{
				worldObj.spawnEntityInWorld(new EntityItem(worldObj, player.posX, player.posY, player.posZ, inv[a]));
				decrStackSize(a, 1);
				return true;
			}
		}
		return false;
	}
	
	private ForgeDirection getLeftSide()
	{
		switch(direction)
		{
		case 0:
			return ForgeDirection.EAST;
		case 1:
			return ForgeDirection.SOUTH;
		case 2:
			return ForgeDirection.WEST;
		case 3:
			return ForgeDirection.NORTH;
		}
		return ForgeDirection.UNKNOWN;
	}
	
	private ForgeDirection getRightSide()
	{
		switch(direction)
		{
		case 0:
			return ForgeDirection.WEST;
		case 1:
			return ForgeDirection.NORTH;
		case 2:
			return ForgeDirection.EAST;
		case 3:
			return ForgeDirection.SOUTH;
		}
		return ForgeDirection.UNKNOWN;
	}
	
	public boolean renderLeft()
	{
		if(worldObj == null)
		{
			return true;
		}
		ForgeDirection left = getLeftSide();
		
		TileEntity en = worldObj.getBlockTileEntity(xCoord + left.offsetX, yCoord + left.offsetY, zCoord + left.offsetZ);
		
		if(en != null && en instanceof TileEntityRoast)
		{
			if(((TileEntityRoast)en).direction == direction)
			{
				return false;
			}
		}
		
		return true;
	}
	public boolean renderRight()
	{
		if(worldObj == null)
		{
			return true;
		}
		ForgeDirection right = getRightSide();
		
		TileEntity en = worldObj.getBlockTileEntity(xCoord + right.offsetX, yCoord + right.offsetY, zCoord + right.offsetZ);
		
		if(en != null && en instanceof TileEntityRoast)
		{
			if(((TileEntityRoast)en).direction == direction)
			{
				return false;
			}
		}
		
		return true;
	}
}
