package minefantasy.block.tileentity;

import minefantasy.api.forge.IBellowsUseable;
import minefantasy.system.data_minefantasy;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;

public class TileEntityBellows extends TileEntity implements PacketUserMF{
	public int direction;
	public int press = 0;
	public TileEntityBellows()
	{
		
	}
	public void interact(EntityPlayer player, float powerLevel) {
		int x = (int)xCoord;
		int y = (int)yCoord;
		int z = (int)zCoord;
		IBellowsUseable forge = getFacingForge();
		if(press < 10)
		{
			if(player != null)
			{
				player.playSound(data_minefantasy.sound("bellows"), 1, 1);
			}
			else
			{
				worldObj.playSound(xCoord, yCoord, zCoord, data_minefantasy.sound("bellows"), 1.0F, 1.0F, false);
			}
			press = 50;
			if(forge != null)
			{
				forge.onUsedWithBellows(powerLevel);
			}
			sendPacketToClients();
		}
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(press > 0)press -= 2;
		if(press < 0)press = 0;
		sendPacketToClients();
	}
	
	public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        direction = nbt.getInteger("direction");
        press = nbt.getInteger("press");
    }
	
	private void sendPacketToClients() {
		if(!worldObj.isRemote)
		{
			Packet packet = PacketManagerMF.getPacketIntegerArray(this, new int[]{press, direction});
			try
			{
				FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
			} catch(NullPointerException e)
			{
				System.out.println("MineFantasy: Client connection lost");
			}
		}
	}


    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("direction", direction);
        nbt.setInteger("press", press);
    }
	@Override
	public void recievePacket(ByteArrayDataInput data) {
		press = data.readInt();
		direction = data.readInt();
	}
	
	
	
	public ForgeDirection getFacing()
    {
    	TileEntityBellows bellows = (TileEntityBellows)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
    	if(bellows == null)
    		return null;
    	int dir = bellows.direction;
    	switch(dir)//clockwise
        {
        	case 0: //SOUTH
        	    return ForgeDirection.SOUTH;
        	case 1: //WEST
        	    return ForgeDirection.WEST;
        	case 2: //NORTH
        	    return ForgeDirection.NORTH;
        	case 3: //EAST
        	    return ForgeDirection.EAST;
        }
    	return ForgeDirection.SOUTH;
    }
    public IBellowsUseable getFacingForge()
    {
    	ForgeDirection dir = getFacing();    	
    	int x2 = xCoord + dir.offsetX;
    	int y2 = yCoord + dir.offsetY;
    	int z2 = zCoord + dir.offsetZ;
    	
    	TileEntity tile = worldObj.getBlockTileEntity(x2, y2, z2);
    	if(tile != null && tile instanceof IBellowsUseable)
    		return (IBellowsUseable)tile;
    	
    	if(worldObj.getBlockMaterial(x2, y2, z2) != null && worldObj.getBlockMaterial(x2, y2, z2).isSolid())
    	{
    		return getFacingForgeThroughWall();
    	}
    	return null;
    }
    
    public IBellowsUseable getFacingForgeThroughWall()
    {
    	ForgeDirection dir = getFacing();    	
    	int x2 = xCoord + (dir.offsetX*2);
    	int y2 = yCoord + (dir.offsetY*2);
    	int z2 = zCoord + (dir.offsetZ*2);
    	
    	TileEntity tile = worldObj.getBlockTileEntity(x2, y2, z2);
    	if(tile == null)
    		return null;
    	if(tile instanceof IBellowsUseable)
    	{
    		return (IBellowsUseable)tile;
    	}
    	return null;
    }
}
