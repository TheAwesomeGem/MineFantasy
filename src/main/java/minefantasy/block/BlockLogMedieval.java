package minefantasy.block;

import static net.minecraftforge.common.ForgeDirection.UP;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;

import minefantasy.system.data_minefantasy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraftforge.common.ForgeDirection;

public class BlockLogMedieval extends Block
{
    /** The type of tree this log came from. */
    public static final String[] woodType = new String[] {"Ironbark, Ebony"};
    public static final String[] treeTextureTypes = new String[] {"Ironbark_tree", "Ebony_tree"};
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;
    @SideOnly(Side.CLIENT)
    private Icon[] tree_top;

    protected BlockLogMedieval(int id)
    {
        super(id, Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setBurnProperties(id, 4, 3);
    }
    @Override
    public float getBlockHardness(World world, int x, int y, int z)
    {
    	float f = super.getBlockHardness(world, x, y, z);
    	int meta = world.getBlockMetadata(x, y, z);
    	
    	if(meta == 1)f *= 2;
    	
    	return f;
    	
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 31;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random rand)
    {
        return 1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int id, Random rand, int meta)
    {
        return BlockListMF.log.blockID;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World world, int x, int y, int z, int side, int meta)
    {
        byte var7 = 4;
        int var8 = var7 + 1;

        if (world.checkChunksExist(x - var8, y - var8, z - var8, x + var8, y + var8, z + var8))
        {
            for (int var9 = -var7; var9 <= var7; ++var9)
            {
                for (int var10 = -var7; var10 <= var7; ++var10)
                {
                    for (int var11 = -var7; var11 <= var7; ++var11)
                    {
                        int var12 = world.getBlockId(x + var9, y + var10, z + var11);

                        if (Block.blocksList[var12] != null)
                        {
                            Block.blocksList[var12].beginLeavesDecay(world, x + var9, y + var10, z + var11);
                        }
                    }
                }
            }
        }
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int meta, float pitch, float pan, float yaw, int side)
    {
        int var10 = side & 3;
        byte var11 = 0;

        switch (meta)
        {
            case 0:
            case 1:
                var11 = 0;
                break;
            case 2:
            case 3:
                var11 = 8;
                break;
            case 4:
            case 5:
                var11 = 4;
        }

        return var10 | var11;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public Icon getIcon(int side, int meta)
    {
        int k = meta & 12;
        int l = meta & 3;
        return k == 0 && (side == 1 || side == 0) ? this.tree_top[l] : (k == 4 && (side == 5 || side == 4) ? this.tree_top[l] : (k == 8 && (side == 2 || side == 3) ? this.tree_top[l] : this.iconArray [l]));
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
        return meta & 3;
    }

    /**
     * returns a number between 0 and 3
     */
    public static int limitToValidMetadata(int meta)
    {
        return meta & 3;
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int id, CreativeTabs tab, List list)
    {
    	for(int a = 0; a < 2; a ++)
    	{
    		list.add(new ItemStack(id, 1, a));
    	}
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int id)
    {
        return new ItemStack(this.blockID, 1, limitToValidMetadata(id));
    }
    
    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister reg)
    {
    	this.tree_top = new Icon[treeTextureTypes.length];

        for (int i = 0; i < this.tree_top.length; ++i)
        {
            this.tree_top[i] = reg.registerIcon("MineFantasy:Tree/" + treeTextureTypes[i] + "_top");
        }
        this.iconArray = new Icon[treeTextureTypes.length];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = reg.registerIcon("MineFantasy:Tree/" + treeTextureTypes[i] + "_side");
        }
    }

    @Override
    public boolean canSustainLeaves(World world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public boolean isWood(World world, int x, int y, int z)
    {
        return true;
    }
}
