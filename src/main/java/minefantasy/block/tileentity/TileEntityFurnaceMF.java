package minefantasy.block.tileentity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import minefantasy.api.aesthetic.IChimney;
import minefantasy.api.forge.IBellowsUseable;
import minefantasy.api.forge.ItemHandler;
import minefantasy.api.refine.Alloy;
import minefantasy.api.refine.AlloyRecipes;
import minefantasy.api.refine.BloomRecipe;
import minefantasy.api.refine.SpecialFurnaceRecipes;
import minefantasy.block.BlockChimney;
import minefantasy.block.BlockListMF;
import minefantasy.container.ContainerFurnaceMF;
import minefantasy.item.ItemBloom;
import minefantasy.item.ItemListMF;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityFurnaceMF extends TileEntity implements IBellowsUseable, IInventory, ISidedInventory, PacketUserMF 
{

	//UNIVERSAL
	public int fuel;
	public int maxFuel;
	public ItemStack[] inv;
	public int direction;
	public int itemMeta;
	public boolean built = false;
	private Random rand = new Random();
	
	//ANIMATE
	public int numUsers;
	public int doorAngle;
	
	//HEATER
	public float heat;
	public float maxHeat;
	private int aboveType;
	private int justShared;
	
	//FURNACE
	public int progress;
	private int ticksExisted;
	private int ticksSinceSync;
	
	/**
	 * CLIENT VAR
	 * This is used to determine if the block is able to emit smoke
	 */
	private boolean isBurningClient;
	
	public TileEntityFurnaceMF()
	{
		super();
		inv = new ItemStack[8];
		fuel = maxFuel = progress = 0;
	}
	public TileEntityFurnaceMF(int meta)
	{
		this();
		itemMeta = meta;
	}


	@Override
	public void onUsedWithBellows(float powerLevel) 
	{
		if(isHeater())
		{
			if(justShared > 0)
			{
				return;
			}
			justShared = 5;
			
			if(fuel > 0)
			{
				int max = (int)((float)maxHeat * 1.5F);
				if(heat < max)
				{
					heat += 50*powerLevel;
				}
				
				for(int a = 0; a < 10; a ++)
				{
					worldObj.spawnParticle("flame", xCoord+(rand.nextDouble()*0.8D)+0.1D, yCoord + 0.4D, zCoord+(rand.nextDouble()*0.8D)+0.1D, 0, 0.01, 0);
				}
			}
			pumpBellows(-1, 0, powerLevel*0.9F);
			pumpBellows(0, -1, powerLevel*0.9F);
			pumpBellows(0, 1, powerLevel*0.9F);
			pumpBellows(1, 0, powerLevel*0.9F);
		}
	}
	
	
	private void pumpBellows(int x, int z, float pump)
	{
		int share = 2;
		TileEntity tile = worldObj.getBlockTileEntity(xCoord+x, yCoord, zCoord+z);
		if(tile == null)return;
		
		if(tile instanceof TileEntityFurnaceMF)
		{
			TileEntityFurnaceMF furn = (TileEntityFurnaceMF)tile;
			if(furn.isHeater())
			furn.onUsedWithBellows(pump);
		}
	}
	
	@Override
	public void updateEntity() 
	{
		super.updateEntity();
		
		if(!worldObj.isRemote)
		{
			if(numUsers > 0 && doorAngle < 20)
			{
				doorAngle ++;
			}
			
			if(numUsers <= 0 && doorAngle > 0)
			{
				doorAngle --;
			}
			if(doorAngle < 0)doorAngle = 0;
			if(doorAngle > 20)doorAngle = 20;
		}
		
		++ticksExisted;
		if(justShared > 0)justShared --;
		if(ticksExisted % 10 == 0)
		{
			built = structureExists();
		}
		if(isHeater())
		{
			updateHeater();
		}
		else
		{
			updateFurnace();
		}
		//UNIVERSAL
		if(!worldObj.isRemote)
		{
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
			
			
			sendPacketToClients();
		}
	}
	
	private void updateFurnace() 
	{
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
		
		TileEntityFurnaceMF heater = getHeater();
		
		if(heater != null)
		{
			heat = heater.heat;
		}
		else
		{
			heat -= 4;
		}
		boolean canSmelt = false;
		boolean smelted = false;
		
		if(getSpecialResult() != null)
		{
			if(!canFitSpecialResult())
			{
				canSmelt = false;
			}
			else
			{
				canSmelt = true;
				
				if(progress >= getMaxTime())
				{
					smeltSpecial();
					smelted = true;
				}
			}
		}
		else for(int a = 0; a < 4; a ++)
		{
			if(canSmelt(inv[a], inv[a+4]))
			{
				canSmelt = true;
				
				if(progress >= getMaxTime())
				{
					smeltItem(a, a+4);
					smelted = true;
				}
			}
		}
		
		if(canSmelt)
		{
			progress += heat;
		}
		if(!canSmelt || smelted)
		{
			progress = 0;
		}
	}
	
	private boolean canFitSpecialResult() 
	{
		ItemStack spec = getSpecialResult();
		
		if(spec != null)
		{
			int spaceNeeded = spec.stackSize;
			int spaceLeft = 0;
			
			for(int a = 4; a < 8; a ++)
			{
				ItemStack item = inv[a];
				if(inv[a] == null)
				{
					spaceLeft += 64;
				}
				else
				{
					if(inv[a].isItemEqual(spec))
					{
						if(inv[a].stackSize < inv[a].getMaxStackSize())
						{
							spaceLeft += inv[a].getMaxStackSize() - inv[a].stackSize;
						}
					}
				}
			}
			return spec.stackSize <= spaceLeft;
		}
		return false;
	}
	private void smeltSpecial()
	{
		ItemStack res = getSpecialResult().copy();
		
		for(int output = 4; output < 8; output ++)
		{
			if(res.stackSize <= 0)break;
			
			if(inv[output] == null)
			{
				setInventorySlotContents(output, res);
				break;
			}
			else
			{
				if(inv[output].isItemEqual(res))
				{
					int spaceLeft = inv[output].getMaxStackSize() - inv[output].stackSize;
					
					if(res.stackSize <= spaceLeft)
					{
						inv[output].stackSize += res.stackSize;
						break;
					}
					else
					{
						inv[output].stackSize += spaceLeft;
						res.stackSize -= spaceLeft;
					}
				}
			}
		}
		for(int input = 0; input < 4; input ++)
		decrStackSize(input, 1);
	}
	
	public void puffSmoke(Random rand, World world, int x, int y, int z)
	{
		if(rand.nextInt(5) != 0)
		{
			return;
		}
		boolean chim = false;
		
		
		Block block = Block.blocksList[world.getBlockId(x, y+2, z)];
		IChimney chimney = null;
		
		if(block instanceof IChimney)
			chimney = (IChimney)block;
		
		if(chimney != null
		&& chimney.puffSmoke(world, x, y+2, z, (float)1/12, 1, 1))
		{
			chim = true;
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
					chim = true;
					break;
				}
			}	
		}
		if(!chim)
		{
			for(int a = 0; a < 5 ; a ++)
	        {
	        	world.spawnParticle("largesmoke", x+0.5F, y+2, z+0.5F, (rand.nextFloat()-0.5F)/6, 0.065F, (rand.nextFloat()-0.5F)/6);
	        }
		}
	}
	
	private int getMaxTime() 
	{
		if(getType() == 1)return 300000;//BRONZE
		if(getType() == 2)return 250000;//IRON
		if(getType() == 2)return 200000;//STEEL
		if(getType() == 4)return 150000;//DEEP IRON
		
		return 100000;
	}
	private void smeltItem(int input, int output)
	{
		ItemStack res = getResult(inv[input]).copy();
		
		if(inv[output] == null)
		{
			setInventorySlotContents(output, res);
		}
		else
		{
			if(inv[output].isItemEqual(res))
			{
				inv[output].stackSize += res.stackSize;
			}
		}
		
		decrStackSize(input, 1);
	}
	private boolean canSmelt(ItemStack in, ItemStack out)
	{
		if(isHeater()) return false;
		
		if(!built)return false;
		
		ItemStack res = getResult(in);
		if(res == null)
		{
			return false;
		}
		if(out == null)
		{
			return true;
		}
		if(out.isItemEqual(res))
		{
			int max = res.getMaxStackSize();
			if((out.stackSize + res.stackSize) > max)
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		
		return true;
	}
	
	private TileEntityFurnaceMF getHeater() 
	{
		TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
		if(tile != null && tile instanceof TileEntityFurnaceMF)
		{
			if(((TileEntityFurnaceMF)tile).isHeater())
			return (TileEntityFurnaceMF)tile;
		}
		return null;
	}
	
	private TileEntityFurnaceMF getFurnace() 
	{
		TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord);
		if(tile != null && tile instanceof TileEntityFurnaceMF)
		{
			if(!((TileEntityFurnaceMF)tile).isHeater())
			return (TileEntityFurnaceMF)tile;
		}
		return null;
	}
	
	private void updateHeater() 
	{
		if(worldObj.isRemote)return;
		
		TileEntityFurnaceMF furn = getFurnace();
		if(furn != null)
		{
			aboveType = furn.getType();
		}
		if(!built)
		{
			heat = maxHeat = fuel = maxFuel = 0;
			return;
		}
		if(heat < maxHeat)
		{
			heat ++;
		}
		if(heat > maxHeat)
		{
			heat --;
		}
		if(fuel > 0)
		{
			fuel --;
		}
		else
		{
			if(inv[0] != null && isItemFuel(inv[0]))
			{
				fuel = maxFuel = getItemBurnTime(inv[0]);
				maxHeat = getItemHeat(inv[0]);
				
				if(inv[0].getItem().hasContainerItem())
				{
					inv[0] = inv[0].getItem().getContainerItemStack(inv[0]);
				}
				else
				{
					decrStackSize(0, 1);
				}
			}
			if(fuel <= 0)
			{
				if(heat > 0)heat --;
				maxHeat = 0;
			}
		}
		
		
	}
	
	
	
	private float getItemHeat(ItemStack itemStack) 
	{
		return ItemHandler.getForgeHeat(itemStack);
	}
	public ItemStack getResult(ItemStack item)
	{
		if(item == null || item.getItem() == null)return null;
	
		if(getSpecialResult() != null)return null;
		
		ItemStack res = SpecialFurnaceRecipes.getSmeltingResult(item, getSmeltLevel());
		
		if(res == null)//If no special: try vanilla
		{
			res = FurnaceRecipes.smelting().getSmeltingResult(item);
			if(res == null || res.getItem() == null)
			{
				if(item.getItem() instanceof ItemFood)
				{
					return new ItemStack(Item.coal.itemID, 1, 1);
				}
				return null;
			}
			else if(res.getItem() instanceof ItemFood)
			{
				return new ItemStack(Item.coal.itemID, 1, 1);
			}
		}
		
		return res;
	}
	
	public ItemStack getSpecialResult()
	{
		ItemStack[] input = new ItemStack[4];
		for(int a = 0; a < 4; a ++)
		{
			input[a] = inv[a];
		}
		Alloy alloy = SpecialFurnaceRecipes.getResult(input);
		if(alloy != null)
		{
			if(alloy.getLevel() <= getSmeltLevel())
			{
				return SpecialFurnaceRecipes.getResult(input).getRecipeOutput();
			}
		}
		return null;
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

	@SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int height)
    {
        if (this.maxFuel == 0)
        {
            this.maxFuel = 200;
        }

        return this.fuel * height / this.maxFuel;
    }
	
	@SideOnly(Side.CLIENT)
    public int getHeatScaled(int height)
    {
    	if(heat <= 0)return 0;
        int size = (int)heat * height / ItemHandler.forgeMaxTemp;
        
        return (int)Math.min(size, height);
    }
	
	@SideOnly(Side.CLIENT)
    public int getItemHeatScaled(int height)
    {
    	if(maxHeat <= 0)return 0;
    	int size = (int)maxHeat * height / ItemHandler.forgeMaxTemp;
    	
    	return (int)Math.min(size, height);
    }
    
    
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public String getInvName()
	{
		int t = getType();
		String tier = "";
		
		if(isHeater())
		{
        	return StatCollector.translateToLocal("tile.furnace.name") + " " + StatCollector.translateToLocal("block.furnace.heater");
        }
        
        if(t == 1)
        {
            tier = StatCollector.translateToLocal("tier.bronze");
        }
        if(t == 2)
        {
            tier = StatCollector.translateToLocal("tier.iron");
        }
        if(t == 3)
        {
            tier = StatCollector.translateToLocal("tier.steel");
        }
        if(t == 4)
        {
            tier = StatCollector.translateToLocal("tier.iron.deep");
        }
        
        return tier + " " + StatCollector.translateToLocal("tile.furnace.name");
	}
	
	
	public int getSmeltLevel() {
		if(isHeater())
		{
			return -1;
		}
		return getType() - 1;
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
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

		justShared = nbt.getInteger("Shared");
		built = nbt.getBoolean("Built");
		
		fuel = nbt.getInteger("fuel");
		maxFuel = nbt.getInteger("MaxFuel");
		
		heat = nbt.getFloat("heat");
		maxHeat = nbt.getFloat("maxHeat");
		
		direction = nbt.getInteger("Dir");
		progress = nbt.getInteger("progress");
		aboveType = nbt.getInteger("Level");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("Shared", justShared);
		nbt.setInteger("Level", aboveType);
		nbt.setBoolean("Built", built);
		
		nbt.setInteger("fuel", fuel);
		nbt.setInteger("maxFuel", maxFuel);
		
		nbt.setFloat("heat", heat);
		nbt.setFloat("maxHeat", maxHeat);
		
		nbt.setInteger("Dir", direction);
		nbt.setInteger("progress", progress);
		
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

	public int getInventoryStackLimit() {
		return 64;
	}
	
	
	public boolean isBurning() {
		if(isHeater())
		{
			return heat > 0;
		}
		return progress > 0 && heat > 0;
	}

	public boolean isHeater()
	{
		return getType() == 0;
	}
	
	public int getItemBurnTime(ItemStack itemstack) {
		if (itemstack == null) {
			return 0;
		}
		return TileEntityFurnace.getItemBurnTime(itemstack);
	}
	
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return entityplayer.getDistanceSq((double) xCoord + 0.5D,
				(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
	}

	public void openChest()
    {
		if(numUsers == 0)
		{
			this.worldObj.playSoundEffect(xCoord+0.5D, (double)this.yCoord + 0.5D, zCoord+0.5D, data_minefantasy.sound("furnaceOpen"), 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
		++numUsers;
    }

    public void closeChest()
    {
    	--numUsers;
    	if(numUsers == 0 && doorAngle >= 15)
		{
			this.worldObj.playSoundEffect(xCoord+0.5D, (double)this.yCoord + 0.5D, zCoord+0.5D, data_minefantasy.sound("furnaceClose"), 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
    }

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}
	
	private void sendPacketToClients() {
		if (!worldObj.isRemote) {
			try 
			{
				Packet packet = PacketManagerMF.getPacketIntegerArray(this, new int[]
				{ 
						(int)fuel, progress, direction, (int)heat, isBurning() ? 1 : 0, justShared, doorAngle
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
		heat = data.readInt();
		int burn = data.readInt();
		isBurningClient = burn == 1;
		justShared = data.readInt();
		doorAngle = data.readInt();
	}
	
	
	public int getBlockMetadata()
    {
		if(worldObj == null)
			return itemMeta*2;
		
		
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
    
	@Override
	public boolean isInvNameLocalized() {
		return true;
	}
	
	
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(isHeater())
		{
			return new int[]{0};
		}
		return new int[]{0, 1, 2, 3, 4, 5, 6, 7};
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		if(isHeater())
		{
			return slot == 0 && isItemFuel(item);
		}
		return slot < 4 && getResult(item) != null;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item)
    {
		return canInsertItem(slot, item, 0);
    }
	
	public boolean isItemFuel(ItemStack item)
	{
		return this.getItemBurnTime(item) > 0;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) 
	{
		if(!isHeater())
		{
			return slot >= 4;
		}
		return item.itemID == Item.bucketEmpty.itemID;
	}
	
	
	
	public int getCookProgressScaled(int i) 
	{
		return (progress * i) / getMaxTime();
	}
	
	
	/**
	 * Checks a valid block for sides. It can be either the required block, or it's own ID: this allows 
	 * blocks to be put aside each other.
	 * 
	 * HEATER: Requires any stone block
	 * 
	 * FURNACES require metal blocks of their material
	 */
	public boolean isBlockValidForSide(int x, int y, int z)
	{
		if(worldObj == null)
		{
			return false;
		}
		
		Block block = Block.blocksList[worldObj.getBlockId(x, y, z)];
		
		if(block == null)
		{
			return false;
		}
		
		if(block.blockID == worldObj.getBlockId(xCoord, yCoord, zCoord))
		{
			return true;
		}
		return isBlockValidForTop(x, y, z);
	}
	
	
	/**
	 * Checks a valid block for sides. It must be the required block.
	 * 
	 * HEATER: Requires any stone block for bronze, stone above hardness 2.0(like slate)for iron,
	 * and stone above hardness above 5.0(like granite) for steel
	 * 
	 * FURNACES require metal blocks of their material
	 */
	public boolean isBlockValidForTop(int x, int y, int z)
	{
		if(worldObj == null)
		{
			return false;
		}
		
		Block block = Block.blocksList[worldObj.getBlockId(x, y, z)];
		
		if(block == null)
		{
			return false;
		}
		
		if(isHeater())
		{
			float hardness = getBaseFor();
			
			return isBlockCorrectBase(block, x, y, z, hardness);
		}
		
		int id = block.blockID;
		int meta = worldObj.getBlockMetadata(x, y, z);
				
		String metal = getWalls();
		return isMetal(getWalls(), id, meta);
	}
	
	
	
	private boolean isBlockCorrectBase(Block block, int x, int y, int z, float hardness) 
	{
		return block.isOpaqueCube() && worldObj.getBlockMaterial(x, y, z) == Material.rock && block.getBlockHardness(worldObj, x, y, z) >= hardness;
	}
	private String getWalls() 
	{
		switch(getType())
		{
		case 1:return "Bronze";
		case 2:return "Iron";
		case 3:return "Steel";
		case 4:return "DeepIron";
		}
		return "Bronze";
	}
	private float getBaseFor()
	{
		switch(aboveType)
		{
		case 2://IRON
			return 2.0F;//SLATE
		case 3://STEEL
			return 5.0F;//GRANITE
		case 4://DEEP IRON
			return 10.0F;//UNSURE (Obsidian is 50)
		}
		return 0.0F;
	}
	public boolean isBlockValidForSide(ForgeDirection side)
	{
		if(worldObj == null)
		{
			return false;
		}
		
		int x = xCoord + side.offsetX;
		int y = yCoord + side.offsetY;
		int z = zCoord + side.offsetZ;
		
		return isBlockValidForSide(x, y, z);
	}
	
	
	public boolean isSolid(ForgeDirection side)
	{
		if(worldObj == null)
		{
			return false;
		}
		
		int x = xCoord + side.offsetX;
		int y = yCoord + side.offsetY;
		int z = zCoord + side.offsetZ;
		
		Material mat = worldObj.getBlockMaterial(x, y, z);
		
		if(mat == null)return false;
		
		return mat.isSolid();
	}
	
	
	
	/**
	 *  Determines if the furnace is built properly
	 *  HEATER must have sides blocked by stone
	 *  FURNACES must have walls built to specifications
	 */
	private boolean structureExists()
	{
		if(worldObj == null)
		{
			return false;
		}
		
		if(isSolid(getFront()))
		{
			return false;
		}
		
		if(!isHeater() && !isBlockValidForTop(xCoord, yCoord+1, zCoord))
		{
			return false;
		}
		
		if(!isBlockValidForSide(getLeft()))
		{
			return false;
		}
		if(!isBlockValidForSide(getRight()))
		{
			return false;
		}
		if(!isBlockValidForSide(getBack()))
		{
			return false;
		}
		
		
		return true;
	}
	
	
	
	
	
	/**
	 *  Gets the direction the face is at(Opposite to the placer)
	 */
	public ForgeDirection getFront()
	{
		if(direction == 0)//SOUTH PLACE
		{
			return ForgeDirection.NORTH;
		}
		if(direction == 1)//WEST PLACE
		{
			return ForgeDirection.EAST;
		}
		if(direction == 2)//NORTH PLACE
		{
			return ForgeDirection.SOUTH;
		}
		if(direction == 3)//EAST PLACE
		{
			return ForgeDirection.WEST;
		}
		return ForgeDirection.UNKNOWN;
	}
	
	/**
	 *  Gets the direction the back is facing (Same dir as placer)
	 */
	public ForgeDirection getBack()
	{
		if(direction == 0)//SOUTH PLACE
		{
			return ForgeDirection.SOUTH;
		}
		if(direction == 1)//WEST PLACE
		{
			return ForgeDirection.WEST;
		}
		if(direction == 2)//NORTH PLACE
		{
			return ForgeDirection.NORTH;
		}
		if(direction == 3)//EAST PLACE
		{
			return ForgeDirection.EAST;
		}
		return ForgeDirection.UNKNOWN;
	}
	
	/**
	 *  Gets the direction the left is facing
	 */
	public ForgeDirection getLeft()
	{
		if(direction == 0)//SOUTH PLACE
		{
			return ForgeDirection.WEST;
		}
		if(direction == 1)//WEST PLACE
		{
			return ForgeDirection.NORTH;
		}
		if(direction == 2)//NORTH PLACE
		{
			return ForgeDirection.EAST;
		}
		if(direction == 3)//EAST PLACE
		{
			return ForgeDirection.SOUTH;
		}
		return ForgeDirection.UNKNOWN;
	}
	
	/**
	 *  Gets the direction the right is facing
	 */
	public ForgeDirection getRight()
	{
		if(direction == 0)//SOUTH PLACE
		{
			return ForgeDirection.EAST;
		}
		if(direction == 1)//WEST PLACE
		{
			return ForgeDirection.SOUTH;
		}
		if(direction == 2)//NORTH PLACE
		{
			return ForgeDirection.WEST;
		}
		if(direction == 3)//EAST PLACE
		{
			return ForgeDirection.NORTH;
		}
		return ForgeDirection.UNKNOWN;
	}
	
	public boolean isMetal(String ore, int id, int meta)
	{
		for(ItemStack block : OreDictionary.getOres("block"+ore))
		{
			if(block.itemID == id && (block.getItemDamage() == OreDictionary.WILDCARD_VALUE || block.getItemDamage() == meta))
			{
				return true;
			}
		}
		return false;
	}
	public String getTexture() 
	{
		switch(getType())
		{
		case 1://BRONZE
			return "FurnaceMF_bronze";
		case 2://IRON
			return "FurnaceMF_iron";
		case 3://STEEL
			return "FurnaceMF_steel";
		case 4://DEEP IRON
			return "FurnaceMF_deep";
		}
	    
	    return "FurnaceMF_rock";
	}
}
