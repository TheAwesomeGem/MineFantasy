package minefantasy.block.tileentity;

import java.util.Random;

import minefantasy.MineFantasyBase;
import minefantasy.api.aesthetic.IChimney;
import minefantasy.api.forge.IBellowsUseable;
import minefantasy.api.forge.ItemHandler;
import minefantasy.api.refine.BlastRecipes;
import minefantasy.block.BlockChimney;
import minefantasy.block.BlockListMF;
import minefantasy.item.ItemListMF;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityBFurnace extends TileEntity
	implements IInventory, PacketUserMF, ISidedInventory, IBellowsUseable{
	public byte direction;
	private ItemStack[] inventory;
	public int fuel;
	public int maxFuel;
	public int cookTime;
	public static int requiredTime = 500;
	public int ticksExisted;
	public int heat;
	public static int requiredHeat = 2400;
	public boolean canCook;
	private boolean isBuilt;
	private int bellowsBonus;
	private final ItemStack slag = ItemListMF.component(ItemListMF.slag, 1);
	
	public TileEntityBFurnace()
	{
		inventory = new ItemStack[3];
	}
	

	public void updateFurnace()
	{
		if(!this.furnaceExists())
		{
			fuel = maxFuel = cookTime = 0;
			return;
		}
		if(getFuelBlock() != null)
		{
			getFuelBlock().canCook = canCook();
		}
		
		boolean var1 = this.fuel > 0;
        boolean var2 = false;

        if (this.fuel > 0)
        {
            --this.fuel;
        }

        if (!this.worldObj.isRemote)
        {
        	if(this.getFuelBlock() != null)
            if (this.fuel == 0 && this.getFuelBlock().canRefuel())
            {
                this.maxFuel = this.fuel = getItemBurnTime(getFuelSlot());

                if (this.fuel > 0)
                {
                    var2 = true;
                    
                    this.getFuelBlock().decrStackSize(0, 1);
                }
            }

            if (this.isBurning() && this.canSmelt())
            {
                ++this.cookTime;
                if(getFuelBlock() != null && getFuelBlock().bellowsBonus > 0)
                {
                	cookTime += 2;
                }

                if (this.cookTime >= requiredTime)
                {
                    this.cookTime = 0;
                    this.smeltItem();
                    var2 = true;
                }
            }
            else
            {
                this.cookTime = 0;
            }

            if (var1 != this.fuel > 0)
            {
                var2 = true;
            }
        }

        if (var2)
        {
            this.onInventoryChanged();
        }
        if(getFuelBlock() != null)
        {
	        this.getFuelBlock().fuel = this.fuel;
	        this.getFuelBlock().maxFuel = this.maxFuel;
        }
        if(isBurning() && ticksExisted % 5 == 0)
        {
        	puffSmoke(3);
        }
	}
    
	private boolean canSmelt()
    {
		int h = 0;
		if(getFuelBlock() != null)
		{
			h = getFuelBlock().heat;
		}
		
        return canCook() && h >= requiredHeat;
    }
	
	private boolean canRefuel()
	{
		if(this.blockMetadata == 2)
		{
			if(worldObj.isBlockIndirectlyGettingPowered((int)xCoord, (int)yCoord, (int)zCoord))
					return false;
		}
		return true;
	}
	private boolean canCook()
	{
		if (this.inventory[0] == null || this.inventory[1] == null || this.inventory[2] == null)
        {
            return false;
        }
        else
        	if(!isCoal(0) || !isLimestone(1))
        	{
        		return false;
        	}
        else
        {
            ItemStack var1 = BlastRecipes.getResult(this.inventory[2]);
            if (var1 == null) return false;
            if (this.getResultSlot() == null) return true;
            if (!this.getResultSlot().isItemEqual(var1)) return false;
            int result = getResultSlot().stackSize + var1.stackSize;
            
            return canFitSlag() && (result <= getInventoryStackLimit() && result <= var1.getMaxStackSize());
        }
	}
	
	private boolean canFitSlag()
	{
		if (this.getSlagSlot() == null) return true;
        if (!this.getSlagSlot().isItemEqual(slag)) return false;
        int result = getSlagSlot().stackSize + slag.stackSize;
        
        return (result <= 64);
	}


	private boolean isLimestone(int i) {
		return ItemHandler.isFlux(inventory[i]);
	}
	private boolean isCoal(int i) {
		return ItemHandler.isCarbon(inventory[i]);
	}


	public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack var1 = BlastRecipes.getResult(this.inventory[2]);

            if (this.getResultSlot() == null)
            {
                this.setResult(var1.copy());
            }
            else if (this.getResultSlot().isItemEqual(var1))
            {
                this.getResultBlock().incrStackSize(0, var1.stackSize);
            }
            
            if (this.getSlagSlot() == null)
            {
                this.setSlag(slag.copy());
            }
            else if (this.getSlagSlot().isItemEqual(slag))
            {
                this.getResultBlock().incrStackSize(1, slag.stackSize);
            }
            

            for(int a = 0; a < 3; a ++)
            {
            	--this.inventory[a].stackSize;

            	if (this.inventory[a].stackSize <= 0)
            	{
            		this.inventory[a] = null;
            	}
            }
        }
    }
	
	
	public static int getItemBurnTime(ItemStack stack)
    {
        if (stack == null)
        {
            return 0;
        }
        else
        {
        	if(ItemHandler.getBlastFuel(stack) > 0)
        	{
        		return (int)ItemHandler.getBlastFuel(stack)*requiredTime;
        	}
        	return 0;
        }
    }
	
	/**
	 * Applies the update for the Fuel Block
	 */
	public void updateFuel()
	{
		if(worldObj.isRemote)
		{
			return;
		}
		
		if(getInput() != null)
		{
			if(fuel > 0 && heat < requiredHeat)
			{
				heat += 4;
				if(bellowsBonus > 0)heat += 2;
				fuel -= 5;
			}
			if(fuel <= 0)
			{
				if(heat > 0) heat -= 2;
				if(heat < 0) heat = 0;
			}
		}
		else
		{
			if(heat > 0) heat -= 10;
			if(heat < 0) heat = 0;
		}
		//if(heat < 0)heat = 0;
	}
	public void setFuelStack(ItemStack item)
	{
		if(this.hasFuelBlock())
		{
			getFuelBlock().setInventorySlotContents(0, item);
		}
	}
	@SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getCookProgressScaled(int width)
    {
        return this.cookTime * width / requiredTime;
    }
	
	
	@SideOnly(Side.CLIENT)
    public int getHeatProgressScaled(int width)
    {
        return this.heat * width / requiredHeat;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    public int getBurnTimeRemainingScaled(int height)
    {
    	
    	if(fuel <= 0)return 0;
        if (this.maxFuel == 0)
        {
            this.maxFuel = 200;
        }

        return this.fuel * height / this.maxFuel;
        
    }
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		bellowsBonus = nbt.getInteger("Bellows");
		ticksExisted = nbt.getInteger("Time");
		heat = nbt.getInteger("heat");
		direction = nbt.getByte("Facing");
		fuel = nbt.getInteger("Fuel");
		maxFuel = nbt.getInteger("MFuel");
		cookTime = nbt.getInteger("Cook");
		isBuilt = nbt.getBoolean("Build");

		NBTTagList var2 = nbt.getTagList("Items");
        this.inventory = new ItemStack[3];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.inventory.length)
            {
                this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        
        if(nbt.hasKey("CustomName"))
        {
        	customName = nbt.getString("CustomName");
        }
	}
	
	@Override
	public void updateEntity()
	{
		ticksExisted ++;
		
		if(bellowsBonus > 0)bellowsBonus --;
		
		if(ticksExisted % 10 == 0)
		{
			isBuilt = structureExists();
		}
		
		boolean wasBurning = isBurning();
		super.updateEntity();
		if(blockMetadata == 2 && furnaceExists())
		{
			updateFuel();
		}
		
		if(blockMetadata == 1 && furnaceExists())
			updateFurnace();
		if(!furnaceExists())
		{
			fuel = maxFuel = cookTime = 0;
		}
		this.sendPacketToClients();
		if(ticksExisted % 20 == 0 || wasBurning != isBurning())
		{
			onSecondUpdate();
		}
	}
	
	/**
	 * @return 0 = shaft, 1 = furnace 2 = fuel 3 = out
	 */
	public int getType()
	{
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}
	/**
	 * Is Called every second. or when status is updated
	 */
	private void onSecondUpdate() {
		
		worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);
		int radius = 10;
		int height = 10;
		for(int a = -radius; a <= radius; a ++)
		{
			for(int b = -height; b <= height; b ++)
			{
				for(int c = -radius; c <= radius; c ++)
				{
					Random rand = new Random();
					if(rand .nextInt(30) == 0 && canCutSnow(a, b, c, radius))
					{
						Material mat = worldObj.getBlockMaterial((int)xCoord + a, (int)yCoord + b, (int)zCoord + c);
						if(mat != null && mat == Material.snow)
						{
							worldObj.setBlock((int)xCoord + a, (int)yCoord + b, (int)zCoord + c, 0);
						}
					}
				}
			}
		}
	}


	private boolean canCutSnow(int a, int b, int c, int r) {
		if(!isBurning())
			return false;
		if(this.getDistanceFromPos(a, yCoord, c) > r)
			return false;
		
		
		return true;
	}


	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("Bellows", bellowsBonus);
		nbt.setInteger("heat", heat);
		nbt.setInteger("Time", ticksExisted);
		nbt.setByte("Facing", direction);
		nbt.setInteger("Fuel", fuel);
		nbt.setInteger("MFuel", maxFuel);
		nbt.setInteger("Cook", cookTime);
		nbt.setBoolean("Build", isBuilt);
		
		NBTTagList var2 = new NBTTagList();
		
		for (int var3 = 0; var3 < this.inventory.length; ++var3)
        {
            if (this.inventory[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.inventory[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

		nbt.setTag("Items", var2);
		
		if(isInvNameLocalized())
        {
        	nbt.setString("CustomName", customName);
        }
	}
	
	public TileEntityBFurnace getFuelBlock()
	{
		int stepDown = 5;
		if(worldObj.getBlockMetadata(xCoord, yCoord-stepDown, zCoord) != 2)
			return null;
		return getBFurnAt(xCoord, yCoord-stepDown, zCoord);
	}
	
	public TileEntityBFurnace getInput()
	{
		int stepUp = 5;
		if(worldObj.getBlockMetadata(xCoord, yCoord+stepUp, zCoord) != 1)
			return null;
		return getBFurnAt(xCoord, yCoord+stepUp, zCoord);
	}
	
	


	public TileEntityBFurnace getResultBlock()
	{
		TileEntityBFurnace r = null;
		int stepDown = 6;
		
		r = getBFurnAt(xCoord-2, yCoord-stepDown, zCoord);
		if(r != null && worldObj.getBlockMetadata(xCoord-2, yCoord-stepDown, zCoord) == 3)
		return r;
		
		r = getBFurnAt(xCoord+2,yCoord-stepDown, zCoord);
		if(r != null && worldObj.getBlockMetadata(xCoord+2, yCoord-stepDown, zCoord) == 3)
		return r;
		
		r = getBFurnAt(xCoord, yCoord-stepDown, zCoord-2);
		if(r != null && worldObj.getBlockMetadata(xCoord, yCoord-stepDown, zCoord-2) == 3)
		return r;
		
		r = getBFurnAt(xCoord, yCoord-stepDown, zCoord+2);
		if(r != null && worldObj.getBlockMetadata(xCoord, yCoord-stepDown, zCoord+2) == 3)
		return r;
		
		return null;
	}
	public boolean hasFuelBlock()
	{
		return getFuelBlock() != null;
	}
	public boolean hasResultBlock()
	{
		return getResultBlock() != null;
	}
	public boolean furnaceExists()
	{
		return isBuilt;
	}
	public boolean structureExists() {
		if(blockMetadata != 1)
			return true;
		if(this.hasFuelBlock())
			;//System.out.println("Has Fuel");
		else
			return false;
		if(this.hasResultBlock())
			;//System.out.println("Has Output");
		else
			return false;
		if(!isChamberBlock(xCoord, yCoord+1, zCoord))
			return false;
		if(!isChamberBlock(xCoord, yCoord+2, zCoord))
			return false;
		if(!isChamberBlock(xCoord, yCoord-1, zCoord))
			return false;
		if(!isChamberBlock(xCoord, yCoord-2, zCoord))
			return false;
		if(!isChamberBlock(xCoord, yCoord-3, zCoord))
			return false;
		if(!isChamberBlock(xCoord, yCoord-4, zCoord))
			return false;
		
		for(int x = -1; x < 2; x ++)
		{
			for(int z = -1; z < 2; z ++)
			{
				int id = Block.stoneBrick.blockID;
				if(x == 0 && z == 0)
					id = Block.lavaStill.blockID;
				if(worldObj.getBlockId(xCoord+x, yCoord-6, zCoord+z) != id)
				{
					return false;
				}
			}
		}
		
		for(int x = -1; x < 2; x ++)
		{
			for(int z = -1; z < 2; z ++)
			{
				if(worldObj.getBlockId(xCoord+x, yCoord-7, zCoord+z) != Block.stoneBrick.blockID)
				{
					return false;
				}
			}
		}
		
		return worldObj.canBlockSeeTheSky(xCoord, yCoord+3, zCoord);
	}
	public int getFuel()
	{
		if(!hasFuelBlock())
			return 0;
		else
			return getFuelBlock().fuel;
	}
	public ItemStack getFuelSlot()
	{
		if(!hasFuelBlock())
			return null;
		else
			return getFuelBlock().getStackInSlot(0);
	}
	public ItemStack getResultSlot()
	{
		if(!hasResultBlock())
			return null;
		else
			return getResultBlock().getStackInSlot(0);
	}
	public ItemStack getSlagSlot()
	{
		if(!hasResultBlock())
			return null;
		else
			return getResultBlock().getStackInSlot(1);
	}
	public boolean interact(EntityPlayer player)
	{
		if(blockMetadata != 0)
		player.openGui(MineFantasyBase.instance, 7, worldObj, xCoord, yCoord, zCoord);
		return true;
	}
	public String getTexture()
	{
		switch(getBlockMetadata())
		{
			case 1:
				return "in";
			case 2:
					return "fuel";
			case 3:
				return "out";
		}
		return "";
	}
	
	
	
	private boolean isChamberBlock(int x, int y, int z) {
		if(worldObj.getBlockMaterial(x+1, y, z) != Material.rock)return false;
		if(worldObj.getBlockMaterial(x-1, y, z) != Material.rock)return false;
		if(worldObj.getBlockMaterial(x, y, z+1) != Material.rock)return false;
		if(worldObj.getBlockMaterial(x, y, z-1) != Material.rock)return false;
			
		return worldObj.getBlockId(x, y, z) == BlockListMF.Blast.blockID && worldObj.getBlockMetadata(x, y, z) == 0;
	}
	public String getDefaultName() {
		switch(getBlockMetadata())
		{
			case 1:
				return "container.blastfurn";
			case 2:
					return "container.blastfurn.fuel";
			case 3:
				return "container.blastfurn.out";
		}
		return "";
	}
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (getBFurnAt(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return entityplayer.getDistanceSq((double) xCoord + 0.5D,
				(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
	}

	public void openChest() {
	}

	public void closeChest() {
	}
	private void sendPacketToClients() {
		if (!worldObj.isRemote) {
			try {
				Packet packet = PacketManagerMF.getPacketIntegerArray(this,
						new int[] { fuel, maxFuel,
								cookTime, direction, heat });
				FMLCommonHandler.instance().getMinecraftServerInstance()
						.getConfigurationManager()
						.sendPacketToAllPlayers(packet);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}
	public void setResult(ItemStack item)
	{
		if(hasResultBlock())
		{
			getResultBlock().setInventorySlotContents(0, item);
		}
	}
	
	public void setSlag(ItemStack item)
	{
		if(hasResultBlock())
		{
			getResultBlock().setInventorySlotContents(1, item);
		}
	}
	@Override
	public void recievePacket(ByteArrayDataInput data) {
		fuel = data.readInt();
		maxFuel = data.readInt();
		cookTime = data.readInt();
		direction = (byte)data.readInt();
		heat = data.readInt();
	}

	public boolean isBurning() {
		if(this.getBlockMetadata() == 2)
		{
			return heat > 0;
		}
		if(this.getBlockMetadata() == 1)
		{
			if(getFuelBlock() != null)
			{
				return getFuelBlock().heat > 0;
			}
		}
		if(blockMetadata == 1)return fuel > 0;
		TileEntityBFurnace furn = getBFurnAt(xCoord, yCoord+4, zCoord);
		if(furn != null)
		{
			return furn.isBurning();
		}
		return false;
	}

	public int getSizeInventory() {
		return inventory.length;
	}

	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	public ItemStack decrStackSize(int i, int j) {
		if(inventory[i] != null && inventory[i].getItem() != null)
		{
			if(inventory[i].getItem().hasContainerItem())
			{
				inventory[i] = inventory[i].getItem().getContainerItemStack(inventory[i]);
				return inventory[i];
			}
		}
		
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}
	public void incrStackSize(int slot, int num)
	{
		inventory[slot].stackSize += num;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}
	
	public void puffSmoke(int height)
	{
		Random rand = new Random();
	    float dense = getSmokeDensity();
	    
		IChimney chimney = (IChimney)Block.blocksList[worldObj.getBlockId(xCoord, yCoord+1, zCoord)];
		if(chimney == null
		|| (chimney != null && !chimney.puffSmoke(worldObj, xCoord, yCoord+1, zCoord, (float)1/2*dense, 1, 1*height)))
		{
        	worldObj.spawnParticle("largesmoke", xCoord+0.5F, yCoord+1, zCoord+0.5F, (rand.nextFloat()-0.5F)/2 * dense, 0.065F, (rand.nextFloat()-0.5F)/2 * height);
		}
	}
	private float getSmokeDensity() {
		float sc =  1.0F;
		if(getFuelBlock() != null)
		{
			sc = getFuelBlock().getTempEmitions();
		}
		
		return 1.0F * sc;
	}


	private TileEntityBFurnace getBFurnAt(int x, int y, int z) {
		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
		if(tile == null)
			return null;
		
		if(tile instanceof TileEntityBFurnace)
			return (TileEntityBFurnace)tile;
		
		return null;
	}
	
	public float getTempEmitions()
	{
		if(requiredHeat <= 0)requiredHeat = 1;
		
		return heat / requiredHeat;
	}
	
	public ForgeDirection getFacing()
    {
    	switch(direction)
    	{
    	case 2:
    		return ForgeDirection.NORTH;
    	case 3:
    		return ForgeDirection.SOUTH;
    	case 5:
    		return ForgeDirection.EAST;
    	case 4:
    		return ForgeDirection.WEST;
    	}
    	return ForgeDirection.NORTH;
    }
    
    public ForgeDirection getBack()
    {
    	switch(direction)
    	{
    	case 2:
    		return ForgeDirection.SOUTH;
    	case 3:
    		return ForgeDirection.NORTH;
    	case 5:
    		return ForgeDirection.WEST;
    	case 4:
    		return ForgeDirection.EAST;
    	}
    	return ForgeDirection.NORTH;
    }
    
    public ForgeDirection getLeft()
    {
    	switch(direction)
    	{
    	case 2:
    		return ForgeDirection.WEST;
    	case 3:
    		return ForgeDirection.EAST;
    	case 5:
    		return ForgeDirection.NORTH;
    	case 4:
    		return ForgeDirection.SOUTH;
    	}
    	return ForgeDirection.NORTH;
    }
    
    public ForgeDirection getRight()
    {
    	switch(direction)
    	{
    	case 2:
    		return ForgeDirection.EAST;
    	case 3:
    		return ForgeDirection.WEST;
    	case 5:
    		return ForgeDirection.SOUTH;
    	case 4:
    		return ForgeDirection.NORTH;
    	}
    	return ForgeDirection.NORTH;
    }



	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if(this.getType() == 1)
		{
			if(i == 0)//COAL
				return ItemHandler.isCarbon(itemstack);
			
			if(i == 1)//LIME
				return ItemHandler.isFlux(itemstack);
				
			if(i == 2)//IN
				return BlastRecipes.getResult(itemstack) != null;
		}
		if(this.getType() == 2)//FUEL
			return this.getItemBurnTime(itemstack) > 0;
			
		return false;
	}
	
	public double getDistanceFromPos(double x, double y, double z)
    {
        double d3 = (double)this.xCoord + 0.5D - x;
        double d4 = (double)this.yCoord + 0.5D - y;
        double d5 = (double)this.zCoord + 0.5D - z;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }


	@Override
	public int[] getAccessibleSlotsFromSide(int slot) {
		switch(this.getType())
		{
		case 0://SHAFT
			return null;
		case 1://IN
			return new int[]{0, 1, 2};
		case 3://FUEL
			return new int[]{0, 1};
		}
		return new int[]{0};
	}


	@Override
	/**
	 * Put In
	 */
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return isItemValidForSlot(slot, item);
	}
	@Override
	/**
	 * Take From
	 */
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		if(getType() == 1) //IN
		{
			if(slot == 2)
				return true;
		}
		if(getType() == 2 || getType() == 3) //FUEL
		{
			return side == 0;
		}
		return true;
	}


	@Override
	public void onUsedWithBellows(float powerLevel)
	{
		bellowsBonus = (int)(20F*powerLevel);
	}
	
	private String customName;
	public void setCustomName(String name)
	{
		customName = name;
	}
	@Override
	public String getInvName()
	{
		return isInvNameLocalized() ? customName : getDefaultName();
	}
	@Override
	public boolean isInvNameLocalized() 
	{
		return customName != null && customName.length() > 0;
	}
}
