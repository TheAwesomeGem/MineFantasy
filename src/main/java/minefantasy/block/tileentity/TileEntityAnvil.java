package minefantasy.block.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import minefantasy.MineFantasyBase;
import minefantasy.api.IMFCrafter;
import minefantasy.api.anvil.AnvilProps;
import minefantasy.api.anvil.CraftingManagerAnvil;
import minefantasy.api.anvil.IAnvil;
import minefantasy.api.anvil.ICustomRepair;
import minefantasy.api.anvil.ItemRepairHammer;
import minefantasy.api.forge.HeatableItem;
import minefantasy.api.hound.IHoundArmour;
import minefantasy.api.hound.ItemHoundFeedbag;
import minefantasy.api.refine.CrushRecipes;
import minefantasy.block.BlockListMF;
import minefantasy.block.special.BlockAnvilMF;
import minefantasy.block.special.BlockClickHelper;
import minefantasy.block.special.BlockFurnaceMF;
import minefantasy.block.special.ItemBlockFurnaceMF;
import minefantasy.container.ContainerAnvil;
import minefantasy.item.ItemBloom;
import minefantasy.item.ItemHotItem;
import minefantasy.item.ItemListMF;
import minefantasy.item.tool.ItemHammer;
import minefantasy.system.StatListMF;
import minefantasy.system.cfg;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class TileEntityAnvil extends TileEntity implements IInventory, IAnvil, PacketUserMF, IMFCrafter {

	public static int maxWidth = AnvilProps.globalWidth = 8;
	public static int maxHeight = AnvilProps.globalHeight = 5;

	@SideOnly(Side.CLIENT)
	private ItemStack tempResult;
	private EntityPlayer smith;
	private int anvilLevel = -1;
	private boolean hotOut;
	private boolean slagOut = false;
	private int forgeMultiMax = 150;
	public int quality = 100;
	private int peak = 80;
	private int ticks;
    private ItemStack[] items;
    public int forgeTime;
    private int cool;
    public int direction;
    private Random rand = new Random();
    private int maxForgeTime = 200;
    private int maxForgeMultiplier = 2;
    private int forgeMulti;
    private int craftLevel;
    public int itemMetadata;
    
    @SideOnly(Side.CLIENT)
    public int gridTime;
    
	private Object recipe;
	private int canCraft;
	private int canFit;

    public TileEntityAnvil() {
        items = new ItemStack[(maxWidth*maxHeight) + 1];
    }
    public TileEntityAnvil(int metadata)
    {
    	this();
    	itemMetadata = metadata;
    }
    
    public int[] gridSize()
    {
    	if(isBig())
    	{
    		return new int[]{8, 5};
    	}
    	return new int[]{5, 4};
    }

    @Override
    public int getSizeInventory() {
        return items.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return items[slot];
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) 
    {
        super.readFromNBT(tag);
        slagOut = tag.getBoolean("Slag");
        
        NBTTagList var2 = tag.getTagList("Items");
        this.items = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3) 
        {
            NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.items.length)
            {
                this.items[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
        
        direction = tag.getInteger("Dir");
        if(tag.hasKey("quality"))
        {
        	quality = tag.getInteger("quality");
        }
        
        this.forgeTime = tag.getInteger("Forge");
        this.maxForgeTime = tag.getInteger("ForgeMax");
        if(tag.hasKey("CustomName"))
        {
        	customName = tag.getString("CustomName");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound tag) 
    {
        super.writeToNBT(tag);
        tag.setBoolean("Slag", slagOut);
        NBTTagList var2 = new NBTTagList();
        
        tag.setInteger("Dir", direction);
        tag.setInteger("quality", quality);
        
        
        for (int var3 = 0; var3 < this.items.length; ++var3)
        {
            if (this.items[var3] != null) 
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                this.items[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        tag.setTag("Items", var2);
        tag.setInteger("Forge", this.forgeTime);
        tag.setInteger("ForgeMax", this.maxForgeTime);
        if(customName != null)
        {
        	tag.setString("CustomName", customName);
        }
    }

    public int getForgeProgressScaled(int width)
    {
        return this.forgeTime * width / (maxForgeTime * maxForgeMultiplier);
    }
    
    public int getQualityPosScaled(int x, int width) {
        return x * width / 100;
    }
    
    public boolean repair(float effectivness, boolean enchant, float max, boolean reset)
    {
    	if(worldObj.isRemote)return false;
    	
    	float timing = forgeMulti/150;
    	effectivness *= timing;
    	Random rand = new Random();
    	for(ItemStack repair : this.items)
    	{
    		if(repair != null)
    		{
    			int min = (int)(repair.getMaxDamage() * (1-max));
    			if(repair.itemID == Block.anvil.blockID)
    			{
    				if(repair.getItemDamage() > 0 && repair.stackSize == 1)
    				{
    					int lvl = (int)((rand.nextInt(40)+20)*effectivness);
    					if(rand.nextInt(200) < lvl)
    					{
    						int d = repair.getItemDamage();
    						repair.setItemDamage(d - 1);
    					}
    					if(reset)
    					forgeMulti = 1;
    					if(MineFantasyBase.isDebug())
	    				{
    						if(!worldObj.isRemote)
    						{
    							System.out.println("Repair: " + lvl);
    						}
	    				}
    					return true;
    				}
    			}
    			if(repair.stackSize == 1 && !(repair.getItem() instanceof ItemHammer) && !(repair.getItem() instanceof ItemRepairHammer))
    			{
	    			boolean canRepair = enchant || !needsOrnate(repair);
	    			int lvl = (int)((rand.nextInt(40)+20)*effectivness);
	    			if(repair.getItem() instanceof ItemArmor || repair.getItem() instanceof IHoundArmour)
	    			{
	    				lvl = (lvl/5) +1;
	    			}
	    			if(repair.getItem() instanceof ICustomRepair)
	    			{
	    				lvl = (int)((float)lvl * ((ICustomRepair)repair.getItem()).getRepairValue());
	    			}
	    			if(repair.getItemDamage() > min && repair.isItemDamaged() && repair.isItemStackDamageable() && canRepair && canRepair(repair))
	    			{
	    				repair.setItemDamage(repair.getItemDamage() - lvl);
	    				if(repair.getItemDamage() < 0)repair.setItemDamage(0);
	    				if(reset)
	    				forgeMulti = 1;
	    				if(MineFantasyBase.isDebug())
	    				{
	    					if(!worldObj.isRemote)
	    					{
	    						System.out.println("Repair: " + lvl);
	    						System.out.println("Durability: " + (repair.getMaxDamage() - repair.getItemDamage()) + " / " + repair.getMaxDamage() + " (M: " + (repair.getMaxDamage() - min) + ")");
	    					}
	    				}
	    				return true;
	    			}
    			}
    		}
    	}
    	if(reset)
    	forgeMulti = 1;
    	return false;
    }

    private boolean canRepair(ItemStack repair) 
    {
    	if(repair.getItem() instanceof ItemHoundFeedbag)
    	{
    		return false;
    	}
		return true;
	}
	private boolean needsOrnate(ItemStack repair) {
    	if(repair == null)
    		return false;
    	
    	if(repair.isItemEnchanted())
    		return true;
    	
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public int getRepairHammerMetre(int width)
    {
		return (int)((float)width * getRepairHammer());
    }

	public boolean hitWithHammer(EntityPlayer player, int l, float power, int hitStr) 
	{
		onInventoryChanged();
		if(!cfg.advancedAnvil)
		{
			hitStr = 1;
		}
		
    	if(!canCraft())return false;
    	if(worldObj.isRemote)return false;
    	
        if (canPlayerCraft(player) && this.craftLevel <= l && canFitResult() && this.anvilLevel <= this.getAnvilTier()) {
        	
        	if(player.capabilities.isCreativeMode)
        	{
        		quality = 100;
        		smith = player;
        		craft();
            	return true;
        	}
        	if(cfg.advancedAnvil)
        	{
	        	if(hitStr == 0)
	        	{
	        		quality += forgeMulti/16;
	        		if(quality > 100)
	        			quality = 100;
	        	}
	        	
	        	if(hitStr == 1)
	        	{
	        		int m = (int)Math.max(30, forgeMulti);
	        		quality -= m/16;
	        		if(quality <= 0)
	        		{
	        			quality = 100;
	        			
	        			if(canCraft())
	        			{
	        				ItemStack res = getResult();
	        				worldObj.playSound(xCoord+0.5, yCoord, zCoord+0.5, "random.break", 1.0F, 1.0F, true);
	        				worldObj.playSound(xCoord+0.5, yCoord, zCoord+0.5, "random.break", 1.0F, 1.0F, false);
	        				
	        				if(smith != null)
	        				{
	        					smith.renderBrokenItemStack(res);
	        					
	        				}
        					decreseAll();
	        			}
	        		}
	        	}
        	}
        	
        	if(cfg.advancedAnvil)
        	{
        		power ++;
        	}
    		if(hitStr == 0)
    		{
    			power /= 4;
    		}
    		
    		this.forgeTime += forgeMulti*power;
            smith = player;
            forgeMulti = 1;
            cool = 5;
            //sendPacketToServer();
            
            
            return true;
        }
        return false;
    }
	
	public int getQualityPeak()
	{
		return peak;
	}

    private boolean canPlayerCraft(EntityPlayer player) {
		return true;
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
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.items[slot] != null) {
            ItemStack var2 = this.items[slot];
            this.items[slot] = null;
            return var2;
        } else {
            return null;
        }
    }

    @Override
    public void updateEntity() 
    {
        super.updateEntity();
        
        if(worldObj.isRemote)
        {
        	if(gridTime > 0)gridTime --; //TileEntityAnvilRenderer
        }
        if(!worldObj.isRemote)
        {
        	canCraft = canCraft() ? 1 : 0;
        	canFit = canFitResult() ? 1 : 0;
	        if(ticks % 20 == 0)
	    	{
	        	updateInv();
	    	}
	        
	        ticks ++;
	        cool --;
	        if (forgeTime >= (maxForgeTime * maxForgeMultiplier)) 
	        {
	            forgeTime = 0;
	            craft();
	            quality = 100;
	        }
	        
	        if (forgeMulti < forgeMultiMax && cool <= 0) {
	            forgeMulti += 10;
	        }
        
        	peak = 80 + (worldObj.difficultySetting * 5);
        	if(!canCraft())
        	{
        		quality = 100;
        	}
        	sendPacketToClients();
        }
    }
    
    private void updateInv()
    {
    	if(!worldObj.isRemote)
    	{
    		recipe = getResult();
    		syncItems();
    		
    		if (!canCraft() && forgeTime > 0) 
            {
                forgeTime = 0;
                quality = 100;
            }
    	}
    }

	@Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) 
	{
		onInventoryChanged();
        this.items[slot] = itemstack;

        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
        {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    private void sendPacketToClients() 
    {
		if(!worldObj.isRemote)
		{
			Packet packet = PacketManagerMF.getPacketIntegerArray(this, new int[]{0, forgeTime, maxForgeTime, forgeMulti, direction, quality, peak, canCraft, canFit});
			try
			{
				FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
			} catch(NullPointerException e)
			{
				System.out.println("MineFantasy: Client connection lost");
			}
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
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    private void craft() {
    	
    	if(getResult() == null)
    		return;
    	
        ItemStack result;
	        result = getResult().copy();
	     if(result != null)
	     {
	    	 if(smith != null)
	    	 {
	    		 smith.addStat(StatListMF.forged, 1);
	    		 GameRegistry.onItemCrafted(smith, result, this);
	    		 
	    		 result = modifyResult(result);
	    		 
	    		if(result.getItem() instanceof ItemPickaxe)
	    		{
	    			smith.addStat(AchievementList.buildBetterPickaxe, 1);
	    		}
	    		if(result.getItem() instanceof ItemHoe)
	    		{
	    			smith.addStat(AchievementList.buildHoe, 1);
	    		}
	    		if(result.getItem() instanceof ItemSword)
	    		{
	    			smith.addStat(AchievementList.buildSword, 1);
	    		}
	    		if(result.getItem() instanceof ItemBlockFurnaceMF)
	    		{
	    			smith.addStat(AchievementList.buildFurnace, 1);
	    		}
	    		
	    		for(ItemStack copper : OreDictionary.getOres("ingotCopper"))
	    		if(result.itemID == copper.itemID)
	    		{
	    			if(copper.getItemDamage() == OreDictionary.WILDCARD_VALUE || copper.getItemDamage() == result.getItemDamage())
	    			smith.addStat(StatListMF.copper, 1);
	    		}
	    		for(ItemStack tin : OreDictionary.getOres("ingotTin"))
	    		if(result.itemID == tin.itemID)
	    		{
	    			if(tin.getItemDamage() == OreDictionary.WILDCARD_VALUE || tin.getItemDamage() == result.getItemDamage())
	    			smith.addStat(StatListMF.tin, 1);
	    		}
	    	 }
	    	 if(slagOut)
	    	 {
	    		 if(worldObj != null && !worldObj.isRemote)
	    		 {
	    			 for(int a = 0; a < rand.nextInt(3) + 1; a ++)
	    			 {
	    				 EntityItem item = new EntityItem(worldObj, xCoord+0.5D, yCoord+1.0D, zCoord+0.5D, new ItemStack(ItemListMF.misc, 1, ItemListMF.slagSmall));
	    				 worldObj.spawnEntityInWorld(item);
	    			 }
	    		 }
	    	 }
	    	 result = damageResult(result);
	    	 if(heatOutput(result))
	    	 {
	    		 ItemStack out = result.copy();
	    		 result = ItemHotItem.createHotItem(out, false);
	    		 result.stackSize = out.stackSize;
	    	 }
	    	 
	        if (items[getGridSize()] == null) {
	            decreseAll();
	            this.setInventorySlotContents(getGridSize(), result);
	        } else if (items[getGridSize()] != null && items[getGridSize()].isItemEqual(result)) {
	        	int c = (items[getGridSize()].stackSize + result.stackSize);
	        	if(c <= items[getGridSize()].getMaxStackSize())
	        	{
	        		decreseAll();
	            	items[getGridSize()].stackSize+= result.stackSize;
	        	}
	        }
    	}
	     onInventoryChanged();
	     quality = 100;
    }

    private ItemStack damageResult(ItemStack result) {
    	if(worldObj.isRemote)
    	{
    		return result;
    	}
    	int peak = this.getQualityPeak();
    	
    	if(cfg.advancedAnvil && result.isItemStackDamageable())
    	{
    		int max = result.getMaxDamage();
    		float lvl = peak - quality;
    		if(lvl < 0) lvl = 0;
    		
    		int damage = (int)((float)max * (float)(lvl / peak));
    		result.setItemDamage(damage);
    	}
		return result;
	}
	private boolean heatOutput(ItemStack result) {
    	if(MineFantasyBase.isDebug())
    	{
    		return false;
    	}
    	if(result == null)return false;
    	if(result.getItem() instanceof ItemBlock)return false;
    	
		return hotOut;
	}

	private ItemStack modifyResult(ItemStack result) {
    	Random rand = new Random();
    	
    	if(EnchantmentHelper.getFortuneModifier(smith) <= 0)
    		return result;
    	
    	
    	int fortune = rand.nextInt(EnchantmentHelper.getFortuneModifier(smith)+1);
    	
    	int stack = fortune * ((int)64 / result.stackSize)/8;
    	
    	int ss = result.stackSize + stack > 64 ? 64 : result.stackSize + stack;
    	
    	
    	int add = (result.getMaxDamage()/20)*fortune;
    	
    	if(result.isStackable())
    		result.stackSize = ss;
    	
    	if(result.isItemStackDamageable())
    		result.setItemDamage(-add);
    	return result;
	}

	public int getGridSize() {
		return gridSize()[0] * gridSize()[1];
	}

	public boolean canCraft() 
	{
		if(worldObj.isRemote)
		{
			return canCraft == 1;
		}
        return recipe != null;
    }

    public ItemStack getResult()
    {
    	if(worldObj.isRemote)
    	{
    		return null;
    	}
    	
    	if(ticks <= 1)
    		return null;
    	
    	ContainerAnvil container = new ContainerAnvil(this);
    	InventoryCrafting craft = new InventoryCrafting(container, gridSize()[0], gridSize()[1]);
    	for(int a = 0 ; a < getGridSize() ; a ++)
    	{
    		craft.setInventorySlotContents(a, items[a]);
    	}
    	if(getCrushRecipe() != null)
    	{
    		this.craftLevel = 0;
    		this.hotOut = false;
    		this.anvilLevel = -1;
    		this.maxForgeTime = 200;
    		return getCrushRecipe();
    	}
    	
    	return CraftingManagerAnvil.getInstance().findMatchingRecipe(this, craft);
    }
	private ItemStack getCrushRecipe() {
		ItemStack in = null;
		for(int a = 0; a < getGridSize(); a ++)
		{
			if(items[a] != null)
			{
				if(in != null)
				{
					return null;
				}
				in = items[a];
			}
		}
		if(HeatableItem.canHeatItem(in))
		{
			return null;
		}
		if(in != null && in.itemID == ItemListMF.hotItem.itemID)
		{
			if(!TileEntityForge.isProperlyHeated(in))
			{
				return null;
			}
			in = ItemHotItem.getItem(in);
		}
		if(in != null && in.getItem() instanceof ItemBloom)
		{
			hotOut = true;
			slagOut = true;
		}
		else
		{
			slagOut = false;
		}
		return CrushRecipes.getResult(in);
	}
	
	private void decreseAll() 
	{
		if(smith != null)
		{
			if(smith.capabilities.isCreativeMode && MineFantasyBase.isDebug())
			{
				return;
			}
		}
		
    	int fortune = 0;
    	Random rand = new Random();
    	if(smith != null)fortune = EnchantmentHelper.getFortuneModifier(smith);
    	
        for (int s = 0; s < getGridSize(); s++) 
        {
        	
        	if(rand.nextInt(25) >= fortune)
            this.decrStackSize(s, 1);
        }
    }

    private boolean isSilver(ItemStack itemStack)
    {
    	ArrayList<ItemStack> list = OreDictionary.getOres("ingotSilver");
    	for(int i = 0; i < list.size(); i ++)
    	{
    		ItemStack ore = list.get(i);
    		if(ore.isItemEqual(itemStack))
    			return true;
    	}
        return false;
    }

	@Override
	public void setForgeTime(int i) {
		maxForgeTime = i;
	}

	@Override
	public void setHammerUsed(int i) {
		craftLevel = i;
	}
	
	@Override
	public void setHotOutput(boolean i) {
		hotOut = i;
	}

	public int getCraftLevel() {
		return craftLevel;
	}

	@Override
	public void recievePacket(ByteArrayDataInput data) 
	{
		int id = data.readInt();
		
		if(id == 0)
		{
			forgeTime = data.readInt();
			maxForgeTime = data.readInt();
			forgeMulti = data.readInt();
			direction = data.readInt();
			quality = data.readInt();
			peak = data.readInt();
			canCraft = data.readInt();
			canFit = data.readInt();
			return;
		}
		if(id == 1)
		{
			int p = data.readInt();
			int i = data.readInt();
			int slot = data.readInt();
			
			Entity e = worldObj.getEntityByID(p);
			
			if(e != null && e instanceof EntityPlayer)
			{
				BlockAnvilMF.useInventory(worldObj, xCoord, yCoord, zCoord, this, (EntityPlayer)e, i, slot);
			}
			return;
		}
		
	}
	public boolean canFitResult()
	{
		if(worldObj.isRemote)
		{
			return canFit == 1;
		}
		
		ItemStack slot = items[getGridSize()];
		ItemStack result = getResult();
		if(slot == null)return true;
		if(result == null)return false;
		if(!result.isItemEqual(slot))return false;
		int ss = slot.stackSize;
		int rs = result.stackSize;
		int sm = slot.getMaxStackSize() - ss;
		return rs <= sm;
	}

	public int getBlockMetadata()
    {
		if(worldObj == null)
			return itemMetadata;
		
		
        return super.getBlockMetadata();
    }

	@Override
	public void setRequiredAnvil(int i) {
		anvilLevel = i;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
	public String getGui()
	{
		return isBig() ? "anvilArtisan" : "anvil";
	}
	
	public int[][] getPositions()
	{
		if(isBig())
		{
			return new int[][]{
					new int[]{17, 8},
					new int[]{80, 102},
					new int[]{8, 123}
				};
		}
		return new int[][]{
			new int[]{8, 8},
			new int[]{144, 35},
			new int[]{8, 84}
		};
	}
	
	public ItemStack getResultSlot()
	{
		return getStackInSlot(getGridSize());
	}
	
	public EntityPlayer getSmith()
	{
		return smith;
	}
	
	/**
	 * Gets the Anvil Tier (Stone, Bronze, Iron, Steel)
	 * Stone -1, Bronze 0, Iron 1, Steel 2, Ornate 3,
	 */
	public int getAnvilTier()
	{
		int meta = getBlockMetadata();
		
		return anvils[meta][1];
	}
	/**
	 * Gets the Anvil Size true if large
	 */
	public boolean isBig()
	{
		int meta = getBlockMetadata();
		return anvils[meta][0] == 1;
	}
	
	/**
	 * Size, Tier
	 */
	public static int[][] anvils =  new int[][]
	{
		new int[]{0, -1},//Stone
		new int[]{0, 0},//SmlBronze
		new int[]{1, 0},//Bronze
		new int[]{0, 1},//SmlIron
		new int[]{1, 1},//Iron
		new int[]{0, 2},//SmlSteel
		new int[]{1, 2},//Steel
		new int[]{0, 3},//smlDeep
		new int[]{1, 3},//Deep
	};
	
	
	public int getSlotFor(float x, float y) 
	{
		ForgeDirection FD = BlockClickHelper.FD[direction];
		
		if(FD == ForgeDirection.EAST || FD == ForgeDirection.WEST)
		{
			return getSlotForEW(x, y);
		}
		return getSlotForNS(x, y);
	}
	
	public int getSlotForNS(float x, float y) 
	{
		int sizeX = gridSize()[0];
		int sizeY = gridSize()[1];
		
		float x1 = 0F + (3F * 0.0625F);
		float x2 = 1F - (3F * 0.0625F);
		float y1 = 0F + (4F * 0.0625F);
		float y2 = 1F - (4F * 0.0625F);
		
		if(!isBig())
		{
			x1 = 0F + (4.25F * 0.0625F);
			x2 = 1F - (4.25F * 0.0625F);
			y1 = 0F + (5F * 0.0625F);
			y2 = 1F - (5F * 0.0625F);
		}
		
		int[] coord = BlockClickHelper.getCoordsFor(x, y, x1, x2, y1, y2, sizeX, sizeY, direction);

		if(coord == null)
		{
			return -1;
		}
		if(coord[0] >= sizeX) coord[0] =  sizeX-1;
		if(coord[1] >= sizeY) coord[1] =  sizeY-1;
		
		return (coord[0] + coord[1] * sizeX);
	}
	
	
	public int getSlotForEW(float x, float y) 
	{
		int sizeX = gridSize()[0];
		int sizeY = gridSize()[1];
		
		float x1 = 0F + (4F * 0.0625F);
		float x2 = 1F - (4F * 0.0625F);
		float y1 = 0F + (3F * 0.0625F);
		float y2 = 1F - (3F * 0.0625F);
		
		if(!isBig())
		{
			x1 = 0F + (5F * 0.0625F);
			x2 = 1F - (5F * 0.0625F);
			y1 = 0F + (4.25F * 0.0625F);
			y2 = 1F - (4.25F * 0.0625F);
		}
		
		int[] coord = BlockClickHelper.getCoordsFor(x, y, x1, x2, y1, y2, sizeY, sizeX, direction);

		if(coord == null)
		{
			return -1;
		}
		if(coord[0] >= sizeX) coord[0] =  sizeX-1;
		if(coord[1] >= sizeY) coord[1] =  sizeY-1;
		
		return (coord[0] + coord[1] * sizeX);
	}
	
	@Override
	public void onInventoryChanged()
	{
		updateInv();
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
		return getForgeProgressScaled(i);
	}
	
	public boolean isItem3D(ItemStack itemstack)
	{
		if(itemstack == null || itemstack.getItem() == null || itemstack.getItem() instanceof ItemBlock)
		{
			return false;
		}
		if(itemstack.itemID == ItemListMF.hotItem.itemID)
		{
			ItemStack item = ItemHotItem.getItem(itemstack);
			return isItem3D(item);
		}
		return itemstack.getItem().isFull3D();
	}
	public float getItemRotate(ItemStack itemstack)
	{
		if(itemstack == null || itemstack.getItem() == null || itemstack.getItem() instanceof ItemBlock)
		{
			return 45F;
		}
		if(itemstack.itemID == ItemListMF.hotItem.itemID)
		{
			ItemStack item = ItemHotItem.getItem(itemstack);
			return getItemRotate(item);
		}
		return 45F;
	}
	
	private String customName;
	public void setCustomName(String name)
	{
		customName = name;
	}
	@Override
	public String getInvName()
	{
		return isInvNameLocalized() ? customName : "container.mfAnvil";
	}
	@Override
	public boolean isInvNameLocalized() 
	{
		return customName != null && customName.length() > 0;
	}
	
	@SideOnly(Side.CLIENT)
	private float getRepairHammer()
	{
		if(!worldObj.isRemote)return 0;
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(player != null && player.getHeldItem() != null)
		{
			if(player.getHeldItem().getItem() instanceof ItemRepairHammer)
			{
				return ((ItemRepairHammer)player.getHeldItem().getItem()).maxRepair;
			}
		}
		return 0;
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
