package minefantasy.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockSlateSlab extends BlockHalfSlab
{
    /** The type of tree this slab came from. */
    public static final String[] stoneType = new String[] {"slate", "slateTile", "slateDTile", "slateBrick"};

    public BlockSlateSlab(int id, boolean fullSize)
    {
        super(id, fullSize, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setLightOpacity(0);
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int side, int metadata)
    {
    	int meta = metadata & 7;

    	return BlockListMF.slate.getIcon(side, metadata);
    }
    @Override
    public int idPicked(World world, int x, int y, int z)
    {
    	return BlockListMF.stoneSingleSlab.blockID;
    }
    @Override
    public float getBlockHardness(World world, int x, int y, int z)
    {
    	float f = 3.0F;
    	
    	int meta = world.getBlockMetadata(x, y, z);
    	meta = meta & 7;
    	
    	if(meta == 1)f = 5.0F;
    	if(meta == 2)f = 8.0F;
    	if(meta == 3)f = 1.0F;
    	if(meta == 4)f = 0.7F;
    	
    	return f;
    	
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int id, Random rand, int meta)
    {
        return BlockListMF.slateSingleSlab.blockID;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int meta)
    {
        return new ItemStack(BlockListMF.slateSingleSlab.blockID, 2, meta & 7);
    }

    /**
     * Returns the slab block name with step type.
     */
    public String getFullSlabName(int meta)
    {
        if (meta < 0 || meta >= stoneType.length)
        {
            meta = 0;
        }

        return super.getUnlocalizedName() + "." + stoneType[meta];
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    public void getSubBlocks(int id, CreativeTabs tab, List list)
    {
        if (id != BlockListMF.slateDoubleSlab.blockID)
        {
            for (int j = 0; j < stoneType.length; ++j)
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
