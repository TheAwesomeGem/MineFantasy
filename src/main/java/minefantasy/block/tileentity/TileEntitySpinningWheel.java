package minefantasy.block.tileentity;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.api.IMFCrafter;
import minefantasy.api.tailor.StringRecipes;
import minefantasy.block.special.BlockClickHelper;
import minefantasy.block.special.BlockSpinningWheel;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySpinningWheel extends TileEntity implements IInventory, ISidedInventory, IMFCrafter, PacketUserMF
{
	private ItemStack[] inv = new ItemStack[3];
	public int direction;
	private int progress;
	private int maxProgress;
	private StringRecipes recipe;
	private int ticksExisted;
	private int spinAngle;
	private int lastUsedTime;
	
//MAIN LOGIC--------------------------------------------------------------------------------------

	
	
	public boolean use(EntityPlayer player) 
	{
		if(player == null)return false;
		
		if(canCraft())
		{
			progress ++;
			lastUsedTime = 10;
			player.swingItem();
			return true;
		}
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		++ticksExisted;
		
		if(!worldObj.isRemote)
		{
			if(!canCraft())
			{
				progress = 0;
			}
			if(progress >= maxProgress && maxProgress > 0)
			{
				craftItem();
			}
			sendPacketToClients();
		}
		
		if(spinAngle == 20)
		{
			spinAngle = 0;
			spinAngle ++;
			worldObj.playSoundEffect(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, "random.click", 0.2F, 0.2F);
		}
		else if(spinAngle == 5 || spinAngle == 9 || spinAngle == 11)
		{
			spinAngle ++;
			worldObj.playSoundEffect(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, "random.click", 0.1F, 0.8F);
		}
		else
		{
			if(lastUsedTime > 0)
			{
				spinAngle ++;
				lastUsedTime --;
			}
		}
		
		if(ticksExisted % 10 == 0)
		{
			syncItems();
		}
	}
	
	private void craftItem() 
	{
		if(!canCraft())return;
		
		ItemStack res = getResult().copy();
		ItemStack out = getStackInSlot(2);
		
		decrStackSize(1, 1);
		
		if(out == null)
		{
			this.setInventorySlotContents(2, res);
		}
		else
		{
			out.stackSize += res.stackSize;
		}
		progress = 0;
		decrStackSize(0, 1);
	}

	@Override
    public void writeToNBT(NBTTagCompound nbt)
	{
        super.writeToNBT(nbt);
        NBTTagList itemTag = new NBTTagList();
        
        nbt.setInteger("Dir", direction);
        nbt.setInteger("Ticks", ticksExisted);
        
        for (int num = 0; num < this.inv.length; ++num) 
        {
            if (this.inv[num] != null) 
            {
                NBTTagCompound nbtInv = new NBTTagCompound();
                nbtInv.setByte("Slot", (byte) num);
                this.inv[num].writeToNBT(nbtInv);
                itemTag.appendTag(nbtInv);
            }
        }
        nbt.setTag("Items", itemTag);
        
        nbt.setInteger("Progress", progress);
    }
	
	@Override
    public void readFromNBT(NBTTagCompound nbt) 
	{
        super.readFromNBT(nbt);
        
        NBTTagList itemTag = nbt.getTagList("Items");
        this.inv = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < itemTag.tagCount(); ++var3) 
        {
            NBTTagCompound slot = (NBTTagCompound) itemTag.tagAt(var3);
            byte id = slot.getByte("Slot");

            if (id >= 0 && id < this.inv.length) 
            {
                this.inv[id] = ItemStack.loadItemStackFromNBT(slot);
            }
        }
        
        direction = nbt.getInteger("Dir");
        ticksExisted = nbt.getInteger("Ticks");
        this.progress = nbt.getInteger("Progress");
    }
	
	
	private void syncItems()
	{
		recipe = getNewRecipe();
		maxProgress = this.getMaxTime();
		
		if(!worldObj.isRemote)
		{
			for(int a = 0; a < inv.length; a ++)
			{
				Packet packet = PacketManagerMF.getPacketItemStackArray(this, a, inv[a]);
				try
				{
					FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
				} catch(NullPointerException e)
				{
					System.out.println("MineFantasy: Client connection lost");
					return;
				}
			}
		}
	}
	private boolean canCraft()
	{
		if(getRecipe() != null)
		{
			ItemStack stick = getStackInSlot(1);
			
			if(stick == null || stick.itemID != Item.stick.itemID || stick.stackSize <= 0)
			{
				return false;
			}
			
			ItemStack res = getResult();
			ItemStack out = getStackInSlot(2);
			if(res == null)
			{
				return false;
			}
			if(out == null)
			{
				return true;
			}
			
			if(!out.isItemEqual(res))
			{
				return false;
			}
			
			int max = (int)Math.min(out.getMaxStackSize(), this.getInventoryStackLimit());
			
			return out.stackSize + res.stackSize <= max;
		}
		return false;
	}
	
	private StringRecipes getRecipe()
	{
		return recipe;
	}
	private StringRecipes getNewRecipe()
	{
		return StringRecipes.getRecipe(getStackInSlot(0));
	}
	private int getMaxTime()
	{
		return getRecipe() != null ? getRecipe().timeRequired : -1;
	}
	private ItemStack getResult()
	{
		return getRecipe() != null ? getRecipe().output : null;
	}
	
	
//INV LOGIC---------------------------------------------------------------------------------------
	@Override
	public int getSizeInventory() 
	{
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return inv[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int num)
	{
		if(inv[slot] == null)
		{
			return null;
		}
		inv[slot].stackSize -= num;
		if(inv[slot].stackSize <= 0)
		{
			inv[slot] = null;
		}
		return inv[slot];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) 
	{
		inv[slot] = itemstack;
	}

	@Override
	public String getInvName() 
	{
		return "Spinning Wheel";
	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return true;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) 
	{
		return player.getDistance(xCoord, yCoord, zCoord) < 8D;
	}

	@Override
	public void openChest(){}

	@Override
	public void closeChest(){}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) 
	{
		if(slot == 0)
		{
			return StringRecipes.getRecipe(itemstack) != null;
		}
		if(slot == 1)
		{
			return itemstack.itemID == Item.stick.itemID;
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister reg){}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRenderCraftMetre() 
	{
		return canCraft();
	}


	@Override
	@SideOnly(Side.CLIENT)
	public int getProgressBar(int i) 
	{
		return this.getProgressScale(i);
	}
	
	public int getProgressScale(int width)
	{
		return this.progress * width / (maxProgress);
	}
	
	
	
	private void sendPacketToClients()
	{
		if(!worldObj.isRemote)
		{
			Packet packet = PacketManagerMF.getPacketIntegerArray(this, new int[]{0, progress, maxProgress, direction});
			try
			{
				FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
			} catch(NullPointerException e)
			{
				System.out.println("MineFantasy: Client connection lost");
			}
		}
	}

	@Override
	public void recievePacket(ByteArrayDataInput data) 
	{
		try
		{
			int id = data.readInt();
			if(id == 0)
			{
				progress = data.readInt();
				maxProgress = data.readInt();
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
					BlockSpinningWheel.useInventory(worldObj, xCoord, yCoord, zCoord, this, (EntityPlayer)e, i, slot);
				}
				return;
			}
		}catch(Exception e){}
	}
	
	
	public int getSlotFor(float x, float y) 
	{
		int[] coord = BlockClickHelper.getCoordsFor(x, y, 0F, 1F, 0F, 1F, 3, 3, direction);

		if(coord == null)
		{
			return -1;
		}
		
		if(coord[0] == 1)
		{
			if(coord[1] == 2)
			{
				return 0;
			}
		}
		if(coord[1] == 0)
		{
			if(coord[0] == 0)
			{
				return 1;
			}
			if(coord[0] == 2)
			{
				return 2;
			}
		}
		return -1;
	}

	public ItemStack getResultSlot() 
	{
		return getStackInSlot(2);
	}

	public float getArmValue() 
	{
		return (float)this.spinAngle / 20F;
	}
	
	public void onInventoryChanged()
    {
		super.onInventoryChanged();
		if(!worldObj.isRemote)
		{
			syncItems();
		}
    }

	@Override
	public int[] getAccessibleSlotsFromSide(int side) 
	{
		return new int[]{0, 1, 2};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) 
	{
		return this.isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) 
	{
		return slot == 2;
	}
	
	@Override
	public String getResultName()
	{
		if(this.getResult() != null)
		{
			return getResult().getDisplayName();
		}
		return "";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setTempResult(ItemStack item) 
	{
	}
}
