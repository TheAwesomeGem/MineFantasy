package minefantasy.system.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import minefantasy.MineFantasyBase;
import minefantasy.api.IMFCrafter;
import minefantasy.entity.INameableEntity;
import minefantasy.entity.ISyncedInventory;
import minefantasy.item.weapon.ItemBowMF;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketManagerMF implements IPacketHandler {
	
	@Override
	public void onPacketData(INetworkManager network,
			Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		int type = dat.readInt();
		if(type == 0)
		{
			int x = dat.readInt();
			int y = dat.readInt();
			int z = dat.readInt();
	
			try
			{
				World world = ((EntityPlayer)player).worldObj;
				TileEntity tile = world.getBlockTileEntity(x, y, z);
				if (tile instanceof PacketUserMF)
				{
					PacketUserMF user = (PacketUserMF)tile;
					user.recievePacket(dat);
				}
			}
			catch(NullPointerException e)
			{
				
			}
		}
		if(type == 1) // ENTITY
		{
			int entID = dat.readInt();
			EntityPlayer eplayer = (EntityPlayer)player;
			World world = eplayer.worldObj;
			
			for (Object entity : world.loadedEntityList) 
			{
				if (((Entity) entity).entityId == entID && entity instanceof PacketUserMF)
				{
					PacketUserMF user = (PacketUserMF)entity;
					user.recievePacket(dat);
				}
			}
		}
		
		if(type == 2)
		{
			int x = dat.readInt();
			int y = dat.readInt();
			int z = dat.readInt();
	
			try
			{
				World world = ((EntityPlayer)player).worldObj;
				TileEntity tile = world.getBlockTileEntity(x, y, z);
				if (tile instanceof IInventory)
				{
					IInventory user = (IInventory)tile;
					try
					{
						int slot = dat.readInt();
						ItemStack item = readItemStack(dat);
						if(world.isRemote)
						{
							user.setInventorySlotContents(slot, item);
						}
					} catch(IOException e)
					{
						
					}
				}
			}
			catch(NullPointerException e)
			{
				
			}
		}
		
		if(type == 4)
		{
			int id = dat.readInt();
	
			try
			{
				World world = ((EntityPlayer)player).worldObj;
				
				for (Object entity : world.loadedEntityList) 
				{
					if (((Entity) entity).entityId == id)
					{
						if(entity instanceof ISyncedInventory)
						{
							ISyncedInventory user = (ISyncedInventory)entity;
							try
							{
								int slot = dat.readInt();
								ItemStack item = readItemStack(dat);
								user.setItem(item, slot);
							} catch(IOException e)
							{
								
							}
						}
					}
				}
			}
			catch(NullPointerException e)
			{
				
			}
		}
		
		if(type == 3) // ENTITY NAME
		{
			int entID = dat.readInt();
			EntityPlayer eplayer = (EntityPlayer)player;
			World world = eplayer.worldObj;
			
			for (Object entity : world.loadedEntityList) {
				if (((Entity) entity).entityId == entID)
				{
					if(entity instanceof INameableEntity)
					{
						int length = dat.readInt();
						String newname = "";
						for(int a = 0; a < length; a ++)
						{
							newname = (newname + dat.readChar());
						}
						((INameableEntity)entity).sendNewName(newname);
					}
				}
			}
		}
		if(type == -1) // HOUND INV
		{
			int entID = dat.readInt();
			int playID = dat.readInt();
			int screen = dat.readInt();
			EntityPlayer eplayer = (EntityPlayer)player;
			World world = eplayer.worldObj;

			if(eplayer.entityId == playID)
				eplayer.openGui(MineFantasyBase.instance, 2, eplayer.worldObj, entID,
					0, screen);
		}
		
		if(type == 5) // Arrow
		{
			int playID = dat.readInt();
			int itemID = dat.readInt();
			int itemSub = dat.readInt();
			
			EntityPlayer eplayer = (EntityPlayer)player;
			World world = eplayer.worldObj;
			
			if(eplayer.entityId == playID)
			{
				ItemStack bow = eplayer.getHeldItem();
				ItemStack arrow = new ItemStack(itemID, 1, itemSub);
				if(bow != null && arrow != null)
				{
					ItemBowMF.loadArrow(eplayer, bow, arrow, false);
				}
			}
		}
		
		if(type == 6)
		{
			int x = dat.readInt();
			int y = dat.readInt();
			int z = dat.readInt();
	
			try
			{
				World world = ((EntityPlayer)player).worldObj;
				TileEntity tile = world.getBlockTileEntity(x, y, z);
				if (tile instanceof IMFCrafter)
				{
					IMFCrafter user = (IMFCrafter)tile;
					try
					{
						ItemStack item = readItemStack(dat);
						if(world.isRemote)
						{
							user.setTempResult(item);
						}
					} catch(IOException e)
					{
						
					}
				}
			}
			catch(NullPointerException e)
			{
				
			}
		}
	}
	
	public static Packet getEntityPacketInteger(Entity entity, int dat)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int id = entity.entityId;
		try {
			dos.writeInt(1);
			dos.writeInt(id);
			dos.writeInt(dat);
		} catch (IOException e) {
			System.out.println("Failed to send Entity packet");
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MineFantasy";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		return pkt;
	}
	
	
	
	
	
	public static Packet getEntityPacketIntegerArray(Entity entity, int[] dat)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int id = entity.entityId;
		try {
			dos.writeInt(1);
			dos.writeInt(id);
			for(int a = 0; a < dat.length; a ++)
			dos.writeInt(dat[a]);
		} catch (IOException e) {
			System.out.println("Failed to send Entity packet");
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MineFantasy";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		return pkt;
	}
	
	
	
	public static Packet getEntityPacketDoubleArray(Entity entity, double[] dat)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int id = entity.entityId;
		try {
			dos.writeInt(1);
			dos.writeInt(id);
			for(int a = 0; a < dat.length; a ++)
			dos.writeDouble(dat[a]);
		} catch (IOException e) {
			System.out.println("Failed to send Entity packet");
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MineFantasy";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		return pkt;
	}
	
	public static Packet getEntityPacketMotionArray(Entity entity, int moveID, double[] dat)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int id = entity.entityId;
		try {
			dos.writeInt(1);
			dos.writeInt(id);
			dos.writeInt(moveID);
			for(int a = 0; a < dat.length; a ++)
			dos.writeDouble(dat[a]);
		} catch (IOException e) {
			System.out.println("Failed to send Entity packet");
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MineFantasy";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		return pkt;
	}
	
	

	public static Packet getPacketInteger(TileEntity tile, int data) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		int dat = data;
		try {
			dos.writeInt(0);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeInt(dat);
		} catch (IOException e) {
			System.out.println("Failed to send tile Entity packet for tile entity");
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MineFantasy";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		return pkt;
	}
	
	public static Packet getPacketMFResult(TileEntity tile, ItemStack item)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		try {
			dos.writeInt(6);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			writeItemStack(item, dos);
		} catch (IOException e) {
			System.out.println("Failed to send tile Entity packet for tile entity");
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MineFantasy";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		return pkt;
	}
	
	
	public static Packet getPacketIntegerArray(TileEntity tile, int[] data) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		try {
			dos.writeInt(0);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			for(int i = 0; i < data.length ; i ++)
			dos.writeInt(data[i]);
		} catch (IOException e) {
			System.out.println("Failed to send tile Entity packet for tile entity");
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MineFantasy";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		return pkt;
	}
	
	public static Packet getEntityRenamePacket(INameableEntity namer, String dat)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int id = namer.getEntityID();
		try {
			dos.writeInt(3);
			dos.writeInt(id);
			dos.writeInt(dat.length());
			dos.writeChars(dat);
		} catch (IOException e) {
			System.out.println("Failed to send Name packet");
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MineFantasy";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		
		return pkt;
	}
	
	public static Packet getHoundInv(Entity hound, EntityPlayer player, int screen)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int Hid = hound.entityId;
		int id = player.entityId;
		try {
			dos.writeInt(-1);
			dos.writeInt(Hid);
			dos.writeInt(id);
			dos.writeInt(screen);
		} catch (IOException e) {
			System.out.println("Failed to send Hound Chest packet");
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MineFantasy";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		
		return pkt;
	}
	
	
	public static Packet getArrowItemPacket(EntityPlayer player, int ID, int Meta)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		
		int playerID = player.entityId;
		try {
			dos.writeInt(5);
			dos.writeInt(playerID);
			dos.writeInt(ID);
			dos.writeInt(Meta);
		} catch (IOException e) {
			System.out.println("Failed to send Load Arrow packet");
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MineFantasy";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		
		return pkt;
	}
	
	public static ItemStack readItemStack(ByteArrayDataInput stream) throws IOException
    {
        ItemStack itemstack = null;
        int id = stream.readInt();

        if (id >= 0)
        {
            int ss = stream.readInt();
            int dam = stream.readInt();
            itemstack = new ItemStack(id, ss, dam);
            itemstack.stackTagCompound = readNBTTagCompound(stream);
        }

        return itemstack;
    }
	public static NBTTagCompound readNBTTagCompound(ByteArrayDataInput stream) throws IOException
    {
        int id = stream.readInt();

        if (id < 0)
        {
            return null;
        }
        else
        {
            byte[] abyte = new byte[id];
            stream.readFully(abyte);
            return CompressedStreamTools.decompress(abyte);
        }
    }
	
	public static Packet getPacketItemStackArray(TileEntity tile, int slot, ItemStack item)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		try {
			dos.writeInt(2);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			
			dos.writeInt(slot);
			writeItemStack(item, dos);
		} catch (IOException e) {
			System.out.println("Failed to send tile Entity packet for tile entity");
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MineFantasy";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		return pkt;
	}
	
	public static Packet getPacketItemStackArray(Entity entity, int slot, ItemStack item)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(4);
			dos.writeInt(entity.entityId);
			
			dos.writeInt(slot);
			writeItemStack(item, dos);
		} catch (IOException e) {
			System.out.println("Failed to send sync Item packet for entity");
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "MineFantasy";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		return pkt;
	}

    /**
     * Writes the ItemStack's ID (short), then size (byte), then damage. (short)
     */
    public static void writeItemStack(ItemStack item, DataOutputStream stream) throws IOException
    {
        if (item == null)
        {
            stream.writeInt(-1);
        }
        else
        {
            stream.writeInt(item.itemID);
            stream.writeInt(item.stackSize);
            stream.writeInt(item.getItemDamage());
            NBTTagCompound nbttagcompound = null;

            if (item.getItem().isDamageable() || item.getItem().getShareTag())
            {
                nbttagcompound = item.stackTagCompound;
            }

            writeNBTTagCompound(nbttagcompound, stream);
        }
    }

    /**
     * Writes a compressed NBTTagCompound to the OutputStream
     */
    protected static void writeNBTTagCompound(NBTTagCompound nbt, DataOutputStream stream) throws IOException
    {
        if (nbt == null)
        {
            stream.writeInt(-1);
        }
        else
        {
            byte[] abyte = CompressedStreamTools.compress(nbt);
            stream.writeInt((short)abyte.length);
            stream.write(abyte);
        }
    }

}