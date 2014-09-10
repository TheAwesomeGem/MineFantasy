package minefantasy.api.structure;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class StructureModuleMF {
	public final int xCoord;
	public final int yCoord;
	public final int zCoord;
	public final int direction;
	protected final World worldObj;
	
	public StructureModuleMF(World world, int x, int y, int z, int d)
	{
		worldObj = world;
		xCoord = x;
		yCoord = y;
		zCoord = z;
		direction = d;
	}
	public StructureModuleMF(World world, int x, int y, int z)
	{
		this(world, x, y, z, 0);
	}
	
	public abstract void generate();
	
	public int rotateLeft()
	{
		switch(direction)
		{
		case 0:
			return 3;
		case 1:
			return 0;
		case 2:
			return 1;
		case 3:
			return 2;
		}
		return 0;
	}
	
	public int rotateRight()
	{
		switch(direction)
		{
		case 0:
			return 1;
		case 1:
			return 2;
		case 2:
			return 3;
		case 3:
			return 0;
		}
		return 0;
	}
	
	public int reverse()
	{
		switch(direction)
		{
		case 0:
			return 2;
		case 1:
			return 3;
		case 2:
			return 0;
		case 3:
			return 1;
		}
		return 0;
	}
	
	/**
	 * Places a block and rotates it on it's base direction
	 * @param id The blockID
	 * @param meta the block metadata
	 * @param x The x offset(relation to xCoord)
	 * @param y The y offset(relation to yCoord)
	 * @param z The z offset(relation to zCoord)
	 */
	public void placeBlock(int id, int meta, int x, int y, int z)
	{
		placeBlock(id, meta, x, y, z, direction);
	}
	
	/**
	 * Places a block and rotates it on direction
	 * @param id The blockID
	 * @param meta the block metadata
	 * @param xo The x offset(relation to xCoord)
	 * @param yo The y offset(relation to yCoord)
	 * @param zo The z offset(relation to zCoord)
	 * @param dir The direction to face
	 */
	public void placeBlock(int id, int meta, int xo, int yo, int zo, int dir)
	{
		int u = 2;
		int x = xCoord;
		int y = yCoord+yo;
		int z = zCoord;
		
		switch(dir)
		{
		case 0://NORTH
			x += xo;
			z += zo;
			break;
			
		case 1://EAST
			x -= zo;
			z += xo;
			break;
			
		case 2://SOUTH
			x -= xo;
			z -= zo;
			break;
			
		case 3://WEST
			x += zo;
			z -= xo;
			break;
		}
		
		
		worldObj.setBlock(x, y, z, id, meta, u);
	}
	
	
	public int[] offsetPos(int xo, int yo, int zo, int dir)
	{
		int u = 2;
		int x = xCoord;
		int y = yCoord+yo;
		int z = zCoord;
		
		switch(dir)
		{
		case 0://NORTH
			x += xo;
			z += zo;
			break;
			
		case 1://EAST
			x -= zo;
			z += xo;
			break;
			
		case 2://SOUTH
			x -= xo;
			z -= zo;
			break;
			
		case 3://WEST
			x += zo;
			z -= xo;
			break;
		}
		
		
		return new int[]{x, y, z};
	}
	
	
	
	public TileEntity getTileEntity(int xo, int yo, int zo, int dir)
	{
		int u = 2;
		int x = xCoord;
		int y = yCoord+yo;
		int z = zCoord;
		
		switch(dir)
		{
		case 0://NORTH
			x += xo;
			z += zo;
			break;
			
		case 1://EAST
			x -= zo;
			z += xo;
			break;
			
		case 2://SOUTH
			x -= xo;
			z -= zo;
			break;
			
		case 3://WEST
			x += zo;
			z -= xo;
			break;
		}
		
		
		return worldObj.getBlockTileEntity(x, y, z);
	}
	
	
	
	public void notifyBlock(int id, int xo, int yo, int zo, int dir)
	{
		int u = 2;
		int x = xCoord;
		int y = yCoord+yo;
		int z = zCoord;
		
		switch(dir)
		{
		case 0://NORTH
			x += xo;
			z += zo;
			break;
			
		case 1://EAST
			x -= zo;
			z += xo;
			break;
			
		case 2://SOUTH
			x -= xo;
			z -= zo;
			break;
			
		case 3://WEST
			x += zo;
			z -= xo;
			break;
		}
		
		
		worldObj.notifyBlockChange(x, y, z, id);
	}
}
