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

public class BlockStoneSlabMF extends BlockHalfSlab
{
    /** The type of tree this slab came from. */
    public static final String[] stoneType = new String[] {"cobbBrick", "granite", "graniteBrick", "stone", "mudbrick", "cobbBrickRough", "mudBrickRough"};

    public BlockStoneSlabMF(int id, boolean fullSize)
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
    	switch(meta)
    	{
    	case 0:
    		return BlockListMF.cobbBrick.getIcon(0, 0);
    	case 1:
    		return BlockListMF.granite.getIcon(0, 0);
    	case 2:
    		return BlockListMF.graniteBrick.getIcon(0, 0);
    	case 3:
    		return Block.stone.getIcon(0, 0);
    	case 4:
    		return BlockListMF.mudBrick.getIcon(0, 0);
    	case 5:
    		return BlockListMF.cobbBrick.getIcon(0, 3);
    	case 6:
    		return BlockListMF.mudBrick.getIcon(0, 1);
    	}
    	return Block.stone.getIcon(0, 0);
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
    	if(meta == 5)f = 5.0F;
    	if(meta == 6)f = 0.7F;
    	
    	return f;
    	
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int id, Random rand, int meta)
    {
        return BlockListMF.stoneSingleSlab.blockID;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int meta)
    {
        return new ItemStack(BlockListMF.stoneSingleSlab.blockID, 2, meta & 7);
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
    public void getSubBlocks(int id, CreativeTabs tab, List list)
    {
        if (id != BlockListMF.stoneDoubleSlab.blockID)
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
