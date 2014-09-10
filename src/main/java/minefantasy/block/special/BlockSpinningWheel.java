package minefantasy.block.special;

import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import minefantasy.api.tailor.INeedle;
import minefantasy.block.tileentity.TileEntitySpinningWheel;
import minefantasy.item.ItemListMF;
import minefantasy.system.cfg;
import minefantasy.system.network.PacketManagerMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockSpinningWheel extends BlockContainer 
{
	private Random rand = new Random();

	public BlockSpinningWheel(int id)
	{
		super(id, Material.wood);
		this.setCreativeTab(ItemListMF.tabTailor);
	}

	@Override
	public TileEntity createNewTileEntity(World world) 
	{
		return new TileEntitySpinningWheel();
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) 
    {
        int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        TileEntitySpinningWheel tile = (TileEntitySpinningWheel) world.getBlockTileEntity(x, y, z);
        tile.direction = dir;
    }

    /**
     * Called whenever the block is removed.
     */
	@Override
    public void breakBlock(World world, int x, int y, int z, int i1, int i2)
    {
        TileEntitySpinningWheel tile = (TileEntitySpinningWheel) world.getBlockTileEntity(x, y, z);

        if (tile != null) 
        {
            for (int var6 = 0; var6 < tile.getSizeInventory(); ++var6) 
            {
                ItemStack var7 = tile.getStackInSlot(var6);

                if (var7 != null) 
                {
                    float var8 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float var9 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float var10 = this.rand.nextFloat() * 0.8F + 0.1F;

                    while (var7.stackSize > 0)
                    {
                        int var11 = this.rand.nextInt(21) + 10;

                        if (var11 > var7.stackSize) {
                            var11 = var7.stackSize;
                        }

                        var7.stackSize -= var11;
                        EntityItem var12 = new EntityItem(world, (double) ((float) x + var8), (double) ((float) y + var9), (double) ((float) z + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));

                        if (var7.hasTagCompound()) {
                            var12.getEntityItem().setTagCompound((NBTTagCompound) var7.getTagCompound().copy());
                        }

                        float var13 = 0.05F;
                        var12.motionX = (double) ((float) this.rand.nextGaussian() * var13);
                        var12.motionY = (double) ((float) this.rand.nextGaussian() * var13 + 0.2F);
                        var12.motionZ = (double) ((float) this.rand.nextGaussian() * var13);
                        world.spawnEntityInWorld(var12);
                    }
                }
            }
        }

        super.breakBlock(world, x, y, z, i1, i2);
    }

    
    @Override
    public Icon getIcon(int side, int meta)
    {
    	return Block.planks.getIcon(side, meta);
    }
	
    @Override
    public boolean isOpaqueCube() 
    {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType()
    {
    	return cfg.renderId;
    }

   /**
    * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
    */
    @Override
	public boolean renderAsNormalBlock()
    {
       return false;
    }
   
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1, float f2)
    {
		TileEntitySpinningWheel tile = (TileEntitySpinningWheel) world.getBlockTileEntity(x, y, z);
		if(tile == null)
        {
        	return super.onBlockActivated(world, x, y, z, player, i, f, f1, f2);
        }
        if(world.isRemote)
 		{
    		int slot = tile.getSlotFor(f, f2);
            this.useInventory(world, x, y, z, tile, player, i, slot);
            
 			Packet packet = PacketManagerMF.getPacketIntegerArray(tile, new int[]{1, player.entityId, i, slot});
 			try
 			{
 				PacketDispatcher.sendPacketToServer(packet);
 			} catch(NullPointerException e)
 			{
 				System.out.println("MineFantasy: Client connection lost");
 			}
 		}
        ItemStack held = player.getHeldItem();
        if(held != null && held.itemID == Block.cloth.blockID)
        {
        	return true;
        }
		return super.onBlockActivated(world, x, y, z, player, i, f, f1, f2);
    }

	
	private static boolean tryPlaceItem(int slot, TileEntitySpinningWheel tile, EntityPlayer player)
    {
    	boolean completeStack = true;
    	ItemStack heldItem = player.getHeldItem();
    	ItemStack slotItem = tile.getStackInSlot(slot);
    	
    	if(slot == 2 && tile.getResultSlot() != null)
    	{
    		if(player.inventory.addItemStackToInventory(tile.getResultSlot().copy()))
			{
				tile.setInventorySlotContents(2, null);
				return true;
			}
    	}
    	if(slot == 2)
    	{
    		return false;
    	}
    	if(heldItem != null && tile.isItemValidForSlot(slot, heldItem))
    	{
			if(slotItem == null)
			{
				ItemStack place = heldItem.copy();
				if(!completeStack)
				{
					place.stackSize = 1;
				}
				tile.setInventorySlotContents(slot, place);
				
				if(!completeStack)
				{
					heldItem.stackSize --;
				}
				else
				{
					heldItem.stackSize = 0;
				}
				
				if(heldItem.stackSize <= 0)
				{
					player.setCurrentItemOrArmor(0, null);
				}
				return true;
			}
			else
			{
				if(slotItem.isItemEqual(heldItem))
				{
					int transfer = 1;
					if(completeStack)
					{
						if(slotItem.stackSize + heldItem.stackSize <= slotItem.getMaxStackSize())
						{
							 transfer = heldItem.stackSize;
						}
						else
						{
							transfer = slotItem.getMaxStackSize() - slotItem.stackSize;
						}
					}
					if(slotItem.stackSize+transfer <= slotItem.getMaxStackSize())
					{
						slotItem.stackSize += transfer;
						
						heldItem.stackSize -= transfer;
	    				
	    				if(heldItem.stackSize <= 0)
	    				{
	    					player.setCurrentItemOrArmor(0, null);
	    				}
	    				
						return true;
					}
				}
			}
    	}
    	else
    	{
    		if(slotItem != null)
    		{
    			if(player.inventory.addItemStackToInventory(slotItem.copy()))
    			{
    				tile.setInventorySlotContents(slot, null);
    				return true;
    			}
    		}
    	}
		return false;
	}
	@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg){}
	
	
	public static boolean useInventory(World world, int x, int y, int z, TileEntitySpinningWheel tile, EntityPlayer player, int i, int slot) 
    {
		if(tile.isUseableByPlayer(player))
		{
			if(i == 1 && slot >= 0)
			{
				if(tryPlaceItem(slot, tile, player))
	        	{
	        		tile.onInventoryChanged();
	        		return true;
	        	}
			}
			else
			{
				return tile.use(player);
			}
		}
		
		return false;
    }
}
