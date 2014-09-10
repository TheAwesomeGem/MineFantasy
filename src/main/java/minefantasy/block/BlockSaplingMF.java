package minefantasy.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;

import minefantasy.block.special.WorldGenIronbarkTree;
import minefantasy.system.WorldGenEbony;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.minecraftforge.event.terraingen.TerrainGen;

public class BlockSaplingMF extends BlockFlower
{
    public static final String[] WOOD_TYPES = new String[] {"ironbark", "ebony"};
    private static final String[] iconNames = new String[] {"saplingIronbark", "saplingEbony"};
    @SideOnly(Side.CLIENT)
    public Icon[] saplingIcon;

    protected BlockSaplingMF(int id)
    {
        super(id);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (!world.isRemote)
        {
        	int meta = world.getBlockMetadata(x, y, z);
        	
            super.updateTick(world, x, y, z, rand);

            if (world.getBlockLightValue(x, y + 1, z) >= getMinimalLight(meta) && rand.nextInt(getGrowthRate(meta)) == 0)
            {
                this.markOrGrowMarked(world, x, y, z, rand);
            }
        }
    }

    private int getMinimalLight(int type) {
    	if(type == 1)
    		return 11;
		return 9;
	}

	private int getGrowthRate(int meta) {
		int type = meta & 3;
    	if(type == 1)
    		return 60;
		return 10;
	}

	@SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int side, int meta)
    {
		meta &= 3;
        if(meta >= saplingIcon.length)meta = saplingIcon.length-1;
        
        return this.saplingIcon[meta];
    }

    public void markOrGrowMarked(World world, int x, int y, int z, Random rand)
    {
        int l = world.getBlockMetadata(x, y, z);

        if ((l & 8) == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, l | 8, 4);
        }
        else
        {
            this.growTree(world, x, y, z, rand);
        }
    }

    /**
     * Attempts to grow a sapling into a tree
     */
    public void growTree(World world, int x, int y, int z, Random rand)
    {
        if (!TerrainGen.saplingGrowTree(world, rand, x, y, z)) return;

        int l = world.getBlockMetadata(x, y, z) & 3;
        Object object = null;
        int i1 = 0;
        int j1 = 0;
        boolean flag = false;

        if (l == 1)
        {
            object = new WorldGenEbony(true);
        }
        else
        {
            object = new WorldGenIronbarkTree(true);
        }
        world.setBlock(x, y, z, 0, 0, 4);

        if (!((WorldGenerator)object).generate(world, rand, x + i1, y, z + j1))
        {
                world.setBlock(x, y, z, this.blockID, l, 4);
        }
    }

    /**
     * Determines if the same sapling is present at the given location.
     */
    public boolean isSameSapling(World world, int x, int y, int z, int meta)
    {
        return world.getBlockId(x, y, z) == this.blockID && (world.getBlockMetadata(x, y, z) & 3) == meta;
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int id)
    {
        return id & 3;
    }

    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int id, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(id, 1, 0));
        list.add(new ItemStack(id, 1, 1));
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    @Override
    public void registerIcons(IconRegister reg)
    {
    	saplingIcon = new Icon[iconNames.length];
        for (int i = 0; i < this.saplingIcon.length; ++i)
        {
            this.saplingIcon[i] = reg.registerIcon("MineFantasy:Tree/" + iconNames[i]);
        }
    }

	public float getBonemealChance(int type) {
    	if(type == 1)
    		return 0.05F;
    	
		return 0.45F;
	}
}
