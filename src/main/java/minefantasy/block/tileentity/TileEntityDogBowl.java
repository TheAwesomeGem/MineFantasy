package minefantasy.block.tileentity;

import minefantasy.block.special.BlockDogBowl;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class TileEntityDogBowl extends TileEntity implements PacketUserMF{
    public int food;
    public int itemMeta;
    
    public TileEntityDogBowl(int m)
    {
        itemMeta = m;
    }
    public TileEntityDogBowl()
    {
        
    }

    public boolean canPutFood() 
    {
        return food < getFoodMax();
    }
    public int getFoodMax()
    {
    	if(getBlockMetadata() == 1)
    	{
    		return 64;
    	}
    	if(getBlockMetadata() == 2)
    	{
    		return 100;
    	}
    	return 32;
    }

    public void addFood(int healAmount) 
    {
        food += healAmount;
        sendPacketToClients();
    }
    
    @Override
    public int getBlockMetadata()
    {
		if(worldObj == null)
			return itemMeta;
		
		
        return super.getBlockMetadata();
    }
    
    public void updateEntity()
    {
    	super.updateEntity();
    	sendPacketToClients();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        food = nbt.getInteger("food");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("food", food);
    }
    
    @Override
	public void recievePacket(ByteArrayDataInput data) {
    	int f2 = data.readInt();
		food = f2;
	}
    
    public void sendPacketToClients() {
		if(!worldObj.isRemote)
		{
			Packet packet = PacketManagerMF.getPacketInteger(this, food);
			FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
		}
	}
    
    public String getTex(int meta)
    {
    	if(meta == 1)
    	{
    		return "Iron";
    	}
    	if(meta == 2)
    	{
    		return "Steel";
    	}
    	return "Wood";
    }
}
