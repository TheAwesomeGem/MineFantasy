package minefantasy.block.tileentity;

import java.util.Random;

import minefantasy.api.IMFCrafter;
import minefantasy.api.aesthetic.IChimney;
import minefantasy.api.cooking.IHeatSource;
import minefantasy.api.forge.HeatableItem;
import minefantasy.api.forge.IBellowsUseable;
import minefantasy.api.forge.ItemHandler;
import minefantasy.block.BlockListMF;
import minefantasy.block.special.BlockClickHelper;
import minefantasy.block.special.BlockForge;
import minefantasy.item.ItemHotItem;
import minefantasy.item.ItemListMF;
import minefantasy.system.cfg;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityForge extends TileEntity implements IMFCrafter, IBellowsUseable, IInventory, PacketUserMF, ISidedInventory, IHeatSource
{
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private ItemStack[] items = new ItemStack[10];

    /** The number of ticks that the furnace will keep burning */
    public int fuel = 0;
    private Random rand = new Random();
    /** the heat of the forge */
    public float heat;
    /**
     * -1 = not constructed, 1 = constructed, 0 = undecided
     */
    public int constructed;
    private int itemDam;
    public float bonus;
    public int extinguishBonus;
    public boolean isLit;
    public float itemHeat;
    private int adjacentLava;
    private int ticksExisted;
    public int justShared;
    public int forgeMaxTemp = -1;
	public int direction;

	public TileEntityForge()
	{
		super();
	}
	public TileEntityForge(int d)
	{
		this();
		itemDam = d;
	}
    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.items.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int slot)
    {
        return this.items[slot];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int slot, int num)
    {
        if (this.items[slot] != null)
        {
            ItemStack var3;

            if (this.items[slot].stackSize <= num)
            {
                var3 = this.items[slot];
                this.items[slot] = null;
                return var3;
            }
            else
            {
                var3 = this.items[slot].splitStack(num);

                if (this.items[slot].stackSize == 0)
                {
                    this.items[slot] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    public float getTotalHeat()
    {
    	float r = heat + bonus;
    	if(r > forgeMaxTemp)
    		r = forgeMaxTemp;
    	
    	return r;
    }
    
    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.items[slot] != null)
        {
            ItemStack var2 = this.items[slot];
            this.items[slot] = null;
            return var2;
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
        this.items[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "Forge";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList var2 = nbt.getTagList("Items");
        this.items = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.items.length)
            {
                this.items[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        constructed = nbt.getInteger("constructed");
        ticksExisted = nbt.getInteger("ticksExisted");
        direction = nbt.getInteger("Dir");
        this.fuel = nbt.getInteger("BurnTime");
        heat = nbt.getFloat("temperature");
        itemHeat = nbt.getFloat("itemTemperature");
        bonus = nbt.getFloat("bellows");
        justShared = nbt.getInteger("Shared");
        isLit = nbt.getBoolean("fired");
        extinguishBonus = nbt.getInteger("extinguishBonus");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        
        nbt.setInteger("constructed", constructed);
        nbt.setInteger("extinguishBonus", extinguishBonus);
        nbt.setInteger("ticksExisted", ticksExisted);
        nbt.setInteger("Dir", direction);
        nbt.setFloat("temperature", heat);
        nbt.setFloat("itemTemperature", itemHeat);
        nbt.setFloat("bellows", bonus);
        nbt.setInteger("BurnTime", this.fuel);
        nbt.setInteger("Shared", justShared);
        NBTTagList var2 = new NBTTagList();
        nbt.setBoolean("fired", isLit);

        for (int var3 = 0; var3 < this.items.length; ++var3)
        {
            if (this.items[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.items[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        nbt.setTag("Items", var2);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    public int getBurnTimeRemainingScaled(int height)
    {
        return this.fuel * height / this.getMaxFuel();
    }
    
    public int getHeatScaled(int height)
    {
    	if(getTotalHeat() <= 0)return 0;
        return (int)this.getTotalHeat() * height / forgeMaxTemp;
    }
    public int getItemHeatScaled(int height)
    {
    	if(itemHeat <= 0)return 0;
        return (int)this.itemHeat * height / forgeMaxTemp;
    }
    
    /**
     * Returns true if the furnace is currently burning
     */
    public boolean isBurning()
    {
        return this.heat > 0 && isLit;
    }

    public void updateEntity()
    {
    	if(forgeMaxTemp < 0)
    	{
    		forgeMaxTemp = getMaxTemp();
    	}
    	
    	--justShared;
    	++ticksExisted;
        boolean wasBurning = isBurning();
        boolean sendUpdate = false;
        
        if(!worldObj.isRemote)
        {
        	if(fuel <= 0 && heat <= 0)
        	{
        		setLit(false);
        	}
        	if(isLit && fuel <= 0)
        	{
        		setLit(false);
        	}
        	else if(!cfg.lightForge)
        	{
        		setLit(true);
        	}
        	if(ticksExisted % 100 == 0)
        	{
        		adjacentLava = this.getAdjacentLava();
        	}
        	if(ticksExisted % 1 == 0)
        	{
        		shareTemp();
        	}
	        if(bonus > 0)bonus --;
	        if(fuel > 0)
	        {
	        	if(isLit)
	        	{
	        		fuel --;
	        		if(heat < getMaxTemp() && heat < itemHeat)
	        		{
	        			heat ++;
	        		}
	        	}
	        }
	        if(heat > getMaxTemp())
	        {
	        	heat = getMaxTemp();
	        }
	        if(!isLit || fuel <= 0)
	        {
	        	if(heat > 0)
	        	heat --;
	        }
	        if(constructed == 1 && fuel < getMaxFuel() && this.isItemFuel(items[0]))
	        {
	        	int burn = getItemBurnTime(items[0]);
	        	if(burn > 0 && fuel <= (getMaxFuel()-burn))
	        	{
	        		shareTemp();
	        		this.consumeFuel(items[0]);
	        		
	        		if(items[0].getItem().hasContainerItem())
	        		{
	        			ItemStack cont = items[0].getItem().getContainerItemStack(items[0]);
	        			setInventorySlotContents(0, cont);
	        		}
	        		else
	        		{
	        			decrStackSize(0, 1);
	        		}
	        	}
	        }
	        
	        if(heat > 0)
	        {
	        	for(int a = 1; a < items.length; a ++)
	        	{
	        		heatItem(a);
	        	}
	        }
	        for(int a = 1; a < items.length; a ++)
        	{
        		coolItem(a);
        	}
	        
	        if(isBurning())
	        {
	        	if(worldObj.getBlockMaterial(xCoord, yCoord+1, zCoord) != null && worldObj.getBlockMaterial(xCoord, yCoord+1, zCoord) == Material.water)
	        	{
	        		douse();
	        	}
	        }
	        
	        
	        if(isBurning() && worldObj.canLightningStrikeAt(xCoord, yCoord+1, zCoord))
	        {
	        	if(rand.nextInt(5) == 0)
	        	{
	        		for(int a = 0; a < 3 ; a ++)
	        		{
	        			worldObj.spawnParticle("splash", xCoord+(rand.nextDouble()*0.8D)+0.1D, yCoord + 0.4D, zCoord+(rand.nextDouble()*0.8D)+0.1D, 0, 0.01, 0);
	        		}
	        		
	        		worldObj.playSoundEffect(xCoord+0.5F, yCoord+0.5F, zCoord+0.5F, "random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
	        		
	        		if(heat > 1)
	        		{
		        		this.heat -= rand.nextInt(20);
		        		if(heat < 1) heat = 1;
	        		}
	        		if(heat < 5 && fuel > 0)
	        		{
	        			this.fuel -= rand.nextInt(20);
		        		if(fuel < 0) fuel = 0;
	        		}
	        	}
	        	if(rand.nextInt(20) == 0)
	        	{
	        		extinguish(Block.waterStill.blockID, 0);
	        	}
	        }
	        if(extinguishBonus > 0)
	        {
	        	extinguishBonus --;
	        }
	        
	        if(constructed == 0 || ticksExisted % 100 == 0)
        	{
        		findConstruction();
        	}
	        if(isLit && constructed == -1)
	        {
	        	setLit(false);
	        }
        }
        if (wasBurning != isBurning())
        {
            sendUpdate = true;
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

        if (sendUpdate)
        {
            this.onInventoryChanged();
        }
        if(this.isBurning())
		{
			puffSmoke(new Random(), worldObj, xCoord, yCoord, zCoord);
		}
        if(!worldObj.isRemote)
        {
	        if(ticksExisted % 20 == 0)
	        {
	        	syncItems();
	        }
	        sendPacketToClients();
        }
    }

	public void findConstruction() 
	{
		constructed = isConstructed() ? 1 : -1;
		
		if(constructed == -1 && isLit)
		{
			setLit(false);
		}
	}
	private int getOnMetadata()
	{
		return getType()*2 + 1;
	}
	
	private int getOffMetadata()
	{
		return getType()*2;
	}
	public int getBlockMetadata()
    {
		if(worldObj == null)
			return itemDam*2;
		
		
        if (this.blockMetadata == -1)
        {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        }

        return this.blockMetadata;
    }
	
	public void puffSmoke(Random rand, World world, int x, int y, int z)
	{
		if(rand.nextInt(15) != 0)
		{
			return;
		}
		
		Block block = Block.blocksList[world.getBlockId(x, y+2, z)];
		IChimney chimney = null;
		
		if(block instanceof IChimney)
			chimney = (IChimney)block;
		
		if(chimney != null
		&& chimney.puffSmoke(world, x, y+2, z, (float)1/12, 1, 1))
		{
			return;
		}
		
		for(int x1 = -1; x1 <= 1; x1 ++)
		{
			for(int z1 = -1; z1 <= 1; z1 ++)
			{
				Block block1 = Block.blocksList[world.getBlockId(x+x1, y+2, z+z1)];
				IChimney chimney1 = null;
				
				if(block1 instanceof IChimney)
					chimney1 = (IChimney)block1;
				
				if(chimney1 != null
				&& chimney1.puffSmoke(world, x+x1, y+2, z+z1, (float)1/12, 1, 1))
				{
					break;
				}
			}	
		}
	}

	private void heatItem(int slot) {
		ItemStack itemStack = items[slot];
		if(itemStack == null)
		{
			return;
		}
		
		if(HeatableItem.canHeatItem(itemStack))
		{
			setInventorySlotContents(slot, ItemHotItem.createHotItem(itemStack));
			return;
		}
	
		if(itemStack.itemID == ItemListMF.hotItem.itemID)
		{
			int multiplier = rand.nextInt(5)+1;
			double buff = (double)((double)getTotalHeat() / (double)ItemHandler.forgeMaxTemp * multiplier);
			int itemHeat = (int)(ItemHotItem.getTemp(itemStack));
			buff *= 1+((double)adjacentLava/4);
			
			ItemStack ingot = ItemHotItem.getItem(itemStack);
			if(ingot != null)
			if(HeatableItem.doesRuinItem(ingot, itemHeat))
			{
				decrStackSize(slot, 1);
			}
			if(itemHeat > getTotalHeat() || (int)(itemHeat+buff) > getTotalHeat())
			{
				return;
			}
			ItemHotItem.setTemp(itemStack, (int)(itemHeat + buff));
		}
		
	}
	
	
	
	private void coolItem(int slot) {
		ItemStack itemStack = items[slot];
		if(itemStack == null)
		{
			return;
		}
		
		if(rand.nextInt(10) == 0 && itemStack.itemID == ItemListMF.hotItem.itemID)
		{
			int multiplier = rand.nextInt(5)+1;
			double buff = 1;
			int itemHeat = (int)(ItemHotItem.getTemp(itemStack));
			
			ItemStack ingot = ItemHotItem.getItem(itemStack);
			if(ingot != null)
			if(HeatableItem.doesRuinItem(ingot, itemHeat))
			{
				decrStackSize(slot, 1);
			}
			if(itemHeat <= this.getTotalHeat() || (int)(itemHeat-buff) <= this.getTotalHeat())
			{
				return;
			}
			ItemHotItem.setTemp(itemStack, (int)(itemHeat - buff));
			
			if(itemHeat <=0)
			{
				if(ItemHotItem.getItem(itemStack) != null)
				{
					setInventorySlotContents(slot, ItemHotItem.getItem(itemStack));
				}
			}
		}
	}
	
	
	
	private void dampenItem(int slot) {
		ItemStack itemStack = items[slot];
		if(itemStack == null)
		{
			return;
		}
	
		if(itemStack.itemID == ItemListMF.hotItem.itemID)
		{
			if(ItemHotItem.getItem(itemStack) != null)
			{
				setInventorySlotContents(slot, ItemHotItem.getItem(itemStack));
			}
			else
			{
				ItemHotItem.setTemp(itemStack, 0);
			}
		}
	}
	
	public void splashWater()
	{
		if(!isBurning())return;
		heat -= 100;
		if(heat < 0)heat = 0;
		
		worldObj.playSound(xCoord+0.5F, yCoord+0.4F, zCoord+0.5F, "random.fizz", 1.0F, 1.0F, true);
		worldObj.spawnParticle("smoke", xCoord+0.5F, yCoord+0.4F, zCoord+0.5F, 0, 0.05F, 0);
		
		
		
		if (!worldObj.isRemote) {
			try {
				Packet packet = PacketManagerMF.getPacketIntegerArray(this,
						new int[] {1, 0});
				FMLCommonHandler.instance().getMinecraftServerInstance()
						.getConfigurationManager()
						.sendPacketToAllPlayers(packet);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void douse()
	{
		if(!isBurning())return;
		heat = itemHeat = bonus = 0;
		worldObj.playSound(xCoord+0.5F, yCoord+0.4F, zCoord+0.5F, "random.fizz", 2.0F, 0.5F, true);
		for(int a = 0; a < 10; a ++)
		{
			worldObj.spawnParticle("largesmoke", xCoord+(rand.nextDouble()*0.8F)+0.1F, yCoord+0.4F, zCoord+(rand.nextDouble()*0.8F)+0.1F, 0, 0.05F, 0);
		}
		for(int a = 1; a < items.length; a ++)
    	{
    		dampenItem(a);
    	}
		setLit(false);
		fuel = 0;
		
		
		
		if (!worldObj.isRemote) {
			try {
				Packet packet = PacketManagerMF.getPacketIntegerArray(this,
						new int[] {1, 1});
				FMLCommonHandler.instance().getMinecraftServerInstance()
						.getConfigurationManager()
						.sendPacketToAllPlayers(packet);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}
	
	private float getItemHeat(ItemStack itemStack) {
		return ItemHandler.getForgeHeat(itemStack);
	}

	private void sendPacketToClients()
	{
		try 
		{
			Packet packet = PacketManagerMF.getPacketIntegerArray(this,
					new int[] {0, fuel,
							(int)heat*10, (int)bonus*10, (int)itemHeat*10, direction, isLit ? 1 : 0, constructed});
			FMLCommonHandler.instance().getMinecraftServerInstance()
					.getConfigurationManager()
					.sendPacketToAllPlayers(packet);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
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

    @Override
	public void recievePacket(ByteArrayDataInput data) {
    	int type = data.readInt();
    	
    	if(type == 0)
    	{
			fuel = data.readInt();
			heat = data.readInt()/10F;
			bonus = data.readInt()/10F;
			itemHeat = data.readInt()/10F;
			direction = data.readInt();
			
			int i = data.readInt();
			setLit(i == 1);
			constructed = data.readInt();
    	}
		if(type == 1)
		{
			int action = data.readInt();
			if(action == 0)
			{
				splashWater();
			}
			if(action == 1)
			{
				douse();
			}
		}
		if(type == 2)
		{
			int p = data.readInt();
			int i = data.readInt();
			int slot = data.readInt();
			
			Entity e = worldObj.getEntityByID(p);
			
			if(e != null && e instanceof EntityPlayer)
			{
				BlockForge.useInventory(worldObj, xCoord, yCoord, zCoord, this, (EntityPlayer)e, i, slot);
			}
			return;
		}
	}
    


    /**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
     * fuel
     */
    public static int getItemBurnTime(ItemStack fuel)
    {
        if (fuel == null)
        {
            return 0;
        }
        else
        {
        	return (int)ItemHandler.getForgeFuel(fuel)*2;
        }
    }
    
    /**
     * Determines if putting this fuel in lights the forge too(like lava)
     */
    public static boolean doesItemLight(ItemStack fuel)
    {
    	return ItemHandler.willLight(fuel);
    }
    
    public static int getItemTemperature(ItemStack fuel)
    {
        if (fuel == null)
        {
            return 0;
        }
        else
        {
        	return ItemHandler.getForgeHeat(fuel);
        }
    }

    /**
     * Return true if item is a fuel source (getItemBurnTime() > 0).
     */
    public boolean isItemFuel(ItemStack fuel)
    {
    	if(fuel == null)
    	{
    		return false;
    	}
        return getItemBurnTime(fuel) > 0 && getItemTemperature(fuel) <= this.forgeMaxTemp;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer user)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : user.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}

	public int getMaxBellowsBonus() {
		return (int)(heat/4);
	}

	public void onUsedWithBellows(float powerLevel) {
		if(!isBurning())return;
		if(justShared > 0)
		{
			return;
		}
		if(fuel <= 0)
		{
			return;
		}
		justShared = 5;
		if(heat < itemHeat)
        {
        	heat += 50*powerLevel;
        }
		if(bonus < getMaxBellowsBonus())
		{
			bonus += 100*powerLevel;
		}
		if(bonus > getMaxBellowsBonus())
		{
			bonus = getMaxBellowsBonus();
		}
		
		for(int a = 0; a < 10; a ++)
		{
			worldObj.spawnParticle("flame", xCoord+(rand.nextDouble()*0.8D)+0.1D, yCoord + 0.4D, zCoord+(rand.nextDouble()*0.8D)+0.1D, 0, 0.01, 0);
		}
		
		pumpBellows(-1, 0, powerLevel*0.9F);
		pumpBellows(0, -1, powerLevel*0.9F);
		pumpBellows(0, 1, powerLevel*0.9F);
		pumpBellows(1, 0, powerLevel*0.9F);
	
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if(i == 0)//FUEL
		{
			if(getStackInSlot(0) == null)
			{
				return this.getItemBurnTime(itemstack) > 0;
			}
		}
			
		return HeatableItem.canHeatItem(itemstack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{0,1,2,3,4,5,6,7,8,9};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		if(slot > 0)
		{
			if(items[slot] == null)
			{
				return true;
			}
		}
		return true;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		if(side == 0)
		{
			if(item != null)
			{
				if(isProperlyHeated(item))
				{
					return slot > 0;
				}
				if(item.getItem() == Item.bucketEmpty)
				{
					return slot == 0;
				}
			}
		}
		return false;
	}

	public static boolean isProperlyHeated(ItemStack item) 
	{
		if(item.getItem() == null)
		{
			return false;
		}
		if(item.getItem() instanceof ItemHotItem)
		{
			if(!ItemHotItem.showTemp(item))
			{
				return true;
			}
			else
			{
				ItemStack item2 = ItemHotItem.getItem(item);
				int temp = ItemHotItem.getTemp(item)-1;
				if(HeatableItem.canWorkItem(item2, temp))
				{
					return true;
				}
			}
		}
		return false;
	}
	private int getAdjacentLava()
	{
		int amount = 0;
		for(int x = -1; x <= 1; x ++)
		{
			for(int z = -1; z <= 1; z ++)
			{
				for(int y = -1; y <= 1; y ++)
				{
					if(!(x == 0 && y == 0 &&  z == 0))
					{
						if(worldObj.getBlockMaterial(xCoord + x, yCoord + y, zCoord + z) == Material.lava)amount ++;
					}
				}
			}
		}
		return amount;
	}
	
	/**
	 * @return Front, Left, Right, Back
	 */
	public boolean[] showSides()
	{
		if(worldObj == null)
		{
			return new boolean[]{true, true, true, true};
		}
		boolean front = !isForge(0, 0, 1);
		boolean left = !isForge(-1, 0, 0);
		boolean right = !isForge(1, 0, 0);
		boolean back = !isForge(0, 0, -1);
		
		return new boolean[]{front, left, right, back};
	}
	
	private void shareTemp()
	{
		shareTo(-1, 0);
		shareTo(1, 0);
		shareTo(0, -1);
		shareTo(0, 1);
	}
	private void shareTo(int x, int z)
	{
		if(fuel <= 0)return;
		
		int share = 2;
		TileEntity tile = worldObj.getBlockTileEntity(xCoord+x, yCoord, zCoord+z);
		if(tile == null)return;
		
		if(tile instanceof TileEntityForge)
		{
			TileEntityForge forge = (TileEntityForge)tile;
			
			if(isLit && !forge.isLit && forge.fuel > 0 && forge.extinguishBonus <= 0)
			{
				forge.setLit(true);
			}
			if(!forge.isBurning() && heat > 1)
			{
				forge.heat = 1;
			}
			if(forge.heat < (heat - share))
			{
				forge.heat += share;
				heat -= share;
			}
			share = 20;
			if(forge.fuel < (fuel - share))
			{
				forge.fuel += share;
				fuel -= share;
			}
		}
	}
	
	
	private void pumpBellows(int x, int z, float pump)
	{
		if(fuel <= 0)return;
		
		int share = 2;
		TileEntity tile = worldObj.getBlockTileEntity(xCoord+x, yCoord, zCoord+z);
		if(tile == null)return;
		
		if(tile instanceof TileEntityForge)
		{
			TileEntityForge forge = (TileEntityForge)tile;
			forge.onUsedWithBellows(pump);
		}
	}
	private boolean isForge(int x, int y, int z)
	{
		return worldObj.getBlockId(xCoord+x, yCoord+y, zCoord+z) == BlockListMF.forge.blockID;
	}
	
	public boolean tryAddItem(int slotNum, ItemStack item)
	{
		if(slotNum < 0 || slotNum >= items.length || item == null)
		{
			return false;
		}
		
		ItemStack slot = items[slotNum];
		if(slot == null && HeatableItem.canHeatItem(item))
		{
			ItemStack copy = item.copy();
			copy.stackSize = 1;
			setInventorySlotContents(slotNum, copy);
			if(!worldObj.isRemote)
        	{
        		syncItems();
        	}
			
			return true;
		}
		return false;
	}
	public int getType()
	{
		int meta = getBlockMetadata();
		return (int)Math.floor(meta/2);
	}
	public String getTexture() {
		if(getType() == 1)
		{
			return "forge_cobble";
		}
		if(getType() == 2)
		{
			return "forge_obsidian";
		}
		return "forge";
	}
	public int getSlotFor(float x, float y) 
	{
		int[] coord = BlockClickHelper.getCoordsFor(x, y, 0F, 1F, 0F, 1F, 3, 3, direction);

		if(coord == null)
		{
			return -1;
		}
		return (coord[0] + coord[1]*3) + 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRenderCraftMetre() 
	{
		return fuel > 0 || heat > 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getProgressBar(int width) 
	{
		return this.fuel * width / (getMaxFuel());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getResultName()
	{
		int seconds = (int)Math.floor(fuel/20);
		int mins = (int)Math.floor(seconds/60);
		seconds -= mins*60;
		
		String s = "";
		if(seconds < 10)
		{
			s += "0";
		}
		
		return StatCollector.translateToLocal("info.fuel") + "= " + mins + ":" + s + seconds;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setTempResult(ItemStack res) 
	{
	}
	@Override
	public boolean canPlaceAbove() 
	{
		return true;
	}
	@Override
	public int getHeat() 
	{
		return (int) heat;
	}
	public void setLit(boolean b) 
	{
		if(b && !isConstructed())
		{
			b = false;
		}
		isLit = b;
	}
	
	public void extinguishByHand() 
	{
		extinguishByHand(16);
	}
	public void extinguishByHand(int a) 
	{
		extinguishSide(-1, 0, a);
		extinguishSide(1, 0, a);
		extinguishSide(0, -1, a);
		extinguishSide(0, 1, a);
		
		extinguish();
	}
	private void extinguishSide(int x, int z, int c)
	{
		if(c <= 0)return;
		
		TileEntity tile = worldObj.getBlockTileEntity(xCoord+x, yCoord, zCoord+z);
		if(tile == null)return;
		
		if(tile instanceof TileEntityForge)
		{
			TileEntityForge forge = (TileEntityForge)tile;
			
			if(forge.isLit)
			{
				forge.extinguishByHand(c-1);
			}
		}
	}
	
	public void extinguish() 
	{
		extinguish(Block.sand.blockID, 0);
	}
	
	public void extinguish(int block, int meta) 
	{
		worldObj.playSoundEffect(xCoord+0.5F, yCoord+0.25F, zCoord+0.5F, "random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
		worldObj.spawnParticle("largesmoke", xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, 0.0D, 0.0D, 0.0D);
		worldObj.spawnParticle("tilecrack_" + block + "_" + meta, xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, 0.0D, 0.0D, 0.0D);
		
		extinguishBonus = 20;
		setLit(false);
	}
	
	public int getMaxFuel()
	{
		return 20*60*5;
	}
	public void consumeFuel(ItemStack item)
	{
		if(item == null)return;
		
		fuel = (int)(Math.min(getMaxFuel(), fuel + getItemBurnTime(item)));
		itemHeat = getItemHeat(item);
		
		if(!isLit && doesItemLight(item))
		{
			worldObj.playSoundEffect(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, "random.fizz", 1.0F, 0.5F + rand.nextFloat());
			setLit(true);
		}
	}
	
	public int getMaxTemp()
	{
		if(getType() == 2)
		{
			return 5000;
		}
		return 1000;
	}
	
	private boolean isConstructed()
	{
		if(getType() == 2)
		{
			return isObsidianBuilt();
		}
		return true;
	}
	private boolean isObsidianBuilt()
	{
		//CHECK FOR A 3x3 BASE
		for(int x = -1; x <= 1; x ++)
		{
			for(int z = -1; z <= 1; z ++)
			{
				int id = worldObj.getBlockId(xCoord+x, yCoord-1, zCoord+z);
				if(id != Block.obsidian.blockID)
				{
					return false;
				}
			}
		}
		
		//COUNT THE SIDES
		int sides = 0;

		for(int x = -1; x <= 1; x ++)
		{
			for(int z = -1; z <= 1; z ++)
			{
				if(!(x == 0 && z == 0))
				{
					int id = worldObj.getBlockId(xCoord+x, yCoord, zCoord+z);
					if(id == Block.obsidian.blockID)
					{
						sides ++;
					}
					TileEntity tile = worldObj.getBlockTileEntity(xCoord+x, yCoord, zCoord+z);
					if(tile != null && tile instanceof TileEntityForge)
					{
						if(((TileEntityForge)tile).getType()==2)
						{
							sides ++;
						}
					}
				}
			}
		}
				
		return sides >= 5;
	}
}
