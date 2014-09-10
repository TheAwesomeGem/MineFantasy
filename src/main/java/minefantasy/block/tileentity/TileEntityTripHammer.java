package minefantasy.block.tileentity;

import java.util.Random;

import minefantasy.api.forge.HeatableItem;
import minefantasy.api.refine.CrushRecipes;
import minefantasy.item.ItemHotItem;
import minefantasy.item.ItemListMF;
import minefantasy.system.cfg;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.client.particle.EntityCrit2FX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;

public class TileEntityTripHammer extends TileEntity implements PacketUserMF, IInventory, ISidedInventory{
	public int direction;
	public int angle = 0;
	public int force;
	Random rand = new Random();
	private ItemStack inv[];
	public int itemMetadata;
	public TileEntityTripHammer()
	{
		inv = new ItemStack[2];
	}
	
	public TileEntityTripHammer(int metadata)
	{
		this();
		itemMetadata = metadata;
	}
	public void interact(int power) {
		if(worldObj.isRemote)return;
		
		force = 2;
		angle += power;
		if(angle >= getMaxArm())
		{
			angle = 0;
			hitHammer();
		}
	}
	
	private void spawnParticle(ItemStack item)
	{
		int x = (int)xCoord;
		int y = (int)yCoord;
		int z = (int)zCoord;
		
		ForgeDirection dir = getFacing();
		double x1 = 0 + ((double)dir.offsetX*0.4D);
		double z1 = 0 + ((double)dir.offsetZ*0.4D);
		
		worldObj.spawnParticle("crit", x-0.5D+x1, y-0.5D, z-0.5D+z1, 0, 0, 0);
		worldObj.spawnParticle("smoke", x-0.5D+x1, y-0.5D, z-0.5D+z1, 0, 0, 0);
		if(item != null)
		{
			worldObj.spawnParticle("tilecrack_" + item.getItem().itemID, xCoord + ((double)dir.offsetX*0.4D), yCoord, zCoord + ((double)dir.offsetZ*0.4D), (-0.5+rand.nextDouble())*0.1D, 0.2*rand.nextDouble(), (-0.5+rand.nextDouble())*0.1D);
		}
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, data_minefantasy.sound("AnvilSucceed"), 1, 1);
	}
	
	private ForgeDirection getFacing() {
		switch(direction)//clockwise
        {
        	case 0: //SOUTH
        	    return ForgeDirection.SOUTH;
        	case 1: //WEST
        	    return ForgeDirection.WEST;
        	case 2: //NORTH
        	    return ForgeDirection.NORTH;
        	case 3: //EAST
        	    return ForgeDirection.EAST;
        }
    	return ForgeDirection.SOUTH;
	}
	private void hitHammer() {
		ItemStack res = getResult(inv[0]);
		spawnParticle(inv[0]);
		
		boolean craft = false;
		
		if(res != null)
		{
			if(inv[1] == null)
			{
				inv[1] = res.copy();
				craft = true;
			}
			else if(inv[1].isItemEqual(res))
			{
				int total = res.stackSize + inv[1].stackSize;
				if(total <= res.getMaxStackSize())
				{
					craft = true;
					inv[1].stackSize += res.stackSize;
				}
			}
		}
		
		if(craft)
		{
			decrStackSize(0, 1);
		}
	}
	private ItemStack getResult()
	{
		return getResult(inv[0]);
	}
	private ItemStack getResult(ItemStack item) {
		if(item == null)
		{
			return null;
		}
		ItemStack input = item.copy();
		if(HeatableItem.canHeatItem(input))
		{
			return null;
		}
		if(input != null)
		{
			if(input.itemID == ItemListMF.hotItem.itemID)
			{
				input = ItemHotItem.getItem(input);
				
				int temp = ItemHotItem.getTemp(item);
				if(!HeatableItem.canWorkItem(input, temp))return null;
			}
		}
		return CrushRecipes.getResult(input);
	}
	public float getArmValue()
	{
		return (float)angle/(float)getMaxArm();
	}
	public int getMaxArm()
	{
		if(getType() == 1)
		{
			return 10;
		}
		return 45;
	}
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(getType() == 0)
		{
			updateEntity1();
		}
		else
		{
			updateEntity2();
		}
		sendPacketToClients();
	}
	private void updateEntity2() {
		if(force > 0 || automate())
		{
			force --;
			ForgeDirection fd = getFacing();
			TileEntity tile = worldObj.getBlockTileEntity(xCoord+fd.offsetX, yCoord+fd.offsetY, zCoord+fd.offsetZ);
			if(tile != null && tile instanceof TileEntityTripHammer)
			{
				TileEntityTripHammer hammer = (TileEntityTripHammer)tile;
				if(hammer.getFacing() == getFacing())
				{
					hammer.interact(4);
				}
			}
			angle ++;
		}
	}

	private boolean automate() {
		if(!cfg.redstoneHammer)return false;
		
		if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			ForgeDirection fd = getFacing();
			TileEntity tile = worldObj.getBlockTileEntity(xCoord+fd.offsetX, yCoord+fd.offsetY, zCoord+fd.offsetZ);
			if(tile != null && tile instanceof TileEntityTripHammer)
			{
				TileEntityTripHammer hammer = (TileEntityTripHammer)tile;
				if(hammer.getFacing() == getFacing())
				{
					if(hammer.getResult() != null)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public void updateEntity1()
	{
		if(force <= 0)
		{
			if(angle > 0)angle --;
			if(angle < 0)angle = 0;
		}
		else
		{
			force --;
		}
	}
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        direction = nbt.getInteger("direction");
        angle = nbt.getInteger("angle");
        force = nbt.getInteger("Force");
        
        NBTTagList nbttaglist = nbt.getTagList("Items");
        inv = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
					.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < inv.length) {
				inv[byte0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}
    }
	
	private void sendPacketToClients() {
		if(!worldObj.isRemote)
		{
			Packet packet = PacketManagerMF.getPacketIntegerArray(this, new int[]{angle, direction, force});
			try
			{
				FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
			} catch(NullPointerException e)
			{
				System.out.println("MineFantasy: Client connection lost");
			}
		}
	}


    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("direction", direction);
        nbt.setInteger("angle", angle);
        nbt.setInteger("Force", force);
        
        
        
        NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			if (inv[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				inv[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("Items", nbttaglist);
    }
	@Override
	public void recievePacket(ByteArrayDataInput data) {
		angle = data.readInt();
		direction = data.readInt();
		force = data.readInt();
	}
	public int getSizeInventory() {
		return inv.length;
	}

	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	public ItemStack decrStackSize(int i, int j) {
		if (inv[i] != null) {
			if (inv[i].stackSize <= j) {
				ItemStack itemstack = inv[i];
				inv[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inv[i].splitStack(j);
			if (inv[i].stackSize == 0) {
				inv[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}
	@Override
	public String getInvName() {
		return "Trip Hammer";
	}
	@Override
	public boolean isInvNameLocalized() {
		return true;
	}
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return entityplayer.getDistanceSq((double) xCoord + 0.5D,
				(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
	}
	
	@Override
	public void openChest() {
	}
	@Override
	public void closeChest() {
	}
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return getResult(itemstack) != null;
	}
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{0, 1};
	}
	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return slot == 0;
	}
	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return slot == 1;
	}
	
	public int getType()
	{
		return getBlockMetadata();
	}
	
	@Override
	public int getBlockMetadata()
    {
		if(worldObj == null)
			return itemMetadata;
		
		
        if (this.blockMetadata == -1)
        {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        }

        return this.blockMetadata;
    }
	
}
