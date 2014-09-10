package minefantasy.block.tileentity;

import java.util.Random;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import minefantasy.MineFantasyBase;
import minefantasy.api.IMFCrafter;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;

public class TileEntityRoad extends TileEntity implements PacketUserMF, IMFCrafter
{
	private float buildTime;
	private int lastUsed = 0;
	private int[] surface = new int[2];
	private int ticksExisted;
	private Random rand = new Random();
	
	public TileEntityRoad()
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRenderCraftMetre() 
	{
		return buildTime > 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getProgressBar(int i) 
	{
		return (int)((float)i * buildTime);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getResultName() 
	{
		return "";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setTempResult(ItemStack res) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(!worldObj.isRemote)
		{
			if(lastUsed < 0)
			{
				++lastUsed;
			}
			
			if(lastUsed > 0)
			{
				--lastUsed;
			}
		    else if(buildTime > 0)
			{
				buildTime = 0;
			}
			++ticksExisted;
			if(ticksExisted % 20 == 0)
			{
				sendPacketToClients();
			}
		}
	}
	
	public void setSurface(int id, int meta)
	{
		if(worldObj == null || worldObj.isRemote)
		{
			return;
		}
		if(id == Block.grass.blockID)
		{
			id = Block.dirt.blockID;
		}
		surface[0] = id;
		surface[1] = meta;
		
		sendPacketToClients();
	}

	public void sendPacketToClients()
	{
		if(!worldObj.isRemote)
		{
			try
			{	
				Packet packet = PacketManagerMF.getPacketIntegerArray(this, new int[]{(int)(buildTime*50), surface[0], surface[1]});
				FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
			} 
			catch(NullPointerException e)
			{
				System.out.println("MineFantasy: Client connections lost");
			}
		}
	}
	@Override
	public void recievePacket(ByteArrayDataInput data) 
	{
		buildTime = (float)(data.readInt() / 50F);
		int id2 = data.readInt();
		int meta2 = data.readInt();
		
		if(blockChange(id2, meta2))
		{
			if(MineFantasyBase.isDebug())
				System.out.println("Detected Road tex change");
			
			surface[0] = id2;
			surface[1] = meta2;
			
			worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	private boolean blockChange(int id, int meta)
	{
		if(id != surface[0])return true;
		if(meta != surface[1])return true;
		
		return false;
	}
	
	
	public void writeToNBT(NBTTagCompound nbt)
    {
		super.writeToNBT(nbt);
		
		nbt.setIntArray("surface", surface);
    }
	public void readFromNBT(NBTTagCompound nbt)
    {
		super.readFromNBT(nbt);
		
		surface = nbt.getIntArray("surface");
    }

	public int[] getSurface() 
	{
		return surface;
	}

	public boolean canBuild()
	{
		if(worldObj == null || worldObj.isRemote)
		{
			return false;
		}
		if(lastUsed < 0)
		{
			return false;
		}
		lastUsed = 10;
		buildTime += rand.nextFloat()/2;
		sendPacketToClients();
		if(buildTime >= 1.0F)
		{
			buildTime = 0;
			lastUsed = -10;
			return true;
		}
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "dig.grass", 0.5F, 1.0F);
		return false;
	}
}
