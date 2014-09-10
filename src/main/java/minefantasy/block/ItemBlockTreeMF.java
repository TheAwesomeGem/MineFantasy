package minefantasy.block;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class ItemBlockTreeMF extends ItemBlock{

    public ItemBlockTreeMF(int id)
    {
        super(id);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void getSubItems(int id, CreativeTabs tabs, List list)
    {
        for (int n = 0; n < 2; ++n)
        {
            list.add(new ItemStack(id, 1, n));
        }
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
    	int i = itemstack.getItemDamage();
    	int id = itemstack.itemID;
    	if(id == BlockListMF.log.blockID)
    	{
	        switch(i)
	        {
	            case 0:
	                return StatCollector.translateToLocal("tile.log.ironbark");
	            case 1:
	                return StatCollector.translateToLocal("tile.log.ebony");
	        }
    	}
    	
    	if(id == BlockListMF.planks.blockID)
    	{
	        switch(i)
	        {
	        case 0:
                return StatCollector.translateToLocal("tile.planks.ironbark");
            case 1:
                return StatCollector.translateToLocal("tile.planks.ebony");
	        }
    	}
    	
    	if(id == BlockListMF.leaves.blockID)
    	{
	        switch(i)
	        {
	        case 0:
                return StatCollector.translateToLocal("tile.leaves.ironbark");
            case 1:
                return StatCollector.translateToLocal("tile.leaves.ebony");
	        }
    	}
    	
    	if(id == BlockListMF.sapling.blockID)
    	{
	        switch(i)
	        {
	        case 0:
                return StatCollector.translateToLocal("tile.sapling.ironbark");
            case 1:
                return StatCollector.translateToLocal("tile.sapling.ebony");
	        }
    	}
        return "Unnamed";
    }
    
    @Override
    public EnumRarity getRarity(ItemStack item)
    {
    	int d = item.getItemDamage();
    	if(d == 1)
    	{
    		return EnumRarity.uncommon;
    	}
    	return EnumRarity.common;
    }
}
