package minefantasy.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class BlockSlate extends Block
{
	public static final int amount = 4;
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;
    
    public BlockSlate(int id)
    {
        super(id, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public Icon getIcon(int side, int meta)
    {
        return this.iconArray[meta % this.iconArray.length];
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
        return meta;
    }


    @SideOnly(Side.CLIENT)

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int id, CreativeTabs tab, List list)
    {
        for (int j = 0; j < amount; ++j)
        {
            list.add(new ItemStack(id, 1, j));
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    @Override
    public void registerIcons(IconRegister reg)
    {
    	iconArray = new Icon[amount];
        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = reg.registerIcon("MineFantasy:Basic/slate" + i);
        }
    }
    @Override
    public Block setUnlocalizedName(String name)
    {
    	this.setTextureName("minefantasy:Basic/" + name);
    	return super.setUnlocalizedName(name);
    }
}
