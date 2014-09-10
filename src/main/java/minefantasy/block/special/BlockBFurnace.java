package minefantasy.block.special;

import java.util.ArrayList;
import java.util.Random;

import minefantasy.api.aesthetic.IChimney;
import minefantasy.block.tileentity.TileEntityBFurnace;
import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class BlockBFurnace extends BlockContainer implements IChimney{

    private Random rand = new Random();
	private Icon[] icon;

    public BlockBFurnace(int i, int n, Material m) {
        super(i, m);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
    {
    	int ic = this.getBlockTextureInt(world, x, y, z, side);
    	return icon[ic];
    }
    public int getBlockTextureInt(IBlockAccess world, int x, int y, int z, int side)
    {
    	TileEntityBFurnace tile = (TileEntityBFurnace) world.getBlockTileEntity(x, y, z);
    	if(tile != null)
    	{
    		byte facing = tile.direction;
    		int meta = tile.getBlockMetadata();
    		if(meta == 2) // output
    		{
    			if(side == 0) return 0;
    		}
    		if(meta == 1 || meta == 2)//Input/fuel
    		{
    			if(side != facing)
    				return 0;
    			else
    				return 2;
    		}
    	}
    	if(side == 0 || side == 1)
    		return 1;
    	return 0;
    }
    
    @Override
    public Icon getIcon(int side, int meta)
    {
    	int ic = this.getBlockTextureFromSideAndMetadataInt(side, meta);
    	return icon[ic];
    }
    public int getBlockTextureFromSideAndMetadataInt(int side, int meta)
    {
    	int facing = 3;
    		if(meta == 2) // output
    		{
    			if(side == 0) return 0;
    		}
    		if(meta == 1 || meta == 2)//Input/fuel
    		{
    			if(side != facing)
    				return 0;
    			else
    				return 2;
    		}
    	if(side == 0 || side == 1)
    		return 1;
    	return 0;
    }
    @Override
    public void addCreativeItems(ArrayList itemList) {

    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
    }
    
    @Override
    public TileEntity createNewTileEntity(World w) {
        return new TileEntityBFurnace();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {
        int dir = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntityBFurnace tile = (TileEntityBFurnace) world.getBlockTileEntity(x, y, z);
        tile.direction = getFacingOnMeta(dir);
        if(item.hasDisplayName())
        {
        	tile.setCustomName(item.getDisplayName());
        }
    }

    private byte getFacingOnMeta(int dir) {
		switch(dir)
		{
			case 0:
				return 2;
			case 1:
				return 5;
			case 2:
				return 3;
			case 3:
				return 4;
		}
		return 0;
	}

	/**
     * Called whenever the block is removed.
     */
    @Override
    public void breakBlock(World world, int x, int y, int z, int i1, int i2)
    {
        	TileEntityBFurnace tile = (TileEntityBFurnace)world.getBlockTileEntity(x, y, z);

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

                            if (var11 > var7.stackSize)
                            {
                                var11 = var7.stackSize;
                            }

                            var7.stackSize -= var11;
                            EntityItem var12 = new EntityItem(world, (double)((float)x + var8), (double)((float)y + var9), (double)((float)z + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));

                            if (var7.hasTagCompound())
                            {
                                var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
                            }

                            float var13 = 0.05F;
                            var12.motionX = (double)((float)this.rand.nextGaussian() * var13);
                            var12.motionY = (double)((float)this.rand.nextGaussian() * var13 + 0.2F);
                            var12.motionZ = (double)((float)this.rand.nextGaussian() * var13);
                            world.spawnEntityInWorld(var12);
                        }
                    }
                }
            }
        super.breakBlock(world, x, y, z, i1, i2);
    }

    

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1, float f2) {
    	TileEntityBFurnace tile = (TileEntityBFurnace)world.getBlockTileEntity(x, y, z);
    	if(tile == null || tile.getBlockMetadata() == 0)
    		return false;
    	if((tile.getBlockMetadata() == 1 || tile.getBlockMetadata() == 2) && tile.direction != i)
    		return false;
    	return tile.interact(player);
    }
    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
    	boolean power = false;
    	TileEntityBFurnace bfurn = getFurnaceInstance(world, x, y, z);
    	if(bfurn == null)
    		return 0;
    	
    	power = bfurn.isBurning();
        if (power)
        {
        	return 15;
        }
    	return super.getLightValue(world, x, y, z);
    }
    
    private TileEntityBFurnace getFurnaceInstance(IBlockAccess world, int x, int y, int z) {
    	if(world.getBlockTileEntity(x, y, z) == null)
    		return null;
    	
    	TileEntityBFurnace bfurn = getBFurnAt(world, x, y, z);
    	if(bfurn == null)
    		return null;
    	
    	return bfurn;
	}

	@Override
    public int damageDropped(int meta)
    {
    	return meta;
    }
	public void registerIcons(IconRegister reg)
    {
		icon = new Icon[3];
		icon[0] = reg.registerIcon("MineFantasy:Furn/Blast_Furnace_Side");
		icon[1] = reg.registerIcon("MineFantasy:Furn/Blast_Furnace_Top");
		icon[2] = reg.registerIcon("MineFantasy:Furn/Blast_Furnace_Face");
    }
    
    private TileEntityBFurnace getBFurnAt(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile == null)
			return null;
		
		if(tile instanceof TileEntityBFurnace)
			return (TileEntityBFurnace)tile;
		
		return null;
	}
    
    @Override
	public boolean puffSmoke(World world, int x, int y, int z, float num, float speedX, float speedY)
	{
    	if(world.getBlockMetadata(x, y, z) > 0)
    		return false;
    	
    	IChimney chimney = (IChimney)Block.blocksList[world.getBlockId(x, y, z)];
		if(chimney == null)
		{
			return false;
		}
		
		Random rand = new Random();
		
		if(Block.blocksList[world.getBlockId(x, y+1, z)] instanceof IChimney)
		{
			IChimney chimney2 = (IChimney)Block.blocksList[world.getBlockId(x, y+1, z)];
			return chimney2.puffSmoke(world, x, y+1, z, num, speedX, speedY);
		}
		
		for(int a = 0; a < 30*num ; a ++)
        {
        	if(!world.isBlockSolidOnSide(x, y+1, z, ForgeDirection.DOWN))
        	world.spawnParticle("largesmoke", x+0.5F, y+1, z+0.5F, (rand.nextFloat()-0.5F)/6*speedX, 0.065F*speedY, (rand.nextFloat()-0.5F)/6*speedX);
        }
		
		return true;
	}
}
