package minefantasy.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockWoodSlabMF extends BlockHalfSlab
{
    /** The type of tree this slab came from. */
    public static final String[] woodType = new String[] {"ironbark", "ebony", "rePlanks", "hay"};

    public BlockWoodSlabMF(int id, boolean fullSize)
    {
        super(id, fullSize, Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setLightOpacity(0);
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int side, int metadata)
    {
    	if((metadata & 7) < 2)
    	{
    		return BlockListMF.planks.getIcon(side, metadata & 7);
    	}
    	if((metadata & 7) == 2)
    	{
    		return BlockListMF.rePlanks.getIcon(0, 0);
    	}
    	if((metadata & 7) == 3)
    	{
    		return BlockListMF.hayRoof.getIcon(0, 0);
    	}
        return BlockListMF.planks.getIcon(side, 0);
    }

    @Override
    public int idPicked(World world, int x, int y, int z)
    {
    	return BlockListMF.woodSingleSlab.blockID;
    }
    
    @Override
    public float getBlockHardness(World world, int x, int y, int z)
    {
    	float f = super.getBlockHardness(world, x, y, z);
    	int meta = world.getBlockMetadata(x, y, z);
    	meta = meta & 7;
    	
    	if(meta == 1)f *= 2;
    	if(meta == 2)f = BlockListMF.rePlanks.getBlockHardness(world, x, y, z);
    	if(meta == 3)f = BlockListMF.hayRoof.getBlockHardness(world, x, y, z);
    	
    	return f;
    	
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int id, Random rand, int meta)
    {
        return BlockListMF.woodSingleSlab.blockID;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int meta)
    {
        return new ItemStack(BlockListMF.woodSingleSlab.blockID, 2, meta & 7);
    }

    /**
     * Returns the slab block name with step type.
     */
    public String getFullSlabName(int meta)
    {
        if (meta < 0 || meta >= woodType.length)
        {
            meta = 0;
        }

        return super.getUnlocalizedName() + "." + woodType[meta];
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int id, CreativeTabs tab, List list)
    {
        if (id != BlockListMF.woodDoubleSlab.blockID)
        {
            for (int j = 0; j < woodType.length; ++j)
            {
                list.add(new ItemStack(id, 1, j));
            }
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister reg) {}
}
