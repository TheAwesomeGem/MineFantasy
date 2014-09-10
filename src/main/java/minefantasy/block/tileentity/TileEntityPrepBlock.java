package minefantasy.block.tileentity;

import java.util.Random;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.MineFantasyBase;
import minefantasy.api.IMFCrafter;
import minefantasy.api.anvil.IHammer;
import minefantasy.api.cooking.FoodPrepRecipe;
import minefantasy.api.cooking.IUtensil;
import minefantasy.api.cooking.UtensilManager;
import minefantasy.item.ToolMaterialMedieval;
import minefantasy.item.tool.ItemKnifeMF;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPrepBlock extends TileEntity implements IInventory, PacketUserMF, IMFCrafter
{
	/**
	 * The first slot (0) is the item on board, the second (1) is the texture mask
	 */
	private ItemStack[] inv = new ItemStack[2];
	private float time;
	private String requiredUtensil = "";
	private FoodPrepRecipe recipe;
	public int direction;
	private int progBar;
	private Random rand = new Random();
	
	public boolean displayGlint;
	
	public TileEntityPrepBlock()
	{
		inv[1] = new ItemStack(Block.planks);
	}

	@Override
	public void updateEntity()
	{
		if(worldObj != null && !worldObj.isRemote)
		{
			progBar = this.getScaledProg(100);
			sendPacketToClients();
			
			int id = 0;
			int ss = 0;
			int meta = 0;
			
			if(inv[0] != null)
			{
				id = inv[0].itemID;
				ss = inv[0].stackSize;
				meta = inv[0].getItemDamage();
			}
		}
	}
	
	private void sendPacketToClients() {
		int id = 0;
		int meta = 0;
		int enc = 0;
		if(inv[0] != null)
		{
			id = inv[0].itemID;
			meta = inv[0].getItemDamage();
			enc = inv[0].isItemEnchanted() ? 1 : 0;
		}
		
		int id2 = 0;
		int meta2 = 0;
		
		if(inv[1] != null)
		{
			id2 = inv[1].itemID;
			meta2 = inv[1].getItemDamage();
		}
		
		if(!worldObj.isRemote || FMLCommonHandler.instance().getSide().isServer())
		{
			try
			{	
				Packet packet = PacketManagerMF.getPacketIntegerArray(this, new int[]{direction, id, meta, id2, meta2, enc, progBar});
				FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
			} 
			catch(NullPointerException e)
			{
				System.out.println("MineFantasy: Client connections lost");
			}
		}
	}
	
	
	public boolean interact(EntityPlayer user, boolean leftClk)
	{
		boolean use = true;
		ItemStack held = user.getHeldItem();
		if(!worldObj.isRemote && held != null && held.getItem() != null && held.getItem() instanceof IUtensil)//BUILD SURFACE
		{
			String type = ((IUtensil)held.getItem()).getType(held);
			
			if(type.equalsIgnoreCase("mallet") && inv[0] != null && inv[0].getItem() instanceof ItemBlock)
			{
				held.damageItem(1, user);
				worldObj.playSoundEffect(xCoord+0.5F, yCoord, zCoord+0.5F, data_minefantasy.sound("mallet_build"), 1.0F, 0.8F + rand.nextFloat()*0.2F);
				inv[1] = inv[0].copy();
				return true;
			}
		}
		
		if(!worldObj.isRemote && inv[0] == null && !leftClk)//PUT ITEM
		{
			if(held != null)
			{
				inv[0] = held.copy();
				inv[0].stackSize = 1;
				
				if(!user.capabilities.isCreativeMode)
				{
					held.stackSize --;
					time = 0;
					if(held.stackSize <= 0)
					{
						held = null;
					}
				}
			}
			else
			{
				use = false;
			}
		}
		else if(!worldObj.isRemote && UtensilManager.getTypeOfTool(held) == "Null" && !leftClk)//TAKE ITEM
		{
			if(!user.capabilities.isCreativeMode)
			{
				worldObj.spawnEntityInWorld(new EntityItem(worldObj, user.posX, user.posY, user.posZ, inv[0]));
			}
			time = 0;
			inv[0] = null;
		}
		
		tryToCraft(held, user);
		
		return use;
	}
	
	
	
	
	
	
	
	
	
	private void tryToCraft(ItemStack tool, EntityPlayer user) 
	{
		recipe = FoodPrepRecipe.getRecipeFor(inv[0], tool);
		boolean cook = false;
		if(recipe != null)
		{
			if(tool.getItem() instanceof ItemKnifeMF)
			{
				if(((ItemKnifeMF)tool.getItem()).getMaterial() == ToolMaterialMedieval.DRAGONFORGE)
				{
					cook = true;
					worldObj.spawnParticle("flame", xCoord+rand.nextFloat(), yCoord+0.2D, zCoord+rand.nextFloat(), 0, 0, 0);
				}
			}
			if(requiredUtensil != UtensilManager.getTypeOfTool(tool))
			{
				time = 0;
				requiredUtensil = UtensilManager.getTypeOfTool(tool);
			}
			
			float e = 1.0F;
			if(tool.getItem() != null && tool.getItem() instanceof IUtensil)
			{
				e = ((IUtensil)tool.getItem()).getEfficiency(tool);
			}
			
			if(!worldObj.isRemote)
			{
				time += e;
				tool.damageItem(1, user);
				if(tool.getItemDamage() >= tool.getMaxDamage() && tool.stackSize <= 1)
		    	{
		    		user.renderBrokenItemStack(tool);
		    		user.destroyCurrentEquippedItem();
		    	}
				worldObj.playSoundEffect(xCoord+0.5D, yCoord, zCoord+0.5D, recipe.prepSound, 1, 1);
			}
			
			if(inv != null && inv[0] != null)
			{
				worldObj.spawnParticle("iconcrack_" + inv[0].itemID, xCoord+rand.nextFloat(), yCoord+0.2D, zCoord+rand.nextFloat(), 0F, 0F, 0F);
			}
			
			if(!worldObj.isRemote && time >= recipe.time)
			{
				ItemStack result = recipe.output;
				if(cook && result != null && FurnaceRecipes.smelting().getSmeltingResult(recipe.output) != null)
				{
					result = FurnaceRecipes.smelting().getSmeltingResult(recipe.output);
				}
				int ss = result.stackSize;
				if(inv[0] != null)
				{
					ss *= inv[0].stackSize;
				}
				if(ss <= result.getMaxStackSize())
				{
					inv[0] = result.copy();
					inv[0].stackSize = ss;
					time = 0;
				}
			}
		}
	}

	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList var2 = nbt.getTagList("Items");
        this.inv = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.inv.length)
            {
                this.inv[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        direction = nbt.getInteger("Dir");
        time = nbt.getFloat("Time");
        if(nbt.hasKey("Tool"))
        {
        	requiredUtensil = nbt.getString("Tool");
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setFloat("Time", time);
        if(requiredUtensil != null)
        {
        	nbt.setString("Tool", requiredUtensil);
        }
        nbt.setInteger("Dir", direction);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.inv.length; ++var3)
        {
            if (this.inv[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.inv[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        nbt.setTag("Items", var2);
    }
    
    
    
    
	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inv[i];
	}

	public ItemStack decrStackSize(int i, int j) {
		if(inv[i] != null && inv[i].getItem() != null)
		{
			if(inv[i].getItem().hasContainerItem())
			{
				inv[i] = inv[i].getItem().getContainerItemStack(inv[i]);
				return inv[i];
			}
		}
		
		if (inv[i] != null) 
		{
			if (inv[i].stackSize <= j) 
			{
				ItemStack itemstack = inv[i];
				inv[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inv[i].splitStack(j);
			if (inv[i].stackSize == 0) {
				inv[i] = null;
			}
			return itemstack1;
		} else 
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		inv[i] = itemstack;
	}

	@Override
	public String getInvName() 
	{
		return "Board";
	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return true;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) 
	{
		return entityplayer.getDistance(xCoord, yCoord, zCoord) < 8;
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return false;
	}

	@Override
	public void recievePacket(ByteArrayDataInput data)
	{
		direction = data.readInt();
		int id = data.readInt();
		int meta = data.readInt();
		int id2 = data.readInt();
		int meta2 = data.readInt();
		int enc = data.readInt();
		
		progBar = data.readInt();
		
		if(worldObj != null && worldObj.isRemote)
		{
			inv[0] = new ItemStack(id, 1, meta);
			
			if(blockChange(id2, meta2))
			{
				if(MineFantasyBase.isDebug())
					System.out.println("Detected Benchtop tex change");
				
				worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
			}
			inv[1] = new ItemStack(id2, 1, meta2);
			displayGlint = enc == 1;
		}
	}

	private boolean blockChange(int id, int meta)
	{
		int id2 = 0;
		int meta2 = 0;
		
		if(inv[1] != null)
		{
			id2 = inv[1].itemID;
			meta2 = inv[1].getItemDamage();
		}
		if(id != id2)return true;
		if(meta != meta2)return true;
		
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRenderCraftMetre() 
	{
		return progBar > 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getProgressBar(int i)
	{
		return (int)((float)i / 100F * (float)progBar);
	}
	
	public int getScaledProg(int i)
	{
		if(recipe == null)return -1;
		return (int)((float)i / (float)recipe.time * (float)time);
	}
	@Override
	public String getResultName()
	{
		return "";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setTempResult(ItemStack item) 
	{
	}
}
