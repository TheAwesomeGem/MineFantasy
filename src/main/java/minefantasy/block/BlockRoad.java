package minefantasy.block;

import java.util.Random;


import minefantasy.block.tileentity.TileEntityRoad;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 * 
 * ROAD META: (0 and up)
 * Dirt, Sand, Cobblestone, Stone, Gravel
 */
public class BlockRoad extends BlockContainer {
    
    public BlockRoad(int i, float f)
    {
        super(i, Material.ground);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
        this.setLightOpacity(0);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
    	if(this == BlockListMF.Lowroad)
    		return AxisAlignedBB.getAABBPool().getAABB((double)(x + 0), (double)(y + 0), (double)(z + 0), (double)(x + 1), (double)(y + 0.5), (double)(z + 1));
    	
        return AxisAlignedBB.getAABBPool().getAABB((double)(x + 0), (double)(y + 0), (double)(z + 0), (double)(x + 1), (double)(y + 1), (double)(z + 1));
    }
    
    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
    {
    	TileEntityRoad tile = (TileEntityRoad) world.getBlockTileEntity(x, y, z);
    	int def = Block.dirt.blockID;
    	
    	if(world.getBlockMetadata(x,y,z) == 1)
    	{
    		def = Block.sand.blockID;
    	}
    	int[] tex = tile.getSurface();
    	if(tex != null)
    	{
    		if(tex.length >= 2)
    		{
    			int i = tex[0];
    			if(i <= 0)
    			{
    				i = def;
    			}
    			Block block = Block.blocksList[i];
    			if(block != null)
    			{
    				return block.getIcon(side, tex[1]);
    			}
    		}
    	}
    	return null;
    }
    
    @Override
    public Icon getIcon(int side, int meta)
    {
    	if(meta == 1)
    	{
    		return Block.dirt.getIcon(side, 0);
    	}
    	return Block.dirt.getIcon(side, 0);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    public int idPicked(World world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x,y,z);

    	return meta == 0 ? Block.dirt.blockID : Block.sand.blockID;
    }
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
    	this.updateTick(world, x, y, z, new Random());
    	super.onBlockAdded(world, x, y, z);
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
    	if(world.getBlockId(x, y-1, z) == Block.grass.blockID)
    	{
    		world.setBlock(x, y-1, z, Block.dirt.blockID, 0, 2);
    	}
    	super.updateTick(world, x, y, z, random);
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int meta)
    {
    	if(world.getBlockId(x, y-1, z) == Block.grass.blockID)
    	{
    		world.setBlock(x, y-1, z, Block.dirt.blockID, 0, 2);
    	}
    	TileEntityRoad road = (TileEntityRoad) world.getBlockTileEntity(x, y, z);
    	if(road != null)
    	{
    		road.sendPacketToClients();
    	}
    	
    	super.onNeighborBlockChange(world, x, y, z, meta);
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1, float f2)
    {
    	TileEntityRoad road = (TileEntityRoad)world.getBlockTileEntity(x, y, z);
    	if(road == null)
    	{
    		return false;
    	}
    	int blockID = road.getSurface()[0];
    	
        ItemStack itemstack = player.getHeldItem();
        if(itemstack != null)
        {
        	if(!player.canPlayerEdit(x, y, z, i, itemstack))
        	{
        		return false;
        	}
        	Block block = Block.blocksList[itemstack.itemID];
        	if(itemstack.getItem() instanceof ItemBlock && block != null)
    		{
        		if(!road.canBuild())
            	{
            		return true;
            	}
        		
        		if(upgradeRoad(world, x, y, z, 4, itemstack, block))
        		{
        			if(!player.capabilities.isCreativeMode && !world.isRemote)
        			{
        				itemstack.stackSize --;
        				if(itemstack.stackSize <= 0)
        				{
        					player.setCurrentItemOrArmor(0, null);
        				}
        			}
        			return true;
    			}
    		}
    		if(itemstack.getItem() instanceof ItemSpade)
            {
    			if(this == BlockListMF.road)
    			{
    				if(!world.isRemote)
    				{
    					int m = world.getBlockMetadata(x, y, z);
    					world.playAuxSFX(2001, x, y, z, blockID);
    					world.setBlock(x, y, z, BlockListMF.Lowroad.blockID, m, 2);
    				}
                	return true;
                }
        	}
        }
        return false;
    }
    /**
     * Resets the Texture
     * @param ID the block right clicked with
     * @return
     */
    private boolean upgradeRoad(World world, int x, int y, int z, int r, ItemStack held, Block block) 
    {
    	if(held == null)
    	{
    		return false;
    	}
    	if(!block.isOpaqueCube())
    	{
    		return false;
    	}
    	boolean flag = false;
    	
		for(int x2 = -r; x2 <= r; x2 ++)
		{
			for(int y2 = -r; y2 <= r; y2 ++)
			{
				for(int z2 = -r; z2 <= r; z2 ++)
				{
					int id = world.getBlockId(x+x2, y+y2, z+z2);
					int m = world.getBlockMetadata(x+x2, y+y2, z+z2);
					if((id == BlockListMF.road.blockID || id == BlockListMF.Lowroad.blockID))
					{
						if(getDistance(x+x2, y+y2, z+z2, x, y, z) < r*1)
						{
							flag = true;
							{
								TileEntityRoad road = (TileEntityRoad)world.getBlockTileEntity(x+x2, y+y2, z+z2);
								world.playAuxSFX(2001, x+x2, y+y2, z+z2, held.itemID);
								if(road != null)
								{
									road.setSurface(held.itemID, held.getItemDamage());
								}
							}
						}
					}
				}
			}
		}
		return flag;
	}

	@Override
    public int idDropped(int meta, Random rand, int i) 
	{
		if(meta == 1)
			return Block.sand.blockID;
					
        return Block.dirt.blockID;
    }
	
	
	public double getDistance(double x, double y, double z, int posX, int posY, int posZ)
    {
        double var7 = posX - x;
        double var9 = posY - y;
        double var11 = posZ - z;
        return (double)MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
    }

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityRoad();
	}
}
