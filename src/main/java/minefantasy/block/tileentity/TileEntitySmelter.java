package minefantasy.block.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import minefantasy.api.aesthetic.IChimney;
import minefantasy.api.anvil.ITongs;
import minefantasy.api.cooking.IHeatSource;
import minefantasy.api.forge.IBellowsUseable;
import minefantasy.api.forge.ItemHandler;
import minefantasy.api.forge.TongsHelper;
import minefantasy.api.refine.Alloy;
import minefantasy.api.refine.AlloyRecipes;
import minefantasy.api.refine.BloomRecipe;
import minefantasy.block.BlockChimney;
import minefantasy.block.BlockListMF;
import minefantasy.item.ItemBloom;
import minefantasy.item.ItemListMF;
import minefantasy.item.tool.ItemTongs;
import minefantasy.system.cfg;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntitySmelter extends TileEntity implements IBellowsUseable, IInventory, ISidedInventory,
		PacketUserMF
{
	private ItemStack inv[];
	public int fuel;
	public int maxFuel;
	public int progress;
	public int direction;
	public int itemMetadata;
	private int lastTemp = 0;
	private int ticksExisted;
	public int maxTime = 400;
	private int bellows;
	private static List<ItemStack>allowedSmelts = getAllowedSmelts();
	/**
	 * weather the model displays a hot ingot(CLIENT SYNC)
	 */
	public boolean showIngot;

	public TileEntitySmelter() {
		inv = new ItemStack[18];
		fuel = 0;
		maxFuel = 0;
		progress = 0;
	}
	public TileEntitySmelter(int metadata)
	{
		this();
		itemMetadata = metadata;
	}

	public int getSizeInventory() {
		return inv.length;
	}

	public ItemStack getStackInSlot(int i) {
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
				return itemstack;
			}
			ItemStack itemstack1 = inv[i].splitStack(j);
			if (inv[i].stackSize == 0)
			{
				inv[i] = null;
			}
			return itemstack1;
		} else 
		{
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inv[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public String getInvName() {
		return "Bloomery";
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

		lastTemp = nbt.getInteger("LastTemp");
		bellows = nbt.getInteger("bellows");
		maxTime = nbt.getInteger("maxTime");
		fuel = nbt.getInteger("BurnTime");
		direction = nbt.getInteger("Dir");
		progress = nbt.getInteger("CookTime");
		maxFuel = getItemBurnTime(inv[0]);
	}

	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setInteger("LastTemp", lastTemp);
		nbt.setInteger("BurnTime", fuel);
		nbt.setInteger("Dir", direction);
		nbt.setInteger("bellows", bellows);
		nbt.setInteger("CookTime", progress);
		nbt.setInteger("maxTime", maxTime);
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

	public int getCookProgressScaled(int i) {
		return (progress * i) / maxTime;
	}

	public int getBurnTimeRemainingScaled(int i) {
		if (maxFuel == 0) {
			maxFuel = maxTime;
		}
		return (fuel * i) / maxFuel;
	}

	public boolean isBurning() {
		if(getTier() == 0)
		{
			return fuel > 0;
		}
		if(worldObj != null && worldObj.isRemote)
		{
			return showIngot;
		}
		return getHeatSource() > 0 && getRecipe() != null;
	}

	public void updateEntity() 
	{
		if(!worldObj.isRemote)
		showIngot = getTier() == 0 ? hasSmelted() : isBurning();
		
		if(bellows > 0)bellows --;
		if(getTier() >= 1)
		{
			int time = 400;
			for(int a = 1; a < getSizeInventory()-1; a ++)
			{
				if(inv[a] != null)
				{
					time += 50;
				}
			}
			if(!worldObj.isRemote)
			maxTime = time;
		}
		else
		{
			maxTime = BloomRecipe.getTime(inv[1]);
		}
		boolean flag = fuel > 0;
		boolean flag1 = false;
		boolean flag2 = isBurning();
		if (getTier() == 0 && fuel > 0) 
		{
				fuel -= 2;
		}
		if (!worldObj.isRemote) 
		{
			++ticksExisted;
			if(getTier() > 0 && ticksExisted % 20 == 0)
			{
				this.lastTemp = getTemperature();
			}
			if (getTier() == 0 && fuel <= 0 && canSmelt()) 
			{
				maxFuel = fuel = getItemBurnTime(inv[0]);
				if (fuel > 0) 
				{
					flag1 = true;
					if (inv[0] != null)
					{
						if(inv[0].getItem().hasContainerItem())
						{
							inv[0] = inv[0].getItem().getContainerItemStack(inv[0]);
						}
						else
						{
							inv[0].stackSize--;
							if (inv[0].stackSize == 0) 
							{
								inv[0] = null;
							}
						}
					}
				}
			}
			if (isBurning() && canSmelt() && (getTier() > 0 || fuel > 0)) {
				progress += getHeatSource();
				if (progress >= maxTime) {
					progress = 0;
					smeltItem();
					flag1 = true;
				}
			} else {
				progress = 0;
			}
		}
		if (flag1) {
			onInventoryChanged();
		}
		if (this.isBurning())
		{
			int x = (int) xCoord;
			int y = (int) yCoord;
			int z = (int) zCoord;
			Random rand = new Random();
			if (!worldObj.isBlockSolidOnSide(x, y + 1, z, ForgeDirection.DOWN))
				worldObj.spawnParticle("largesmoke", x + 0.5F, y, z + 0.5F, 0,
						0.065F, 0);
		}
		if(!worldObj.isRemote)
		{
			int off = getOffMetadata();
			int on = getOnMetadata();
			boolean update = false;
			if(isBurning() && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != on)
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, on, 3);
				worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);
				update=true;
			}
			if(!isBurning() && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) != off)
			{
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, off, 3);
				worldObj.updateAllLightTypes(xCoord, yCoord, zCoord);
				update=true;
			}
			if(getTier() > 0 && update)
			{
				this.lastTemp = getTemperature();
			}
		}
		if(this.isBurning())
		{
			puffSmoke(new Random(), worldObj, xCoord, yCoord, zCoord);
		}
		sendPacketToClients();
	}
	
	public void puffSmoke(Random rand, World world, int x, int y, int z)
	{
		if(getTier() >= 1)
		{
			if(rand.nextInt(10) == 0)
	        {
	        	world.spawnParticle("smoke", x+0.5F, y+1, z+0.5F, (rand.nextFloat()-0.5F)/6, 0.065F, (rand.nextFloat()-0.5F)/6);
	        }
			return;
		}
		Block block = Block.blocksList[world.getBlockId(x, y+1, z)];
		IChimney chimney = null;
		
		if(block instanceof IChimney)
			chimney = (IChimney)block;
		
		if(chimney == null
		|| (chimney != null && !chimney.puffSmoke(world, x, y+1, z, (float)1/6, 1, 1)))
		{
				for(int a = 0; a < 5 ; a ++)
		        {
		        	world.spawnParticle("largesmoke", x+0.5F, y+1, z+0.5F, (rand.nextFloat()-0.5F)/6, 0.065F, (rand.nextFloat()-0.5F)/6);
		        }
		}
	}

	private boolean canSmelt() {
		if(getTier() >= 1 && worldObj != null)
		{
			if(getHeatSource() <= 0)return false;
		}
		ItemStack result = getRecipe();
		if (result == null)
			return false;
		if (inv[getOutSlot()] == null)
			return true;
		if (inv[getOutSlot()] != null
				&& inv[getOutSlot()].isItemEqual(result)
				&& inv[getOutSlot()].stackSize < (inv[getOutSlot()]
						.getMaxStackSize() - (result.stackSize - 1)))
			return true;
		return false;
	}
	
	private int getHeatSource() 
	{
		if(getTier() == 0)
		{
			if(bellows > 0)
			{
				return 3;
			}
			return 1;
		}
		
		return (int)Math.floor(lastTemp/250F);
	}
	public int getTemperature()
	{
		if(worldObj == null)
		{
			return 0;
		}
		Material mat = worldObj.getBlockMaterial(xCoord, yCoord-1, zCoord);
		if(mat != null)
		{
			if(mat == Material.fire)
			{
				return 200;
			}
			if(mat == Material.lava)
			{
				return 1000;
			}
		}
		TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
		
		if(tile != null)
		{
			if(tile instanceof IHeatSource)
			{
				return ((IHeatSource)tile).getHeat();
			}
		}
		return 0;
	}
	/**
	 * @return If the output is occupied(SERVER) and if told by packet sync(CLIENT)
	 */
	public boolean hasSmelted()
	{
		if(worldObj == null)return false;
		
		if(worldObj.isRemote)
		{
			return showIngot;
		}
		return inv[getOutSlot()] != null;
	}

	public int getOutSlot()
	{
		return getSizeInventory()-1;
	}
	public void smeltItem() {
		if (!canSmelt()) 
		{
			return;
		}
		
		ItemStack itemstack = getRecipe();
		
		if (inv[getOutSlot()] == null) 
		{
			inv[getOutSlot()] = itemstack.copy();
		} 
		
		else if (inv[getOutSlot()].itemID == itemstack.itemID) 
		{
			inv[getOutSlot()].stackSize += itemstack.stackSize;
		}
		
		for(int a = 1; a < (getSizeInventory()-1); a ++)
		{
			if (inv[a] != null) 
			{
				inv[a].stackSize--;
				if (inv[a].stackSize <= 0) {
					inv[a] = null;
				}
			}
		}
	}

	private boolean isWrought(ItemStack itemstack) 
	{
		if(itemstack == null)
		{
			return false;
		}
		return itemstack.isItemEqual(ItemListMF.component(ItemListMF.ingotWroughtIron));
	}
	public int getItemBurnTime(ItemStack itemstack) {
		if (itemstack == null) {
			return 0;
		}
		if(getTier() == 0)
		{
			return (int)((float)TileEntityFurnace.getItemBurnTime(itemstack));
		}
		return TileEntityFurnace.getItemBurnTime(itemstack)/4;
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}
		return entityplayer.getDistanceSq((double) xCoord + 0.5D,
				(double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
	}

	public void openChest() {
	}

	public void closeChest() {
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	private ItemStack getRecipe() {
		if(getTier() == 0)
		{
			if(getResult(inv[1]) != null)
			{
				return ItemHandler.isCarbon(inv[2]) ? getResult(inv[1]) : null;
			}
			else
				return null;
		}
		
		if(getTier() == 1)
		{
			ItemStack[] input = new ItemStack[getSizeInventory()-2];
			for(int a = 1; a < 10; a ++)
			{
				input[a-1] = inv[a];
			}
			Alloy alloy = AlloyRecipes.getResult(input);
			if(alloy != null)
			{
				if(alloy.getLevel() <= 0)
				{
					return AlloyRecipes.getResult(input).getRecipeOutput();
				}
			}
		}
		
		if(getTier() == 2)
		{
			ItemStack[] input = new ItemStack[getSizeInventory()-2];
			for(int a = 1; a < 17; a ++)
			{
				input[a-1] = inv[a];
			}
			Alloy alloy = AlloyRecipes.getResult(input);
			if(alloy != null)
			{
				if(alloy.getLevel() <= 1)
				{
					return AlloyRecipes.getResult(input).getRecipeOutput();
				}
			}
		}
		return null;
	}

	private ItemStack getResult(ItemStack itemStack) {
		if(BloomRecipe.getResult(itemStack) != null)
		{
			return BloomRecipe.getResult(itemStack);
		}
		ItemStack furnaceRec = FurnaceRecipes.smelting().getSmeltingResult(itemStack);
		
		if(furnaceRec != null && this.canAcceptOut(furnaceRec))
		{
			if(cfg.easyBlooms)
			{
				return furnaceRec;
			}
			return ItemBloom.createBloom(furnaceRec);
		}
		
		return null;
	}
	private boolean canAcceptOut(ItemStack result) {
		if(result == null)
		{
			return false;
		}
		ItemStack result2 = result;
		
		if(result.itemID == ItemListMF.bloom.itemID)
		{
			result2 = ItemBloom.getItem(result);
		}
		
		for(ItemStack compare : allowedSmelts)
		{
			if(compare.isItemEqual(result2))
			{
				return true;
			}
			if(compare.itemID == result2.itemID && compare.getItemDamage() == OreDictionary.WILDCARD_VALUE)
			{
				return true;
			}
		}
		return false;
	}
	private void sendPacketToClients() {
		if (!worldObj.isRemote) {
			try {
				boolean display = hasSmelted();
				if(getTier() > 0)
				{
					display = isBurning();
				}
				Packet packet = PacketManagerMF.getPacketIntegerArray(this,
						new int[] { fuel, maxFuel,
								progress, direction, showIngot ? 1 : 0, maxTime, lastTemp});
				FMLCommonHandler.instance().getMinecraftServerInstance()
						.getConfigurationManager()
						.sendPacketToAllPlayers(packet);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	@Deprecated
	private void sendPacketToServer() {

		if (worldObj.isRemote) {
			try {
				Packet packet = PacketManagerMF.getPacketIntegerArray(this,
						new int[] { fuel, maxFuel,
								progress, direction });
				PacketDispatcher.sendPacketToServer(packet);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void recievePacket(ByteArrayDataInput data) {
		fuel = data.readInt();
		maxFuel = data.readInt();
		progress = data.readInt();
		direction = data.readInt();
		int smelt = data.readInt();
		showIngot = smelt == 1;
		maxTime = data.readInt();
		lastTemp = data.readInt();
	}
	public int getBlockMetadata()
    {
		if(worldObj == null)
			return itemMetadata*2;
		
		
        if (this.blockMetadata == -1)
        {
            this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        }

        return this.blockMetadata;
    }
	
	public int getTier()
	{
		int meta = getBlockMetadata();
		return (int)Math.floor(meta/2);
	}
	
	private int getOnMetadata()
	{
		return getTier()*2 + 1;
	}
	
	private int getOffMetadata()
	{
		return getTier()*2;
	}
    
	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	public boolean canPutInFuel(ItemStack itemstack) {
		if(itemstack == null)return false;
		
		if(this.getItemBurnTime(itemstack) <= 0)
			return false;
		
		ItemStack fs = getStackInSlot(1);
		if(fs != null)
		{
			if(!fs.isItemEqual(itemstack))
				return false;
			else
			{
				if(fs.stackSize >= fs.getMaxStackSize())return false;
			}
		}
		return true;
	}
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(getTier() == 0)
		{
			if(side == 1)//TOP
			{
				return new int[]{2, 1};
			}
			if(side == 0)//BOTTOM
			{
				return new int[]{getOutSlot(), 0};
			}
			return new int[]{0};
		}
		return new int[]{0, getOutSlot()};
	}
	
	private int getFrontSide()
	{
		switch(direction)
		{
		case 0:
			return 2;
		case 1:
			return 3;
		case 2:
			return 4;
		case 3:
			return 5;
		}
		return 2;
	}
	
	private int getLeftSide()
	{
		switch(direction)
		{
		case 0:
			return 5;
		case 1:
			return 2;
		case 2:
			return 3;
		case 3:
			return 4;
		}
		return 2;
	}
	
	private int getRightSide()
	{
		switch(direction)
		{
		case 0:
			return 4;
		case 1:
			return 5;
		case 2:
			return 2;
		case 3:
			return 3;
		}
		return 2;
	}
	
	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		if(j == 0)
		{
			return false;
		}
		if(this.getTier() == 0)
		{
			return this.isItemValidForSlot(i, itemstack);
		}
		if(i == 0)
		{
			return this.canPutInFuel(itemstack);
		}
		return false;
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item)
    {
		if(slot == 0)
		{
			return isItemFuel(item);
		}
		if(slot == 1)
		{
			return true;
		}
		if(slot == 2)
		{
			return ItemHandler.isCarbon(item);
		}
		
		return false;
    }
	
	private boolean isItemFuel(ItemStack item) {
		return this.getItemBurnTime(item) > 0;
	}
	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		if(this.getTier() == 0)
		{
			if(i == 0)
			{
				if(!isItemFuel(itemstack))
				{
					return true;
				}
			}
			if(i == 3)
			{
				return true;
			}
		}
		return i == getOutSlot() && !isItemFuel(itemstack);
	}
	public boolean goesInCarbon(ItemStack itemSlot) {
		if(ItemHandler.isCarbon(itemSlot))
		{
			if(getItemBurnTime(itemSlot)> 0)
			{
				if(getResult(inv[1]) == null)
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
	public boolean goesInFuel(ItemStack itemSlot) {
		if(ItemHandler.isCarbon(itemSlot))
		{
			if(getResult(inv[1]) != null)
			{
				return false;
			}
		}
		return getItemBurnTime(itemSlot)> 0;
	}
	
	private static List<ItemStack> getAllowedSmelts() {
		List list = new ArrayList();
		
		list.add(new ItemStack(Item.ingotGold));
		
		list = addOresIng("Copper", list);
		list = addOresIng("Tin", list);
		list = addOresIng("Bronze", list);
		list = addOresIng("Iron", list);
		list = addOresIng("Gold", list);
		list = addOresIng("Silver", list);
		list = addOresIng("RefinedIron", list);
		list = addOresIng("Zinc", list);
		list = addOresIng("Nickel", list);
		list = addOresIng("Tungsten", list);
		list = addOresIng("Bismuth", list);
		list = addOresIng("Lead", list);
		list = addOresIng("Steel", list);
		list = addOresIng("Mithril", list);
		
		return list;
	}
	private static List addOresIng(String ores, List list) {
		return addOres("ingot"+ores, list);
	}
	private static List addOres(String ores, List list) {
		for(ItemStack ore: OreDictionary.getOres(ores))
		{
			list.add(ore);
		}
		return list;
	}
	
	public boolean tryTakeItem(EntityPlayer player)
	{
		if(getTier() != 0)return false;
		
		if(player == null)
		{
			return false;
		}
		
		if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ITongs)
		{
			ItemStack tongs = player.getHeldItem();
			if(TongsHelper.getHeldItem(tongs) == null)
			{
				int s = getOutSlot();
				ItemStack slot = inv[s];
				if(slot != null)
				{
					if(TongsHelper.trySetHeldItem(tongs, slot))
					{
						if(!player.worldObj.isRemote)
						{
							System.out.println("Pickup");
						}
						decrStackSize(s, 1);
						return true;
					}
				}
			}
		}
		return false;
	}
	public ItemStack getResultSlot() {
		return inv[getOutSlot()];
	}
	@Override
	public void onUsedWithBellows(float powerLevel)
	{
		bellows = (int)(20F*powerLevel);
	}
	
	public int getHeatScaled(int height)
    {
    	if(lastTemp <= 0)return 0;
        return lastTemp * height / ItemHandler.forgeMaxTemp;
    }
	
	public boolean willShowBase()
	{
		if(worldObj == null)
		{
			return false;
		}
		if(this.getTier() == 0)
		{
			return false;
		}
		
		if(worldObj.getBlockMaterial(xCoord, yCoord-1, zCoord) != null && worldObj.getBlockMaterial(xCoord, yCoord-1, zCoord) == Material.fire)
    	{
    		return true;
    	}
		if(worldObj.getBlockMaterial(xCoord, yCoord-1, zCoord) != null && worldObj.getBlockMaterial(xCoord, yCoord-1, zCoord) == Material.lava)
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
}
