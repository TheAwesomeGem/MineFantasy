package minefantasy.block.tileentity;

import java.util.Random;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.api.aesthetic.IChimney;
import minefantasy.api.cooking.OvenRecipes;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class TileEntityOven extends TileEntity implements IInventory, PacketUserMF, ISidedInventory
{
	private int ticksExisted;
	private ItemStack[] items;
	public int direction;
	public int itemMeta;
	public int fuel;
	private Random rand = new Random();
    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    public int maxFuel;

    /** The number of ticks that the current item has been cooking for */
    public int progress;
    
    //ANIMATE
  	public int numUsers;
  	public int doorAngle;
	public boolean isBurningClient;
	
	public TileEntityOven()
	{
		items = new ItemStack[3];
	}
	public TileEntityOven(int meta)
	{
		this();
		itemMeta = meta;
	}
	
	@Override
	public int getSizeInventory() 
	{
		return items.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) 
	{
		return items[i];
	}

	public void onInventoryChanged()
    {
		super.onInventoryChanged();
		
		if(worldObj != null && !worldObj.isRemote)
		{
			syncItems();
		}
    }
	@Override
    public ItemStack decrStackSize(int slot, int ammount)
	{
		onInventoryChanged();
        if (this.items[slot] != null) 
        {
            ItemStack var3;

            if (this.items[slot].stackSize <= ammount) 
            {
                var3 = this.items[slot];
                this.items[slot] = null;
                return var3;
            } else 
            {
                var3 = this.items[slot].splitStack(ammount);

                if (this.items[slot].stackSize == 0) 
                {
                    this.items[slot] = null;
                }

                return var3;
            }
        } else 
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) 
    {
        if (this.items[slot] != null) 
        {
            ItemStack var2 = this.items[slot];
            this.items[slot] = null;
            return var2;
        } else {
            return null;
        }
    }

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		items[i] = itemstack;
	}

	@Override
	public String getInvName()
	{
		int t = getType();
		String tier = "";
		
        
        if(t == 0)
        {
            tier = StatCollector.translateToLocal("tier.bronze");
        }
        if(t == 1)
        {
            tier = StatCollector.translateToLocal("tier.iron");
        }
        if(t == 2)
        {
            tier = StatCollector.translateToLocal("tier.steel");
        }
        if(t == 3)
        {
            tier = StatCollector.translateToLocal("tier.iron.deep");
        }
        
        return tier + " " + StatCollector.translateToLocal("container.oven");
	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return entityplayer.getDistance(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D) < 8F;
	}

	public void openChest()
    {
		if(numUsers == 0)
		{
			this.worldObj.playSoundEffect(xCoord+0.5D, (double)this.yCoord + 0.5D, zCoord+0.5D, data_minefantasy.sound("furnaceOpen"), 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 1.4F);
		}
		++numUsers;
    }

    public void closeChest()
    {
    	--numUsers;
    	if(numUsers == 0 && doorAngle >= 10)
		{
			this.worldObj.playSoundEffect(xCoord+0.5D, (double)this.yCoord + 0.5D, zCoord+0.5D, data_minefantasy.sound("furnaceClose"), 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 1.4F);
		}
    }
	
	@SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getCookProgressScaled(int width)
    {
        return this.progress * width / 400;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    public int getBurnTimeRemainingScaled(int width)
    {
        if (this.maxFuel == 0)
        {
            this.maxFuel = 200;
        }

        return this.fuel * width / this.maxFuel;
    }

    /**
     * Returns true if the furnace is currently burning
     */
    public boolean isBurning()
    {
        return this.fuel > 0;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
    	++ticksExisted;
        boolean flag = this.fuel > 0;
        boolean flag1 = false;

        if (this.fuel > 0)
        {
            --this.fuel;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.fuel == 0 && this.canSmelt())
            {
                this.maxFuel = this.fuel = getItemBurnTime(this.items[1]);

                if (this.fuel > 0)
                {
                    flag1 = true;

                    if (this.items[1] != null)
                    {
                        --this.items[1].stackSize;

                        if (this.items[1].stackSize == 0)
                        {
                            this.items[1] = this.items[1].getItem().getContainerItemStack(items[1]);
                        }
                    }
                }
            }

            if (this.isBurning() && this.canSmelt())
            {
                this.progress += getCookSpeed();

                if (this.progress >= 400)
                {
                    this.progress = 0;
                    this.smeltItem();
                    flag1 = true;
                }
            }
            else
            {
                this.progress = 0;
            }

            int off = getOffMetadata();
			int on = getOnMetadata();
			if(isBurning() && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != on)
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, on, 3);
				worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);
			}
			if(!isBurning() && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != off)
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, off, 3);
				worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);
			}
            
			if(numUsers > 0 && doorAngle < 15)
			{
				doorAngle ++;
			}
			
			if(numUsers <= 0 && doorAngle > 0)
			{
				doorAngle --;
			}
			if(doorAngle < 0)doorAngle = 0;
			if(doorAngle > 15)doorAngle = 15;
			
			
			if(ticksExisted % 20 == 0)
	        {
	        	syncItems();
	        }
			sendPacketToClients();
        }

        if (flag1)
        {
            this.onInventoryChanged();
        }
        
        if(worldObj.isRemote)
		{
			if(isBurning())
			{
				puffSmoke(new Random(), worldObj, xCoord, yCoord, zCoord);
				
				if(rand.nextInt(10)==0)
					worldObj.spawnParticle("flame", xCoord+(rand.nextDouble()*0.8D)+0.1D, yCoord + 0.4D, zCoord+(rand.nextDouble()*0.8D)+0.1D, 0, 0.01, 0);
			}
			return;
		}
    }

    private int getCookSpeed() 
    {
    	int tier = getType();
		return 1 + tier;
	}
	/**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        if (this.items[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = getResult();
            if (itemstack == null) return false;
            if (this.items[2] == null) return true;
            if (!this.items[2].isItemEqual(itemstack)) return false;
            int result = items[2].stackSize + itemstack.stackSize;
            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        }
    }

    public ItemStack getResult() 
    {
    	return getResult(this.items[0]);
    }
    public ItemStack getResult(ItemStack item) 
    {
		ItemStack result = OvenRecipes.getSmeltingResult(item);
		
		if(result != null)
		{
			return result;
		}
		result = FurnaceRecipes.smelting().getSmeltingResult(item);
		
		if(result != null && result.getItem() instanceof ItemFood)
		{
			return result;
		}
		return null;
	}
	/**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = getResult();

            if (this.items[2] == null)
            {
                this.items[2] = itemstack.copy();
            }
            else if (this.items[2].isItemEqual(itemstack))
            {
                items[2].stackSize += itemstack.stackSize;
            }

            --this.items[0].stackSize;

            if (this.items[0].stackSize <= 0)
            {
                this.items[0] = null;
            }
        }
    }
    

	
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList nbttaglist = nbt.getTagList("Items");
        items = new ItemStack[getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if (byte0 >= 0 && byte0 < items.length)
            {
                items[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        fuel = nbt.getInteger("Fuel");
        maxFuel = nbt.getInteger("FuelMax");
        progress = nbt.getInteger("Progress");

        direction = nbt.getInteger("Dir");
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("Dir", direction);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < items.length; i++)
        {
            if (items[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                items[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        nbt.setInteger("Fuel", fuel);
        nbt.setInteger("FuelMax", maxFuel);
        nbt.setInteger("Progress", progress);
        
        nbt.setTag("Items", nbttaglist);
    }
    
    
    public static int getItemBurnTime(ItemStack input)
    {
        if (input == null)
        {
            return 0;
        }
        else
        {
            int i = input.getItem().itemID;
            Item item = input.getItem();

            if (input.getItem() instanceof ItemBlock && Block.blocksList[i] != null)
            {
                Block block = Block.blocksList[i];

                if (block == Block.woodSingleSlab)
                {
                    return 150;
                }

                if (block.blockMaterial == Material.wood)
                {
                    return 300;
                }

                if (block == Block.coalBlock)
                {
                    return 16000;
                }
            }

            if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().equals("WOOD")) return 200;
            if (i == Item.stick.itemID) return 100;
            if (i == Item.coal.itemID) return 1600;
            if (i == Item.bucketLava.itemID) return 20000;
            if (i == Block.sapling.blockID) return 100;
            if (i == Item.blazeRod.itemID) return 2400;
            return GameRegistry.getFuelValue(input);
        }
    }
    
    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int slot, ItemStack item)
    {
    	if(item == null)return false;
    	
    	if(slot == 0)
    	{
    		return getResult(item) != null;
    	}
    	if(slot == 1)
    	{
    		return isItemFuel(item);
    	}
        return false;
    }

    public boolean isItemFuel(ItemStack item) 
    {
		return getItemBurnTime(item) > 0;
	}
	/**
     * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
     * block.
     */
    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_sides);
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side)
    {
        return this.isItemValidForSlot(slot, item);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side)
    {
    	if(slot == 1)
    	{
    		return item != null && item.itemID == Item.bucketEmpty.itemID;
    	}
        return slot == 2;
    }
    private static final int[] slots_top = new int[] {0};
    private static final int[] slots_bottom = new int[] {2, 1};
    private static final int[] slots_sides = new int[] {1};
    
    
	@Override
    public int getBlockMetadata()
    {
		if(worldObj == null)
			return itemMeta;
		
		
        if (this.blockMetadata == -1)
        {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        }

        return this.blockMetadata;
    }
    
	/**
	 *  Heater:0||Bronze:1||Iron:2||Steel:3||;
	 */
	public int getType()
	{
		int meta = getBlockMetadata();
		return (int)Math.floor(meta/2);
	}
	
	private int getOnMetadata()
	{
		return getType()*2 + 1;
	}
	
	private int getOffMetadata()
	{
		return getType()*2;
	}
	
	private void sendPacketToClients() {
		if (!worldObj.isRemote) {
			try 
			{
				Packet packet = PacketManagerMF.getPacketIntegerArray(this, new int[]
				{ 
						(int)fuel, progress, direction, isBurning() ? 1 : 0, doorAngle
				});
				
				
				FMLCommonHandler.instance().getMinecraftServerInstance()
						.getConfigurationManager()
						.sendPacketToAllPlayers(packet);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void recievePacket(ByteArrayDataInput data)
	{
		fuel = data.readInt();
		progress = data.readInt();
		direction = data.readInt();
		int burn = data.readInt();
		isBurningClient = burn == 1;
		doorAngle = data.readInt();
	}
	
	public String getTexture() 
	{
		switch(getType())
		{
		case 0://BRONZE
			return "oven_bronze";
		case 1://IRON
			return "oven_iron";
		case 2://STEEL
			return "oven_steel";
		case 3://DEEP IRON
			return "oven_deep";
		}
	    
	    return "oven";
	}
	
	public void syncItems() 
    {
		if(!worldObj.isRemote)
		{
			for(int a = 0; a < items.length; a ++)
			{
				Packet packet = PacketManagerMF.getPacketItemStackArray(this, a, items[a]);
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
	
	
	public void puffSmoke(Random rand, World world, int x, int y, int z)
	{
		if(rand.nextInt(10) != 0)
		{
			return;
		}
		
		Block block = Block.blocksList[world.getBlockId(x, y+1, z)];
		IChimney chimney = null;
		
		if(block instanceof IChimney)
			chimney = (IChimney)block;
		
		if(chimney != null
		&& chimney.puffSmoke(world, x, y+1, z, (float)1/12, 1, 1))
		{
			return;
		}
		
    	world.spawnParticle("largesmoke", x+0.5F, y+1, z+0.5F, (rand.nextFloat()-0.5F)/6, 0.065F, (rand.nextFloat()-0.5F)/6);
	}
}
