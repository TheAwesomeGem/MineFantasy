package minefantasy.block.tileentity;

import java.util.Random;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.api.IMFCrafter;
import minefantasy.api.tailor.CraftingManagerTailor;
import minefantasy.api.tailor.INeedle;
import minefantasy.api.tailor.ITailor;
import minefantasy.api.tailor.StringList;
import minefantasy.block.special.BlockClickHelper;
import minefantasy.block.special.BlockTailor;
import minefantasy.container.ContainerTailor;
import minefantasy.item.ItemHotItem;
import minefantasy.system.StatListMF;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

/**
*
* @author Anonymous Productions
* 
* Sources are provided for educational reasons.
* though small bits of code, or methods can be used in your own creations.
* 
* BlockTailor
*/
public class TileEntityTailor extends TileEntity implements IInventory, PacketUserMF, ITailor, IMFCrafter
{
	@SideOnly(Side.CLIENT)
	private ItemStack tempResult;
	
	public int direction;
	private ItemStack[] inv;
	public int progress;
	private int maxProgress = 200;
	private int ticks;
	private int recipeTier;
	public int reqString;
	private float sewTime;
	private float sewProg;
	private ItemStack recipe;
	
	public TileEntityTailor()
	{
		inv = new ItemStack[21];
	}
	
	
//MAIN LOGIC--------------------------------------------------------------------------------------
	
	/**
	 * Called when the player right clicks the block
	 * @return true if it does anything
	 */
	public boolean onUse(EntityPlayer player)
	{
		if(player == null)
		{
			return false;
		}
		ItemStack item = player.getHeldItem();
		
		if(item != null && item.getItem() != null && item.getItem() instanceof INeedle && canCraft() && hasString(false))
		{
			if(recipeTier == 0 || ((INeedle)item.getItem()).getTier() >= recipeTier)
			{
				worldObj.playSound((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D, "step.cloth", 1.0F, (worldObj.rand.nextFloat() * 0.1F) + 0.9F, true);
				sewProg += ((INeedle)item.getItem()).getEfficiency();
				if(!player.capabilities.isCreativeMode)
				{
					item.damageItem(1, player);
				}
			}
			else
			{
				worldObj.playSound((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D, "step.cloth", 1.0F, (worldObj.rand.nextFloat() * 0.1F) + 0.4F, true);
			}
			onInventoryChanged();
			return true;
		}
		return false;
	}
	
	/**
	 * checks if there is string to use
	 * @param consume true if it consumes string false if it is checking
	 * @return true if there's string to use
	 */
	private boolean hasString(boolean consume)
	{
		if(this.reqString <= -1)
		{
			return true;
		}
		else
		{
			for(int a = 0; a < 4; a ++)
			{
				ItemStack slot = getStackInSlot(a);
				
				if(slot != null)
				{
					if(StringList.doesMatchTier(slot, reqString))
					{
						if(consume)
						decrStackSize(a, 1);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Override
    public void updateEntity() 
	{
        super.updateEntity();
        ticks ++;
        if (progress >= (maxProgress))
        {
            progress = 0;
            sewProg = 0.0F;
            craft();
        }
        if(sewProg >= sewTime && sewTime > 0)
    	{
    		sewProg -= sewTime;
    		if(hasString(true))
    		{
    			progress ++;
    		}
    	}
        
        if (!canCraft() && progress > 0) 
        {
            progress = 0;
        }
        
        if(!worldObj.isRemote)
        {
        	if(ticks % 20 == 0)
        	{
        		this.syncItems();
        	}
        	sendPacketToClients();
        }
    }
	
	
	public ItemStack getResult()
	{
		return recipe;
	}
	public ItemStack getRecipe()
    {
    	if(ticks <= 1)
    		return null;
    	
    	ContainerTailor container = new ContainerTailor(this);
    	
    	InventoryCrafting craft = new InventoryCrafting(container, 4, 4);
    	
    	for(int a = 0 ; a < 16 ; a ++)
    	{
    		craft.setInventorySlotContents(a, inv[a+4]);
    	}
    	
    	return CraftingManagerTailor.getInstance().findMatchingRecipe(this, craft);
    }
	public boolean canCraft() 
	{
        return getResult() != null && canFitResult();
    }
	
	public boolean canFitResult()
	{
		ItemStack slot = inv[getOutputSlot()];
		ItemStack result = getResult();
		
		if(slot == null)return true;
		if(result == null)return false;
		
		if(!result.isItemEqual(slot))return false;
		
		int ss = slot.stackSize;
		int rs = result.stackSize;
		int sm = slot.getMaxStackSize() - ss;
		
		return rs <= sm;
	}
	
	
	private void craft() 
	{
    	if(getResult() == null)
    		return;
    	
        ItemStack result = getResult().copy();
        
	     if(result != null)
	     {
	        if (inv[getOutputSlot()] == null) 
	        {
	            decreseAll();
	            this.setInventorySlotContents(getOutputSlot(), result);
	        } 
	        else if (inv[getOutputSlot()] != null && inv[getOutputSlot()].isItemEqual(result)) 
	        {
	        	int c = (inv[getOutputSlot()].stackSize + result.stackSize);
	        	if(c <= inv[getOutputSlot()].getMaxStackSize())
	        	{
	        		decreseAll();
	            	inv[getOutputSlot()].stackSize+= result.stackSize;
	        	}
	        }
    	}
	    this.onInventoryChanged();
    }
	
	private void decreseAll() 
	{
    	Random rand = new Random();
    	
        for (int s = 4; s < getOutputSlot(); s++) 
        {
            this.decrStackSize(s, 1);
        }
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
	
	private void syncItems() 
    {
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
			
			Packet packet2 = PacketManagerMF.getPacketMFResult(this, getResult());
			try
			{
				FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet2);
			} catch(NullPointerException e)
			{
				System.out.println("MineFantasy: Client connection lost");
				return;
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
					BlockTailor.useInventory(worldObj, xCoord, yCoord, zCoord, this, (EntityPlayer)e, i, slot);
				}
				return;
			}
		} catch (Exception e)
		{
			System.err.println("MineFantasy: Failed to process packet from Tailor Bench");
		}
	}
	
	@Override
	public void onInventoryChanged()
	{
		super.onInventoryChanged();
		
		recipe = getRecipe();
		syncItems();
	}
	@Override
    public void writeToNBT(NBTTagCompound nbt)
	{
        super.writeToNBT(nbt);
        NBTTagList itemTag = new NBTTagList();
        
        nbt.setInteger("Dir", direction);
        nbt.setFloat("Sew", sewProg);
        
        for (int var3 = 0; var3 < this.inv.length; ++var3) 
        {
            if (this.inv[var3] != null) 
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.inv[var3].writeToNBT(var4);
                itemTag.appendTag(var4);
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
        sewProg = nbt.getFloat("Sew");
        
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
        this.progress = nbt.getInteger("Progress");
    }
	
//OTHER MECHANICS--------------------------------------------------------------------------------
	@Override
	public String getInvName() 
	{
		return "Tailor Bench";
	}

	@Override
	public boolean isInvNameLocalized()
	{
		return true;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return i < 4 && StringList.isString(itemstack);
	}

	public int getProgressScale(int width)
	{
		return this.progress * width / (maxProgress);
	}
		
	@Override
	public int getSizeInventory() 
	{
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) 
	{
		return inv[i];
	}

	@Override
    public ItemStack decrStackSize(int slot, int amount)
	{
		onInventoryChanged();
        if (this.inv[slot] != null) 
        {
            ItemStack result;

            if (this.inv[slot].stackSize <= amount) 
            {
                result = this.inv[slot];
                this.inv[slot] = null;
                onInventoryChanged();
                return result;
            } 
            else 
            {
                result = this.inv[slot].splitStack(amount);

                if (this.inv[slot].stackSize == 0)
                {
                    this.inv[slot] = null;
                }

                onInventoryChanged();
                return result;
            }
        } 
        else 
        {
        	onInventoryChanged();
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.inv[slot] != null) 
        {
            ItemStack var2 = this.inv[slot];
            this.inv[slot] = null;
            return var2;
        } 
        else 
        {
            return null;
        }
    }

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		onInventoryChanged();
		inv[i] = itemstack;
	}


	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openChest(){}
	@Override
	public void closeChest(){}
	public int getOutputSlot()
	{
		return getSizeInventory()-1;
	}
	@Override
	public void setStitchCount(int i) 
	{
		maxProgress = i;
	}

	@Override
	public void setString(int str) 
	{
		this.reqString = str;
	}

	@Override
	public void setSewTime(float f) 
	{
		sewTime = f;
	}


	@Override
	public void setTier(int i) 
	{
		recipeTier = i;
	}

	@SideOnly(Side.CLIENT)
	public ItemStack getRenderItem(int i)
	{
		return inv[i];
	}
	
	
	public int getSlotFor(float x, float y) 
	{
		int[] coord = BlockClickHelper.getCoordsFor(x, y, 0F, 1F, 0F, 1F, 6, 6, direction);

		if(coord == null)
		{
			return -1;
		}
		
		if(coord[0] == 1 || coord[1] < 2)
		{
			return -1;
		}
		coord[1] -= 2;
		if(coord[0] == 0)
		{
			return coord[1];
		}
		coord[0] -= 2;
		
		return (coord[1]*4 + coord[0] + 4);
	}


	public ItemStack getResultSlot() 
	{
		return getStackInSlot(getSizeInventory()-1);
	}


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
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getResultName()
	{
		if(worldObj.isRemote)
		{
			return tempResult != null ? tempResult.getDisplayName() : "";
		}
		return "";
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void setTempResult(ItemStack item) 
	{
		tempResult = item;
	}
}
