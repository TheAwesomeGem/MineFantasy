package minefantasy.block.tileentity;

import java.util.Random;

import minefantasy.api.IMFCrafter;
import minefantasy.api.leatherwork.EnumToolType;
import minefantasy.api.tanner.LeathercuttingRecipes;
import minefantasy.api.tanner.TanningRecipes;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class TileEntityTanningRack extends TileEntity implements PacketUserMF, IMFCrafter, IInventory{

    private int ticksExisted;
    public int direction;
    public float progress;
    private ItemStack hungItem;
    Random rand = new Random();

    public TileEntityTanningRack() {
    }

    public boolean canTan() 
    {
    	return TanningRecipes.instance().getTanningResult(getHung()) != null;
    }
    
    public boolean canCut()
    {
    	return LeathercuttingRecipes.instance().getCuttingResult(getHung()) != null;
    }
    
    public void setHung(ItemStack item)
    {
    	hungItem = item;
    }

    public boolean use(EntityPlayer player, EnumToolType toolType, float quality) 
    {
    	if (canTan() && toolType == EnumToolType.KNIFE)
        {
    		worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "step.cloth", 1.0F, 1.0F);
    		progress += quality;
    		
    		if(progress >= getMaxProgress())
    		{
    			progress = 0.0F;
	    		if(!worldObj.isRemote)
	    		{
		            setHung(TanningRecipes.instance().getTanningResult(getHung()));
		            syncItem();
	    		}
	            if(player.getHeldItem() != null)
	            {
	            	player.getHeldItem().damageItem(1, player);
	            }
	            return true;
    		}
        }
        if (canCut() && toolType == EnumToolType.CUTTER)
        {
        	worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "mob.sheep.shear", 0.5F, 0.65F);
        	progress += quality;
        	
        	if(progress >= getMaxProgress())
    		{
        		progress = 0.0F;
	        	if(!worldObj.isRemote)
	    		{
		        	int rs;
		        	ItemStack result = LeathercuttingRecipes.instance().getCuttingResult(getHung()).copy();
		        	rs = result.stackSize * hungItem.stackSize;
		        	setHung(result);
		        	hungItem.stackSize = rs;
		        	this.retrieveItem(player);
		            syncItem();
	    		}
	        	if(player.getHeldItem() != null)
	            {
	            	player.getHeldItem().damageItem(1, player);
	            }
	            return true;
    		}
        }
        return false;
    }
    private float getMaxProgress() 
    {
		return 50F;
	}

	@Override
    public void updateEntity()
    {
    	super.updateEntity();
    	ticksExisted ++;
    	sendPacketToClients();
    	
    	if(ticksExisted % 20 == 0)
    	{
    		syncItem();
    	}
    	if(!canCraft())
    	{
    		progress = 0.0F;
    	}
    }

	public boolean canHang() 
	{
        return getHung() == null;
    }

    public void hang(ItemStack item) 
    {
    	setHung(item);
    	Random rand = new Random();
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "mob.horse.leather", rand .nextFloat() + 1.5F, (rand.nextFloat()*0.4F) + 0.8F);
		syncItem();
    }

	public ItemStack getHung() 
	{
		return hungItem;
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        
        if(getHung() != null)
        {
        	NBTTagCompound slot = new NBTTagCompound();
            this.hungItem.writeToNBT(slot);
            
            tag.setTag("Hung", slot);
        }
        tag.setInteger("Dir", direction);
        tag.setFloat("Progress", progress);
    }

    public void readFromNBT(NBTTagCompound tag) 
    {
        super.readFromNBT(tag);
        
        if(tag.hasKey("Hung"))
        {
        	hungItem = ItemStack.loadItemStackFromNBT((NBTTagCompound) tag.getTag("Hung"));
        }
        
        direction = tag.getInteger("Dir");
        progress = tag.getFloat("Progress");
    }
	private void sendPacketToClients() {
		if(!worldObj.isRemote)
		{
			try
			{
				Packet packet = PacketManagerMF.getPacketIntegerArray(this, new int[]{direction, (int)progress*100});
				FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
			} catch(NullPointerException e)
			{
				System.out.println("MineFantasy: Lost Client connection");
			}
		}
	}
	@Override
	public void recievePacket(ByteArrayDataInput data)
	{
		direction = data.readInt();
		int p = data.readInt();
		progress = (float)p / 100F;
	}
	public boolean canHang(ItemStack itemstack)
	{
		return TanningRecipes.instance().getTanningResult(itemstack)!= null ||
				LeathercuttingRecipes.instance().getCuttingResult(itemstack)!= null;
	}
	
	public void syncItem() 
    {
		if(!worldObj.isRemote)
		{
			Packet packet = PacketManagerMF.getPacketItemStackArray(this, 0, getHung());
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

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return getHung();
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		getHung().stackSize --;
		if(getHung().stackSize <= 0)
		{
			setHung(null);
		}
		return getHung();
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		setHung(itemstack);
	}

	@Override
	public String getInvName() 
	{
		return null;
	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) 
	{
		return false;
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

	public void retrieveItem(EntityPlayer player) 
	{
		if(player.worldObj.isRemote)
		{
			return;
		}
		double x = player.posX;
		double y = player.posY;
		double z = player.posZ;
		
		EntityItem drop = new EntityItem(worldObj, x+0.5D, y+0.5D, z+0.5D, getHung().copy());
		worldObj.spawnEntityInWorld(drop);
    	hang(null);
	}

	private boolean canCraft() 
	{
		return canTan() || canCut();
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
		return (int)((float)i / 50F * progress);
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