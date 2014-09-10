package minefantasy.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;

import minefantasy.item.ItemListMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class BlockSlag extends Block
{
    private Random rand = new Random();

	protected BlockSlag(int id)
    {
        super(id, Material.sand);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBoundsForSlagDepth(0);
        MinecraftForge.setBlockHarvestLevel(this, "shovel", 0);
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    @Override
    public void registerIcons(IconRegister reg)
    {
        this.blockIcon = reg.registerIcon("minefantasy:Basic/Slag_MF");
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z) & 15;
        float f = 0.125F;
        return AxisAlignedBB.getAABBPool().getAABB((double)x + this.minX, (double)y + this.minY, (double)z + this.minZ, (double)x + this.maxX, (double)((float)y + (float)l * f), (double)z + this.maxZ);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.setBlockBoundsForSlagDepth(0);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        this.setBlockBoundsForSlagDepth(world.getBlockMetadata(x, y, z));
    }

    /**
     * calls setBlockBounds based on the depth of the snow. Int is any values 0x0-0x15, usually this blocks metadata.
     */
    protected void setBlockBoundsForSlagDepth(int meta)
    {
        int j = meta & 15;
        float f = (float)(1 * (1 + j)) / 16.0F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }


    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int id, Random rand, int meta)
    {
        return 0;
    }
    @Override
    public int damageDropped(int meta)
    {
        return 0;
    }


    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int meta)
    {
        return meta == 1 ? true : super.shouldSideBeRendered(world, x, y, z, meta);
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        return 1;
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int i1, int i2)
    {
    	super.breakBlock(world, x, y, z, i1, i2);
    	int meta = world.getBlockMetadata(x, y, z);
    	for(int a = 0; a <= meta; a ++)
    	{
    		ItemStack drop = getDrop();

            if (drop != null) {
                float var8 = this.rand.nextFloat() * 0.8F + 0.1F;
                float var9 = this.rand.nextFloat() * 0.8F + 0.1F;
                float var10 = this.rand.nextFloat() * 0.8F + 0.1F;

                while (drop.stackSize > 0) 
                {
                    int var11 = this.rand.nextInt(21) + 10;

                    if (var11 > drop.stackSize)
                    {
                        var11 = drop.stackSize;
                    }

                    drop.stackSize -= var11;
                    EntityItem var12 = new EntityItem(world, (double) ((float) x + var8), (double) ((float) y + var9), (double) ((float) z + var10), new ItemStack(drop.itemID, var11, drop.getItemDamage()));

                    if (drop.hasTagCompound()) 
                    {
                        var12.getEntityItem().setTagCompound((NBTTagCompound) drop.getTagCompound().copy());
                    }

                    float var13 = 0.05F;
                    var12.motionX = (double) ((float) this.rand .nextGaussian() * var13);
                    var12.motionY = (double) ((float) this.rand.nextGaussian() * var13 + 0.2F);
                    var12.motionZ = (double) ((float) this.rand.nextGaussian() * var13);
                    world.spawnEntityInWorld(var12);
                }
            }
    	}
    }

	private ItemStack getDrop() 
	{
		int meta = ItemListMF.slag;
		if(rand.nextInt(10) == 0)
		{
			meta = ItemListMF.flux;
		}
		if(rand.nextInt(20) == 0)
		{
			return new ItemStack(Item.coal, 1, 1);
		}
		return ItemListMF.component(meta);
	}
	
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity e)
    {
        e.motionX *= 0.4D;
        e.motionZ *= 0.4D;
    }
}
