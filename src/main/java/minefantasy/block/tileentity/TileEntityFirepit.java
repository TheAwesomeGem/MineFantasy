package minefantasy.block.tileentity;

import java.util.Random;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.api.IMFCrafter;
import minefantasy.api.cooking.IHeatSource;
import minefantasy.block.BlockListMF;
import minefantasy.item.ItemListMF;
import minefantasy.system.network.PacketManagerMF;
import minefantasy.system.network.PacketUserMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;

public class TileEntityFirepit extends TileEntity implements IHeatSource, IMFCrafter, PacketUserMF
{
	public int fuel = 0;
	private int maxFuel = 12000; //10 minutes
	private float charcoal = 0;
	private int ticksExisted;
	private Random rand = new Random();
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		++ticksExisted;
		
		if(!worldObj.isRemote)
		{
			if(!isLit() && fuel > 0 && ticksExisted % 10 == 0)
			{
				tryLight();
			}
			
			if(isLit())
			{
				if(fuel > 0)
				{
					fuel --;
					charcoal += (0.25F / 20F / 60F);
				}
				
				if(fuel <= 0)
				{
					setLit(false);
				}
				
				if(isLit() && isWet())
				{
					extinguish(Block.waterStill.blockID, 0);
				}
			}
			sendPacketToClients();
		}
		if(worldObj.isRemote)
		{
			if(ticksExisted % 5 == 0 && isLit())
			{
				worldObj.spawnParticle("smoke", xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, 0.0D, 0.0D, 0.0D);
				worldObj.spawnParticle("flame", xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, 0.0D, 0.0D, 0.0D);
				
				if(rand.nextInt(100) == 0)
				{
					worldObj.playSound((double)((float)xCoord + 0.5F), (double)((float)yCoord + 0.5F), (double)((float)zCoord + 0.5F), "fire.fire", 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
				}
			}
			
			if(ticksExisted % 20 == 0)
			{
				if(worldObj.getBlockId(xCoord, yCoord, zCoord) != BlockListMF.firepit.blockID)
				{
					System.out.println("No Block");
				}
			}
		}
	}
	
	private boolean isWet()
	{
		if(isWater(-1, 0, 0) || isWater(1, 0, 0) || isWater(0, 0, -1) || isWater(0, 0, 1) || isWater(0, 1, 0))
		{
			return true;
		}
		if(worldObj.getBlockId(xCoord, yCoord+1, zCoord) == BlockListMF.roast.blockID)
		{
			if(worldObj.canLightningStrikeAt(xCoord, yCoord+2, zCoord))
			{
				return true;
			}
		}
		
		return worldObj.canLightningStrikeAt(xCoord, yCoord+1, zCoord);
	}

	public int getCharcoalDrop() 
	{
		return (int)(Math.floor(charcoal));
	}
	
	public boolean isBurning()
	{
		return isLit() && fuel > 0;
	}

	public boolean isLit()
	{
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1;
	}
	
	private void tryLight()
	{
		if(isFire(-1, 0, 0) || isFire(1, 0, 0) || isFire(0, 0, -1) || isFire(0, 0, 1) || isFire(0, -1, 0) || isFire(0, 1, 0))
		{
			setLit(true);
		}
	}
	
	private boolean isFire(int x, int y, int z) 
	{
		return worldObj.getBlockMaterial(xCoord+x, yCoord+y, zCoord+z) == Material.fire;
	}
	private boolean isWater(int x, int y, int z) 
	{
		return worldObj.getBlockMaterial(xCoord+x, yCoord+y, zCoord+z) == Material.water;
	}

	public boolean addFuel(ItemStack input)
	{
		int amount = getItemBurnTime(input);
		if(amount > 0 && fuel < maxFuel)
		{
			fuel += amount;
			if(fuel > maxFuel)
			{
				fuel = maxFuel;
			}
			return true;
		}
		return false;
	}
	public void setLit(boolean lit)
	{
		if(worldObj != null)
		{
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, lit ? 1 : 0, 2);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		
		nbt.setInteger("fuel", fuel);
		nbt.setInteger("maxFuel", maxFuel);
		nbt.setInteger("ticksExisted", ticksExisted);
		
		nbt.setFloat("charcoal", charcoal);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		
		setLit(nbt.getBoolean("isLit"));
		
		fuel = nbt.getInteger("fuel");
		maxFuel = nbt.getInteger("maxFuel");
		ticksExisted = nbt.getInteger("ticksExisted");
		
		charcoal = nbt.getFloat("charcoal");
	}
	
	/**
	 * Gets the burn time
	 * 
	 * Wood tools and plank item are 1 minute
	 * sticks and saplings are 30seconds
	 */
	public static int getItemBurnTime(ItemStack input)
    {
        if (input == null)
        {
            return 0;
        }
        else
        {
            int i = input.getItem().itemID;
            int d = input.getItemDamage();
            Item item = input.getItem();

            if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 3600;//3Mins
            if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 1800;//1.5Mins
            if (item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().equals("WOOD")) return 2400;//2Mins
            if (i == Item.stick.itemID) return 600;//30Sec
            if (i == ItemListMF.plank.itemID) return 1200;//1Min
            
            if(i == ItemListMF.misc.itemID)
            {
            	if (d == ItemListMF.plankIronbark) return 1800;//1.5Mins
            	if (d == ItemListMF.plankEbony) return 3600;//3Mins
            }
            
            return 0;
        }
    }

	@Override
	public boolean canPlaceAbove() 
	{
		return true;
	}

	@Override
	public int getHeat() 
	{
		if(!isBurning())return 0;
		
		return 300;
	}
	
	private void sendPacketToClients()
	{
		try
		{
			Packet packet = PacketManagerMF.getPacketInteger(this, fuel);
			FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().sendPacketToAllPlayers(packet);
		} 
		catch(Exception e)
		{
			System.out.println("MineFantasy: Client connections lost");
		}
	}

	@Override
	public void recievePacket(ByteArrayDataInput data)
	{
		fuel = data.readInt();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldRenderCraftMetre() 
	{
		return fuel > 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getProgressBar(int width) 
	{
		return this.fuel * width / (maxFuel);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getResultName()
	{
		int seconds = (int)Math.floor(fuel/20);
		int mins = (int)Math.floor(seconds/60);
		seconds -= mins*60;
		
		String s = "";
		if(seconds < 10)
		{
			s += "0";
		}
		
		return StatCollector.translateToLocal("info.fuel") + "= " + mins + ":" + s + seconds;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setTempResult(ItemStack res) 
	{
	}

	public void extinguish() 
	{
		extinguish(Block.sand.blockID, 0);
	}
	
	public void extinguish(int block, int meta) 
	{
		worldObj.playSoundEffect(xCoord+0.5F, yCoord+0.25F, zCoord+0.5F, "random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
		worldObj.spawnParticle("largesmoke", xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, 0.0D, 0.0D, 0.0D);
		worldObj.spawnParticle("tilecrack_" + block + "_" + meta, xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, 0.0D, 0.0D, 0.0D);
		
		setLit(false);
	}
}
