package minefantasy.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.ColorizerFoliage;

public class ItemBlockUtilOre extends ItemBlock
{
    public ItemBlockUtilOre(int id)
    {
        super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(int id, CreativeTabs tabs, List list)
    {
        for (int n = 0; n < 3; ++n)
        {
            list.add(new ItemStack(id, 1, n));
        }
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int meta)
    {
        return meta;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int meta)
    {
        return BlockListMF.oreUtil.getIcon(0, meta);
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    @Override
    public String getUnlocalizedName(ItemStack item)
    {
    	int type = item.getItemDamage();
    	
    	if(type == 1)
    	{
    		return StatCollector.translateToLocal("tile.oreUtil.nitre");
    	}
    	if(type == 2)
    	{
    		return StatCollector.translateToLocal("tile.oreUtil.sulfur");
    	}
    			
        return StatCollector.translateToLocal("tile.oreSilver");
    }
    
}
